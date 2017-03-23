package theflogat.technomancy.common.tiles.base;

import java.util.ArrayList;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import thaumcraft.api.aspects.IAspectContainer;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import cofh.api.energy.IEnergyHandler;

public interface ICouplable {

    public class Couple {

        public static ArrayList<String> forbidInv = new ArrayList<String>();
        public static ArrayList<String> forbidAC = new ArrayList<String>();
        public static ArrayList<String> forbidFl = new ArrayList<String>();
        public static ArrayList<String> forbidRF = new ArrayList<String>();

        static {
            forbidAC.add("thaumcraft.common.tiles.TileMirrorEssentia");
            forbidAC.add("theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter");
            forbidInv.add("theflogat.technomancy.common.tiles.technom.TileItemTransmitter");
        }

        public static ArrayList<String> getType(TileEntity te) {
            ArrayList<String> interfaceTypes = new ArrayList<String>();
            if (te instanceof IInventory && !forbidInv.contains(te.getClass().getName())) {
                interfaceTypes.add(Type.ITEM.id);
            }
            if ((te instanceof IFluidHandler || te instanceof IFluidTank) && !forbidFl.contains(te.getClass().getName())) {
                interfaceTypes.add(Type.FLUID.id);
            }
            if (CompatibilityHandler.th) {
                if (te instanceof IAspectContainer && !forbidAC.contains(te.getClass().getName())) {
                    interfaceTypes.add(Type.ESSENTIA.id);
                }
            }
            if (te instanceof IEnergyHandler && !forbidRF.contains(te.getClass().getName())) {
                interfaceTypes.add(Type.ENERGYRF.id);
            }

            return interfaceTypes;
        }
    }

    public enum Type {
        ENERGYRF("rf"),
        ESSENTIA("essentia"),
        ITEM("item"),
        FLUID("fluid");

        public String id;

        Type(String id) {
            this.id = id;
        }
    }

    public abstract Type getType();

    public abstract void addPos(ChunkCoordinates coords);

    public abstract void clear();
}
