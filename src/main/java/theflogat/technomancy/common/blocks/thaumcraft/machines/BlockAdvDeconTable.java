package theflogat.technomancy.common.blocks.thaumcraft.machines;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileAdvDeconTable;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.util.helpers.WorldHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAdvDeconTable extends BlockContainerAdvanced {

    public BlockAdvDeconTable() {
        setBlockName(Ref.getId(Names.advDeconTable));
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase ent, ItemStack items) {
        super.onBlockPlacedBy(w, x, y, z, ent, items);
        TileAdvDeconTable te = getTE(w, x, y, z);
        if (te != null && ent instanceof EntityPlayer)
            te.owner = ((EntityPlayer) ent).getDisplayName();
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileAdvDeconTable te = getTE(w, x, y, z);
        if (te != null && !player.isSneaking()) {
            if (te.getStackInSlot(0) == null) {
                if (player.getHeldItem() != null) {
                    te.setInventorySlotContents(0, player.getHeldItem());
                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                    return true;
                }
            } else {
                if (!w.isRemote) {
                    WorldHelper.spawnEntItem(w, player.posX, player.posY, player.posZ, te.getStackInSlot(0));
                }
                te.setInventorySlotContents(0, null);
                return true;
            }
        }
        return super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
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
        return RenderIds.idAdvDeconTable;
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileAdvDeconTable();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(Ref.getAsset(Names.advDeconTable));
    }

    private static TileAdvDeconTable getTE(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return tile instanceof TileAdvDeconTable ? (TileAdvDeconTable) tile : null;
    }

    @Override
    public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
        super.getNBTInfo(comp, l, meta);
        l.add("Owner:" + comp.getString("owner"));
    }
}
