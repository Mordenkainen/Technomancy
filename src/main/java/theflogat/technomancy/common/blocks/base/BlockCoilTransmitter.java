package theflogat.technomancy.common.blocks.base;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public abstract class BlockCoilTransmitter extends BlockContainerAdvanced {

    public BlockCoilTransmitter() {
        setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess w, int x, int y, int z, int side) {
        TileCoilTransmitter tile = getTE(w, x, y, z);
        if (tile != null) {
            return tile.redstoneState ? 15 : 0;
        }
        return 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess w, int x, int y, int z, int side) {
        TileCoilTransmitter tile = getTE(w, x, y, z);
        if (tile != null) {
            return tile.redstoneState ? 15 : 0;
        }
        return 0;
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
        TileCoilTransmitter tile = getTE(world, x, y, z);
        if (tile != null) {
            return tile.isBoosted();
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack items) {
        super.onBlockPlacedBy(w, x, y, z, entity, items);
        TileCoilTransmitter tile = getTE(w, x, y, z);
        if (tile != null) {
            tile.clear();
            tile.facing = w.getBlockMetadata(x, y, z);
            w.setBlockMetadataWithNotify(x, y, z, 0, 0);
        }
    }

    @Override
    public int onBlockPlaced(World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        return ForgeDirection.OPPOSITES[side];
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        TileCoilTransmitter tile = getTE(world, x, y, z);
        if (tile != null) {
            switch (tile.facing) {
                case 0:
                case 1:
                    setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
                    break;
                case 2:
                case 3:
                    setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);
                    break;
                case 4:
                case 5:
                    setBlockBounds(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
                    break;
            }
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister icon) {
        blockIcon = icon.registerIcon(Reference.getAsset(Names.ESSENTIATRANS));
    }

    private static TileCoilTransmitter getTE(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return tile instanceof TileCoilTransmitter ? (TileCoilTransmitter) tile : null;
    }

    @Override
    public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
        super.getNBTInfo(comp, l, meta);
    }
}
