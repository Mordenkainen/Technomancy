package theflogat.technomancy.common.blocks.machines;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileAdvDeconTable;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.util.InvHelper;

public class BlockAdvDeconTable extends BlockBase{
	
	public BlockAdvDeconTable() {
		setBlockName(Ref.getId(Names.advDeconTable));
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase ent,	ItemStack items) {
		TileEntity te = w.getTileEntity(x, y, z);
		if(te!=null && te instanceof TileAdvDeconTable && ent instanceof EntityPlayer)
			((TileAdvDeconTable)te).owner = ((EntityPlayer)ent).getDisplayName();
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = w.getTileEntity(x, y, z);
		if(te!=null && te instanceof TileAdvDeconTable && !player.isSneaking()){
			TileAdvDeconTable tile = (TileAdvDeconTable) te;
			if(tile.getStackInSlot(0)==null){
				if(player.getHeldItem()!=null){
					tile.setInventorySlotContents(0, player.getHeldItem());
					player.inventory.mainInventory[player.inventory.currentItem] = null;
					return true;
				}
			}else{
				InvHelper.spawnEntItem(w, player.posX, player.posY, player.posZ, tile.getStackInSlot(0));
				tile.setInventorySlotContents(0, null);
				return true;
			}
		}
		return false;
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
		return RenderIds.idAdvDeconTable;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileAdvDeconTable();
	}

}
