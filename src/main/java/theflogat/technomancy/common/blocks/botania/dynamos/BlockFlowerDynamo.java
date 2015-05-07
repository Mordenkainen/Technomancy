package theflogat.technomancy.common.blocks.botania.dynamos;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockDynamoBase;
import theflogat.technomancy.common.tiles.botania.dynamos.TileFlowerDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import vazkii.botania.api.wand.IWandHUD;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlowerDynamo extends BlockDynamoBase implements IWandHUD {

	public BlockFlowerDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.flowerDynamo);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.flowerDynamo));
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
	public int getRenderType() {
		return RenderIds.idFlowerDynamo;
	}
}
