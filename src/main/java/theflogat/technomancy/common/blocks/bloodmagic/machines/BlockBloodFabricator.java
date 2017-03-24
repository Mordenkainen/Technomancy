package theflogat.technomancy.common.blocks.bloodmagic.machines;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.util.helpers.WorldHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBloodFabricator extends BlockContainerAdvanced {

    public BlockBloodFabricator() {
        setBlockName(Reference.MOD_PREFIX + Names.BLOODFABRICATOR);
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.bucket) {
            if (!w.isRemote) {
                TileEntity entity = w.getTileEntity(x, y, z);
                if (entity instanceof TileBloodFabricator) {
                    if (((TileBloodFabricator) entity).tank.getFluidAmount() >= 1000) {
                        ((TileBloodFabricator) entity).drain(null, new FluidStack(BloodMagic.lifeEssenceFluid, 1000), true);
                        if (player.getHeldItem().stackSize == 1) {
                            player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(BloodMagic.bucketLife);
                        } else {
                            ItemStack items = player.inventory.mainInventory[player.inventory.currentItem].copy();
                            items.stackSize--;
                            player.inventory.mainInventory[player.inventory.currentItem] = items;
                            if (!player.inventory.addItemStackToInventory(new ItemStack(BloodMagic.bucketLife))) {
                                WorldHelper.spawnEntItem(w, player.posX, player.posY, player.posZ, new ItemStack(BloodMagic.bucketLife));
                            }
                        }
                    }
                }
            } else {
                return true;
            }
        }
        return super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister icon) {
        blockIcon = icon.registerIcon(Reference.getAsset(Names.BLOODFABRICATOR));
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileBloodFabricator();
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
        return RenderIds.idBloodFabricator;
    }

}
