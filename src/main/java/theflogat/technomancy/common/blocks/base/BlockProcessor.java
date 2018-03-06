package theflogat.technomancy.common.blocks.base;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.util.helpers.InvHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockProcessor extends BlockContainerAdvanced {

    @SideOnly(Side.CLIENT)
    public IIcon[] icons;

    protected String name;

    @Override
    public String getUnlocalizedName() {
        return "tile." + Reference.MOD_PREFIX + Names.PROCESSOR + name;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister icon) {
        icons = new IIcon[4];
        icons[0] = icon.registerIcon(Reference.TEXTURE_PREFIX + Names.PROCESSOR + name + "Side");
        icons[1] = icon.registerIcon(Reference.TEXTURE_PREFIX + Names.PROCESSOR + name + "Active");
        icons[2] = icon.registerIcon(Reference.TEXTURE_PREFIX + Names.PROCESSOR + name + "Inactive");
        icons[3] = icon.registerIcon(Reference.TEXTURE_PREFIX + Names.PROCESSOR + name + "Top");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int side, final int meta) {
        if (side == 1) {
            return icons[3];
        }
        if (side > 1) {
            return icons[2];
        }
        return icons[0];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final IBlockAccess access, final int x, final int y, final int z, final int side) {
        if (side == 1) {
            return icons[3];
        }
        if (side > 1) {
            TileEntity tile = access.getTileEntity(x, y, z);
            if (tile instanceof TileProcessorBase && ((TileProcessorBase) tile).isActive) {
                return icons[1];
            }
            return icons[2];
        }
        return icons[0];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random r) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileProcessorBase && ((TileProcessorBase) te).isActive) {
            float f = x + 0.5F;
            float f1 = y + 0.2F + r.nextFloat() * 5.0F / 16.0F;
            float f2 = z + 0.5F;
            float f3 = 0.52F;
            float f4 = r.nextFloat() * 0.5F - 0.25F;
            world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);

            world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);

            world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);

            world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getLightValue(final IBlockAccess world, final int x, final int y, final int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileProcessorBase && ((TileProcessorBase) tile).isActive) {
            return 12;
        }
        return super.getLightValue(world, x, y, z);
    }

    @Override
    public void breakBlock(final World world, final int x, final int y, final int z, final Block id, final int meta) {
        InvHelper.dropItemsFromTile(world, x, y, z);
        super.breakBlock(world, x, y, z, id, meta);
    }
}
