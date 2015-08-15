package theflogat.technomancy.client.gui;

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
import theflogat.technomancy.common.items.tome.ItemRitualTome.Res;
import theflogat.technomancy.lib.Ref;

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
								new ItemStack(Items.glowstone_dust),new ItemStack(Items.dye, 1, 0),null,
								new ItemStack(Items.dye, 1, 0),new ItemStack(Items.glowstone_dust),
							},{

								new ItemStack(Items.glowstone_dust),new ItemStack(Items.dye, 1, 15),null,
								new ItemStack(Items.dye, 1, 15),new ItemStack(Items.glowstone_dust),
							},{

								new ItemStack(Items.glowstone_dust),new ItemStack(Items.dye, 1, 1),null,
								new ItemStack(Items.dye, 1, 1),new ItemStack(Items.glowstone_dust),	
							},{

								new ItemStack(Items.glowstone_dust),new ItemStack(Items.dye, 1, 2),null,
								new ItemStack(Items.dye, 1, 2),new ItemStack(Items.glowstone_dust),	
							},{

								new ItemStack(Items.glowstone_dust),new ItemStack(Items.dye, 1, 4),null,
								new ItemStack(Items.dye, 1, 4),new ItemStack(Items.glowstone_dust),	
							}
					}))
				},
				{
					new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeMult(new ItemStack[]{
							new ItemStack(TMBlocks.catalyst, 1, 0),new ItemStack(TMBlocks.catalyst, 1, 1),new ItemStack(TMBlocks.catalyst, 1, 2),
							new ItemStack(TMBlocks.catalyst, 1, 3),new ItemStack(TMBlocks.catalyst, 1, 4)
					}, new ItemStack[][]{
							{
								new ItemStack(Blocks.obsidian),new ItemStack(Items.dye, 1, 0),new ItemStack(Items.ender_pearl),
								new ItemStack(Items.dye, 1, 0),new ItemStack(Blocks.gold_block),new ItemStack(Items.dye, 1, 0),
								new ItemStack(Items.ender_pearl),new ItemStack(Items.dye, 1, 0),new ItemStack(Blocks.obsidian)
							},{
								new ItemStack(Items.golden_apple),new ItemStack(Items.dye, 1, 15),new ItemStack(Items.golden_carrot),
								new ItemStack(Items.dye, 1, 15),new ItemStack(Blocks.gold_block),new ItemStack(Items.dye, 1, 15),
								new ItemStack(Items.golden_carrot),new ItemStack(Items.dye, 1, 15),new ItemStack(Items.golden_apple)
							},{
								new ItemStack(Items.blaze_rod),new ItemStack(Items.dye, 1, 1),new ItemStack(Items.blaze_rod),
								new ItemStack(Items.dye, 1, 1),new ItemStack(Blocks.gold_block),new ItemStack(Items.dye, 1, 1),
								new ItemStack(Items.blaze_rod),new ItemStack(Items.dye, 1, 1),new ItemStack(Items.blaze_rod)
							},{
								new ItemStack(Items.fish),new ItemStack(Items.dye, 1, 2),new ItemStack(Blocks.clay),
								new ItemStack(Items.dye, 1, 2),new ItemStack(Blocks.gold_block),new ItemStack(Items.dye, 1, 2),
								new ItemStack(Blocks.clay),new ItemStack(Items.dye, 1, 2),new ItemStack(Items.fish)
							},{
								new ItemStack(Blocks.grass),new ItemStack(Items.dye, 1, 4),new ItemStack(Blocks.grass),
								new ItemStack(Items.dye, 1, 4),new ItemStack(Blocks.gold_block),new ItemStack(Items.dye, 1, 4),
								new ItemStack(Blocks.grass),new ItemStack(Items.dye, 1, 4),new ItemStack(Blocks.grass)
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
		pagesD[0][1].setImageOffsets(64, 0);
		pagesD[0][2].addLines(new String[]{
				"Tier 2:",
				"Will destroy every blocks and kill every living thing in a 11x11x11 area.",
				"Ritual is consumed."
		});
		pagesD[0][3].setImageOffsets(64, 0);
		pagesD[0][4].addLines(new String[]{
				"Tier 3:",
				"Will destroy every blocks and kill every living thing in a 19x19x19 area.",
				"Ritual is consumed."
		});
		pagesD[0][5].setImageOffsets(64, 0);
		
		pagesD[1][0].addText("The Birth of the Foutain is a ritual that converts power of existence of nearby beings into " +
							"a self-sustaining mass of power of existence. It takes the shape of a cobblestone Graal since " +
							"cobblestone is the most common ressource and easiest to create with the power of existence.");
		pagesD[1][1].addText("The color of its content indicates how rich the fountain currently is. Green means close to empty while " +
							"dark blue means it is nearing its max capacity.");
		pagesD[1][2].setImageOffsets(-64, 0);

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
		pagesL[0][1].setImageOffsets(64, 0);
		pagesL[0][2].addLines(new String[]{
				"Tier 2:",
				"Will kill every monster above the catalyst in a 3x3x11 area."
		});
		pagesL[0][3].setImageOffsets(64, 0);
		pagesL[0][4].addLines(new String[]{
				"Tier 3:",
				"Will kill every monster above the catalyst in a 7x7 area.",
				"Range: World Max Height"
		});
		pagesL[0][5].setImageOffsets(64, 0);

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
		pagesF[0][1].setImageOffsets(64, 0);
		pagesF[0][2].addLines(new String[]{
				"Tier 2:",
				"Will create a 9x9x19 lava pool.",
				"Ritual is consumed."
		});
		pagesF[0][3].setImageOffsets(64, 0);
		pagesF[0][4].addLines(new String[]{
				"Tier 3: DISABLED.",
				"Will make a Volcano emerge.",
				"Ritual is consumed."
		});
		pagesF[0][5].setImageOffsets(64, 0);
		
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
		pagesW[0][1].setImageOffsets(64, 0);
		pagesW[0][2].addLines(new String[]{
				"Tier 2:",
				"Places water underneath in a 7x7 area."
		});
		pagesW[0][3].setImageOffsets(64, 0);
		pagesW[0][4].addLines(new String[]{
				"Tier 3: DISABLED.",
				"Replaces blocks underneath with water in a 19x19 area.",
				"Ritual is consumed."
		});
		pagesW[0][5].setImageOffsets(64, 0);
		
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
		pagesE[0][1].setImageOffsets(64, 0);
		pagesE[0][2].addLines(new String[]{
				"Tier 2:",
				"Closes all the gaps underneath the ritual in a 7x7 area.",
				"Good for closing caves. Ritual is consumed."
		});
		pagesE[0][3].setImageOffsets(64, 0);
		pagesE[0][4].addLines(new String[]{
				"Tier 3: DISABLED.",
				"Closes all the gaps underneath the ritual in a 11x11 area.",
				"Good for closing caves. Ritual is consumed."
		});
		pagesE[0][5].setImageOffsets(64, 0);
		
		Page[][] pagesP = {
				{
					new Page(Type.TEXT)
				},{
					new Page(Type.TEXT),new Page(Type.HANDLER, new PageRecipeInst(new ItemStack(TMBlocks.existenceBurner), new ItemStack[]{
						new ItemStack(Items.emerald),new ItemStack(Blocks.anvil),new ItemStack(Items.emerald)
					}))
				}
		};
		pagesP[0][0].addText("Every living being comes from a single source: Power of Existence. This Power is the very root of any existence " +
							"hence its name. A small amount of it is present in each being. When a large amount of it is focused in one place it becomes " +
							"rather dangerous but there exists ways to prevent instability.");
		pagesP[1][0].addText("You have found a way to collect a meek amount of existence and store it in a single device. The power is collected from " +
							"nearby creatures. Unfortunately you have also discovered that doing so kills off these creatures. Apparently, the smarter the " +
							"creature the more power it has.");
		
		
		
		ButtonEntry but[][] = {
				{
					new ButtonEntry(30, 12, "Introduction", 0, pages101[0]),
					new ButtonEntry(30, 20, "Crystal Blocks", 1, pages101[1]),
					new ButtonEntry(30, 28, "Catalysts", 2, pages101[2]),
				},
				{
					new ButtonEntry(30, 12, "Black Hole", 0, pagesD[0]),
					new ButtonEntry(30, 20, "Birth of the Fountain", 0, pagesD[1])
				},{
					new ButtonEntry(30, 12, "Purification", 0, pagesL[0]),
				},{
					new ButtonEntry(30, 12, "Fire", 0, pagesF[0])
				},{
					new ButtonEntry(30, 12, "Water", 0, pagesW[0])
				},{
					new ButtonEntry(30, 12, "Collapse", 0, pagesE[0]),
				},{
					new ButtonEntry(30, 12, "Power of Existence", 0, pagesP[0]),
					new ButtonEntry(30, 12, "Farming Power", 0, pagesP[1])
				}
		};

		tabs = new ButtonTab[7];
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
		}
		return "";
	}
}
