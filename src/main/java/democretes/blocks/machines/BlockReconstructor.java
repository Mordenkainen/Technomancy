package democretes.blocks.machines;

import thaumcraft.api.aspects.AspectList;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.base.BlockBase;
import democretes.lib.Names;
import democretes.lib.Ref;
import democretes.lib.RenderIds;
import democretes.tiles.thaumcraft.machine.TileReconstructor;

public class BlockReconstructor extends BlockBase {

	public BlockReconstructor(int id) {
		super(id);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.reconstructor);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileReconstructor te = (TileReconstructor) world.getBlockTileEntity(x, y, z);
		if (te == null || player.isSneaking()){
			return false;
		}
		
		ItemStack items = player.getCurrentEquippedItem();
		if (te.getStackInSlot(0) != null && items == null){
			player.inventory.addItemStackToInventory(te.getStackInSlot(0));
			te.setInventorySlotContents(0, null);
			te.aspects = new AspectList();
		} else if (te.getStackInSlot(0) == null && items != null){
			ItemStack newItems = items.copy();
			newItems.stackSize = 1;
			--items.stackSize;
			te.setInventorySlotContents(0, newItems);
		} 
		
		world.markBlockForUpdate(x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
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
	public Icon iconReconstructor;

	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IconRegister icon) {
		this.iconReconstructor = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.condenserBlock);
	}

}
