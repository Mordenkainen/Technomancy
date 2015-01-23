package theflogat.technomancy.common.blocks.dynamos;

import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.dynamos.TileBloodDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileNodeDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.RedstoneSet;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class BlockNodeDynamo extends BlockBase {

	public BlockNodeDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.nodeDynamo);
	}
	
	@Override
	public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
		if(((TileNodeDynamo)w.getTileEntity(x, y, z)).boost)
				InvHelper.spawnEntItem(w, x, y, z, new ItemStack(TMItems.itemBoost, 1));
		super.breakBlock(w, x, y, z, block, meta);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileNodeDynamo tile = (TileNodeDynamo)world.getTileEntity(x, y, z);
		tile.facing = 0;
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!w.isRemote) {
			if (player.getHeldItem() != null) { 
				TileEntity tile = w.getTileEntity(x, y, z);
				if(tile instanceof TileNodeDynamo){
					if(player.getHeldItem().getItem()==Item.getItemFromBlock(Blocks.redstone_torch) && ((TileNodeDynamo)tile).set != RedstoneSet.LOW){
						if(player.getHeldItem().stackSize==1){
							player.inventory.mainInventory[player.inventory.currentItem] = null;
						}else{
							player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
						}						
						((TileNodeDynamo)tile).set = RedstoneSet.LOW;
					}else if(player.getHeldItem().getItem()==Items.redstone && ((TileNodeDynamo)tile).set != RedstoneSet.HIGH){
						if(player.getHeldItem().stackSize==1){
							player.inventory.mainInventory[player.inventory.currentItem] = null;
						}else{
							player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
						}						
						((TileNodeDynamo)tile).set = RedstoneSet.HIGH;
					}else if(player.getHeldItem().getItem()==Items.gunpowder && ((TileNodeDynamo)tile).set != RedstoneSet.NONE){
						if(player.getHeldItem().stackSize==1){
							player.inventory.mainInventory[player.inventory.currentItem] = null;
						}else{
							player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
						}						
						((TileNodeDynamo)tile).set = RedstoneSet.NONE;
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileNodeDynamo();
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.idNodeDynamo;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icon = Blocks.stone.getIcon(0, 0);
	}
}
