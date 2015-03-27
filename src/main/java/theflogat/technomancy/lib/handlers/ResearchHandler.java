package theflogat.technomancy.lib.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class ResearchHandler {
	
	public static HashMap<String, Object> recipes = new HashMap<String, Object>();
	
	public static void init() {
		try{
			ResearchCategories.registerCategory("TECHNOMANCY", new ResourceLocation("technom", "textures/misc/technomancyCategory.png"),
					new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));
	
			initThaumcraft();
		}catch(Exception e){e.printStackTrace();}
	}
	
	private static void initThaumcraft(){
//		Object pTECHNOBASICS = Array.newInstance(Thaumcraft.ResearchPage, 3);
//		Array.set(pTECHNOBASICS, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.TECHNOBASICS.1"));
//		Array.set(pTECHNOBASICS, 1, Thaumcraft.ResearchPageCrucible.newInstance(Thaumcraft.CrucibleRecipe.cast(recipes.get("NeutronizedMetal"))));
//		Array.set(pTECHNOBASICS, 2, Thaumcraft.ResearchPageIRecipe.newInstance((IRecipe)recipes.get("NeutronizedGear")));
//		
//		Object TECHNOBASICS = Thaumcraft.ResearchItemConst.newInstance("TECHNOBASICS", "TECHNOMANCY", new AspectList(), 0, 0, 0,
//				new ResourceLocation("technom", "textures/misc/technomancyBasics.png"));
//		TECHNOBASICS = Thaumcraft.setPages.invoke(TECHNOBASICS, Thaumcraft.ResearchPageArr.cast(pTECHNOBASICS));
//		TECHNOBASICS = Thaumcraft.setRound.invoke(TECHNOBASICS);
//		TECHNOBASICS = Thaumcraft.setStub.invoke(TECHNOBASICS);
//		TECHNOBASICS = Thaumcraft.setAutoUnlock.invoke(TECHNOBASICS);TECHNOBASICS = Thaumcraft.registerResearchItem.invoke(TECHNOBASICS);
//		
//		Object pQUANTUMJARS = Array.newInstance(Thaumcraft.ResearchPage, 3);
//		Array.set(pQUANTUMJARS, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.QUANTUMJARS.1"));
//		Array.set(pQUANTUMJARS, 1, Thaumcraft.ResearchPageIArcRecipe.newInstance(Thaumcraft.ArcaneRecipe.cast(recipes.get("QuantumJar"))));
//		Array.set(pQUANTUMJARS, 2, Thaumcraft.ResearchPageIArcRecipe.newInstance(Thaumcraft.ArcaneRecipe.cast(recipes.get("QuantumGlass"))));
//		
//		Object QUANTUMJARS = Thaumcraft.ResearchItemStConst.newInstance("QUANTUMJARS", "TECHNOMANCY", new AspectList().add(Aspect.ORDER, 5)
//				.add(Aspect.VOID, 5).add(Aspect.MAGIC, 5), 1, -2, 0, new ItemStack(TMBlocks.essentiaContainer));
//
//		QUANTUMJARS = Thaumcraft.setPages.invoke(QUANTUMJARS, Thaumcraft.ResearchPageArr.cast(pQUANTUMJARS));
//		QUANTUMJARS = Thaumcraft.setParents.invoke(QUANTUMJARS, (Object)new String[] { "TECHNOBASICS" });
//		QUANTUMJARS = Thaumcraft.setSecondary.invoke(QUANTUMJARS);QUANTUMJARS = Thaumcraft.registerResearchItem.invoke(QUANTUMJARS);
//		
//		Object pDYNAMO = Array.newInstance(Thaumcraft.ResearchPage, 4);
//		Array.set(pDYNAMO, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.DYNAMO.1"));
//		Array.set(pDYNAMO, 1, Thaumcraft.ResearchPageIArcRecipe.newInstance(Thaumcraft.ArcaneRecipe.cast(recipes.get("EssentiaDynamo"))));
//		Array.set(pDYNAMO, 2, Thaumcraft.ResearchPageString.newInstance("techno.research_page.DYNAMO.2"));
//		Array.set(pDYNAMO, 3, Thaumcraft.ResearchPageIArcRecipe.newInstance(Thaumcraft.ArcaneRecipe.cast(recipes.get("NodeDynamo"))));
//		
//		Object DYNAMO = Thaumcraft.ResearchItemStConst.newInstance("DYNAMO", "TECHNOMANCY", new AspectList().add(Aspect.MECHANISM, 5)
//				.add(Aspect.ENERGY, 5).add(Aspect.MAGIC, 5), 2, 2, 3, new ItemStack(TMBlocks.nodeDynamo));
//
//		DYNAMO = Thaumcraft.setPages.invoke(DYNAMO, Thaumcraft.ResearchPageArr.cast(pDYNAMO));
//		DYNAMO = Thaumcraft.setParents.invoke(DYNAMO, (Object)new String[] { "TECHNOBASICS" });
//		DYNAMO = Thaumcraft.setSecondary.invoke(DYNAMO);DYNAMO = Thaumcraft.registerResearchItem.invoke(DYNAMO);
//		
//		Object pPEN = Array.newInstance(Thaumcraft.ResearchPage, 3);
//		Array.set(pPEN, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.PEN.1"));
//		Array.set(pPEN, 1, Thaumcraft.ResearchPageIRecipe.newInstance((IRecipe)recipes.get("Pen")));
//		Array.set(pPEN, 2, Thaumcraft.ResearchPageIRecipe.newInstance((IRecipe)recipes.get("PenCore")));
//		
//		Object PEN = Thaumcraft.ResearchItemStConst.newInstance("PEN", "TECHNOMANCY", new AspectList(), 4, -4, 0, new ItemStack(TMItems.itemPen));
//
//		PEN = Thaumcraft.setPages.invoke(PEN, Thaumcraft.ResearchPageArr.cast(pPEN));
//		PEN = Thaumcraft.setParents.invoke(PEN, (Object)new String[] {});
//		PEN = Thaumcraft.setAutoUnlock.invoke(PEN);PEN = Thaumcraft.registerResearchItem.invoke(PEN);
//
//		Object pTESLACOIL = Array.newInstance(Thaumcraft.ResearchPage, 4);
//		Array.set(pTESLACOIL, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.TESLACOIL.1"));;
//		Array.set(pTESLACOIL, 1, Thaumcraft.ResearchPageString.newInstance("techno.research_page.TESLACOIL.2"));
//		Array.set(pTESLACOIL, 2, Thaumcraft.ResearchPageIArcRecipe.newInstance(Thaumcraft.ArcaneRecipe.cast(recipes.get("TeslaCoil"))));
//		Array.set(pTESLACOIL, 3, Thaumcraft.ResearchPageIArcRecipe.newInstance(Thaumcraft.ArcaneRecipe.cast(recipes.get("CoilCoupler"))));
//		
//		Object TESLACOIL = Thaumcraft.ResearchItemStConst.newInstance("TESLACOIL", "TECHNOMANCY", new AspectList().add(Aspect.EXCHANGE, 5)
//				.add(Aspect.ELDRITCH, 5).add(Aspect.MECHANISM, 5).add(Aspect.AURA, 5), 3, -3, 3, new ItemStack(TMBlocks.teslaCoil));
//
//		TESLACOIL = Thaumcraft.setPages.invoke(TESLACOIL, Thaumcraft.ResearchPageArr.cast(pTESLACOIL));
//		TESLACOIL = Thaumcraft.setParents.invoke(TESLACOIL, (Object)new String[] { "QUANTUMJARS" });
//		TESLACOIL = Thaumcraft.setRound.invoke(TESLACOIL);TESLACOIL = Thaumcraft.registerResearchItem.invoke(TESLACOIL);
//	
//		Object pENERGIZEDWAND = Array.newInstance(Thaumcraft.ResearchPage, 2);
//		Array.set(pENERGIZEDWAND, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.ELECTRICWAND.1"));
//		Array.set(pENERGIZEDWAND, 1, Thaumcraft.ResearchPageIInfRecipe.newInstance(Thaumcraft.InfusionRecipe.cast(
//				recipes.get("EnergizedWandRod"))));
//		
//		Object ENERGIZEDWAND = Thaumcraft.ResearchItemStConst.newInstance("ENERGIZEDWAND", "TECHNOMANCY", new AspectList().add(Aspect.TOOL, 5)
//				.add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5).add(Aspect.EXCHANGE, 5), -1, 2, 3, new ItemStack(TMItems.itemWandCores, 1, 0));
//
//		ENERGIZEDWAND = Thaumcraft.setPages.invoke(ENERGIZEDWAND, Thaumcraft.ResearchPageArr.cast(pENERGIZEDWAND));
//		ENERGIZEDWAND = Thaumcraft.setParents.invoke(ENERGIZEDWAND,(Object)new String[]{"TECHNOBASICS"});
//		ENERGIZEDWAND = Thaumcraft.setHidden.invoke(ENERGIZEDWAND);
//		ENERGIZEDWAND = Thaumcraft.setItemTriggers.invoke(ENERGIZEDWAND, (Object) new ItemStack[]{ new ItemStack(Thaumcraft.itemWandRod, 1, 1)});
//		ENERGIZEDWAND = Thaumcraft.registerResearchItem.invoke(ENERGIZEDWAND);
//		
//		Object pCONDENSER = Array.newInstance(Thaumcraft.ResearchPage, 2);
//		Array.set(pCONDENSER, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.CONDENSER.1"));
//		Array.set(pCONDENSER, 1, Thaumcraft.ResearchPageIInfRecipe.newInstance(Thaumcraft.InfusionRecipe.cast(recipes.get("Condenser"))));
//		
//		Object CONDENSER = Thaumcraft.ResearchItemStConst.newInstance("CONDENSER", "TECHNOMANCY", new AspectList().add(Aspect.ENERGY, 5)
//				.add(Aspect.ORDER, 5).add(Aspect.EXCHANGE, 5), 2, 3, 3, new ItemStack(TMBlocks.condenserBlock));
//
//		CONDENSER = Thaumcraft.setPages.invoke(CONDENSER, Thaumcraft.ResearchPageArr.cast(pCONDENSER));
//		CONDENSER = Thaumcraft.setParents.invoke(CONDENSER, (Object)new String[] { "DYNAMO" });
//		CONDENSER = Thaumcraft.registerResearchItem.invoke(CONDENSER);
//	
//		Object pPROCESSOR = Array.newInstance(Thaumcraft.ResearchPage, 2);
//		Array.set(pPROCESSOR, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.PROCESSOR.1"));
//		Array.set(pPROCESSOR, 1, Thaumcraft.ResearchPageIArcRecipe.newInstance(Thaumcraft.ArcaneRecipe.cast(recipes.get("Processor"))));
//		
//		Object PROCESSOR = Thaumcraft.ResearchItemStConst.newInstance("PROCESSOR", "TECHNOMANCY", new AspectList().add(Aspect.FIRE, 5)
//				.add(Aspect.EXCHANGE, 5).add(Aspect.ENTROPY, 5), -2, -1, 3, new ItemStack(TMBlocks.processorTC));
//
//		PROCESSOR = Thaumcraft.setPages.invoke(PROCESSOR, Thaumcraft.ResearchPageArr.cast(pPROCESSOR));
//		PROCESSOR = Thaumcraft.setParents.invoke(PROCESSOR, (Object)new String[] { "TECHNOBASICS" });
//		PROCESSOR = Thaumcraft.registerResearchItem.invoke(PROCESSOR);
//		
//		Object pNODEGENERATOR = Array.newInstance(Thaumcraft.ResearchPage, 3);
//		Array.set(pNODEGENERATOR, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.NODEGENERATOR.1"));
//		Array.set(pNODEGENERATOR, 1, Thaumcraft.ResearchPageString.newInstance("techno.research_page.NODEGENERATOR.2"));
//		Array.set(pNODEGENERATOR, 2, Thaumcraft.ResearchPageIInfRecipe.newInstance(Thaumcraft.InfusionRecipe.cast(recipes.get("NodeGenerator"))));
//		
//		Object NODEGENERATOR = Thaumcraft.ResearchItemStConst.newInstance("NODEGENERATOR", "TECHNOMANCY", new AspectList().add(Aspect.MECHANISM, 5)
//				.add(Aspect.ENERGY, 5).add(Aspect.TAINT, 5).add(Aspect.AURA, 5), 4, 3, 3, new ItemStack(TMBlocks.nodeGenerator));
//
//		NODEGENERATOR = Thaumcraft.setPages.invoke(NODEGENERATOR, Thaumcraft.ResearchPageArr.cast(pNODEGENERATOR));
//		NODEGENERATOR = Thaumcraft.setParents.invoke(NODEGENERATOR, (Object)new String[] { "CONDENSER" });
//		NODEGENERATOR = Thaumcraft.setSpecial.invoke(NODEGENERATOR);NODEGENERATOR = Thaumcraft.registerResearchItem.invoke(NODEGENERATOR);
//		
//		Object pBIOMEMORPHER = Array.newInstance(Thaumcraft.ResearchPage, 2);
//		Array.set(pBIOMEMORPHER, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.BIOMEMORPHER.1"));
//		Array.set(pBIOMEMORPHER, 1, Thaumcraft.ResearchPageIArcRecipe.newInstance(Thaumcraft.ArcaneRecipe.cast(recipes.get("BiomeMorpher"))));
//		
//		Object BIOMEMORPHER = Thaumcraft.ResearchItemStConst.newInstance("BIOMEMORPHER", "TECHNOMANCY", new AspectList().add(Aspect.EXCHANGE, 5)
//				.add(Aspect.TAINT, 5).add(Aspect.AURA, 5).add(Aspect.EARTH, 5), -3, 0, 3, new ItemStack(TMBlocks.biomeMorpher));
//
//		BIOMEMORPHER = Thaumcraft.setPages.invoke(BIOMEMORPHER, Thaumcraft.ResearchPageArr.cast(pBIOMEMORPHER));
//		BIOMEMORPHER = Thaumcraft.setParents.invoke(BIOMEMORPHER, (Object)new String[] { "PROCESSOR" });
//		BIOMEMORPHER = Thaumcraft.setSecondary.invoke(BIOMEMORPHER);BIOMEMORPHER = Thaumcraft.registerResearchItem.invoke(BIOMEMORPHER);
//		
//		Object pELECTRICBELLOWS = Array.newInstance(Thaumcraft.ResearchPage, 2);
//		Array.set(pELECTRICBELLOWS, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.ELECTRICBELLOWS.1"));
//		Array.set(pELECTRICBELLOWS, 1, Thaumcraft.ResearchPageIArcRecipe.newInstance(Thaumcraft.ArcaneRecipe.cast(recipes.get("ElectricBellows"))));
//		
//		Object ELECTRICBELLOWS = Thaumcraft.ResearchItemStConst.newInstance("ELECTRICBELLOWS", "TECHNOMANCY", new AspectList()
//		.add(Aspect.ENERGY, 5).add(Aspect.AIR, 5).add(Aspect.FIRE, 5), -3, -2, 2, new ItemStack(TMBlocks.electricBellows));
//
//		ELECTRICBELLOWS = Thaumcraft.setPages.invoke(ELECTRICBELLOWS, Thaumcraft.ResearchPageArr.cast(pELECTRICBELLOWS));
//		ELECTRICBELLOWS = Thaumcraft.setParents.invoke(ELECTRICBELLOWS, (Object)new String[] { "PROCESSOR", "BELLOWS" });
//		ELECTRICBELLOWS = Thaumcraft.registerResearchItem.invoke(ELECTRICBELLOWS);
//		
//		Object pFLUXLAMP = Array.newInstance(Thaumcraft.ResearchPage, 2);
//		Array.set(pFLUXLAMP, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.FLUXLAMP.1"));
//		Array.set(pFLUXLAMP, 1, Thaumcraft.ResearchPageIInfRecipe.newInstance(Thaumcraft.InfusionRecipe.cast(recipes.get("FluxLamp"))));
//		
//		Object FLUXLAMP = Thaumcraft.ResearchItemStConst.newInstance("FLUXLAMP", "TECHNOMANCY", new AspectList().add(Aspect.ORDER, 5)
//				.add(Aspect.MECHANISM, 5).add(Aspect.EXCHANGE, 5).add(Aspect.WATER, 5), -4, -1, 2, new ItemStack(TMBlocks.fluxLamp));
//
//		FLUXLAMP = Thaumcraft.setPages.invoke(FLUXLAMP, Thaumcraft.ResearchPageArr.cast(pFLUXLAMP));
//		FLUXLAMP = Thaumcraft.setParents.invoke(FLUXLAMP, (Object)new String[] { "PROCESSOR" });
//		FLUXLAMP = Thaumcraft.setRound.invoke(FLUXLAMP);FLUXLAMP = Thaumcraft.registerResearchItem.invoke(FLUXLAMP);
//		
//		Object pELDRITCHCONSUMER= Array.newInstance(Thaumcraft.ResearchPage, 2);
//		Array.set(pELDRITCHCONSUMER, 0, Thaumcraft.ResearchPageString.newInstance("techno.research_page.ELDRITCHCONSUMER.1"));
//		Array.set(pELDRITCHCONSUMER, 1, Thaumcraft.ResearchPageIInfRecipe.newInstance(Thaumcraft.InfusionRecipe
//				.cast(recipes.get("EldritchConsumer"))));
//		
//		Object ELDRITCHCONSUMER = Thaumcraft.ResearchItemStConst.newInstance("ELDRITCHCONSUMER", "TECHNOMANCY", new AspectList()
//		.add(Aspect.MECHANISM,10).add(Aspect.EXCHANGE,10).add(Aspect.MOTION,10).add(Aspect.MINE,10), 3, -1, 8,
//		new ItemStack(TMBlocks.eldritchConsumer));
//
//		ELDRITCHCONSUMER = Thaumcraft.setPages.invoke(ELDRITCHCONSUMER, Thaumcraft.ResearchPageArr.cast(pELDRITCHCONSUMER));
//		ELDRITCHCONSUMER = Thaumcraft.setParents.invoke(ELDRITCHCONSUMER, (Object)new String[] { "TESLACOIL" });
//		ELDRITCHCONSUMER = Thaumcraft.setRound.invoke(ELDRITCHCONSUMER);
//		ELDRITCHCONSUMER = Thaumcraft.registerResearchItem.invoke(ELDRITCHCONSUMER);
		
		if(Ids.itemMaterial){
			new ResearchItem("TECHNOBASICS", "TECHNOMANCY", new AspectList(), 0, 0, 0,
				new ResourceLocation("technom", "textures/misc/technomancyBasics.png")).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.TECHNOBASICS.1"), new ResearchPage((CrucibleRecipe)recipes.get("NeutronizedMetal")),
				new ResearchPage((IRecipe)recipes.get("MagicCoil")), new ResearchPage((IRecipe)recipes.get("NeutronizedGear"))
				}).setAutoUnlock().setRound().setStub().registerResearchItem();
		}
		
		if(Ids.contEssentia && Ids.cosmeticOpaque && Ids.itemMaterial){
			new ResearchItem("QUANTUMJARS", "TECHNOMANCY", new AspectList().add(Aspect.ORDER, 5).add(Aspect.VOID, 5).add(Aspect.MAGIC, 5), 1, -2, 0,
				new ItemStack(TMBlocks.essentiaContainer)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.QUANTUMJARS.1"),
				new ResearchPage((IArcaneRecipe)recipes.get("QuantumJar")), new ResearchPage((IArcaneRecipe)recipes.get("QuantumGlass"))
				}).setParents(new String[] { "TECHNOBASICS" }).setSecondary().registerResearchItem();
		}
		
		if(Ids.itemMaterial){
			ArrayList<ResearchPage> pages = new ArrayList<ResearchPage>();
			Block iconBlock = null;
			if(Ids.dynEssentia){
				iconBlock = TMBlocks.essentiaDynamo;
				pages.add(new ResearchPage("techno.research_page.DYNAMO.1"));
				pages.add(new ResearchPage((IArcaneRecipe)recipes.get("EssentiaDynamo")));
			}
			if(Ids.dynNode){
				iconBlock = TMBlocks.nodeDynamo;
				pages.add(new ResearchPage("techno.research_page.DYNAMO.2"));
				pages.add(new ResearchPage((IArcaneRecipe)recipes.get("NodeDynamo")));
			}
			if(Ids.dynEssentia || Ids.dynNode){
				if(Ids.itemBoost) {
					pages.add(new ResearchPage("techno.research_page.DYNAMO.3"));
					pages.add(new ResearchPage(CraftingHandler.itemBoost));
				}
				new ResearchItem("DYNAMO", "TECHNOMANCY", new AspectList().add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.MAGIC, 5), 2, 2, 3,
						new ItemStack(iconBlock)).setPages(pages.toArray(new ResearchPage[pages.size()])).setParents(new String[] { "TECHNOBASICS" }).setSecondary()
						.registerResearchItem();
			}
		}
		
		if(Ids.itemMaterial && Ids.condenser && Ids.dynEssentia && Ids.dynNode && Ids.nodeGen){
			new ResearchItem("NODEGENERATOR", "TECHNOMANCY", new AspectList().add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.TAINT, 5)
				.add(Aspect.AURA, 5), 4, 3, 3, new ItemStack(TMBlocks.nodeGenerator)).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.NODEGENERATOR.1"), new ResearchPage("techno.research_page.NODEGENERATOR.2"),
				new ResearchPage("techno.research_page.NODEGENERATOR.3"), new ResearchPage((InfusionRecipe)recipes.get("NodeGenerator"))
				}).setParents(new String[] { "CONDENSER" }).setSpecial()
				.registerResearchItem();
		}
		
		if(Ids.pen){
			new ResearchItem("PEN", "TECHNOMANCY", new AspectList(), 4, -4, 0, new ItemStack(TMItems.itemPen)).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.PEN.1"), new ResearchPage((IRecipe)recipes.get("Pen")),
				new ResearchPage((IRecipe)recipes.get("PenCore"))}).setParents(new String[] {}).setAutoUnlock().registerResearchItem();
		}
		
		if(Ids.itemMaterial && Ids.processorTC && Ids.biomeMorpher){
			new ResearchItem("BIOMEMORPHER", "TECHNOMANCY", new AspectList().add(Aspect.EXCHANGE, 5).add(Aspect.TAINT, 5).add(Aspect.AURA, 5)
				.add(Aspect.EARTH, 5), -3, 0, 3, new ItemStack(TMBlocks.biomeMorpher)).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.BIOMEMORPHER.1"), new ResearchPage((IArcaneRecipe)recipes.get("BiomeMorpher"))})
				.setParents(new String[] { "PROCESSOR" }).setSecondary().registerResearchItem();
		}
		
		if(Ids.itemMaterial && Ids.contEssentia && Ids.cosmeticOpaque && Ids.wirelessCoil){
			new ResearchItem("TESLACOIL", "TECHNOMANCY", new AspectList().add(Aspect.EXCHANGE, 5).add(Aspect.ELDRITCH, 5).add(Aspect.MECHANISM, 5)
				.add(Aspect.AURA, 5), 3, -3, 3, new ItemStack(TMBlocks.teslaCoil)).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.TESLACOIL.1"), new ResearchPage((IArcaneRecipe)recipes.get("TeslaCoil")),
				new ResearchPage((IArcaneRecipe)recipes.get("CoilCoupler"))}).setParents(new String[] { "QUANTUMJARS"}).setRound()
				.registerResearchItem();
		}
		
		if(Ids.itemMaterial && Ids.processorTC && Ids.fluxLamp){
			new ResearchItem("FLUXLAMP", "TECHNOMANCY", new AspectList().add(Aspect.ORDER, 5).add(Aspect.MECHANISM, 5).add(Aspect.EXCHANGE, 5)
				.add(Aspect.WATER, 5), -4, -1, 2, new ItemStack(TMBlocks.fluxLamp)).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.FLUXLAMP.1"), new ResearchPage((InfusionRecipe)recipes.get("FluxLamp"))})
				.setParents(new String[] { "PROCESSOR"}).setRound().registerResearchItem();
		}
		
		if(Ids.itemMaterial && Ids.wandCores){
			new ResearchItem("ENERGIZEDWAND", "TECHNOMANCY", new AspectList().add(Aspect.TOOL, 5).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5)
				.add(Aspect.EXCHANGE, 5), -1, 2, 3, new ItemStack(TMItems.itemWandCores, 1, 0)).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.ELECTRICWAND.1"), new ResearchPage((InfusionRecipe)recipes.get("EnergizedWandRod"))})
				.setParents(new String[] { "TECHNOBASICS" }).setHidden().setItemTriggers(new ItemStack(Thaumcraft.itemWandRod, 1, 1))
				.registerResearchItem();
		}
		
		if(Ids.itemMaterial && Ids.processorTC && Ids.electricBellows){
			new ResearchItem("ELECTRICBELLOWS", "TECHNOMANCY", new AspectList().add(Aspect.ENERGY, 5).add(Aspect.AIR, 5).add(Aspect.FIRE, 5), -3, -2, 2,
				new ItemStack(TMBlocks.electricBellows)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.ELECTRICBELLOWS.1"),
				new ResearchPage((IArcaneRecipe)recipes.get("ElectricBellows"))}).setParents(new String[] {"PROCESSOR", "BELLOWS"})
				.registerResearchItem();
		}
		
		if(Ids.itemMaterial && Ids.dynEssentia && Ids.dynNode && Ids.condenser){
			new ResearchItem("CONDENSER", "TECHNOMANCY", new AspectList().add(Aspect.ENERGY, 5).add(Aspect.ORDER, 5).add(Aspect.EXCHANGE, 5), 2, 3, 3,
				new ItemStack(TMBlocks.condenserBlock)).setPages(new ResearchPage [] { new ResearchPage("techno.research_page.CONDENSER.1"),
				new ResearchPage((InfusionRecipe)recipes.get("Condenser"))}).setParents(new String[] {"DYNAMO"}).registerResearchItem();
		}
		
		if(Ids.itemMaterial && Ids.processorTC){
			new ResearchItem("PROCESSOR", "TECHNOMANCY", new AspectList().add(Aspect.FIRE, 5).add(Aspect.EXCHANGE, 5).add(Aspect.ENTROPY, 5), -2, -1, 3,
				new ItemStack(TMBlocks.processorTC)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.PROCESSOR.1"),
				new ResearchPage((IArcaneRecipe)recipes.get("Processor"))}).setParents(new String[] {"TECHNOBASICS"}).registerResearchItem();
		}
		
		if(Ids.itemMaterial && Ids.wirelessCoil && Ids.eldrichConsumer){
			new ResearchItem("ELDRITCHCONSUMER", "TECHNOMANCY", new AspectList().add(Aspect.MECHANISM,10).add(Aspect.EXCHANGE,10).add(Aspect.MOTION,10)
				.add(Aspect.MINE,10), 3, -1, 8, new ItemStack(TMBlocks.eldritchConsumer)).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.ELDRITCHCONSUMER.1"), new ResearchPage((InfusionRecipe)recipes.get("EldritchConsumer"))})
				.setParents(new String[] {"TESLACOIL"}).setRound().registerResearchItem();
		}
	}
}