package vazkii.botania.api.subtile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.IInternalMethodHandler;
import vazkii.botania.api.lexicon.LexiconEntry;

public class SubTileEntity
{
  protected TileEntity supertile;
  public static final String TAG_TYPE = "type";
  
  public void setSupertile(TileEntity tile)
  {
    this.supertile = tile;
  }
  
  public boolean canUpdate()
  {
    return false;
  }
  
  public void onUpdate() {}
  
  public void writeToPacketNBT(NBTTagCompound cmp) {}
  
  public void readFromPacketNBT(NBTTagCompound cmp) {}
  
  public String getUnlocalizedName()
  {
    return BotaniaAPI.getSubTileStringMapping(getClass());
  }
  
  @SideOnly(Side.CLIENT)
  public Icon getIcon()
  {
    Icon icon = BotaniaAPI.internalHandler.getSubTileIconForName(getUnlocalizedName());
    return icon;
  }
  
  public boolean onWanded(EntityPlayer player, ItemStack wand)
  {
    return false;
  }
  
  public LexiconEntry getEntry()
  {
    return null;
  }
  
  @SideOnly(Side.CLIENT)
  public ChunkCoordinates getBinding()
  {
    return null;
  }
  
  @SideOnly(Side.CLIENT)
  public void renderHUD(Minecraft mc, ScaledResolution res) {}
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.subtile.SubTileEntity
 * JD-Core Version:    0.7.0.1
 */