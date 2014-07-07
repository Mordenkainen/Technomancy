package democretes.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import democretes.blocks.TMBlocks;
import democretes.compat.Thaumcraft;
import democretes.compat.ThermalExpansion;
import democretes.items.TMItems;

public class CraftingHandler {

	public static void initFurnaceRecipe() {
		for(int i = 0; i < 5; i++) {
			FurnaceRecipes.smelting().addSmelting(TMItems.processedIron.itemID, i, new ItemStack(Item.ingotIron, 2 + i, 0), 100);
		}
		for(int i = 0; i < 5; i++) {
			FurnaceRecipes.smelting().addSmelting(TMItems.processedGold.itemID, i, new ItemStack(Item.ingotGold, 2 + i, 0), 100);
		}
		try{
			for(int i = 0; i < 5; i++) {
				FurnaceRecipes.smelting().addSmelting(TMItems.processedCopper.itemID, i, new ItemStack(ThermalExpansion.ingotCopper.getItem(),
						2 + i, ThermalExpansion.ingotCopper.getItemDamage()), 100);
			}
			for(int i = 0; i < 5; i++) {
				FurnaceRecipes.smelting().addSmelting(TMItems.processedTin.itemID, i, new ItemStack(ThermalExpansion.ingotTin.getItem(),
						2 + i, ThermalExpansion.ingotTin.getItemDamage()), 100);
			}
			for(int i = 0; i < 5; i++) {
				FurnaceRecipes.smelting().addSmelting(TMItems.processedSilver.itemID, i, new ItemStack(ThermalExpansion.ingotSilver.getItem(),
						2 + i, ThermalExpansion.ingotSilver.getItemDamage()), 100);
			}
			for(int i = 0; i < 5; i++) {
				FurnaceRecipes.smelting().addSmelting(TMItems.processedLead.itemID, i, new ItemStack(ThermalExpansion.ingotLead.getItem(),
						2 + i, ThermalExpansion.ingotLead.getItemDamage()), 100);
			}
			for(int i = 0; i < 5; i++) {
				FurnaceRecipes.smelting().addSmelting(TMItems.processedNickel.itemID, i, new ItemStack(ThermalExpansion.ingotNickel.getItem(),
						2 + i, ThermalExpansion.ingotNickel.getItemDamage()), 100);
			}
		}catch(Exception e){e.printStackTrace();}
	}

