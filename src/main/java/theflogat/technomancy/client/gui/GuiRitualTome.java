package theflogat.technomancy.client.gui;

import java.util.ArrayList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import theflogat.technomancy.client.gui.tome.GuiTomeTemplate;
import theflogat.technomancy.client.gui.tome.buttons.ButtonEntry;
import theflogat.technomancy.client.gui.tome.buttons.ButtonTab;
import theflogat.technomancy.client.gui.tome.render.pages.Page;
import theflogat.technomancy.client.gui.tome.render.pages.Page.Type;
import theflogat.technomancy.client.gui.tome.render.pages.PageRecipeInst;
import theflogat.technomancy.client.gui.tome.render.pages.PageRecipeMult;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.tome.ItemRitualTome.Res;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.ThermalExpansion;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;

public class GuiRitualTome extends GuiTomeTemplate{

	public GuiRitualTome() {
		super("Ritual");

	}

	static {
		int pageMax = 21;
		Page[][] pages101 = {
				{
					new Page(Type.TEXT)
				},
				{
					new Page(Type.TEXT), new Page(Type.HANDLER, new PageRecipeMult(new ItemStack[]{
							new ItemStack(TMBlocks.crystalBlock, 1, 0),new ItemStack(TMBlocks.crystalBlock, 1, 1),new ItemStack(TMBlocks.crystalBlock, 1, 2),
							new ItemStack(TMBlocks.crystalBlock, 1, 3),new ItemStack(TMBlocks.crystalBlock, 1, 4)
					}, 
					new ItemStack[][]{
							{
								new ItemStack(Items.GLOWSTONE_DUST),new ItemStack(Items.DYE, 1, 0),null,
								new ItemStack(Items.DYE, 1, 0),new ItemStack(Items.GLOWSTONE_DUST),
							},{

								new ItemStack(Items.GLOWSTONE_DUST),new ItemStack(Items.DYE, 1, 15),null,
								new ItemStack(Items.DYE, 1, 15),new ItemStack(Items.GLOWSTONE_DUST),
							},{

								new ItemStack(Items.GLOWSTONE_DUST),new ItemStack(Items.DYE, 1, 1),null,
								new ItemStack(Items.DYE, 1, 1),new ItemStack(Items.GLOWSTONE_DUST),
							},{

								new ItemStack(Items.GLOWSTONE_DUST),new ItemStack(Items.DYE, 1, 2),null,
								new ItemStack(Items.DYE, 1, 2),new ItemStack(Items.GLOWSTONE_DUST),
							},{

								new ItemStack(Items.GLOWSTONE_DUST),new ItemStack(Items.DYE, 1, 4),null,
								new ItemStack(Items.DYE, 1, 4),new ItemStack(Items.GLOWSTONE_DUST),
							}
					}))
				},
				{
					new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeMult(new ItemStack[]{
							new ItemStack(TMBlocks.catalyst, 1, 0),new ItemStack(TMBlocks.catalyst, 1, 1),new ItemStack(TMBlocks.catalyst, 1, 2),
							new ItemStack(TMBlocks.catalyst, 1, 3),new ItemStack(TMBlocks.catalyst, 1, 4)
					}, new ItemStack[][]{
							{
								new ItemStack(Blocks.OBSIDIAN),new ItemStack(Items.DYE, 1, 0),new ItemStack(Items.ENDER_PEARL),
								new ItemStack(Items.DYE, 1, 0),new ItemStack(Blocks.GOLD_BLOCK),new ItemStack(Items.DYE, 1, 0),
								new ItemStack(Items.ENDER_PEARL),new ItemStack(Items.DYE, 1, 0),new ItemStack(Blocks.OBSIDIAN)
							},{
								new ItemStack(Items.GOLDEN_APPLE),new ItemStack(Items.DYE, 1, 15),new ItemStack(Items.GOLDEN_CARROT),
								new ItemStack(Items.DYE, 1, 15),new ItemStack(Blocks.GOLD_BLOCK),new ItemStack(Items.DYE, 1, 15),
								new ItemStack(Items.GOLDEN_CARROT),new ItemStack(Items.DYE, 1, 15),new ItemStack(Items.GOLDEN_APPLE)
							},{
								new ItemStack(Items.BLAZE_ROD),new ItemStack(Items.DYE, 1, 1),new ItemStack(Items.BLAZE_ROD),
								new ItemStack(Items.DYE, 1, 1),new ItemStack(Blocks.GOLD_BLOCK),new ItemStack(Items.DYE, 1, 1),
								new ItemStack(Items.BLAZE_ROD),new ItemStack(Items.DYE, 1, 1),new ItemStack(Items.BLAZE_ROD)
							},{
								new ItemStack(Blocks.GRASS),new ItemStack(Items.DYE, 1, 2),new ItemStack(Blocks.GRASS),
								new ItemStack(Items.DYE, 1, 2),new ItemStack(Blocks.GOLD_BLOCK),new ItemStack(Items.DYE, 1, 2),
								new ItemStack(Blocks.GRASS),new ItemStack(Items.DYE, 1, 2),new ItemStack(Blocks.GRASS)
							},{
								new ItemStack(Items.FISH),new ItemStack(Items.DYE, 1, 4),new ItemStack(Blocks.CLAY),
								new ItemStack(Items.DYE, 1, 4),new ItemStack(Blocks.GOLD_BLOCK),new ItemStack(Items.DYE, 1, 4),
								new ItemStack(Blocks.CLAY),new ItemStack(Items.DYE, 1, 4),new ItemStack(Items.FISH)
							}
					}))
				}
		};

