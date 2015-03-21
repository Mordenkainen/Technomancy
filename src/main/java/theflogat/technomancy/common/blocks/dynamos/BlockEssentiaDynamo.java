package theflogat.technomancy.common.blocks.dynamos;

import theflogat.technomancy.common.blocks.base.BlockDynamoBase;
import theflogat.technomancy.common.tiles.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEssentiaDynamo extends BlockDynamoBase {

	public BlockEssentiaDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.essentiaDynamo);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.essentiaDynamo));
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEssentiaDynamo();
	}

	@Override
	public int getRenderType() {
		return RenderIds.idEssentiaDynamo;
	}
}