	public static void initThaumcraftRecipes() {
		try{
			if(ThermalExpansion.te){
				//Infusion Recipes
				ResearchHandler.recipes.put("NodeGenerator", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "NODEGENERATOR",
						new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, 
						new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75),
						new ItemStack(TMBlocks.nodeDynamo, 1, 0), 
						new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Block.blockDiamond),
					new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
					new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0),
					new ItemStack(ThermalExpansion.blockEnergyCell, 1, 3)}));
				ResearchHandler.recipes.put("FluxLamp", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10,
						new AspectList().add(Aspect.MECHANISM,  45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45),
						new ItemStack(Thaumcraft.blockMetalDevice, 1, 7),
						new ItemStack[] { new ItemStack(Thaumcraft.itemShard, 1 , 4), new ItemStack(Thaumcraft.itemShard, 1, 4),
					new ItemStack(Item.bucketEmpty), new ItemStack(Item.bucketEmpty), new ItemStack(Item.emerald), new ItemStack(Item.emerald)}));
				ResearchHandler.recipes.put("EnergizedWandRod", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "ENERGIZEDWAND",
						new ItemStack(TMItems.itemWandCores, 1, 0), 25,
						new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE,
								50),new ItemStack(Thaumcraft.itemWandRod, 1, 1),
								new ItemStack[] { new ItemStack((ThermalExpansion.capacitorResonant).getItem(), 1, 4), new ItemStack((ThermalExpansion.powerCoilElectrum)
										.getItem(), 1, 193), new ItemStack((ThermalExpansion.powerCoilSilver).getItem(), 1, 194),
										new ItemStack((ThermalExpansion.powerCoilGold).getItem(), 1, 195), new ItemStack(TMItems.itemMaterial, 1, 1) }));
				ResearchHandler.recipes.put("Condenser", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "CONDENSER",
						new ItemStack(TMBlocks.condenserBlock), 25,	new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50)
						.add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1 , 2), 
						new ItemStack[]{ThermalExpansion.machineFrame,new ItemStack(TMItems.itemMaterial, 1, 1),new ItemStack(TMItems.itemMaterial,
								1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4)}));
				ResearchHandler.recipes.put("EldritchConsumer", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "ELDRITCHCONSUMER",
						new ItemStack(TMBlocks.eldritchConsumer, 1), 50,new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128)
						.add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1 , 9), 
						new ItemStack[]{new ItemStack(Item.pickaxeDiamond),ThermalExpansion.powerCoilGold,new ItemStack(Thaumcraft.blockJar),
						new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium)}));

				//Arcane Recipes
				ResearchHandler.recipes.put("QuantumGlass", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "QUANTUMJARS",
						new ItemStack(TMBlocks.cosmeticOpaque,	4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), 
						new Object[] { "GG", "GG", 
					'G', new ItemStack(Block.glass)		}));
				ResearchHandler.recipes.put("QuantumJar", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "QUANTUMJARS",
						new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10),
						new Object[] { "QNQ", "Q Q", "QQQ",
					'Q', new ItemStack(TMBlocks.cosmeticOpaque),
					'N', new ItemStack(TMItems.itemMaterial, 0)		}));
				ResearchHandler.recipes.put("NodeDynamo", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "DYNAMO",
						new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25)
						.add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10),
						new Object[] {" C ", "GIG", "IRI",
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'I', new ItemStack(TMItems.itemMaterial, 0),
					'R', new ItemStack(Item.redstone)				}));
				ResearchHandler.recipes.put("EssentiaDynamo", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0),
						new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25),
						new Object[] {" C ", "GIG", "IWI",
					'W', new ItemStack(Thaumcraft.blockJar, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'I', new ItemStack(Thaumcraft.blockTube, 1, 0),				}));	
				ResearchHandler.recipes.put("BiomeMorpher", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher),
						new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), 
						new Object[] {" E ", "TOB", "GCG",
					'E', new ItemStack(Item.emerald),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 11),
					'O', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 0),
					'B', new ItemStack(Thaumcraft.blockCustomPlant, 1, 4),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)		}));
				ResearchHandler.recipes.put("TeslaCoil", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "TESLACOIL", new ItemStack(TMBlocks.teslaCoil),
						new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.FIRE, 20)
						.add(Aspect.EARTH, 20),
						new Object[] {" N ", " C ", "TBT",
					'N', new ItemStack(TMItems.itemMaterial, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'B', new ItemStack(Thaumcraft.blockTube, 1, 4)		}));
				ResearchHandler.recipes.put("CoilCoupler", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "TESLACOIL", new ItemStack(TMItems.itemMaterial, 1, 4),
						new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 15),
						new Object[] {" C ", " T ", " S ",
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'S', new ItemStack(Item.stick)	}));
				ResearchHandler.recipes.put("ElectricBellows", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "ELECTRICBELLOWS",
						new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30),
						new Object[] {"TG ", "EBC", "TG ",
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'E', ThermalExpansion.cellBasicFrame,
					'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				ResearchHandler.recipes.put("Processor", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0),
						new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), 
						new Object[] {" A ", "BMB", "ICI",
					'A', new ItemStack(Item.redstone),
					'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6),
					'M', ThermalExpansion.machineFrame,
					'I', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));

				//Crucible Recipes
				ResearchHandler.recipes.put("NeutronizedMetal", Thaumcraft.addCrucibleRecipe.invoke(null, "THAUMIUM",	new ItemStack(TMItems.itemMaterial, 1, 0),
						new ItemStack(Thaumcraft.itemResource, 1, 2), new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENERGY, 2)));

				//Crafting Recipes
				ResearchHandler.recipes.put("MagicCoil", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 1), 
						new Object[] {"  N", " T ", "N  ", 
					'N', new ItemStack(Item.redstone), 
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2)	}));
				ResearchHandler.recipes.put("NeutronizedGear", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 2), 
						new Object[] {" T ", "TIT", " T ",
					'T', new ItemStack(TMItems.itemMaterial, 0),
					'I', new ItemStack(Item.ingotIron)	}));
				ResearchHandler.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), 
						new Object[] {" NI", "NIN", "IN ",
					Character.valueOf('N'), "nuggetIron",
					Character.valueOf('I'), "dyeBlack"		}));
				ResearchHandler.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), 
						new Object[] {			" IC", "IPI", "NI ",
					'I', new ItemStack(Item.ingotIron),
					'C', new ItemStack(Thaumcraft.itemWandCap, 1, 0),
					'P', new ItemStack(TMItems.itemMaterial, 1, 3),
					'N', new ItemStack(Item.goldNugget)		}));

				//Wand Recipes
				ItemStack electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
				Thaumcraft.setCap.invoke(electric.getItem(), electric, (WandCap)WandCap.caps.get("thaumium"));
				Thaumcraft.setRod.invoke(electric.getItem(), electric, (WandRod)WandRod.rods.get("electric"));
				Thaumcraft.addArcaneCraftingRecipe.invoke(null, "ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 60).add(Aspect.ORDER, 60)
						.add(Aspect.EARTH, 60).add(Aspect.FIRE, 60).add(Aspect.WATER, 60).add(Aspect.ENTROPY, 60), 
						new Object[]{"  C", " R ", "C  ", 
					Character.valueOf('C'), ((WandCap)WandCap.caps.get("thaumium")).getItem(), 
					Character.valueOf('R'), ((WandRod)WandRod.rods.get("electric")).getItem()		});
			}else{
				//Infusion Recipes
				ResearchHandler.recipes.put("NodeGenerator", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "NODEGENERATOR",
						new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, 
						new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75),
						new ItemStack(TMBlocks.nodeDynamo, 1, 0), 
						new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Block.blockDiamond),
					new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
					new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0),
					new ItemStack(Block.blockGold, 1, 0)}));
				ResearchHandler.recipes.put("FluxLamp", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10,
						new AspectList().add(Aspect.MECHANISM,  45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45),
						new ItemStack(Thaumcraft.blockMetalDevice, 1, 7),
						new ItemStack[] { new ItemStack(Thaumcraft.itemShard, 1 , 4), new ItemStack(Thaumcraft.itemShard, 1, 4),
					new ItemStack(Item.bucketEmpty), new ItemStack(Item.bucketEmpty), new ItemStack(Item.emerald), new ItemStack(Item.emerald)}));
				ResearchHandler.recipes.put("EnergizedWandRod", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "ENERGIZEDWAND",
						new ItemStack(TMItems.itemWandCores, 1), 25,
						new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE,
								50),new ItemStack(Thaumcraft.itemWandRod, 1, 1), new ItemStack[] { new ItemStack(Block.blockRedstone, 1),
					new ItemStack(Block.blockGold, 1, 193),new ItemStack(TMItems.itemMaterial, 1, 2),
					new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1) }));
				ResearchHandler.recipes.put("Condenser", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "CONDENSER",
						new ItemStack(TMBlocks.condenserBlock), 25,	new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50)
						.add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1 , 2), 
						new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 2), new ItemStack(TMItems.itemMaterial, 1, 1),
					new ItemStack(TMItems.itemMaterial,1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
					new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4)}));
				
				ResearchHandler.recipes.put("EldritchConsumer", Thaumcraft.addInfusionCraftingRecipe.invoke(null, "ELDRITCHCONSUMER",
						new ItemStack(TMBlocks.eldritchConsumer, 1), 50,new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128)
						.add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1 , 9), 
						new ItemStack[]{new ItemStack(Item.pickaxeDiamond),new ItemStack(Item.redstone),new ItemStack(Thaumcraft.blockJar),
						new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium)}));

				//Arcane Recipes
				ResearchHandler.recipes.put("QuantumGlass", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "QUANTUMJARS",
						new ItemStack(TMBlocks.cosmeticOpaque,	4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), 
						new Object[] { "GG", "GG", 
					'G', new ItemStack(Block.glass)		}));
				ResearchHandler.recipes.put("QuantumJar", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "QUANTUMJARS",
						new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10),
						new Object[] { "QNQ", "Q Q", "QQQ",
					'Q', new ItemStack(TMBlocks.cosmeticOpaque),
					'N', new ItemStack(TMItems.itemMaterial, 0)		}));
				ResearchHandler.recipes.put("NodeDynamo", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "DYNAMO",
						new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25)
						.add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10),
						new Object[] {" C ", "GIG", "IRI",
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'I', new ItemStack(TMItems.itemMaterial, 0),
					'R', new ItemStack(Item.redstone)				}));
				ResearchHandler.recipes.put("EssentiaDynamo", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0),
						new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25),
						new Object[] {" C ", "GIG", "IWI",
					'W', new ItemStack(Thaumcraft.blockJar, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'I', new ItemStack(Thaumcraft.blockTube, 1, 0),				}));	
				ResearchHandler.recipes.put("BiomeMorpher", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher),
						new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), 
						new Object[] {" E ", "TOB", "GCG",
					'E', new ItemStack(Item.emerald),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 11),
					'O', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 0),
					'B', new ItemStack(Thaumcraft.blockCustomPlant, 1, 4),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)		}));
				ResearchHandler.recipes.put("TeslaCoil", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "TESLACOIL", new ItemStack(TMBlocks.teslaCoil),
						new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.FIRE, 20)
						.add(Aspect.EARTH, 20),
						new Object[] {" N ", " C ", "TBT",
					'N', new ItemStack(TMItems.itemMaterial, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'B', new ItemStack(Thaumcraft.blockTube, 1, 4)		}));
				ResearchHandler.recipes.put("CoilCoupler", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "TESLACOIL", new ItemStack(TMItems.itemMaterial, 1, 4),
						new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 15),
						new Object[] {" C ", " T ", " S ",
					'C', new ItemStack(TMItems.itemMaterial, 1, 1),
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'S', new ItemStack(Item.stick)	}));
				ResearchHandler.recipes.put("ElectricBellows", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "ELECTRICBELLOWS",
						new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30),
						new Object[] {"TG ", "EBC", "TG ",
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'G', new ItemStack(TMItems.itemMaterial, 1, 2),
					'E', new ItemStack(Block.blockRedstone, 1),
					'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				ResearchHandler.recipes.put("Processor", Thaumcraft.addArcaneCraftingRecipe.invoke(null, "PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0),
						new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), 
						new Object[] {" A ", "BMB", "ICI",
					'A', new ItemStack(Item.redstone),
					'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6),
					'M', new ItemStack(Block.blockRedstone, 1),
					'I', new ItemStack(Thaumcraft.itemResource, 1, 2),
					'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));

				//Crucible Recipes
				ResearchHandler.recipes.put("NeutronizedMetal", Thaumcraft.addCrucibleRecipe.invoke(null, "THAUMIUM",	new ItemStack(TMItems.itemMaterial, 1, 0),
						new ItemStack(Thaumcraft.itemResource, 1, 2), new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENERGY, 2)));

				//Crafting Recipes
				ResearchHandler.recipes.put("MagicCoil", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 1), 
						new Object[] {"  N", " T ", "N  ", 
					'N', new ItemStack(Item.redstone), 
					'T', new ItemStack(Thaumcraft.itemResource, 1, 2)	}));
				ResearchHandler.recipes.put("NeutronizedGear", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 2), 
						new Object[] {" T ", "TIT", " T ",
					'T', new ItemStack(TMItems.itemMaterial, 0),
					'I', new ItemStack(Item.ingotIron)	}));
				ResearchHandler.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), 
						new Object[] {" NI", "NIN", "IN ",
					Character.valueOf('N'), "nuggetIron",
					Character.valueOf('I'), "dyeBlack"		}));
				ResearchHandler.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), 
						new Object[] {			" IC", "IPI", "NI ",
					'I', new ItemStack(Item.ingotIron),
					'C', new ItemStack(Thaumcraft.itemWandCap, 1, 0),
					'P', new ItemStack(TMItems.itemMaterial, 1, 3),
					'N', new ItemStack(Item.goldNugget)		}));

				//Wand Recipes
				ItemStack electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
				Thaumcraft.setCap.invoke(electric.getItem(), electric, (WandCap)WandCap.caps.get("thaumium"));
				Thaumcraft.setRod.invoke(electric.getItem(), electric, (WandRod)WandRod.rods.get("electric"));
				Thaumcraft.addArcaneCraftingRecipe.invoke(null, "ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 60).add(Aspect.ORDER, 60)
						.add(Aspect.EARTH, 60).add(Aspect.FIRE, 60).add(Aspect.WATER, 60).add(Aspect.ENTROPY, 60), 
						new Object[]{"  C", " R ", "C  ", 
					Character.valueOf('C'), ((WandCap)WandCap.caps.get("thaumium")).getItem(), 
					Character.valueOf('R'), ((WandRod)WandRod.rods.get("electric")).getItem()		});
			}
		}catch(Exception e){e.printStackTrace();}
	}

	public static void initBloodMagicRecipes() {
		try{
			if(ThermalExpansion.te){
				//Altar Recipes
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMBlocks.bloodDynamo), new ItemStack(ThermalExpansion.blockDynamo), 2,
						10000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 0), new ItemStack(Item.ingotIron), 1, 1000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 1), ThermalExpansion.powerCoilGold, 1, 1000, 100, 100, false);

				//Normal Recipes
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.bloodFabricator), 
						new Object[] {" T ", "IMI", "CAC",
					'T', new ItemStack(ThermalExpansion.blockTank, 1, 3),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'M', ThermalExpansion.machineFrame,
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'A', ThermalExpansion.tesseractFrameFull});
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.processorBM),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.machineFrame,
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'B', getBlock(1, "bloodRune", 0),
					'A', new ItemStack(Item.redstone)});
			} else {
				//Altar Recipes
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMBlocks.bloodDynamo), new ItemStack(Block.blockRedstone, 1), 2,
						10000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 0), new ItemStack(Item.ingotIron, 1), 1, 1000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 1), new ItemStack(Item.redstone, 1),
						1, 1000, 100, 100, false);

				//Normal Recipes
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.bloodFabricator), 
						new Object[] {" T ", "IMI", "CAC",
					'T', new ItemStack(Block.glass, 1, 3),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'M', new ItemStack(Block.blockRedstone, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'A', new ItemStack(Item.eyeOfEnder, 1, 0)});
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.processorBM),
						new Object[] {" A ", "BMB", "ICI",
					'M', new ItemStack(Block.blockRedstone, 1, 0),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'B', getBlock(1, "bloodRune", 0),
					'A', new ItemStack(Item.redstone)});
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
					'W', new ItemStack(Item.redstone),
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'G', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel"				});
				oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), 
						new Object[] {"CDC", "IDI", " P ",
					'C', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel",
					'D', "manaDiamond",
					'P', ThermalExpansion.tesseractFrameFull			});
				oreDictRecipe(new ItemStack(TMBlocks.processorBO),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.machineFrame,
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Item.redstone)				});
			}else{
				//ManaInfusion
				RecipeManaInfusion manaCoilRecipe = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0),
						new ItemStack(Item.redstone), 3000);		

				//Normal Recipes
				oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1),
						new Object[] {" M ", "MIM", " M ",
					'M', "ingotManasteel",
					'I', "ingotIron"		});
				oreDictRecipe(new ItemStack(TMBlocks.flowerDynamo), 
						new Object[] {" C ", "GIG", "IWI",
					'W', new ItemStack(Item.redstone),
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'G', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel"				});
				oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), 
						new Object[] {"CDC", "IDI", " P ",
					'C', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel",
					'D', "manaDiamond",
					'P', new ItemStack(Item.eyeOfEnder, 1, 0)			});
				oreDictRecipe(new ItemStack(TMBlocks.processorBO),
						new Object[] {" A ", "BMB", "ICI",
					'M', new ItemStack(Item.redstone),
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Item.redstone)				});
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