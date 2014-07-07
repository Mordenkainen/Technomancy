package democretes.compat;

import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ThermalExpansion {

	public static boolean te = true;

	public static ItemStack pulverizer;
	public static ItemStack sawmill;
	public static ItemStack smelter;
	public static ItemStack crucible;
	public static ItemStack transposer;
	public static ItemStack iceGen;
	public static ItemStack rockGen;
	public static ItemStack waterGen;
	public static ItemStack assembler;
	public static ItemStack charger;
	public static ItemStack machineFrame;
	
	
	public static ItemStack ingotSilver;
	public static ItemStack ingotLead;
	public static ItemStack ingotNickel;
	public static ItemStack ingotPlatinum;
	public static ItemStack ingotCopper;
	public static ItemStack ingotTin;
	public static ItemStack powerCoilElectrum;
	public static ItemStack capacitorResonant;
	public static ItemStack powerCoilSilver;
	public static ItemStack powerCoilGold;

	public static ItemStack cellBasicFrame;
	public static ItemStack tesseractFrameFull;
	
	public static Block blockEnergyCell;

	public static Block blockDynamo;
	public static Block blockTank;


	public static void init(){
		try{
			Class TEI = Class.forName("thermalexpansion.item.TEItems");
			ingotSilver = (ItemStack) TEI.getField("ingotSilver").get(TEI);
			ingotLead = (ItemStack) TEI.getField("ingotLead").get(TEI);
			ingotNickel = (ItemStack) TEI.getField("ingotNickel").get(TEI);
			ingotPlatinum = (ItemStack) TEI.getField("ingotPlatinum").get(TEI);
			ingotCopper = (ItemStack) TEI.getField("ingotCopper").get(TEI);
			ingotTin = (ItemStack) TEI.getField("ingotTin").get(TEI);
			powerCoilElectrum = (ItemStack) TEI.getField("powerCoilElectrum").get(TEI);
			capacitorResonant = (ItemStack) TEI.getField("capacitorResonant").get(TEI);
			powerCoilSilver = (ItemStack) TEI.getField("powerCoilSilver").get(TEI);
			powerCoilGold = (ItemStack) TEI.getField("powerCoilGold").get(TEI);
			
			Class TEB = Class.forName("thermalexpansion.block.TEBlocks");
			blockEnergyCell = (Block) TEB.getField("blockEnergyCell").get(TEB);
			blockDynamo = (Block) TEB.getField("blockDynamo").get(TEB);
			blockTank = (Block) TEB.getField("blockTank").get(TEB);
			
			Class TBM = Class.forName("thermalexpansion.block.machine.BlockMachine");
			pulverizer = (ItemStack) TBM.getField("pulverizer").get(TBM);
			sawmill = (ItemStack) TBM.getField("sawmill").get(TBM);
			smelter = (ItemStack) TBM.getField("smelter").get(TBM);
			crucible = (ItemStack) TBM.getField("crucible").get(TBM);
			transposer = (ItemStack) TBM.getField("transposer").get(TBM);
			iceGen = (ItemStack) TBM.getField("iceGen").get(TBM);
			rockGen = (ItemStack) TBM.getField("rockGen").get(TBM);
			waterGen = (ItemStack) TBM.getField("waterGen").get(TBM);
			assembler = (ItemStack) TBM.getField("assembler").get(TBM);
			charger = (ItemStack) TBM.getField("charger").get(TBM);
			machineFrame = (ItemStack) TBM.getField("machineFrame").get(TBM);
			
			Class TTF = Class.forName("thermalexpansion.block.ender.BlockTesseract");
			tesseractFrameFull = (ItemStack) TTF.getField("tesseractFrameFull").get(TTF);
			
			Class TBC = Class.forName("thermalexpansion.block.energycell.BlockEnergyCell");
			cellBasicFrame = (ItemStack) TBC.getField("cellBasicFrame").get(TBC);

		}catch(Exception e){te = false;}
	}
}
