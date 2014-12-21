package theflogat.technomancy.common.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.IScribeTools;
import theflogat.technomancy.handlers.compat.Thaumcraft;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPen extends ItemBase implements IScribeTools {

	public ItemPen() {
		this.maxStackSize = 1;
		this.canRepair = true;
		setMaxDamage(3000);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
		try{
			TileEntity tile = w.getTileEntity(x, y, z);
			int md = w.getBlockMetadata(x, y, z);
			Block bi = w.getBlock(x, y, z);
	
			if ((tile != null) && (Thaumcraft.TileTable.isInstance(tile)) && (md != 6)) {
				if (w.isRemote) {return false;}
				for (int a = 2; a < 6; a++) {
					ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[a];
					TileEntity tile2 = w.getTileEntity(x + dir.offsetX , y + dir.offsetY, z + dir.offsetZ);
					
					int md2 = w.getBlockMetadata(x + dir.offsetX , y + dir.offsetY, z + dir.offsetZ);
					
					if (tile2 != null && Thaumcraft.TileTable.isInstance(tile2) && md2 < 6) {
						w.setBlock(x, y, z, bi, a, 0);
						w.setTileEntity(x, y, z, (TileEntity) Thaumcraft.TileResearchTable.getConstructor().newInstance());
						w.setBlock(x + dir.offsetX , y + dir.offsetY, z + dir.offsetZ, bi, dir.getOpposite().ordinal() + 4, 0);
						
						w.markBlockForUpdate(x, y, z);
						w.markBlockForUpdate(x + dir.offsetX , y + dir.offsetY, z + dir.offsetZ);
	
	
	
	
						TileEntity tile3 = w.getTileEntity(x, y, z);
						if (tile3 != null && Thaumcraft.TileResearchTable.isInstance(tile3)) {
							((IInventory)tile3).setInventorySlotContents(0, stack.copy());
							if (!player.capabilities.isCreativeMode) {
								player.inventory.decrStackSize(player.inventory.currentItem, 1);
							}
							w.markBlockForUpdate(x, y, z);
						}
						return true;
					}
				}
			}
		}catch(Exception e){e.printStackTrace();}
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon) {
		itemIcon = icon.registerIcon(Ref.TEXTURE_PREFIX + "penItem");
	}

	public String getUnlocalizedName(ItemStack stack) {
		return Ref.MOD_PREFIX + Names.pen;
	}

	public void addInformation(ItemStack itemstack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("It's the 21st century,");
		info.add("why are we using quills and ink?");
	}

}
