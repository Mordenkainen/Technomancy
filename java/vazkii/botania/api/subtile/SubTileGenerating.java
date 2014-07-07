package vazkii.botania.api.subtile;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.IInternalMethodHandler;
import vazkii.botania.api.internal.IManaNetwork;
import vazkii.botania.api.mana.IManaCollector;

public class SubTileGenerating
  extends SubTileEntity
{
  private static final String TAG_MANA = "mana";
  private static final String TAG_COLLECTOR_X = "collectorX";
  private static final String TAG_COLLECTOR_Y = "collectorY";
  private static final String TAG_COLLECTOR_Z = "collectorZ";
  protected int mana;
  int sizeLastCheck = -1;
  protected TileEntity linkedCollector = null;
  public int knownMana = -1;
  ChunkCoordinates cachedCollectorCoordinates = null;
  
  public boolean canUpdate()
  {
    return true;
  }
  
  public void onUpdate()
  {
    super.onUpdate();
    
    linkCollector();
    if (canGeneratePassively())
    {
      int delay = getDelayBetweenPassiveGeneration();
      if ((delay > 0) && (this.supertile.worldObj.getWorldTime() % delay == 0L))
      {
        if (shouldSyncPassiveGeneration()) {
          PacketDispatcher.sendPacketToAllInDimension(this.supertile.getDescriptionPacket(), this.supertile.worldObj.provider.dimensionId);
        }
        addMana(getValueForPassiveGeneration());
      }
      emptyManaIntoCollector();
    }
    if (this.supertile.worldObj.isRemote)
    {
      double particleChance = 1.0D - this.mana / getMaxMana() / 2.0D;
      Color color = new Color(getColor());
      if (Math.random() > particleChance) {
        BotaniaAPI.internalHandler.sparkleFX(this.supertile.worldObj, this.supertile.xCoord + 0.3D + Math.random() * 0.5D, this.supertile.yCoord + 0.5D + Math.random() * 0.5D, this.supertile.zCoord + 0.3D + Math.random() * 0.5D, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, (float)Math.random(), 5);
      }
    }
  }
  
  public void linkCollector()
  {
    int range = 6;
    
    boolean needsNew = false;
    if (this.linkedCollector == null)
    {
      needsNew = true;
      if (this.cachedCollectorCoordinates != null)
      {
        TileEntity tileAt = this.supertile.worldObj.getBlockTileEntity(this.cachedCollectorCoordinates.posX, this.cachedCollectorCoordinates.posY, this.cachedCollectorCoordinates.posZ);
        if ((tileAt != null) && ((tileAt instanceof IManaCollector)))
        {
          this.linkedCollector = tileAt;
          needsNew = false;
        }
        this.cachedCollectorCoordinates = null;
      }
    }
    if (!needsNew)
    {
      TileEntity tileAt = this.supertile.worldObj.getBlockTileEntity(this.linkedCollector.xCoord, this.linkedCollector.yCoord, this.linkedCollector.zCoord);
      if (!(tileAt instanceof IManaCollector))
      {
        this.linkedCollector = null;
        needsNew = true;
      }
      else
      {
        this.linkedCollector = tileAt;
      }
    }
    if (needsNew)
    {
      IManaNetwork network = BotaniaAPI.internalHandler.getManaNetworkInstance();
      int size = network.getAllCollectorsInWorld(this.supertile.worldObj.provider.dimensionId).size();
      if (size != this.sizeLastCheck)
      {
        ChunkCoordinates coords = new ChunkCoordinates(this.supertile.xCoord, this.supertile.yCoord, this.supertile.zCoord);
        this.linkedCollector = network.getClosestCollector(coords, this.supertile.worldObj.provider.dimensionId, 6);
        this.sizeLastCheck = size;
      }
    }
  }
  
  public void addMana(int mana)
  {
    this.mana = Math.min(getMaxMana(), this.mana + mana);
  }
  
  public void emptyManaIntoCollector()
  {
    if (this.linkedCollector != null)
    {
      IManaCollector collector = (IManaCollector)this.linkedCollector;
      if (!collector.isFull())
      {
        collector.recieveMana(this.mana);
        this.mana = 0;
      }
    }
  }
  
  public boolean shouldSyncPassiveGeneration()
  {
    return false;
  }
  
  public boolean canGeneratePassively()
  {
    boolean rain = (this.supertile.worldObj.getWorldChunkManager().getBiomeGenAt(this.supertile.xCoord, this.supertile.zCoord).getIntRainfall() > 0) && ((this.supertile.worldObj.isRaining()) || (this.supertile.worldObj.isThundering()));
    return (!this.supertile.worldObj.isRemote) && (this.supertile.worldObj.isDaytime()) && (!rain) && (this.supertile.worldObj.canBlockSeeTheSky(this.supertile.xCoord, this.supertile.yCoord + 1, this.supertile.zCoord));
  }
  
  public int getDelayBetweenPassiveGeneration()
  {
    return 20;
  }
  
  public int getValueForPassiveGeneration()
  {
    return 1;
  }
  
  public boolean onWanded(EntityPlayer player, ItemStack wand)
  {
    if (!player.worldObj.isRemote) {
      PacketDispatcher.sendPacketToAllInDimension(this.supertile.getDescriptionPacket(), this.supertile.worldObj.provider.dimensionId);
    }
    this.knownMana = this.mana;
    player.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 1.0F);
    
    return super.onWanded(player, wand);
  }
  
  public int getMaxMana()
  {
    return 20;
  }
  
  public int getColor()
  {
    return 16777215;
  }
  
  public void readFromPacketNBT(NBTTagCompound cmp)
  {
    this.mana = cmp.getInteger("mana");
    
    int x = cmp.getInteger("collectorX");
    int y = cmp.getInteger("collectorY");
    int z = cmp.getInteger("collectorZ");
    
    this.cachedCollectorCoordinates = new ChunkCoordinates(x, y, z);
  }
  
  public void writeToPacketNBT(NBTTagCompound cmp)
  {
    cmp.setInteger("mana", this.mana);
    
    int x = this.linkedCollector == null ? 0 : this.linkedCollector.xCoord;
    int y = this.linkedCollector == null ? -1 : this.linkedCollector.yCoord;
    int z = this.linkedCollector == null ? 0 : this.linkedCollector.zCoord;
    
    cmp.setInteger("collectorX", x);
    cmp.setInteger("collectorY", y);
    cmp.setInteger("collectorZ", z);
  }
  
  public ChunkCoordinates getBinding()
  {
    if (this.linkedCollector == null) {
      return null;
    }
    return new ChunkCoordinates(this.linkedCollector.xCoord, this.linkedCollector.yCoord, this.linkedCollector.zCoord);
  }
  
  public void renderHUD(Minecraft mc, ScaledResolution res)
  {
    String name = StatCollector.translateToLocal("tile.botania:flower." + getUnlocalizedName() + ".name");
    int color = 0x66000000 | getColor();
    BotaniaAPI.internalHandler.drawSimpleManaHUD(color, this.knownMana, getMaxMana(), name, res);
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.subtile.SubTileGenerating
 * JD-Core Version:    0.7.0.1
 */