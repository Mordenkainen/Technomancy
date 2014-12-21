package theflogat.technomancy.common.blocks.dynamos;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.items.api.ToolWrench;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.dynamos.TileFlowerDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import vazkii.botania.api.wand.IWandHUD;

public class BlockFlowerDynamo extends BlockBase implements IWandHUD {

	public BlockFlowerDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.flowerDynamo);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) { 
				TileEntity entity = world.getTileEntity(x, y, z);
				if (entity instanceof TileFlowerDynamo) {
					if (ToolWrench.isWrench(player.getHeldItem())) {
						((TileFlowerDynamo)entity).rotateBlock();
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
		TileDynamoBase tile = (TileDynamoBase)world.getTileEntity(x, y, z);
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
