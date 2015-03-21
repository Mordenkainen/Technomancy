package theflogat.technomancy.common.blocks.machines;

import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBloodFabricator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.util.InvHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBloodFabricator extends BlockBase {

	public BlockBloodFabricator() {
		setBlockName(Ref.MOD_PREFIX + Names.bloodFabricator);
	}
	
	@Override
	public boolean onBlockActivated(World w, int x,	int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem() != null && player.getHeldItem().getItem()==Items.bucket) {
			if (!w.isRemote) {
				TileEntity entity = w.getTileEntity(x, y, z);
				if (entity instanceof TileBloodFabricator) {
					if(((TileBloodFabricator)entity).tank.getFluidAmount()>=1000){
						((TileBloodFabricator)entity).drain(null, new FluidStack(BloodMagic.lifeEssenceFluid, 1000), true);
						if(player.getHeldItem().stackSize==1){
							player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(BloodMagic.bucketLife);
						}else{
							ItemStack items = player.inventory.mainInventory[player.inventory.currentItem].copy();
							items.stackSize--;
							player.inventory.mainInventory[player.inventory.currentItem] = items;
							if(!player.inventory.addItemStackToInventory(new ItemStack(BloodMagic.bucketLife))) {
								InvHelper.spawnEntItem(w, (int)player.posX, (int)player.posY, (int)player.posZ, new ItemStack(BloodMagic.bucketLife));
							}
						}
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)    {
        int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.bloodFabricator));
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileBloodFabricator();
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
		return RenderIds.idBloodFabricator;
	}

}
