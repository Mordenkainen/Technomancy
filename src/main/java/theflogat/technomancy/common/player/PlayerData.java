package theflogat.technomancy.common.player;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import theflogat.technomancy.lib.TMConfig;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.network.PacketHandler;
import theflogat.technomancy.util.Loc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerData {

    public enum Affinity {

        EARTH(0),
        FIRE(1),
        WATER(2),
        LIGHT(3),
        DARK(4),
        NORMAL(5);

        int id;
        public static Affinity[] allAff = { EARTH, FIRE, WATER, LIGHT, DARK };

        private Affinity(int id) {
            id = ordinal();
        }

        public String getName() {
            switch (id) {
                case 0:
                    return Reference.getId("earth");
                case 1:
                    return Reference.getId("fire");
                case 2:
                    return Reference.getId("water");
                case 3:
                    return Reference.getId("light");
                case 4:
                    return Reference.getId("dark");
            }
            return Reference.getId("normal");
        }

        public Color getColor() {
            switch (id) {
                case 0:
                    return Color.GREEN;
                case 1:
                    return Color.RED;
                case 2:
                    return Color.BLUE;
                case 3:
                    return Color.WHITE;
                case 4:
                    return Color.BLACK;
            }
            return new Color(0x2266AA);
        }

        public String getRName() {
            return "r" + getName();
        }

        public static Affinity getAffinity(int id) {
            for (Affinity aff : Affinity.allAff) {
                if (aff.id == id) {
                    return aff;
                }
            }
            return NORMAL;
        }
    }

    public static NBTTagCompound getData(EntityPlayer player) {
        NBTTagCompound forgeData = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        NBTTagCompound data = forgeData.getCompoundTag(Reference.MOD_ID);

        // For some reason it sometimes is null
        if (data == null) {
            data = new NBTTagCompound();
        }

        if (!forgeData.hasKey(Reference.MOD_ID)) {
            forgeData.setTag(Reference.MOD_ID, data);
        }
        if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, forgeData);
        }

        return data;
    }

    public static void prepareData(EntityPlayer player) {
        if (Loc.isServer()) {
            syncData(player);
        }
        NBTTagCompound data = getData(player);
        if (!(data.hasKey("existencelevel"))) {
            data.setInteger("existencelevel", 1);
            data.setInteger("rexistencelevel", 0);
        }
        if (!(data.hasKey("existencepower"))) {
            data.setInteger("existencepower", 1);
        }
        for (Affinity aff : Affinity.allAff) {
            if (!(data.hasKey(aff.getName()))) {
                data.setInteger(aff.getName(), 1);
                data.setInteger(aff.getRName(), 0);
            }
        }
    }

    public static void syncData(EntityPlayer player) {
        PacketHandler.instance.sendTo(PacketHandler.getPacket((EntityPlayerMP) player), (EntityPlayerMP) player);
    }

    public static Affinity getAffinity(EntityPlayer player) {
        NBTTagCompound data = getData(player);
        if (data.getInteger("existencelevel") >= 20) {
            HashMap<Affinity, Integer> map = getAffinityValues(data);
            int total = map.get(Affinity.NORMAL);
            for (Affinity aff : Affinity.allAff) {
                if (map.get(aff) > total / 2) {
                    return aff;
                }
            }
        }
        return Affinity.NORMAL;
    }

    public static HashMap<Affinity, Integer> getAffinityValues(NBTTagCompound data) {
        HashMap<Affinity, Integer> map = new HashMap<Affinity, Integer>();
        int total = 0;
        for (Affinity aff : Affinity.allAff) {
            map.put(aff, data.getInteger(aff.getName()));
            total += data.getInteger(aff.getName());
        }
        map.put(Affinity.NORMAL, total);
        return map;
    }

    public static void addAffinity(World w, EntityPlayer player, Affinity aff, int i) {
        if (aff == null) {
            return;
        }
        NBTTagCompound data = getData(player);
        for (int j = 0; j < i; j++) {
            try {
                int randVal = data.getInteger(aff.getName()) * 10 - data.getInteger(aff.getRName());
                if (w.rand.nextInt(randVal) == 0) {
                    data.setInteger(aff.getName(), data.getInteger(aff.getName()) + 1);
                    data.setInteger(aff.getRName(), 0);
                } else {
                    data.setInteger(aff.getRName(), data.getInteger(aff.getRName()) + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addExistencePower(World w, String userName) {
        NBTTagCompound data = getData(w.getPlayerEntityByName(userName));
        int randVal = data.getInteger("existencelevel") * 100 - data.getInteger("rexistencelevel");
        if (w.rand.nextInt(randVal) == 0) {
            data.setInteger("existencelevel", data.getInteger("existencelevel") + 1);
            data.setInteger("rexistencelevel", 0);
        } else {
            data.setInteger("rexistencelevel", data.getInteger("rexistencelevel") + 1);
        }
    }

    public static void addExistencePower(Random rand, EntityPlayer player) {
        NBTTagCompound data = getData(player);
        if (data.hasKey("existencelevel")) {
            int randVal = data.getInteger("existencelevel") * 100 - data.getInteger("rexistencelevel");
            if (rand.nextInt(randVal) == 0) {
                data.setInteger("existencelevel", data.getInteger("existencelevel") + 1);
                data.setInteger("rexistencelevel", 0);
            } else {
                data.setInteger("rexistencelevel", data.getInteger("rexistencelevel") + 1);
            }
        }
    }

    public static int getExistenceLevel(EntityPlayer player) {
        NBTTagCompound data = getData(player);
        if (player == null || !(data.hasKey("existencelevel"))) {
            return 1;
        }
        return data.getInteger("existencelevel");
    }

    public static int getCurrentPower(EntityPlayer player) {
        NBTTagCompound data = getData(player);
        if (player == null || !(data.hasKey("existencepower"))) {
            return 1;
        }
        return data.getInteger("existencepower");
    }

    public static void renderHUD(EntityPlayer player) {

        HUDHandler.renderHUD(PlayerData.getCurrentPower(player), PlayerData.getExistenceLevel(player));
    }

    @SideOnly(Side.CLIENT)
    public static class HUDHandler {

        public static final ResourceLocation modelTexture = new ResourceLocation(Reference.HUD_TEXTURE);
        public static final ResourceLocation exTexture = new ResourceLocation(Reference.HUD_EX_TEXTURE);

        public static void renderHUD(int amt, int maxAmount) {
            GL11.glPushMatrix();
            int xSize = 32;
            int ySize = 32;
            float ratio = ((float) amt) / ((float) maxAmount);
            int scale = 20;
            int amount = (int) (scale * ratio);

            Minecraft.getMinecraft().getTextureManager().bindTexture(modelTexture);
            drawRectangle(TMConfig.hudStartX, TMConfig.hudStartY, 0, 0, xSize, ySize);
            Color c = getAffinity(Minecraft.getMinecraft().thePlayer).getColor();
            GL11.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
            Minecraft.getMinecraft().getTextureManager().bindTexture(exTexture);
            drawRectangle(TMConfig.hudStartX, TMConfig.hudStartY + 5 + scale - amount, 0, 0, xSize, amount);

            GL11.glPopMatrix();
        }

        public static void drawRectangle(int x, int y, int u, int v, double width, double length) {
            float f = 0.00390625F;
            float f1 = 0.00390625F;
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(x, y + length, 0, (u * f), (v + length) * f1);
            tessellator.addVertexWithUV(x + width, y + length, 0, (u + width) * f, (v + length) * f1);
            tessellator.addVertexWithUV(x + width, y, 0, (u + width) * f, v * f1);
            tessellator.addVertexWithUV(x, y, 0, u * f, v * f1);
            tessellator.draw();
        }
    }
}
