package theflogat.technomancy.lib.compat.thaumcraft;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.ItemWandCasting;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.lib.handlers.CraftingHandler;

public class TechnoResearch {
	
	public static HashMap<String, Object> recipes = new HashMap<String, Object>();
	
	public static void init() {
		try{
			ResearchCategories.registerCategory("TECHNOMANCY", new ResourceLocation("technom", "textures/misc/technomancyCategory.png"),
					new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));
	
			initThaumcraft();
		}catch(Exception e){e.printStackTrace();}
	}
	
	private static void initThaumcraft(){
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
				pages.add(new ResearchPage("techno.research_page.DYNAMO.2"));
				pages.add(new ResearchPage((IArcaneRecipe)recipes.get("EssentiaDynamo")));
			}
			if(Ids.dynNode){
				iconBlock = TMBlocks.nodeDynamo;
				pages.add(new ResearchPage("techno.research_page.DYNAMO.3"));
				pages.add(new ResearchPage((IArcaneRecipe)recipes.get("NodeDynamo")));
			}
			if(Ids.dynEssentia || Ids.dynNode){
				if(Ids.itemBoost) {
					pages.add(new ResearchPage("techno.research_page.DYNAMO.4"));
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
				new ResearchPage(CraftingHandler.coilCoupler)}).setParents(new String[] { "QUANTUMJARS"}).setRound()
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
				.setParents(new String[] { "TECHNOBASICS" }).setHidden().setItemTriggers(new ItemStack(Thaumcraft.itemWandRod, 1, 2))
				.registerResearchItem();
			if(Ids.scepter) {
				ItemStack scepter = new ItemStack(TMItems.itemTechnoturgeScepter, 1, 73);
				scepter.setTagInfo("sceptre", new NBTTagByte((byte)1));
				((ItemWandCasting)scepter.getItem()).setCap(scepter, WandCap.caps.get("thaumium"));
				((ItemWandCasting)scepter.getItem()).setRod(scepter, WandRod.rods.get("technoturge"));
				new ResearchItem("TECHNOTURGESCEPTER", "TECHNOMANCY", new AspectList().add(Aspect.TOOL, 5).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5)
						.add(Aspect.EXCHANGE, 5), -1, 3, 3, scepter).setPages(new ResearchPage[] {
						new ResearchPage("techno.research_page.TECHNOTURGESCEPTER.1"), new ResearchPage((InfusionRecipe)recipes.get("TechnoturgeScepterRod")),
						new ResearchPage((IArcaneRecipe)recipes.get("TechnoturgeScepterRodT")), new ResearchPage((IArcaneRecipe)recipes.get("TechnoturgeScepterRodV"))})
						.setParents(new String[] { "ENERGIZEDWAND" }).setConcealed().registerResearchItem();
			}
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
				.add(Aspect.MINE,10), 3, -1, 3, new ItemStack(TMBlocks.eldritchConsumer)).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.ELDRITCHCONSUMER.1"), new ResearchPage((InfusionRecipe)recipes.get("EldritchConsumer"))})
				.setParents(new String[] {"TESLACOIL"}).setRound().registerResearchItem();
		}
		
		if(Ids.advDeconTable && Ids.itemMaterial){
			new ResearchItem("ADVDECONTABLE", "TECHNOMANCY", new AspectList().add(Aspect.MECHANISM,10).add(Aspect.EXCHANGE,10).add(Aspect.MAGIC,10)
				.add(Aspect.TOOL,10).add(Aspect.CRAFT, 10), 1, -1, 3, new ItemStack(TMBlocks.advDeconTable)).setPages(new ResearchPage[] {
				new ResearchPage("techno.research_page.ADVDECONTABLE.1"), new ResearchPage((InfusionRecipe)recipes.get("AdvDeconTable"))})
				.setParents(new String[] {"TECHNOBASICS", "DECONSTRUCTOR"}).setRound().registerResearchItem();
		}
	}
}