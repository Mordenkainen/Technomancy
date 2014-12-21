package theflogat.technomancy.common.blocks.machines;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileReconstructor;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockReconstructor extends BlockBase {

	public BlockReconstructor() {
		setBlockName(Ref.MOD_PREFIX + Names.reconstructor);
	}

//	@Override
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
//		TileReconstructor te = (TileReconstructor) world.getTileEntity(x, y, z);
//		if (te == null || player.isSneaking()){
//			return false;
//		}
//		
//		ItemStack items = player.getCurrentEquippedItem();
//		if (te.getStackInSlot(0) != null && items == null){
//			player.inventory.addItemStackToInventory(te.getStackInSlot(0));
//			te.setInventorySlotContents(0, null);
//			te.aspects = new AspectList();
//		} else if (te.getStackInSlot(0) == null && items != null){
//			ItemStack newItems = items.copy();
//			newItems.stackSize = 1;
//			--items.stackSize;
//			te.setInventorySlotContents(0, newItems);
//		} 
//		
//		world.markBlockForUpdate(x, y, z);
//		return true;
//	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileReconstructor();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.idReconstructor;
	}

	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.condenserBlock);
	}

}
