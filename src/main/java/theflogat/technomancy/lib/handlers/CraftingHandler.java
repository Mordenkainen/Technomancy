package theflogat.technomancy.lib.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.technom.ItemProcessedOre;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.lib.compat.ThermalExpansion;
import theflogat.technomancy.util.Ore;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingHandler {

	public static void initFurnaceRecipes() {
		for (Ore ore : Ore.ores) {
			if (ore.getEnabled()) {
				for(int i = 0; i < ItemProcessedOre.MAXSTAGE; i++) {
					FurnaceRecipes.smelting().func_151394_a(new ItemStack(ore.getPure(), 1, i), new ItemStack(ore.ingot().getItem(), ore.getIngotsPerStage(i), ore.ingot().getItemDamage()), 100);
				}
			}
		}
	}

	public static void initThaumcraftRecipes() {
		try{
			if(CompatibilityHandler.te){
				//Infusion Recipes
				if(Ids.nodeGen)
					ResearchHandler.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR",
							new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, 
							new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75),
							new ItemStack(TMBlocks.nodeDynamo, 1, 0), 
							new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Blocks.diamond_block),
						new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
						new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0),
						new ItemStack(ThermalExpansion.blockCell, 1, 3)}));
				if(Ids.fluxLamp)
					ResearchHandler.recipes.put("FluxLamp", ThaumcraftApi.addInfusionCraftingRecipe("FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10,
							new AspectList().add(Aspect.MECHANISM,  45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45),
							new ItemStack(Thaumcraft.blockMetalDevice, 1, 7),
							new ItemStack[] { new ItemStack(Thaumcraft.itemShard, 1 , 4), new ItemStack(Thaumcraft.itemShard, 1, 4),
						new ItemStack(Items.bucket), new ItemStack(Items.bucket), new ItemStack(Items.emerald), new ItemStack(Items.emerald)}));
				if(Ids.wandCores)
					ResearchHandler.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ENERGIZEDWAND",
							new ItemStack(TMItems.itemWandCores, 1, 0), 8,
							new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE,
									50),new ItemStack(Thaumcraft.itemWandRod, 1, 2),
									new ItemStack[] { new ItemStack((ThermalExpansion.capacitorResonant).getItem(), 1, 4), new ItemStack((ThermalExpansion.powerCoilElectrum)
											.getItem(), 1, 3), new ItemStack((ThermalExpansion.powerCoilSilver).getItem(), 1, 2),
											new ItemStack((ThermalExpansion.powerCoilGold).getItem(), 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1) }));
				if(Ids.condenser)
					ResearchHandler.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER",
							new ItemStack(TMBlocks.condenserBlock), 8,	new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50)
							.add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1 , 2), 
							new ItemStack[]{ThermalExpansion.frameMachineBasic,new ItemStack(TMItems.itemMaterial, 1, 1),new ItemStack(TMItems.itemMaterial,
									1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4)}));
				if(Ids.eldrichConsumer)
					ResearchHandler.recipes.put("EldritchConsumer", ThaumcraftApi.addInfusionCraftingRecipe("ELDRITCHCONSUMER",
							new ItemStack(TMBlocks.eldritchConsumer, 1), 16,new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128)
							.add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1 , 9), 
							new ItemStack[]{new ItemStack(Items.diamond_pickaxe),ThermalExpansion.powerCoilGold,new ItemStack(Thaumcraft.blockJar),
						new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium)}));

				//Arcane Recipes
				if(Ids.cosmeticOpaque)
					ResearchHandler.recipes.put("QuantumGlass", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
							new ItemStack(TMBlocks.cosmeticOpaque,	4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), 
							new Object[] { "GG", "GG", 
						'G', new ItemStack(Blocks.glass)		}));
				if(Ids.contEssentia)
					ResearchHandler.recipes.put("QuantumJar", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
							new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10),
							new Object[] { "QNQ", "Q Q", "QQQ",
						'Q', new ItemStack(TMBlocks.cosmeticOpaque),
						'N', new ItemStack(TMItems.itemMaterial, 0)		}));
				if(Ids.dynNode)
					ResearchHandler.recipes.put("NodeDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO",
							new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25)
							.add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10),
							new Object[] {" C ", "GIG", "IRI",
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'I', new ItemStack(TMItems.itemMaterial, 0),
						'R', new ItemStack(Items.redstone)				}));
				if(Ids.dynEssentia)
					ResearchHandler.recipes.put("EssentiaDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0),
							new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25),
							new Object[] {" C ", "GIG", "IWI",
						'W', new ItemStack(Thaumcraft.blockJar, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'I', new ItemStack(Thaumcraft.blockTube, 1, 0),				}));
				if(Ids.biomeMorpher)
					ResearchHandler.recipes.put("BiomeMorpher", ThaumcraftApi.addArcaneCraftingRecipe("BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher),
							new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), 
							new Object[] {" E ", "TOB", "GCG",
						'E', new ItemStack(Items.emerald),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 11),
						'O', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 0),
						'B', new ItemStack(Thaumcraft.blockCustomPlant, 1, 4),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)		}));
				if(Ids.wirelessCoil){
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
				}
				if(Ids.electricBellows)
					ResearchHandler.recipes.put("ElectricBellows", ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS",
							new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30),
							new Object[] {"TG ", "EBC", "TG ",
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'E', ThermalExpansion.frameCellBasic,
						'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				if(Ids.processorTC)
					ResearchHandler.recipes.put("Processor", ThaumcraftApi.addArcaneCraftingRecipe("PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0),
							new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), 
							new Object[] {" A ", "BMB", "ICI",
						'A', new ItemStack(Items.redstone),
						'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6),
						'M', ThermalExpansion.frameMachineBasic,
						'I', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));

				//Crucible Recipes
				if(Ids.itemMaterial){
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
				}
				if(Ids.pen)
					ResearchHandler.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), 
							new Object[] {" NI", "NIN", "IN ",
						Character.valueOf('N'), "nuggetIron",
						Character.valueOf('I'), "dyeBlack"		}));
				if(Ids.pen)
					ResearchHandler.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), 
							new Object[] {			" IC", "IPI", "NI ",
						'I', new ItemStack(Items.iron_ingot),
						'C', new ItemStack(Thaumcraft.itemWandCap, 1, 0),
						'P', new ItemStack(TMItems.itemMaterial, 1, 3),
						'N', new ItemStack(Items.gold_nugget)		}));

				//Wand Recipes
				if(Ids.wandCores){
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
			}else{
				//Infusion Recipes
				if(Ids.nodeGen)
					ResearchHandler.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR",
							new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, 
							new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75),
							new ItemStack(TMBlocks.nodeDynamo, 1, 0), 
							new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Blocks.diamond_block),
						new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
						new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0),
						new ItemStack(Blocks.gold_block, 1, 0)}));
				if(Ids.fluxLamp)
					ResearchHandler.recipes.put("FluxLamp", ThaumcraftApi.addInfusionCraftingRecipe("FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10,
							new AspectList().add(Aspect.MECHANISM,  45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45),
							new ItemStack(Thaumcraft.blockMetalDevice, 1, 7),
							new ItemStack[] { new ItemStack(Thaumcraft.itemShard, 1 , 4), new ItemStack(Thaumcraft.itemShard, 1, 4),
						new ItemStack(Items.bucket), new ItemStack(Items.bucket), new ItemStack(Items.emerald), new ItemStack(Items.emerald)}));
				if(Ids.wandCores)
					ResearchHandler.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ENERGIZEDWAND",
							new ItemStack(TMItems.itemWandCores, 1), 8,
							new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE,
									50),new ItemStack(Thaumcraft.itemWandRod, 1, 2), new ItemStack[] { new ItemStack(Blocks.redstone_block, 1),
						new ItemStack(Blocks.gold_block, 1, 193),new ItemStack(TMItems.itemMaterial, 1, 2),
						new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1) }));
				if(Ids.condenser)
					ResearchHandler.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER",
							new ItemStack(TMBlocks.condenserBlock), 8,	new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50)
							.add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1 , 2), 
							new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 2), new ItemStack(TMItems.itemMaterial, 1, 1),
						new ItemStack(TMItems.itemMaterial,1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
						new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4)}));
				if(Ids.eldrichConsumer)
					ResearchHandler.recipes.put("EldritchConsumer", ThaumcraftApi.addInfusionCraftingRecipe("ELDRITCHCONSUMER",
							new ItemStack(TMBlocks.eldritchConsumer, 1), 16,new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128)
							.add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1 , 9), 
							new ItemStack[]{new ItemStack(Items.diamond_pickaxe),new ItemStack(Items.redstone),new ItemStack(Thaumcraft.blockJar),
						new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium)}));

				//Arcane Recipes
				if(Ids.cosmeticOpaque)
					ResearchHandler.recipes.put("QuantumGlass", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
							new ItemStack(TMBlocks.cosmeticOpaque,	4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), 
							new Object[] { "GG", "GG", 
						'G', new ItemStack(Blocks.glass)		}));
				if(Ids.contEssentia)
					ResearchHandler.recipes.put("QuantumJar", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
							new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10),
							new Object[] { "QNQ", "Q Q", "QQQ",
						'Q', new ItemStack(TMBlocks.cosmeticOpaque),
						'N', new ItemStack(TMItems.itemMaterial, 0)		}));
				if(Ids.dynNode)
					ResearchHandler.recipes.put("NodeDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO",
							new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25)
							.add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10),
							new Object[] {" C ", "GIG", "IRI",
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'I', new ItemStack(TMItems.itemMaterial, 0),
						'R', new ItemStack(Items.redstone)				}));
				if(Ids.dynEssentia)
					ResearchHandler.recipes.put("EssentiaDynamo", ThaumcraftApi.addArcaneCraftingRecipe( "DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0),
							new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25),
							new Object[] {" C ", "GIG", "IWI",
						'W', new ItemStack(Thaumcraft.blockJar, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'I', new ItemStack(Thaumcraft.blockTube, 1, 0),				}));
				if(Ids.biomeMorpher)
					ResearchHandler.recipes.put("BiomeMorpher", ThaumcraftApi.addArcaneCraftingRecipe("BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher),
							new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), 
							new Object[] {" E ", "TOB", "GCG",
						'E', new ItemStack(Items.emerald),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 11),
						'O', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 0),
						'B', new ItemStack(Thaumcraft.blockCustomPlant, 1, 4),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)		}));
				if(Ids.wirelessCoil)
					ResearchHandler.recipes.put("TeslaCoil", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMBlocks.teslaCoil),
							new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.FIRE, 20)
							.add(Aspect.EARTH, 20),
							new Object[] {" N ", " C ", "TBT",
						'N', new ItemStack(TMItems.itemMaterial, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'B', new ItemStack(Thaumcraft.blockTube, 1, 4)		}));
				if(Ids.itemMaterial)
					ResearchHandler.recipes.put("CoilCoupler", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMItems.itemMaterial, 1, 4),
							new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 15),
							new Object[] {" C ", " T ", " S ",
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'S', new ItemStack(Items.stick)	}));
				if(Ids.electricBellows)
					ResearchHandler.recipes.put("ElectricBellows", ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS",
							new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30),
							new Object[] {"TG ", "EBC", "TG ",
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'E', new ItemStack(Blocks.redstone_block, 1),
						'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				if(Ids.processorTC)
					ResearchHandler.recipes.put("Processor", ThaumcraftApi.addArcaneCraftingRecipe("PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0),
							new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), 
							new Object[] {" A ", "BMB", "ICI",
						'A', new ItemStack(Items.redstone),
						'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6),
						'M', new ItemStack(Blocks.redstone_block, 1),
						'I', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));

				//Crucible Recipes
				if(Ids.itemMaterial){
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
				}
				if(Ids.pen)
					ResearchHandler.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), 
							new Object[] {" NI", "NIN", "IN ",
						Character.valueOf('N'), "nuggetIron",
						Character.valueOf('I'), "dyeBlack"		}));
				if(Ids.pen)
					ResearchHandler.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), 
							new Object[] {			" IC", "IPI", "NI ",
						'I', new ItemStack(Items.iron_ingot),
						'C', new ItemStack(Thaumcraft.itemWandCap, 1, 0),
						'P', new ItemStack(TMItems.itemMaterial, 1, 3),
						'N', new ItemStack(Items.gold_nugget)		}));

				//Wand Recipes
				if(Ids.wandCores){
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
			}
		}catch(Exception e){e.printStackTrace();}
	}

	@SuppressWarnings("unchecked")
	private static IRecipe oreDictRecipe(ItemStack res, Object[] params) {
		IRecipe rec = new ShapedOreRecipe(res, params);
		CraftingManager.getInstance().getRecipeList().add(rec);
		return rec;
	}

	public static void initTechnomancyRecipes() {
		if(Ids.itemBoost)
			GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemBoost), new Object[]{
				" R ",
				"RNR",
				" G ",
				'R', Items.redstone, 'N', Items.quartz, 'G', Items.gold_ingot
			});

		if(Ids.crystalBlock){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 0), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeBlack"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 1), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeWhite"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 2), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeRed"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 3), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeGreen"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 4), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeBlue"
			}));
		}

		if(Ids.catalyst){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 0), new Object[]{
				"BRA",
				"RGR",
				"ARB",
				'G', Blocks.gold_block, 'R', "dyeBlack", 'B', Blocks.obsidian, 'A', Items.ender_pearl
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 1), new Object[]{
				"BRA",
				"RGR",
				"ARB",
				'G', Blocks.gold_block, 'R', "dyeWhite", 'B', Items.golden_apple, 'A', Items.golden_carrot
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 2), new Object[]{
				"BRB",
				"RGR",
				"BRB",
				'G', Blocks.gold_block, 'R', "dyeRed", 'B', Items.blaze_rod
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 4), new Object[]{
				"ARB",
				"RGR",
				"BRA",
				'G', Blocks.gold_block, 'R', "dyeBlue", 'B', Blocks.clay, 'A', Items.fish
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 3), new Object[]{
				"BRB",
				"RGR",
				"BRB",
				'G', Blocks.gold_block, 'R', "dyeGreen", 'B', Blocks.grass
			}));
		}
	}
}