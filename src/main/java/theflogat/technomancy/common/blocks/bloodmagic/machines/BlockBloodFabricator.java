package theflogat.technomancy.common.blocks.bloodmagic.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.util.helpers.WorldHelper;

public class BlockBloodFabricator extends BlockContainerAdvanced {

	public BlockBloodFabricator() {
		setUnlocalizedName(Ref.MOD_PREFIX + Names.bloodFabricator);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem()==Items.BUCKET) {
			if (!w.isRemote) {
				TileEntity entity = w.getTileEntity(pos);
				if (entity instanceof TileBloodFabricator) {
					if(((TileBloodFabricator)entity).tank.getFluidAmount()>=1000){
						((TileBloodFabricator)entity).drain(new FluidStack(BloodMagic.lifeEssenceFluid, 1000), true);
						if(player.getHeldItem(hand).getCount()==1){
							player.inventory.mainInventory.set(player.inventory.currentItem, new ItemStack(BloodMagic.bucketLife));
						}else{
							ItemStack items = player.inventory.mainInventory.get(player.inventory.currentItem).copy();
							items.shrink(1);
							player.inventory.mainInventory.set(player.inventory.currentItem, items);
							if(!player.inventory.addItemStackToInventory(new ItemStack(BloodMagic.bucketLife))) {
								WorldHelper.spawnEntItem(w, player.posX, player.posY, player.posZ, new ItemStack(BloodMagic.bucketLife));
							}
						}
					}
				}
			} else {
				return true;
			}
		}
		return super.onBlockActivated(w, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileBloodFabricator();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
