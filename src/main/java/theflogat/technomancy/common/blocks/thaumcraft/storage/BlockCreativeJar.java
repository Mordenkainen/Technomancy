package theflogat.technomancy.common.blocks.thaumcraft.storage;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileCreativeJar;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.Thaumcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCreativeJar extends BlockContainerAdvanced {

    @SideOnly(Side.CLIENT)
    public static IIcon iconLiquid;

    @SideOnly(Side.CLIENT)
    public static IIcon iconJar;
    
    public BlockCreativeJar() {
        super();
        this.setHardness(1F);
        this.setBlockName(Reference.MOD_PREFIX + Names.CREATIVEJAR);
        this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.75F, 0.8125F);
    }

    @Override
    public void onBlockPlacedBy(final World w, final int x, final int y, final int z, final EntityLivingBase entity, final ItemStack items) {
        super.onBlockPlacedBy(w, x, y, z, entity, items);
        final TileEntity tile = w.getTileEntity(x, y, z);
        if (tile instanceof TileCreativeJar) {
            ((TileCreativeJar) tile).facing = 2;
        }
    }

    @Override
    public boolean onBlockActivated(final World w, final int x, final int y, final int z, final EntityPlayer player, final int side, final float hitX, final float hitY, final float hitZ) {
        if (w.getTileEntity(x, y, z) instanceof TileCreativeJar) {
            final TileCreativeJar container = (TileCreativeJar) w.getTileEntity(x, y, z);
            ItemStack item = player.getHeldItem();
            // Empties Jars
            if (player.isSneaking() && container.amount >= 0 && item == null && container.aspectFilter == null) {
                container.amount = 0;
                if (w.isRemote) {
                    w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:jar", 1.0F, 1.0F, false);
                }
                return true;
            }
            if (item != null) {
                // Adds Essentia from Phials
                if (item.getItem() == Thaumcraft.itemEssence && item.getItemDamage() == 1 && container.amount <= (container.maxAmount - 8) && container.addToContainer(((IEssentiaContainerItem) player.getHeldItem().getItem()).getAspects(player.getHeldItem()).getAspects()[0], 8) == 0) {
                    item.stackSize -= 1;
                    final ItemStack phial = new ItemStack(Thaumcraft.itemEssence, 1, 0);
                    if (!player.inventory.addItemStackToInventory(phial)) {
                        w.spawnEntityInWorld(new EntityItem(w, x + 0.5F, y + 0.5F, z + 0.5F, phial));
                    }
                    w.playSoundAtEntity(player, "game.neutral.swim", 0.25F, 1.0F);
                    player.inventoryContainer.detectAndSendChanges();
                    return true;
                }
                // Adds Essentia to Phials
                if (item.getItem() == Thaumcraft.itemEssence && item.getItemDamage() == 0 && container.aspect != null) {
                    final Aspect asp = Aspect.getAspect(container.aspect.getTag());
                    if (container.takeFromContainer(container.aspect, 8)) {
                        item.stackSize -= 1;
                        w.playSoundAtEntity(player, "game.neutral.swim", 0.25F, 1.0F);
                        final ItemStack phial = new ItemStack(Thaumcraft.itemEssence, 1, 1);
                        setAspects(phial, new AspectList().add(asp, 8));
                        if (!player.inventory.addItemStackToInventory(phial)) {
                            w.spawnEntityInWorld(new EntityItem(w, x + 0.5F, y + 0.5F, z + 0.5F, phial));
                        }
                        player.inventoryContainer.detectAndSendChanges();
                        return true;
                    }
                }
            }
        }
        return super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(final World w, final int meta) {
        return new TileCreativeJar();
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
        return RenderIds.idCreativeJar;
    }

    public void setAspects(final ItemStack itemstack, final AspectList aspects) {
        if (!itemstack.hasTagCompound()) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        aspects.writeToNBT(itemstack.getTagCompound());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister icon) {
        iconJar = icon.registerIcon(Reference.MODEL_PREFIX + Names.ESSENTIACONTAINER);
        iconLiquid = icon.registerIcon(Reference.TEXTURE_PREFIX + "animatedglow");
        blockIcon = Blocks.stained_glass.getIcon(0, 2);
    }
}
