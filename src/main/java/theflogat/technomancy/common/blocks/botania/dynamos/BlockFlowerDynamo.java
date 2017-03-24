package theflogat.technomancy.common.blocks.botania.dynamos;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockDynamoBase;
import theflogat.technomancy.common.tiles.botania.dynamos.TileFlowerDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.lib.RenderIds;
import vazkii.botania.api.wand.IWandHUD;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlowerDynamo extends BlockDynamoBase implements IWandHUD {

    public BlockFlowerDynamo() {
        super();
        setBlockName(Reference.getId(Names.FLOWERDYNAMO));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister icon) {
        blockIcon = icon.registerIcon(Reference.getAsset(Names.FLOWERDYNAMO));
    }

    @Override
    public TileEntity createNewTileEntity(final World w, final int meta) {
        return new TileFlowerDynamo();
    }

    @Override
    public void renderHUD(final Minecraft mc, final ScaledResolution res, final World world, final int x, final int y, final int z) {
        ((TileFlowerDynamo) world.getTileEntity(x, y, z)).renderHUD(mc, res);
    }

    @Override
    public int getRenderType() {
        return RenderIds.idFlowerDynamo;
    }
}
