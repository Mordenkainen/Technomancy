package theflogat.technomancy.client.renderers.gui;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import theflogat.technomancy.client.renderers.gui.tome.GuiTomeTemplate;
import theflogat.technomancy.client.renderers.gui.tome.buttons.ButtonEntry;
import theflogat.technomancy.client.renderers.gui.tome.buttons.ButtonTab;
import theflogat.technomancy.client.renderers.gui.tome.render.pages.Page;
import theflogat.technomancy.client.renderers.gui.tome.render.pages.Page.Type;
import theflogat.technomancy.client.renderers.gui.tome.render.pages.PageRecipeInst;
import theflogat.technomancy.client.renderers.gui.tome.render.pages.PageRecipeMult;
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
		
		pages101[1][0].addText("Crystals are what you use for the frame of the Rituals. They contain enough power to emit light. It loses width if "+
				"it is place above another crystal");
		
		pages101[2][0].addText("Catalyst are the core of the Rituals. They determine the main element which the Ritual will lean towards. " +
				"They can be activated either by being right clicked or by receiving a redstone signal.");


		Page[][] pagesD = {
				{
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.BHT1))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.BHT2))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.BHT3)))
				},{
					new Page(Type.TEXT)
				},{
					new Page(Type.TEXT)
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
		
		Page[][] pagesL = {
				{
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.BHT1))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.BHT1))),
					new Page(Type.TEXT), new Page(Type.IMAGE, new ResourceLocation(Ref.getGui(Res.BHT1))),
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
		pagesD[0][3].setImageOffsets(64, 0);
		pagesL[0][4].addLines(new String[]{
				"Tier 3:",
				"Will kill every monster above the catalyst in a 7x7 area.",
				"Range: World Max Height"
		});
		pagesL[0][5].setImageOffsets(64, 0);
		
		ButtonEntry but[][] = {
				{
					new ButtonEntry(30, 12, "Introduction", 0, pages101[0]),
					new ButtonEntry(30, 20, "Crystal Blocks", 1, pages101[1]),
					new ButtonEntry(30, 28, "Catalysts", 2, pages101[2]),
				},
				{
					new ButtonEntry(30, 12, "Black Hole", 0, pagesD[0]),
				},{
					new ButtonEntry(30, 12, "Purification", 0, pagesL[0]),
				},{
					
				},{
					new ButtonEntry(30, 12, "Water", 0, pagesD[0])
				},{
					new ButtonEntry(30, 12, "Cave In", 0, pagesD[0]),
				}
		};

		tabs = new ButtonTab[6];
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
		}
		return "";
	}
}
