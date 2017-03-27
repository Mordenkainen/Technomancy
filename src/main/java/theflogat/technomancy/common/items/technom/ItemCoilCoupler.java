package theflogat.technomancy.common.items.technom;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.tiles.base.ICouplable;
import theflogat.technomancy.common.tiles.base.ICouplable.Couple;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public class ItemCoilCoupler extends Item {

    public ItemCoilCoupler() {
        super();
        setUnlocalizedName(Reference.getId(Names.COILCOUPLER));
        setCreativeTab(Technomancy.tabsTM);
    }

    @Override
    public void registerIcons(final IIconRegister reg) {
        itemIcon = reg.registerIcon(Reference.getAsset(Names.COILCOUPLER));
    }

    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World w, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
            stack.stackTagCompound.setBoolean("ent", false);
            stack.stackTagCompound.setString("type", "");
            stack.stackTagCompound.setInteger("x", 0);
            stack.stackTagCompound.setInteger("y", 0);
            stack.stackTagCompound.setInteger("z", 0);
            stack.stackTagCompound.setInteger("dimId", 0);
        }

        if (!w.isRemote) {
            final TileEntity te = w.getTileEntity(x, y, z);
            if (te != null) {
                if (te instanceof ICouplable) {
                    if (player.isSneaking()) {
                        ((ICouplable) te).clear();
                        player.addChatComponentMessage(new ChatComponentText("Links Cleared"));
                        return false;
                    } else {
                        player.addChatComponentMessage(new ChatComponentText("Begin Linking"));
                        stack.stackTagCompound.setBoolean("ent", true);
                        stack.stackTagCompound.setString("type", ((ICouplable) te).getType().id);
                        stack.stackTagCompound.setInteger("x", x);
                        stack.stackTagCompound.setInteger("y", y);
                        stack.stackTagCompound.setInteger("z", z);
                        stack.stackTagCompound.setInteger("dimId", te.getWorldObj().provider.dimensionId);
                        return true;
                    }
                } else {
                    final List<String> t = Couple.getType(te);
                    if (t.contains(stack.stackTagCompound.getString("type"))) {
                        if (stack.stackTagCompound.getBoolean("ent")) {
                            if (te.getWorldObj().provider.dimensionId == stack.stackTagCompound.getInteger("dimId")) {
                                if (!areCoordsEqual(stack.stackTagCompound, x, y, z)) {
                                    final int[] i = retrievePos(stack.stackTagCompound);
                                    if (w.getTileEntity(i[0], i[1], i[2]) instanceof ICouplable) {
                                        ((ICouplable) w.getTileEntity(i[0], i[1], i[2])).addPos(new ChunkCoordinates(x, y, z));
                                        player.addChatComponentMessage(new ChatComponentText("Linked"));
                                        return true;
                                    }
                                    player.addChatComponentMessage(new ChatComponentText("Core got removed"));
                                    return false;
                                }
                            } else {
                                player.addChatComponentMessage(new ChatComponentText("Cannot create an interdimensional link"));
                            }
                        } else {
                            player.addChatComponentMessage(new ChatComponentText("No source to link from"));
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean areCoordsEqual(final NBTTagCompound comp, final int x, final int y, final int z) {
        return comp.getInteger("x") == x && comp.getInteger("y") == y && comp.getInteger("z") == z;
    }

    private static int[] retrievePos(final NBTTagCompound comp) {
        return new int[] { comp.getInteger("x"), comp.getInteger("y"), comp.getInteger("z") };
    }

}