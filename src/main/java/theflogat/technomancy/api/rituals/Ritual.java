package theflogat.technomancy.api.rituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import theflogat.technomancy.common.player.PlayerData;
import theflogat.technomancy.common.player.PlayerData.Affinity;
import theflogat.technomancy.util.helpers.RitualHelper;

public abstract class Ritual {

    public enum Type {
        EARTH(0),
        FIRE(1),
        WATER(2),
        LIGHT(3),
        DARK(4);

        public int id;
        public static Type[] allTypes = { EARTH, FIRE, WATER, LIGHT, DARK };

        private Type(final int id) {
            this.id = id;
        }

        public static Type getType(final int id) {
            for (final Type t : allTypes) {
                if (t.id == id) {
                    return t;
                }
            }
            return null;
        }

        public Affinity getAffinity() {
            return Affinity.getAffinity(id);
        }
    }

    protected Type[] frame = { null, null, null };
    protected Type core;
    protected int id;

    public Ritual(final Type[] frame, final Type core) {
        final Type[] tmpFrame = frame.clone();
        for (int i = 0; i < Math.min(tmpFrame.length, this.frame.length); i++) {
            this.frame[i] = tmpFrame[i];
        }
        this.core = core;
    }

    protected void setId(final int id) {
        this.id = id;
    }

    public boolean isCoreComplete(final World w, final int x, final int y, final int z) {
        return w.getBlockMetadata(x, y, z) == core.id;
    }

    public boolean isFrameComplete(final World w, final int x, final int y, final int z) {
        for (int i = 0; i < frame.length; i++) {
            if (frame[i] == null) {
                if (!checkEmpty(w, x, y, z, i)) {
                    return false;
                }
            } else {
                if (!RitualHelper.checkForT(w, x, y, z, frame[i].id, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void removeFrame(final World w, final int x, final int y, final int z) {
        for (int i = 0; i < frame.length; i++) {
            if (frame[i] != null) {
                RitualHelper.removeT(w, x, y, z, i);
            }
        }
    }

    protected boolean checkEmpty(final World w, final int x, final int y, final int z, final int tier) {
        for (final Type t : Type.allTypes) {
            if (RitualHelper.checkForT(w, x, y, z, t.id, tier)) {
                return false;
            }
        }
        return true;
    }

    public void addAffinity(final World w, final String playerName) {
        final EntityPlayer player = w.getPlayerEntityByName(playerName);
        if (player == null) {
            return;
        }
        PlayerData.addAffinity(w, player, core.getAffinity(), 5);
        for (final Type t : frame) {
            if (t != null) {
                PlayerData.addAffinity(w, player, t.getAffinity(), 1);
            }
        }
        int j = 0;
        for (int i = 0; i < frame.length; i++) {
            if (frame[i] != null) {
                j++;
            }
        }
        for (int i = 0; i <= 25 * j; i++) {
            PlayerData.addExistencePower(w.rand, player);
        }
    }

    public abstract boolean canApplyEffect(World w, int x, int y, int z);

    public abstract void applyEffect(World w, int x, int y, int z);
}