package theflogat.technomancy.common.blocks.dynamos;

import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.items.api.ToolWrench;
import theflogat.technomancy.common.tiles.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEssentiaDynamo extends BlockBase {

	public BlockEssentiaDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.essentiaDynamo);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEssentiaDynamo();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		this.icon = icon.registerIcon(Ref.getAsset(Names.essentiaDynamo));
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) { 
				TileEntity entity = world.getTileEntity(x, y, z);
				if (entity instanceof TileEssentiaDynamo) {
					if (ToolWrench.isWrench(player.getHeldItem())) {
						((TileEssentiaDynamo)entity).rotateBlock();
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
	public int getRenderType() {
		return RenderIds.idEssentiaDynamo;
	}
}
