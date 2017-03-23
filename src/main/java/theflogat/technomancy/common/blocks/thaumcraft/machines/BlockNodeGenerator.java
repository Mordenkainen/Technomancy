package theflogat.technomancy.common.blocks.thaumcraft.machines;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNodeGenerator extends BlockContainerAdvanced {

    public BlockNodeGenerator() {
        setBlockName(Ref.getId(Names.nodeGenerator));
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileNodeGenerator();
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
        return RenderIds.idNodeGenerator;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(Ref.getAsset(Names.nodeGenerator));
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack items) {
        if (items.hasTagCompound()) {
            items.stackTagCompound.setBoolean("RegenDummyBlocks", true);
        }
        super.onBlockPlacedBy(world, x, y, z, entity, items);
        int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
        TileNodeGenerator tile = getTE(world, x, y, z);
        if (tile != null) {
            if (facing == 0) {
                tile.facing = 2;
            } else if (facing == 1) {
                tile.facing = 5;
            } else if (facing == 2) {
                tile.facing = 3;
            } else if (facing == 3) {
                tile.facing = 4;
            }

        }
    }

    private static TileNodeGenerator getTE(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return tile instanceof TileNodeGenerator ? (TileNodeGenerator) tile : null;
    }
}
