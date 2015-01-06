package theflogat.technomancy.handlers.compat;

import theflogat.technomancy.lib.Conf;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ThermalExpansion {

	public static boolean te = true;

	public static ItemStack pulverizer;
	public static ItemStack sawmill;
	public static ItemStack smelter;
	public static ItemStack crucible;
	public static ItemStack transposer;
	public static ItemStack precipitator;
	public static ItemStack extruder;
	public static ItemStack accumulator;
	public static ItemStack assembler;
	public static ItemStack charger;
	public static ItemStack frameMachineBasic;
	
	
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

	public static ItemStack frameCellBasic;
	public static ItemStack frameTesseractFull;
	
	public static Block blockCell;

	public static Block blockDynamo;
	public static Block blockTank;


	public static void init(){
		try{
			Class TEI = Class.forName("thermalexpansion.item.TEItems");
			powerCoilElectrum = (ItemStack) TEI.getField("powerCoilElectrum").get(TEI);
			capacitorResonant = (ItemStack) TEI.getField("capacitorResonant").get(TEI);
			powerCoilSilver = (ItemStack) TEI.getField("powerCoilSilver").get(TEI);
			powerCoilGold = (ItemStack) TEI.getField("powerCoilGold").get(TEI);
			
			Class TFI = Class.forName("thermalfoundation.item.TFItems");
			ingotSilver = (ItemStack) TFI.getField("ingotSilver").get(TFI);
			ingotLead = (ItemStack) TFI.getField("ingotLead").get(TFI);
			ingotNickel = (ItemStack) TFI.getField("ingotNickel").get(TFI);
			ingotPlatinum = (ItemStack) TFI.getField("ingotPlatinum").get(TFI);
			ingotCopper = (ItemStack) TFI.getField("ingotCopper").get(TFI);
			ingotTin = (ItemStack) TFI.getField("ingotTin").get(TFI);
			
			Class TEB = Class.forName("thermalexpansion.block.TEBlocks");
			blockCell = (Block) TEB.getField("blockCell").get(TEB);
			blockDynamo = (Block) TEB.getField("blockDynamo").get(TEB);
			blockTank = (Block) TEB.getField("blockTank").get(TEB);
			
			Class TBM = Class.forName("thermalexpansion.block.machine.BlockMachine");
			pulverizer = (ItemStack) TBM.getField("pulverizer").get(TBM);
			sawmill = (ItemStack) TBM.getField("sawmill").get(TBM);
			smelter = (ItemStack) TBM.getField("smelter").get(TBM);
			crucible = (ItemStack) TBM.getField("crucible").get(TBM);
			transposer = (ItemStack) TBM.getField("transposer").get(TBM);
			precipitator = (ItemStack) TBM.getField("precipitator").get(TBM);
			extruder = (ItemStack) TBM.getField("extruder").get(TBM);
			accumulator = (ItemStack) TBM.getField("accumulator").get(TBM);
			assembler = (ItemStack) TBM.getField("assembler").get(TBM);
			charger = (ItemStack) TBM.getField("charger").get(TBM);
			
			Class TBF = Class.forName("thermalexpansion.block.simple.BlockFrame");
			frameMachineBasic = (ItemStack) TBF.getField("frameMachineBasic").get(TBF);
			frameTesseractFull = (ItemStack) TBF.getField("frameTesseractFull").get(TBF);
			frameCellBasic = (ItemStack) TBF.getField("frameCellBasic").get(TBF);
			
			System.out.println("Technomancy: Thermal Expansion Module Loaded");
		}catch(Exception e){te = false;System.out.println("Technomancy: Failed to load Thermal Expansion Module");Conf.ex(e);}
	}
}
