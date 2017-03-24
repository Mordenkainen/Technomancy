package theflogat.technomancy.common.items.technom;

import java.util.ArrayList;
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
        setUnlocalizedName(Reference.getId(Names.COILCOUPLER));
        setCreativeTab(Technomancy.tabsTM);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        itemIcon = reg.registerIcon(Reference.getAsset(Names.COILCOUPLER));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
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
            TileEntity te = w.getTileEntity(x, y, z);
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
                    ArrayList<String> t = Couple.getType(te);
                    if (t.contains(stack.stackTagCompound.getString("type"))) {
                        if (stack.stackTagCompound.getBoolean("ent")) {
                            if (te.getWorldObj().provider.dimensionId == stack.stackTagCompound.getInteger("dimId")) {
                                if (!areCoordsEqual(stack.stackTagCompound, x, y, z)) {
                                    int[] i = retrievePos(stack.stackTagCompound);
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

    private static boolean areCoordsEqual(NBTTagCompound comp, int x, int y, int z) {
        return comp.getInteger("x") == x && comp.getInteger("y") == y && comp.getInteger("z") == z;
    }

    private static int[] retrievePos(NBTTagCompound comp) {
        return new int[] { comp.getInteger("x"), comp.getInteger("y"), comp.getInteger("z") };
    }

}