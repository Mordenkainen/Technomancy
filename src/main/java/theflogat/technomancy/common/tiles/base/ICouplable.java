package theflogat.technomancy.common.tiles.base;

import java.util.ArrayList;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import thaumcraft.api.aspects.IAspectContainer;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;
import cofh.api.energy.IEnergyHandler;

public interface ICouplable {
	
	public class Couple{
		
		public static ArrayList<String> forbidInv = new ArrayList<String>();
		public static ArrayList<String> forbidAC = new ArrayList<String>();
		public static ArrayList<String> forbidFl = new ArrayList<String>();
		public static ArrayList<String> forbidRF = new ArrayList<String>();
		
		static{
			forbidAC.add("thaumcraft.common.tiles.TileMirrorEssentia");
			forbidAC.add(TileEssentiaTransmitter.class.getName());
			forbidInv.add(TileItemTransmitter.class.getName());
		}
		
		public static Type getType(TileEntity te) {
			if(te==null)
				return null;
			if(te instanceof IInventory && !forbidInv.contains(te.getClass().getName())){
				return Type.ITEM;
			}else if((te instanceof IFluidHandler || te instanceof IFluidTank) && !forbidFl.contains(te.getClass().getName())){
				return Type.FLUID;
			}else if(te instanceof IAspectContainer && !forbidAC.contains(te.getClass().getName())){
				return Type.ESSENTIA;
			}else if(te instanceof IEnergyHandler && !forbidRF.contains(te.getClass().getName())){
				return Type.ENERGYRF;
			}
			
			
			return null;
		}
	}
	
	public enum Type{
		ENERGYRF("rf"),ESSENTIA("essentia"),ITEM("item"),FLUID("fluid");
		
		public String id;
		
		Type(String id){
			this.id = id;
		}
	}
	
	public abstract Type getType();
	public abstract void addPos(ChunkCoordinates coords);
	public abstract void clear();
}
