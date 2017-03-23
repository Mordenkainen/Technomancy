package theflogat.technomancy.common.blocks.thaumcraft.machines;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluxLamp extends BlockContainerAdvanced {

    public BlockFluxLamp() {
        setBlockName(Ref.getId(Names.fluxLamp));
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileFluxLamp();
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
        return RenderIds.idFluxLamp;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister icon) {
        blockIcon = icon.registerIcon(Ref.getAsset(Names.fluxLamp));
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        return side + meta;
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack items) {
        super.onBlockPlacedBy(w, x, y, z, entity, items);
        TileEntity tile = w.getTileEntity(x, y, z);
        if (tile instanceof TileFluxLamp) {
            ((TileFluxLamp) tile).placed = true;
        }
    }
}