		pages101[0][0].addText("Rituals are weak summons of nature's power. They are easy to set up and don't cost too much in terms of resources. " +
				"Read further if you want to know more about them. There should be everything you need to know written here.");

		pages101[1][0].addText("Crystals are what you use for the frame of the Rituals. They contain enough power to emit light. Crystals have a "+
				"natural ability to densify when placed above another one.");

		pages101[2][0].addText("Catalyst are the core of the Rituals. They determine the main element which the Ritual will lean towards. " +
				"They can be activated either by being right clicked or by receiving a redstone signal.");


		Page[][] pagesD = {
				{
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.BH1))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.BH2))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.BH3)))
				},{
					new Page(Type.TEXT),new Page(Type.TEXT),new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.FT))),
				}

		};

		pagesD[0][0].addLines(new String[]{
				"Tier 1:",
				"Will destroy every blocks and kill every living thing in a 7x7x7 area.",
				"Ritual is consumed."
		});
		pagesD[0][1].setImageOffsets(54, 0);
		pagesD[0][2].addLines(new String[]{
				"Tier 2:",
				"Will destroy every blocks and kill every living thing in a 11x11x11 area.",
				"Ritual is consumed."
		});
		pagesD[0][3].setImageOffsets(54, 0);
		pagesD[0][4].addLines(new String[]{
				"Tier 3:",
				"Will destroy every blocks and kill every living thing in a 19x19x19 area.",
				"Ritual is consumed."
		});
		pagesD[0][5].setImageOffsets(54, 0);

		Page[][] pagesL = {
				{
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.PU1))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.PU2))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.PU3))),
				}		
		};
		pagesL[0][0].addLines(new String[]{
				"Tier 1:",
				"Will kill every monster above the catalyst in a 1x1x3."
		});
		pagesL[0][1].setImageOffsets(54, 0);
		pagesL[0][2].addLines(new String[]{
				"Tier 2:",
				"Will kill every monster above the catalyst in a 3x3x11 area."
		});
		pagesL[0][3].setImageOffsets(54, 0);
		pagesL[0][4].addLines(new String[]{
				"Tier 3:",
				"Will kill every monster above the catalyst in a 7x7 area.",
				"Range: World Max Height"
		});
		pagesL[0][5].setImageOffsets(54, 0);

		Page[][] pagesF = {
				{
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.F1))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.F2))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.F3))),
				}
		};
		
		pagesF[0][0].addLines(new String[]{
				"Tier 1:",
				"Will convert Water to Obsidian in a 19x19x19 area under the ritual.",
				"Ritual is consumed."
		});
		pagesF[0][1].setImageOffsets(54, 0);
		pagesF[0][2].addLines(new String[]{
				"Tier 2:",
				"Will create a 9x9x19 lava pool.",
				"Ritual is consumed."
		});
		pagesF[0][3].setImageOffsets(54, 0);
		pagesF[0][4].addLines(new String[]{
				"Tier 3: DISABLED.",
				"Will make a Volcano emerge.",
				"Ritual is consumed."
		});
		pagesF[0][5].setImageOffsets(54, 0);
		
		Page[][] pagesW = {
				{
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.W1))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.W2))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.W3))),
				}
		};
		
		pagesW[0][0].addLines(new String[]{
				"Tier 1:",
				"Places water underneath in a 3x3 area."
		});
		pagesW[0][1].setImageOffsets(54, 0);
		pagesW[0][2].addLines(new String[]{
				"Tier 2:",
				"Places water underneath in a 7x7 area."
		});
		pagesW[0][3].setImageOffsets(54, 0);
		pagesW[0][4].addLines(new String[]{
				"Tier 3:",
				"Replaces blocks underneath with water in a 19x19 area.",
				"Ritual is consumed."
		});
		pagesW[0][5].setImageOffsets(54, 0);
		
		Page[][] pagesE = {
				{
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.CI1))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.CI2))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.CI3))),
				}
		};
		pagesE[0][0].addLines(new String[]{
				"Tier 1:",
				"Closes all the gaps underneath the ritual in a 3x3 area.",
				"Good for closing caves. Ritual is consumed."
		});
		pagesE[0][1].setImageOffsets(54, 0);
		pagesE[0][2].addLines(new String[]{
				"Tier 2:",
				"Closes all the gaps underneath the ritual in a 7x7 area.",
				"Good for closing caves. Ritual is consumed."
		});
		pagesE[0][3].setImageOffsets(54, 0);
		pagesE[0][4].addLines(new String[]{
				"Tier 3:",
				"Closes all the gaps underneath the ritual in a 11x11 area.",
				"Good for closing caves. Ritual is consumed."
		});
		pagesE[0][5].setImageOffsets(54, 0);
		
		Page[][] pagesP = {
				{
					new Page(Type.TEXT),new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMItems.exGem, 1, 100), new ItemStack[]{
						null,new ItemStack(Items.GOLD_NUGGET),null,
						new ItemStack(Items.GOLD_NUGGET),new ItemStack(Items.EMERALD),new ItemStack(Items.GOLD_NUGGET),
						null,new ItemStack(Items.GOLD_NUGGET),null
					}))
				},{
					new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMBlocks.existenceBurner), new ItemStack[]{
						new ItemStack(TMItems.exGem),null,null,
						new ItemStack(Blocks.ANVIL)
					})),new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMBlocks.existenceBurner, 1, 1), CompatibilityHandler.te ?
							new ItemStack[]{new ItemStack(TMBlocks.existenceBurner), ThermalExpansion.frameMachineBasic, ThermalExpansion.powerCoilSilver} :
								new ItemStack[]{new ItemStack(TMBlocks.existenceBurner), new ItemStack(Items.REDSTONE), new ItemStack(Blocks.PISTON)}))
				},{
					new Page(Type.TEXT),new Page(Type.TEXT),new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.FT))),
				},{
					new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMBlocks.existencePylon), new ItemStack[]{
						new ItemStack(Items.REDSTONE),new ItemStack(Items.REDSTONE),new ItemStack(Items.REDSTONE),
						new ItemStack(Items.REDSTONE),new ItemStack(Items.EMERALD),new ItemStack(Items.REDSTONE),
						new ItemStack(Items.REDSTONE),new ItemStack(Blocks.PISTON),new ItemStack(Items.REDSTONE)
					})),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMBlocks.existencePylon, 1, 1), new ItemStack[]{
						new ItemStack(Items.DIAMOND),new ItemStack(TMItems.itemBoost),new ItemStack(TMItems.exGem),new ItemStack(TMBlocks.existencePylon, 1, 0)
					})),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMBlocks.existencePylon, 1, 2), new ItemStack[]{
						new ItemStack(Items.ENDER_PEARL),new ItemStack(Items.DIAMOND),new ItemStack(TMItems.exGem),new ItemStack(TMBlocks.existencePylon, 1, 1)
					}))
				},{
					new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMBlocks.existenceUser, 1, 1), new ItemStack[]{
						null,new ItemStack(Items.GOLDEN_CARROT),null,
						new ItemStack(Items.GOLDEN_APPLE), new ItemStack(TMItems.exGem), new ItemStack(Items.GOLDEN_APPLE),
						null,new ItemStack(Items.GOLDEN_CARROT),null
					})), new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMBlocks.existenceUser, 1, 2), new ItemStack[]{
						null,new ItemStack(Items.IRON_HOE),null,
						new ItemStack(Items.GOLDEN_APPLE), new ItemStack(TMItems.exGem), new ItemStack(Items.GOLDEN_APPLE),
						null,new ItemStack(Items.IRON_HOE),null
					}))
				}
		};
		pagesP[0][0].addText("Every living being comes from a single source: Power of Existence. This Power is the very root of any existence " +
							"hence its name. A small amount of it is present in each being. When a large amount of it is focused in one place it becomes " +
							"rather dangerous but there exists ways to prevent instability.");
		pagesP[0][1].addText("You have discovered that emeraulds are a particularly great catalyst. It seems this particular gem has a very high amount " +
							"of power stored inside it. Maybe this is a clue that can help you master this power. You decide to experiment a little " +
							"and manage to create a gem that can store a small amount of existence.");
		pagesP[1][0].addText("You have found a way to collect a meek amount of existence and store it in a single device. The power is collected from " +
							"nearby creatures. Unfortunately you have also discovered that doing so kills off these creatures. Apparently, the smarter the " +
							"creature the more power it has.");
		pagesP[1][2].addText("You have found a way to make existence collecting much more efficient. It seems raw energy allows to amplify the power collect from" +
							"nearby creatures. Pumping RF into a Dynamic Burner allows the device to draw more power. Unfortunately the RF seems to overwhelmed" +
							"and voided by the Power of Existence.");
		pagesP[2][0].addText("The Birth of the Foutain is a ritual that converts power of existence of nearby beings into " +
							"a self-sustaining mass of power of existence. It takes the shape of a cobblestone Graal since " +
							"this shape is supposedly the best for holding nonsense.");
		pagesP[2][1].addText("The color of its content indicates how rich the fountain currently is. Green means close to empty while " +
							"dark blue means it is nearing its max capacity.");
		pagesP[2][2].setImageOffsets(-64, 0);
		pagesP[3][0].addText("Using emeraulds have proven to be a good way to allow the transfer of Power. Your device, the Pylons have an inherent ability " +
							"to draw Power where it is highly focused and to disperse it wher it is not. There seems to be ways to upgrade the emeraulds' " +
							"power by coupling it with other Powerful items.");
		pagesP[4][0].addText("It seems crops react in an... interesting way to Power of Existence. Your new device uses the Power of Existence to accelerate " +
							"crop growth in a 9x9 area. It must be placed underneath the soil so it is rather convinient.");
		pagesP[4][2].addText("Thinking that having to harvest manually is boring is what made you design an existence based harvester. By infusing hoes with " +
							"a small amount of power of existence seems to harvest ripe crops nearby.");
		
		
		Page[][] pagesT = {
				{
					new Page(Type.TEXT),new Page(Type.TEXT)
				},{
					new Page(Type.TEXT),new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.ET))),
				},{
					new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMBlocks.existenceUser, 1, 2), new ItemStack[]{
						new ItemStack(Blocks.OBSIDIAN), new ItemStack(Items.FLINT_AND_STEEL),new ItemStack(Blocks.OBSIDIAN),
						new ItemStack(Items.FLINT_AND_STEEL), new ItemStack(TMItems.exGem), new ItemStack(Items.FLINT_AND_STEEL),
						new ItemStack(Blocks.OBSIDIAN),new ItemStack(Items.FLINT_AND_STEEL),new ItemStack(Blocks.OBSIDIAN)
					}))
				}
		};
		
		pagesT[0][0].addText("Some lore that you found in a Village's library seems to indicate that some Villagers have shown overworldly characteristics. " +
							"They look just like normal Villagers but exihibit incredible capacities. You found a special note indicating that " +
							"the power comes from some artifacts buried inside the existence mass of Villagers.");
		pagesT[0][1].addText("You think of a way to extract these artifacts and you conclude that Rituals are your best ally.");
		pagesT[1][0].addText("The Ritual of Extraction serves to extract artifacts from special Villagers. It is quite unstable and may cause catastrophies " +
							"if not used correctly.");
		pagesT[2][0].addText("Now that you know how dangerous those Villagers are, you decide to invent a device that seals temporairly this power. " +
							"The Existence Sealing Device allows for an easy way to seal off nearby powerfull villager for 4 seconds at an important " +
							"existence cost.");
		
		ArrayList<String> bof = new ArrayList<String>();
		bof.add("Birth of the");
		bof.add("Fountain");
		
		
		ButtonEntry but[][] = {
				{
					new ButtonEntry("Introduction", pages101[0]),
					new ButtonEntry("Crystal Blocks", pages101[1]),
					new ButtonEntry("Catalysts", pages101[2]),
				},
				{
					new ButtonEntry("Black Hole", pagesD[0]),
				},{
					new ButtonEntry("Purification", pagesL[0]),
				},{
					new ButtonEntry("Fire", pagesF[0])
				},{
					new ButtonEntry("Water", pagesW[0])
				},{
					new ButtonEntry("Collapse", pagesE[0]),
				},{
					new ButtonEntry("Power of Existence", pagesP[0]),
					new ButtonEntry("Farming Power", pagesP[1]),
					new ButtonEntry(bof, pagesP[2]),
					new ButtonEntry("Pylons", pagesP[3]),
					new ButtonEntry("Better Farming", pagesP[4])
				},{
					new ButtonEntry("Treasures?!?", pagesT[0]),
					new ButtonEntry("Extraction", pagesT[1]),
					new ButtonEntry("Protection", pagesT[2])
				}
		};

		tabs = new ButtonTab[8];
		for(int i=0; i<tabs.length; i++){
			tabs[i] = new ButtonTab(9, i*16+9, 0, i*16, i, but[i], getTabFromId(i));
		}
	}

	public static String getTabFromId(int id){
		switch(id){
		case 0:
			return "Ritual 101";
		case 1:
			return "Dark Rituals";
		case 2:
			return "Light Rituals";
		case 3:
			return "Fire Rituals";
		case 4:
			return "Water Rituals";
		case 5:
			return "Earth Rituals";
		case 6:
			return "Power of Existence";
		case 7:
			return "Treasures";
		}
		return "";
	}
}
