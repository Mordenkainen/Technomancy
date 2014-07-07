package democretes.blocks.dynamos;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import buildcraft.api.tools.IToolWrench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.blocks.dynamos.tiles.TileBloodDynamo;
import democretes.blocks.dynamos.tiles.TileDynamoBase;
import democretes.compat.BloodMagic;
import democretes.lib.RenderIds;

public class BlockBloodDynamo extends BlockBase {

	@SideOnly(Side.CLIENT)
	public Icon iconDynamo;

	public BlockBloodDynamo(int id) {
		super(id);
		setUnlocalizedName("techno:bloodDynamo");
	}
	public TileEntity createNewTileEntity(World world) {
		return new TileBloodDynamo();
	}

	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IconRegister icon) {
		iconDynamo = icon.registerIcon(Block.stone.getItemIconName());
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) { 
				TileEntity entity = world.getBlockTileEntity(x, y, z);
				if (entity instanceof TileBloodDynamo) {
					if (player.getHeldItem().getItem() instanceof IToolWrench) {
						((TileBloodDynamo)entity).rotateBlock();
					}else if(player.getHeldItem().getItem()==BloodMagic.divinationSigil){
						player.addChatMessage("Energy: " + ((TileBloodDynamo)entity).getEnergyStored(null) + "/" + ((TileBloodDynamo)entity)
								.getMaxEnergyStored(null));
						player.addChatMessage("Blood: " + ((TileBloodDynamo)entity).tank.getFluidAmount() + "/" + ((TileBloodDynamo)entity)
								.tank.getInfo().capacity);
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
		TileDynamoBase tile = (TileDynamoBase)world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			tile.rotateBlock();		   		
		}
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}  

	public boolean isOpaqueCube(){
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return RenderIds.idBloodDynamo;
	}
}