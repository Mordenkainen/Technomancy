package vazkii.botania.api.subtile;

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
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.IInternalMethodHandler;
import vazkii.botania.api.internal.IManaNetwork;
import vazkii.botania.api.mana.IManaPool;

public class SubTileFunctional
  extends SubTileEntity
{
  private static final String TAG_MANA = "mana";
  private static final String TAG_POOL_X = "poolX";
  private static final String TAG_POOL_Y = "poolY";
  private static final String TAG_POOL_Z = "poolZ";
  public int mana;
  int sizeLastCheck = -1;
  TileEntity linkedPool = null;
  public int knownMana = -1;
  ChunkCoordinates cachedPoolCoordinates = null;
  
  public boolean canUpdate()
  {
    return true;
  }
  
  public void onUpdate()
  {
    super.onUpdate();
    
    linkPool();
    if (this.linkedPool != null)
    {
      IManaPool pool = (IManaPool)this.linkedPool;
      int manaInPool = pool.getCurrentMana();
      int manaMissing = getMaxMana() - this.mana;
      int manaToRemove = Math.min(manaMissing, manaInPool);
      pool.recieveMana(-manaToRemove);
      addMana(manaToRemove);
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
  
  public void linkPool()
  {
    int range = 6;
    
    boolean needsNew = false;
    if (this.linkedPool == null)
    {
      needsNew = true;
      if (this.cachedPoolCoordinates != null)
      {
        TileEntity tileAt = this.supertile.worldObj.getBlockTileEntity(this.cachedPoolCoordinates.posX, this.cachedPoolCoordinates.posY, this.cachedPoolCoordinates.posZ);
        if ((tileAt != null) && ((tileAt instanceof IManaPool)))
        {
          this.linkedPool = tileAt;
          needsNew = false;
        }
        this.cachedPoolCoordinates = null;
      }
    }
    if (!needsNew)
    {
      TileEntity tileAt = this.supertile.worldObj.getBlockTileEntity(this.linkedPool.xCoord, this.linkedPool.yCoord, this.linkedPool.zCoord);
      if (!(tileAt instanceof IManaPool))
      {
        this.linkedPool = null;
        needsNew = true;
      }
      else
      {
        this.linkedPool = tileAt;
      }
    }
    if (needsNew)
    {
      IManaNetwork network = BotaniaAPI.internalHandler.getManaNetworkInstance();
      int size = network.getAllPoolsInWorld(this.supertile.worldObj.provider.dimensionId).size();
      if (size != this.sizeLastCheck)
      {
        ChunkCoordinates coords = new ChunkCoordinates(this.supertile.xCoord, this.supertile.yCoord, this.supertile.zCoord);
        this.linkedPool = network.getClosestPool(coords, this.supertile.worldObj.provider.dimensionId, 6);
        this.sizeLastCheck = size;
      }
    }
  }
  
  public void addMana(int mana)
  {
    this.mana = Math.min(getMaxMana(), this.mana + mana);
  }
  
  public boolean onWanded(EntityPlayer player, ItemStack wand)
  {
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
    
    int x = cmp.getInteger("poolX");
    int y = cmp.getInteger("poolY");
    int z = cmp.getInteger("poolZ");
    
    this.cachedPoolCoordinates = new ChunkCoordinates(x, y, z);
  }
  
  public void writeToPacketNBT(NBTTagCompound cmp)
  {
    cmp.setInteger("mana", this.mana);
    
    int x = this.linkedPool == null ? 0 : this.linkedPool.xCoord;
    int y = this.linkedPool == null ? -1 : this.linkedPool.yCoord;
    int z = this.linkedPool == null ? 0 : this.linkedPool.zCoord;
    
    cmp.setInteger("poolX", x);
    cmp.setInteger("poolY", y);
    cmp.setInteger("poolZ", z);
  }
  
  public ChunkCoordinates getBinding()
  {
    if (this.linkedPool == null) {
      return null;
    }
    return new ChunkCoordinates(this.linkedPool.xCoord, this.linkedPool.yCoord, this.linkedPool.zCoord);
  }
  
  public void renderHUD(Minecraft mc, ScaledResolution res)
  {
    String name = StatCollector.translateToLocal("tile.botania:flower." + getUnlocalizedName() + ".name");
    int color = 0x66000000 | getColor();
    BotaniaAPI.internalHandler.drawSimpleManaHUD(color, this.knownMana, getMaxMana(), name, res);
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.subtile.SubTileFunctional
 * JD-Core Version:    0.7.0.1
 */