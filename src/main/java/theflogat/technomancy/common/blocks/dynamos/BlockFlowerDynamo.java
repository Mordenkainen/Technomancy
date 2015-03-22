package theflogat.technomancy.common.blocks.dynamos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.items.api.ToolWrench;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.dynamos.TileFlowerDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.RedstoneSet;
import vazkii.botania.api.wand.IWandHUD;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "vazkii.botania.api.wand.IWandHUD", modid = "Botania")
public class BlockFlowerDynamo extends BlockBase implements IWandHUD {

	public BlockFlowerDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.flowerDynamo);
	}
	
	@Override
	public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
		if(((TileFlowerDynamo)w.getTileEntity(x, y, z)).boost)
				InvHelper.spawnEntItem(w, x, y, z, new ItemStack(TMItems.itemBoost, 1));
		super.breakBlock(w, x, y, z, block, meta);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.flowerDynamo));
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) { 
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile instanceof TileFlowerDynamo) {
					if (ToolWrench.isWrench(player.getHeldItem())) {
						((TileFlowerDynamo)tile).rotateBlock();
					}else if(player.getHeldItem().getItem()==Item.getItemFromBlock(Blocks.redstone_torch) && ((TileFlowerDynamo)tile).set != RedstoneSet.LOW){
						if(player.getHeldItem().stackSize==1){
							player.inventory.mainInventory[player.inventory.currentItem] = null;
						}else{
							player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
						}
						((TileFlowerDynamo)tile).set = RedstoneSet.LOW;
					}else if(player.getHeldItem().getItem()==Items.redstone && ((TileFlowerDynamo)tile).set != RedstoneSet.HIGH){
						if(player.getHeldItem().stackSize==1){
							player.inventory.mainInventory[player.inventory.currentItem] = null;
						}else{
							player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
						}
						((TileFlowerDynamo)tile).set = RedstoneSet.HIGH;
					}else if(player.getHeldItem().getItem()==Items.gunpowder && ((TileFlowerDynamo)tile).set != RedstoneSet.NONE){
						if(player.getHeldItem().stackSize==1){
							player.inventory.mainInventory[player.inventory.currentItem] = null;
						}else{
							player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
						}						
						((TileFlowerDynamo)tile).set = RedstoneSet.NONE;
					}	
				}
			}else{
				return true;
			}			
		}
		return false;	   
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}   

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileFlowerDynamo();
	}

	@Override
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
		((TileFlowerDynamo)world.getTileEntity(x, y, z)).renderHUD(mc, res);
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
		return RenderIds.idFlowerDynamo;
	}

}
