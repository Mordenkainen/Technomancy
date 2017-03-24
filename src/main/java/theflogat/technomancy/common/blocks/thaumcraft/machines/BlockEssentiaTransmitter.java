package theflogat.technomancy.common.blocks.thaumcraft.machines;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import theflogat.technomancy.common.blocks.base.BlockCoilTransmitter;
import theflogat.technomancy.common.items.technom.ItemBoost;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.helpers.WorldHelper;

public class BlockEssentiaTransmitter extends BlockCoilTransmitter {

    public BlockEssentiaTransmitter() {
        super();
        setBlockName(Reference.getId(Names.ESSENTIATRANS));
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileEssentiaTransmitter();
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem();
        if (stack != null && (stack.getItem() instanceof ItemCoilCoupler || stack.getItem() instanceof ItemBoost)) {
            return false;
        }
        if (super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ)) {
            return true;
        }
        TileEssentiaTransmitter tile = getTE(w, x, y, z);
        if (tile != null) {
            if (player.isSneaking()) {
                if (tile.aspectFilter != null) {
                    if (!w.isRemote) {
                        ItemStack filterItem = new ItemStack(Thaumcraft.itemResource, 1, 13);
                        AspectList filterAspect = new AspectList().add(tile.aspectFilter, 8);
                        ((IEssentiaContainerItem) filterItem.getItem()).setAspects(filterItem, filterAspect);
                        tile.aspectFilter = null;
                        WorldHelper.spawnEntItem(w, x, y, z, filterItem);
                        w.markBlockForUpdate(x, y, z);
                    } else {
                        w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
                    }
                    return true;
                }
            } else if (stack != null && tile.aspectFilter == null) {
                if (stack.getItemDamage() == 13 && stack.getItem() == Thaumcraft.itemResource && stack.getItem() instanceof IEssentiaContainerItem) {
                    if (((IEssentiaContainerItem) stack.getItem()).getAspects(stack) != null) {
                        if (!w.isRemote) {
                            tile.aspectFilter = ((IEssentiaContainerItem) stack.getItem()).getAspects(stack).getAspects()[0];
                            stack.stackSize -= 1;
                            w.markBlockForUpdate(x, y, z);
                        } else {
                            w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block id, int meta) {
        TileEssentiaTransmitter tile = getTE(world, x, y, z);
        if (tile != null && tile.aspectFilter != null && !world.isRemote) {
            ItemStack filterItem = new ItemStack(Thaumcraft.itemResource, 1, 13);
            AspectList filterAspect = new AspectList().add(tile.aspectFilter, 8);
            ((IEssentiaContainerItem) filterItem.getItem()).setAspects(filterItem, filterAspect);
            tile.aspectFilter = null;
            WorldHelper.spawnEntItem(world, x, y, z, filterItem);
        }
    }

    @Override
    public int getRenderType() {
        return RenderIds.idEssentiaTrans;
    }

    @Override
    public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
        super.getNBTInfo(comp, l, meta);
        Aspect filter = Aspect.getAspect(comp.getString("AspectFilter"));
        if (filter != null) {
            l.add("Filter: " + filter.getName());
        }
    }

    private static TileEssentiaTransmitter getTE(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return tile instanceof TileEssentiaTransmitter ? (TileEssentiaTransmitter) tile : null;
    }
}
