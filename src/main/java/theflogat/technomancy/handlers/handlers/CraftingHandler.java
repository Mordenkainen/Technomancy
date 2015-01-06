package theflogat.technomancy.handlers.handlers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.ItemProcessedOre;
import theflogat.technomancy.common.items.TMItems;
import theflogat.technomancy.handlers.compat.Thaumcraft;
import theflogat.technomancy.handlers.compat.ThermalExpansion;
import theflogat.technomancy.handlers.util.CompareItemStack;
import theflogat.technomancy.handlers.util.ItemHelper;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingHandler {



	public static void initFurnaceRecipe() {
		try{
			for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
				FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedIron, 1, i), new ItemStack(Items.iron_ingot, 2 + i, 0), 100);
			}
			for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
				FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedGold, 1, i), new ItemStack(Items.gold_ingot, 2 + i, 0), 100);
			}
			try{
				for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
					FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedCopper, 1, i), new ItemStack(ThermalExpansion.ingotCopper.getItem(),
							2 + i, ThermalExpansion.ingotCopper.getItemDamage()), 100);
				}
				for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
					FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedTin, 1, i), new ItemStack(ThermalExpansion.ingotTin.getItem(),
							2 + i, ThermalExpansion.ingotTin.getItemDamage()), 100);
				}
				for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
					FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedSilver, 1, i), new ItemStack(ThermalExpansion.ingotSilver.getItem(),
							2 + i, ThermalExpansion.ingotSilver.getItemDamage()), 100);
				}
				for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
					FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedLead, 1, i), new ItemStack(ThermalExpansion.ingotLead.getItem(),
							2 + i, ThermalExpansion.ingotLead.getItemDamage()), 100);
				}
				for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
					FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedNickel, 1, i), new ItemStack(ThermalExpansion.ingotNickel.getItem(),
							2 + i, ThermalExpansion.ingotNickel.getItemDamage()), 100);
				}
			}catch(Exception e){e.printStackTrace();
			try{
				ItemStack ingotCopper = ItemHelper.getFirstItemStack(new CompareItemStack() {
					@Override
					public boolean isCorrectItemStack(ItemStack items) {
						return isOre("ingotCopper", items);
					}
				});

				ItemStack ingotTin = ItemHelper.getFirstItemStack(new CompareItemStack() {
					@Override
					public boolean isCorrectItemStack(ItemStack items) {
						return isOre("ingotTin", items);
					}
				});

				ItemStack ingotSilver = ItemHelper.getFirstItemStack(new CompareItemStack() {
					@Override
					public boolean isCorrectItemStack(ItemStack items) {
						return isOre("ingotSilver", items);
					}
				});

				ItemStack ingotLead = ItemHelper.getFirstItemStack(new CompareItemStack() {
					@Override
					public boolean isCorrectItemStack(ItemStack items) {
						return isOre("ingotLead", items);
					}
				});

				ItemStack ingotNickel = ItemHelper.getFirstItemStack(new CompareItemStack() {
					@Override
					public boolean isCorrectItemStack(ItemStack items) {
						return isOre("ingotNickel", items);
					}
				});
				if(ingotCopper!=null){
					for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
						FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedCopper, 1, i), new ItemStack(ThermalExpansion.ingotCopper.getItem(),
								2 + i, ThermalExpansion.ingotCopper.getItemDamage()), 100);
					}
				}if(ingotTin!=null){
					for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
						FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedTin, 1, i), new ItemStack(ThermalExpansion.ingotTin.getItem(),
								2 + i, ThermalExpansion.ingotTin.getItemDamage()), 100);
					}
				}if(ingotSilver!=null){
					for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
						FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedSilver, 1, i), new ItemStack(ThermalExpansion.ingotSilver.getItem(),
								2 + i, ThermalExpansion.ingotSilver.getItemDamage()), 100);
					}
				}if(ingotLead!=null){
					for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
						FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedLead, 1, i), new ItemStack(ThermalExpansion.ingotLead.getItem(),
								2 + i, ThermalExpansion.ingotLead.getItemDamage()), 100);
					}
				}if(ingotNickel!=null){
					for(int i = 0; i < ((ItemProcessedOre)TMItems.processedIron).itemIcon.length; i++) {
						FurnaceRecipes.smelting().func_151394_a(new ItemStack(TMItems.processedNickel, 1, i), new ItemStack(ThermalExpansion.ingotNickel.getItem(),
								2 + i, ThermalExpansion.ingotNickel.getItemDamage()), 100);
					}
				}
			}catch(Exception ex){ex.printStackTrace();}
			}
		}catch(Exception e){e.printStackTrace();}
	}



	public static void initThaumcraftRecipes() {
		try{
			if(ThermalExpansion.te){
				//Infusion Recipes
				ResearchHandler.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR",
						new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, 
						new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75),
						new ItemStack(TMBlocks.nodeDynamo, 1, 0), 
						new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Blocks.diamond_block),
					new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
					new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0),
					new ItemStack(ThermalExpansion.blockCell, 1, 3)}));
				ResearchHandler.recipes.put("FluxLamp", ThaumcraftApi.addInfusionCraftingRecipe("FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10,
						new AspectList().add(Aspect.MECHANISM,  45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45),
						new ItemStack(Thaumcraft.blockMetalDevice, 1, 7),
						new ItemStack[] { new ItemStack(Thaumcraft.itemShard, 1 , 4), new ItemStack(Thaumcraft.itemShard, 1, 4),
					new ItemStack(Items.bucket), new ItemStack(Items.bucket), new ItemStack(Items.emerald), new ItemStack(Items.emerald)}));
				ResearchHandler.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ENERGIZEDWAND",
						new ItemStack(TMItems.itemWandCores, 1, 0), 25,
						new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE,
								50),new ItemStack(Thaumcraft.itemWandRod, 1, 2),
								new ItemStack[] { new ItemStack((ThermalExpansion.capacitorResonant).getItem(), 1, 4), new ItemStack((ThermalExpansion.powerCoilElectrum)
										.getItem(), 1, 3), new ItemStack((ThermalExpansion.powerCoilSilver).getItem(), 1, 2),
										new ItemStack((ThermalExpansion.powerCoilGold).getItem(), 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1) }));
				ResearchHandler.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER",
						new ItemStack(TMBlocks.condenserBlock), 25,	new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50)
						.add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1 , 2), 
						new ItemStack[]{ThermalExpansion.frameMachineBasic,new ItemStack(TMItems.itemMaterial, 1, 1),new ItemStack(TMItems.itemMaterial,
								1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4)}));
				ResearchHandler.recipes.put("EldritchConsumer", ThaumcraftApi.addInfusionCraftingRecipe("ELDRITCHCONSUMER",
						new ItemStack(TMBlocks.eldritchConsumer, 1), 50,new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128)
						.add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1 , 9), 
						new ItemStack[]{new ItemStack(Items.diamond_pickaxe),ThermalExpansion.powerCoilGold,new ItemStack(Thaumcraft.blockJar),
					new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium)}));

				//Arcane Recipes
				ResearchHandler.recipes.put("QuantumGlass", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
						new ItemStack(TMBlocks.cosmeticOpaque,	4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), 
						new Object[] { "GG", "GG", 
					'G', new ItemStack(Blocks.glass)		}));
				ResearchHandler.recipes.put("QuantumJar", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
						new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10),
						new Object[] { "QNQ", "Q Q", "QQQ",
					'Q', new ItemStack(TMBlocks.cosmeticOpaque),
					'N', new ItemStack(TMItems.itemMaterial, 0)		}));
				ResearchHandler.recipes.put("NodeDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO",
						new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25)
						.add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10),
						new Object[] {" C ", "GIG", "IRI",
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'I', new ItemStack(TMItems.itemMaterial, 0),
					'R', new ItemStack(Items.redstone)				}));
				ResearchHandler.recipes.put("EssentiaDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0),
						new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25),
						new Object[] {" C ", "GIG", "IWI",
					'W', new ItemStack(Thaumcraft.blockJar, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'I', new ItemStack(Thaumcraft.blockTube, 1, 0),				}));	
				ResearchHandler.recipes.put("BiomeMorpher", ThaumcraftApi.addArcaneCraftingRecipe("BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher),
						new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), 
						new Object[] {" E ", "TOB", "GCG",
					'E', new ItemStack(Items.emerald),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 11),
					'O', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 0),
					'B', new ItemStack(Thaumcraft.blockCustomPlant, 1, 4),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)		}));
				ResearchHandler.recipes.put("TeslaCoil", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMBlocks.teslaCoil),
						new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.FIRE, 20)
						.add(Aspect.EARTH, 20),
						new Object[] {" N ", " C ", "TBT",
					'N', new ItemStack(TMItems.itemMaterial, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'B', new ItemStack(Thaumcraft.blockTube, 1, 4)		}));
				ResearchHandler.recipes.put("CoilCoupler", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMItems.itemMaterial, 1, 4),
						new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 15),
						new Object[] {" C ", " T ", " S ",
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'S', new ItemStack(Items.stick)	}));
				ResearchHandler.recipes.put("ElectricBellows", ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS",
						new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30),
						new Object[] {"TG ", "EBC", "TG ",
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'E', ThermalExpansion.frameCellBasic,
					'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				ResearchHandler.recipes.put("Processor", ThaumcraftApi.addArcaneCraftingRecipe("PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0),
						new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), 
						new Object[] {" A ", "BMB", "ICI",
					'A', new ItemStack(Items.redstone),
					'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6),
					'M', ThermalExpansion.frameMachineBasic,
					'I', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));

				//Crucible Recipes
				ResearchHandler.recipes.put("NeutronizedMetal", ThaumcraftApi.addCrucibleRecipe("THAUMIUM",	new ItemStack(TMItems.itemMaterial, 1, 0),
						new ItemStack(Thaumcraft.itemResource, 1, 2), new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENERGY, 2)));

				//Crafting Recipes
				ResearchHandler.recipes.put("MagicCoil", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 1), 
						new Object[] {"  N", " T ", "N  ", 
					'N', new ItemStack(Items.redstone), 
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2)	}));
				ResearchHandler.recipes.put("NeutronizedGear", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 2), 
						new Object[] {" T ", "TIT", " T ",
					'T', new ItemStack(TMItems.itemMaterial, 0),
					'I', new ItemStack(Items.iron_ingot)	}));
				ResearchHandler.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), 
						new Object[] {" NI", "NIN", "IN ",
					Character.valueOf('N'), "nuggetIron",
					Character.valueOf('I'), "dyeBlack"		}));
				ResearchHandler.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), 
						new Object[] {			" IC", "IPI", "NI ",
					'I', new ItemStack(Items.iron_ingot),
					'C', new ItemStack(Thaumcraft.itemWandCap, 1, 0),
					'P', new ItemStack(TMItems.itemMaterial, 1, 3),
					'N', new ItemStack(Items.gold_nugget)		}));

				//Wand Recipes
				ItemStack electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
				Thaumcraft.setCap.invoke(electric.getItem(), electric, WandCap.caps.get("thaumium"));
				Thaumcraft.setRod.invoke(electric.getItem(), electric, WandRod.rods.get("electric"));
				ThaumcraftApi.addArcaneCraftingRecipe("ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 60).add(Aspect.ORDER, 60)
						.add(Aspect.EARTH, 60).add(Aspect.FIRE, 60).add(Aspect.WATER, 60).add(Aspect.ENTROPY, 60), 
						new Object[]{"  C", " R ", "C  ", 
					Character.valueOf('C'), WandCap.caps.get("thaumium").getItem(), 
					Character.valueOf('R'), WandRod.rods.get("electric").getItem()});
				electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
				Thaumcraft.setCap.invoke(electric.getItem(), electric, WandCap.caps.get("void"));
				Thaumcraft.setRod.invoke(electric.getItem(), electric, WandRod.rods.get("electric"));
				ThaumcraftApi.addArcaneCraftingRecipe("ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 87).add(Aspect.ORDER, 87)
						.add(Aspect.EARTH, 87).add(Aspect.FIRE, 87).add(Aspect.WATER, 87).add(Aspect.ENTROPY, 87), 
						new Object[]{"  C", " R ", "C  ", 
					Character.valueOf('C'), WandCap.caps.get("void").getItem(), 
					Character.valueOf('R'), WandRod.rods.get("electric").getItem()});
			}else{
				//Infusion Recipes
				ResearchHandler.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR",
						new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, 
						new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75),
						new ItemStack(TMBlocks.nodeDynamo, 1, 0), 
						new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Blocks.diamond_block),
					new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
					new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0),
					new ItemStack(Blocks.gold_block, 1, 0)}));
				ResearchHandler.recipes.put("FluxLamp", ThaumcraftApi.addInfusionCraftingRecipe("FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10,
						new AspectList().add(Aspect.MECHANISM,  45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45),
						new ItemStack(Thaumcraft.blockMetalDevice, 1, 7),
						new ItemStack[] { new ItemStack(Thaumcraft.itemShard, 1 , 4), new ItemStack(Thaumcraft.itemShard, 1, 4),
					new ItemStack(Items.bucket), new ItemStack(Items.bucket), new ItemStack(Items.emerald), new ItemStack(Items.emerald)}));
				ResearchHandler.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ENERGIZEDWAND",
						new ItemStack(TMItems.itemWandCores, 1), 25,
						new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE,
								50),new ItemStack(Thaumcraft.itemWandRod, 1, 2), new ItemStack[] { new ItemStack(Blocks.redstone_block, 1),
					new ItemStack(Blocks.gold_block, 1, 193),new ItemStack(TMItems.itemMaterial, 1, 2),
					new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1) }));
				ResearchHandler.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER",
						new ItemStack(TMBlocks.condenserBlock), 25,	new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50)
						.add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1 , 2), 
						new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 2), new ItemStack(TMItems.itemMaterial, 1, 1),
					new ItemStack(TMItems.itemMaterial,1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
					new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4)}));

				ResearchHandler.recipes.put("EldritchConsumer", ThaumcraftApi.addInfusionCraftingRecipe("ELDRITCHCONSUMER",
						new ItemStack(TMBlocks.eldritchConsumer, 1), 50,new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128)
						.add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1 , 9), 
						new ItemStack[]{new ItemStack(Items.diamond_pickaxe),new ItemStack(Items.redstone),new ItemStack(Thaumcraft.blockJar),
					new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium)}));

				//Arcane Recipes
				ResearchHandler.recipes.put("QuantumGlass", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
						new ItemStack(TMBlocks.cosmeticOpaque,	4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), 
						new Object[] { "GG", "GG", 
					'G', new ItemStack(Blocks.glass)		}));
				ResearchHandler.recipes.put("QuantumJar", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
						new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10),
						new Object[] { "QNQ", "Q Q", "QQQ",
					'Q', new ItemStack(TMBlocks.cosmeticOpaque),
					'N', new ItemStack(TMItems.itemMaterial, 0)		}));
				ResearchHandler.recipes.put("NodeDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO",
						new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25)
						.add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10),
						new Object[] {" C ", "GIG", "IRI",
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'I', new ItemStack(TMItems.itemMaterial, 0),
					'R', new ItemStack(Items.redstone)				}));
				ResearchHandler.recipes.put("EssentiaDynamo", ThaumcraftApi.addArcaneCraftingRecipe( "DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0),
						new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25),
						new Object[] {" C ", "GIG", "IWI",
					'W', new ItemStack(Thaumcraft.blockJar, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'I', new ItemStack(Thaumcraft.blockTube, 1, 0),				}));	
				ResearchHandler.recipes.put("BiomeMorpher", ThaumcraftApi.addArcaneCraftingRecipe("BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher),
						new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), 
						new Object[] {" E ", "TOB", "GCG",
					'E', new ItemStack(Items.emerald),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 11),
					'O', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 0),
					'B', new ItemStack(Thaumcraft.blockCustomPlant, 1, 4),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)		}));
				ResearchHandler.recipes.put("TeslaCoil", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMBlocks.teslaCoil),
						new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.FIRE, 20)
						.add(Aspect.EARTH, 20),
						new Object[] {" N ", " C ", "TBT",
					'N', new ItemStack(TMItems.itemMaterial, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'B', new ItemStack(Thaumcraft.blockTube, 1, 4)		}));
				ResearchHandler.recipes.put("CoilCoupler", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMItems.itemMaterial, 1, 4),
						new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 15),
						new Object[] {" C ", " T ", " S ",
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'S', new ItemStack(Items.stick)	}));
				ResearchHandler.recipes.put("ElectricBellows", ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS",
						new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30),
						new Object[] {"TG ", "EBC", "TG ",
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'E', new ItemStack(Blocks.redstone_block, 1),
					'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				ResearchHandler.recipes.put("Processor", ThaumcraftApi.addArcaneCraftingRecipe("PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0),
						new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), 
						new Object[] {" A ", "BMB", "ICI",
					'A', new ItemStack(Items.redstone),
					'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6),
					'M', new ItemStack(Blocks.redstone_block, 1),
					'I', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));

				//Crucible Recipes
				ResearchHandler.recipes.put("NeutronizedMetal", ThaumcraftApi.addCrucibleRecipe("THAUMIUM",	new ItemStack(TMItems.itemMaterial, 1, 0),
						new ItemStack(Thaumcraft.itemResource, 1, 2), new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENERGY, 2)));

				//Crafting Recipes
				ResearchHandler.recipes.put("MagicCoil", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 1), 
						new Object[] {"  N", " T ", "N  ", 
					'N', new ItemStack(Items.redstone), 
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2)	}));
				ResearchHandler.recipes.put("NeutronizedGear", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 2), 
						new Object[] {" T ", "TIT", " T ",
					'T', new ItemStack(TMItems.itemMaterial, 0),
					'I', new ItemStack(Items.iron_ingot)	}));
				ResearchHandler.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), 
						new Object[] {" NI", "NIN", "IN ",
					Character.valueOf('N'), "nuggetIron",
					Character.valueOf('I'), "dyeBlack"		}));
				ResearchHandler.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), 
						new Object[] {			" IC", "IPI", "NI ",
					'I', new ItemStack(Items.iron_ingot),
					'C', new ItemStack(Thaumcraft.itemWandCap, 1, 0),
					'P', new ItemStack(TMItems.itemMaterial, 1, 3),
					'N', new ItemStack(Items.gold_nugget)		}));

				//Wand Recipes
				ItemStack electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
				Thaumcraft.setCap.invoke(electric.getItem(), electric, WandCap.caps.get("thaumium"));
				Thaumcraft.setRod.invoke(electric.getItem(), electric, WandRod.rods.get("electric"));
				ThaumcraftApi.addArcaneCraftingRecipe("ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 60).add(Aspect.ORDER, 60)
						.add(Aspect.EARTH, 60).add(Aspect.FIRE, 60).add(Aspect.WATER, 60).add(Aspect.ENTROPY, 60), 
						new Object[]{"  C", " R ", "C  ", 
					Character.valueOf('C'), WandCap.caps.get("thaumium").getItem(), 
					Character.valueOf('R'), WandRod.rods.get("electric").getItem()});
				electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
				Thaumcraft.setCap.invoke(electric.getItem(), electric, WandCap.caps.get("void"));
				Thaumcraft.setRod.invoke(electric.getItem(), electric, WandRod.rods.get("electric"));
				ThaumcraftApi.addArcaneCraftingRecipe("ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 87).add(Aspect.ORDER, 87)
						.add(Aspect.EARTH, 87).add(Aspect.FIRE, 87).add(Aspect.WATER, 87).add(Aspect.ENTROPY, 87), 
						new Object[]{"  C", " R ", "C  ", 
					Character.valueOf('C'), WandCap.caps.get("void").getItem(), 
					Character.valueOf('R'), WandRod.rods.get("electric").getItem()});
			}
		}catch(Exception e){e.printStackTrace();}
	}

	public static void initBloodMagicRecipes() {
		try{
			if(ThermalExpansion.te){
				//Altar Recipes
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMBlocks.bloodDynamo), new ItemStack(ThermalExpansion.blockDynamo), 2,
						10000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 0), new ItemStack(Items.iron_ingot), 1, 1000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 1), ThermalExpansion.powerCoilGold, 1, 1000, 100, 100, false);

				//Normal Recipes
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.bloodFabricator), 
						new Object[] {" T ", "IMI", "CAC",
					'T', new ItemStack(ThermalExpansion.blockTank, 1, 3),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'M', ThermalExpansion.frameMachineBasic,
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'A', ThermalExpansion.frameTesseractFull});
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.processorBM),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.frameMachineBasic,
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'B', getBlock(1, "bloodRune", 0),
					'A', new ItemStack(Items.redstone)});
			} else {
				//Altar Recipes
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMBlocks.bloodDynamo), new ItemStack(Blocks.redstone_block, 1), 2,
						10000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 0), new ItemStack(Items.iron_ingot, 1), 1, 1000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 1), new ItemStack(Items.redstone, 1),
						1, 1000, 100, 100, false);

				//Normal Recipes
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.bloodFabricator), 
						new Object[] {" T ", "IMI", "CAC",
					'T', new ItemStack(Blocks.glass, 1, 0),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'M', new ItemStack(Blocks.redstone_block, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'A', new ItemStack(Items.ender_eye, 1, 0)});
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.processorBM),
						new Object[] {" A ", "BMB", "ICI",
					'M', new ItemStack(Blocks.redstone_block, 1, 0),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'B', getBlock(1, "bloodRune", 0),
					'A', new ItemStack(Items.redstone)});
			}
		}catch(Exception e){e.printStackTrace();}
	}



	public static void initBotaniaRecipes() {
		try{
			if(ThermalExpansion.te){
				//ManaInfusion
				RecipeManaInfusion manaCoilRecipe = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0), ThermalExpansion.powerCoilSilver, 3000);		

				//Normal Recipes
				oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1),
						new Object[] {" M ", "MIM", " M ",
					'M', "ingotManasteel",
					'I', "ingotIron"		});
				oreDictRecipe(new ItemStack(TMBlocks.flowerDynamo), 
						new Object[] {" C ", "GIG", "IWI",
					'W', new ItemStack(Items.redstone),
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'G', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel"				});
				oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), 
						new Object[] {"CDC", "IDI", " P ",
					'C', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel",
					'D', "manaDiamond",
					'P', ThermalExpansion.frameTesseractFull			});
				oreDictRecipe(new ItemStack(TMBlocks.processorBO),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.frameMachineBasic,
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Items.redstone)				});
			}else{
				//ManaInfusion
				RecipeManaInfusion manaCoilRecipe = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0),
						new ItemStack(Items.redstone), 3000);		

				//Normal Recipes
				oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1),
						new Object[] {" M ", "MIM", " M ",
					'M', "ingotManasteel",
					'I', "ingotIron"		});
				oreDictRecipe(new ItemStack(TMBlocks.flowerDynamo), 
						new Object[] {" C ", "GIG", "IWI",
					'W', new ItemStack(Items.redstone),
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'G', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel"				});
				oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), 
						new Object[] {"CDC", "IDI", " P ",
					'C', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel",
					'D', "manaDiamond",
					'P', new ItemStack(Items.ender_eye, 1, 0)			});
				oreDictRecipe(new ItemStack(TMBlocks.processorBO),
						new Object[] {" A ", "BMB", "ICI",
					'M', new ItemStack(Items.redstone),
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Items.redstone)				});
			}
		}catch(Exception e){e.printStackTrace();}
	}

	static IRecipe oreDictRecipe(ItemStack res, Object[] params) {
		IRecipe rec = new ShapedOreRecipe(res, params);
		CraftingManager.getInstance().getRecipeList().add(rec);
		return rec;
	}


	static ItemStack getItem(int ID, String itemString, int meta) {
		ItemStack stack = null;
		try {
			String itemClass = null;
			switch(ID) {
			case 0:
				itemClass = "vazkii.botania.common.item.ModItems";
			case 1:
				itemClass = "WayofTime.alchemicalWizardry.ModItems";	    	
			}
			Object obj = Class.forName(itemClass).getField(itemString).get(null);
			if ((obj instanceof Item)) {
				stack = new ItemStack((Item)obj, 1, meta);
			}else if ((obj instanceof ItemStack)) {
				stack = (ItemStack)obj;
			}
		}catch (Exception e){
			FMLLog.warning("[Technomancy] Could not retrieve item identified by: " + itemString, new Object[0]);
		}
		return stack;
	}

	static ItemStack getBlock(int ID, String itemString, int meta) {
		ItemStack stack = null;
		String itemClass = null;
		try{
			switch(ID) {
			case 0:
				itemClass = "vazkii.botania.common.block.ModBlocks";
			case 1:
				itemClass = "WayofTime.alchemicalWizardry.ModBlocks";
			}
			Object obj = Class.forName(itemClass).getField(itemString).get(null);
			if ((obj instanceof Block)) {
				stack = new ItemStack((Block)obj, 1, meta);
			}else if ((obj instanceof ItemStack)) {
				stack = (ItemStack)obj;
			}
		}catch (Exception ex) {
			FMLLog.warning("[Technomancy] Could not retrieve block identified by: " + itemString + " at: " + itemClass, new Object[0]);
		}
		return stack;
	}
}