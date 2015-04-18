package theflogat.technomancy.common.blocks.thaumcraft.machines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.items.base.TMItems;
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
	public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
		TileAdvDeconTable tile = getTE(w, x, y, z);
		if(tile.getBoost()) {
			InvHelper.spawnEntItem(w, x, y, z, new ItemStack(TMItems.itemBoost, 1));
		}
		if(tile.getStackInSlot(0) != null) {
			InvHelper.spawnEntItem(w, x, y, z, tile.getStackInSlot(0));
		}
		super.breakBlock(w, x, y, z, block, meta);
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase ent,	ItemStack items) {
		TileAdvDeconTable te = getTE(w, x, y, z);
		if(te!=null && ent instanceof EntityPlayer)
			te.owner = ((EntityPlayer)ent).getDisplayName();
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileAdvDeconTable te = getTE(w, x, y, z);
		if(te!=null && !player.isSneaking()){
			if(te.getStackInSlot(0)==null){
				if(player.getHeldItem()!=null){
					te.setInventorySlotContents(0, player.getHeldItem());
					player.inventory.mainInventory[player.inventory.currentItem] = null;
					return true;
				}
			}else{
				if(!w.isRemote) {
					InvHelper.spawnEntItem(w, player.posX, player.posY, player.posZ, te.getStackInSlot(0));
				}
				te.setInventorySlotContents(0, null);
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(Ref.getAsset(Names.advDeconTable));
	}
	
	private TileAdvDeconTable getTE(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileAdvDeconTable) {
			return (TileAdvDeconTable)tile;
		}
		return null;
	}
}
