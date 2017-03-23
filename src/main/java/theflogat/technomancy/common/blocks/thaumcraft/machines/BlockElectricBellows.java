package theflogat.technomancy.common.blocks.thaumcraft.machines;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.tiles.TileAlchemyFurnace;
import thaumcraft.common.tiles.TileArcaneFurnace;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileElectricBellows;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import tuhljin.automagy.api.essentia.IEssentiaDistillery;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockElectricBellows extends BlockContainerAdvanced {

    public BlockElectricBellows() {
        setBlockName(Ref.MOD_PREFIX + Names.electricBellows);
    }

    private byte facing;

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack items) {
        super.onBlockPlacedBy(w, x, y, z, entity, items);
        TileEntity tile = w.getTileEntity(x, y, z);
        if (tile instanceof TileElectricBellows) {
            ((TileElectricBellows) tile).facing = this.facing;
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        this.facing = (byte) ForgeDirection.OPPOSITES[side];
        return side + meta;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        int offsetX = 0;
        int offsetZ = 0;
        switch (ForgeDirection.OPPOSITES[side]) {
            case 2:
                offsetZ -= 1;
                break;
            case 3:
                offsetZ += 1;
                break;
            case 4:
                offsetX -= 1;
                break;
            case 5:
                offsetX += 1;
        }
        if (world.getTileEntity(x + offsetX, y, z + offsetZ) instanceof TileAlchemyFurnace || world.getTileEntity(x + (offsetX * 2), y, z + (offsetZ * 2)) instanceof TileArcaneFurnace || world.getTileEntity(x + offsetX, y, z + offsetZ) instanceof TileEntityFurnace) {
            return true;
        }
        try {
            if (Loader.isModLoaded("Automagy") && world.getTileEntity(x + offsetX, y, z + offsetZ) instanceof IEssentiaDistillery) {
                return true;
            }
        } catch (Exception e) {}
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileElectricBellows();
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
        return RenderIds.idElectricBellows;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister icon) {
        blockIcon = icon.registerIcon(Ref.getAsset(Names.electricBellows));
    }
}
