package democretes.blocks.dynamos;

import democretes.blocks.dynamos.tiles.TileDynamoBase;
import buildcraft.api.tools.IToolWrench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.blocks.dynamos.tiles.TileEssentiaDynamo;
import democretes.blocks.dynamos.tiles.TileNodeDynamo;
import democretes.lib.RenderIds;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockEssentiaDynamo extends BlockBase {


	@SideOnly(Side.CLIENT)
	public Icon iconDynamo;

	public BlockEssentiaDynamo(int id) {
		super(id);
		setUnlocalizedName("techno:essentiaDynamo");
	}

	public TileEntity createNewTileEntity(World world) {
		return new TileEssentiaDynamo();
	}

	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IconRegister icon) {
		this.iconDynamo = icon.registerIcon("technom:models/essentiaDynamo");
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) { 
				TileEntity entity = world.getBlockTileEntity(x, y, z);
				if (entity instanceof TileEssentiaDynamo) {
					if (player.getHeldItem().getItem() instanceof IToolWrench) {
						((TileEssentiaDynamo)entity).rotateBlock();
					}		
				}
			}else{
				return true;
			}

		}
		return false;	   
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileDynamoBase tile = (TileDynamoBase)world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			tile.rotateBlock();
		}
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}   

	public int getRenderType() {
		return RenderIds.idEssentiaDynamo;
	}
}
