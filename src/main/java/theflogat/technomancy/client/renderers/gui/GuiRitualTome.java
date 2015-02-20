package theflogat.technomancy.client.renderers.gui;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.client.renderers.gui.tome.GuiTomeTemplate;
import theflogat.technomancy.client.renderers.gui.tome.buttons.ButtonEntry;
import theflogat.technomancy.client.renderers.gui.tome.buttons.ButtonTab;
import theflogat.technomancy.client.renderers.gui.tome.render.pages.Page;
import theflogat.technomancy.client.renderers.gui.tome.render.pages.Page.Type;
import theflogat.technomancy.client.renderers.gui.tome.render.pages.PageRecipeInst;
import theflogat.technomancy.client.renderers.gui.tome.render.pages.PageRecipeMult;
import theflogat.technomancy.common.blocks.base.TMBlocks;

public class GuiRitualTome extends GuiTomeTemplate{
	
	public GuiRitualTome() {
		super("Ritual");
		
	}

	static {
		int pageMax = 21;
		Page[][] pagesA = {
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
						new ItemStack(Items.gold_ingot),new ItemStack(Items.gold_ingot),new ItemStack(Items.gold_ingot),
						new ItemStack(Blocks.iron_block),new ItemStack(Blocks.crafting_table),new ItemStack(Blocks.iron_block),
						new ItemStack(Blocks.iron_block),new ItemStack(Blocks.piston),new ItemStack(Blocks.iron_block)
							},{
						
							},{
								
							}
					}))
				},
				{
					new Page(Type.TEXT),new Page(Type.TEXT)
				}
		};
		
		pagesA[0][0].addText("Rituals are weak summons of nature's power. They are easy to set up and don't cost too much in terms of resources. " +
				"Read further if you want to know more about them. There should be everything you need to know written here.");
		
		Page[][] pagesB = {
//				{
//					new Page(Type.HANDLER, new PageFuel(new ItemStack[]{
//							Burn.burnables[0],
//							Burn.burnables[1],
//							Burn.burnables[2],
//							Burn.burnables[3],
//							Burn.burnables[4],
//							Burn.burnables[5],
//							Burn.burnables[6],
//							Burn.burnables[7],
//							Burn.burnables[8],
//							Burn.burnables[9],
//							Burn.burnables[10],
//							Burn.burnables[11],
//							Burn.burnables[12],
//							Burn.burnables[13],
//					}, new int[]{
//							Burn.val[0],Burn.val[1],Burn.val[2],Burn.val[3],Burn.val[4],Burn.val[5],Burn.val[6],Burn.val[7],Burn.val[8],
//							Burn.val[9],Burn.val[10],Burn.val[11],Burn.val[12],Burn.val[13],
//					})),new Page(Type.HANDLER, new PageFuel(new ItemStack[]{
//							Burn.burnables[14],
//							Burn.burnables[15],
//							Burn.burnables[16],
//							Burn.burnables[17],
//							Burn.burnables[18],
//							Burn.burnables[19],
//							Burn.burnables[20],
//							Burn.burnables[21],
//							Burn.burnables[22],
//							Burn.burnables[23],
//							Burn.burnables[24],
//							Burn.burnables[25],
//							Burn.burnables[26],
//							Burn.burnables[27],
//					}, new int[]{
//							Burn.val[14],Burn.val[15],Burn.val[16],Burn.val[17],Burn.val[18],Burn.val[19],Burn.val[20],Burn.val[21],
//							Burn.val[22],Burn.val[23],Burn.val[24],Burn.val[25],Burn.val[26],Burn.val[27],
//					}))
//				},
//				{
//					new Page(Type.HANDLER, new PageLiquidFuel(new FluidStack[]{
//							Hot.heat[0],
//							Hot.heat[1],
//							Hot.heat[2],
//							Hot.heat[3],
//							Hot.heat[4],
//							Hot.heat[5],
//							Hot.heat[6],
//							Hot.heat[7],
//							Hot.heat[8],
//							Hot.heat[9],
//							Hot.heat[10],
//							Hot.heat[11],
//							Hot.heat[12],
//							Hot.heat[13],
//					}, new int[]{
//							Hot.val[0],Hot.val[1],Hot.val[2],Hot.val[3],Hot.val[4],Hot.val[5],Hot.val[6],Hot.val[7],Hot.val[8],
//							Hot.val[9],Hot.val[10],Hot.val[11],Hot.val[12],Hot.val[13],
//					})),new Page(Type.HANDLER, new PageLiquidFuel(new FluidStack[]{
//							Hot.heat[14],
//							Hot.heat[15],
//							Hot.heat[16],
//							Hot.heat[17],
//							Hot.heat[18],
//							Hot.heat[19],
//							Hot.heat[20],
//							Hot.heat[21],
//							Hot.heat[22],
//							Hot.heat[23],
//							Hot.heat[24],
//							Hot.heat[25],
//							Hot.heat[26],
//							Hot.heat[27],
//					}, new int[]{
//							Hot.val[14],Hot.val[15],Hot.val[16],Hot.val[17],Hot.val[18],Hot.val[19],Hot.val[20],Hot.val[21],
//							Hot.val[22],Hot.val[23],Hot.val[24],Hot.val[25],Hot.val[26],Hot.val[27],
//					}))
//				},
//				{
//					new Page(Type.HANDLER, new PageLiquidFuel(new FluidStack[]{
//							Cold.coolant[0],
//							Cold.coolant[1],
//							Cold.coolant[2],
//							Cold.coolant[3],
//							Cold.coolant[4],
//							Cold.coolant[5],
//							Cold.coolant[6],
//							Cold.coolant[7],
//							Cold.coolant[8],
//							Cold.coolant[9],
//							Cold.coolant[10],
//							Cold.coolant[11],
//							Cold.coolant[12],
//							Cold.coolant[13],
//					}, new int[]{
//							Cold.val[0],Cold.val[1],Cold.val[2],Cold.val[3],Cold.val[4],Cold.val[5],Cold.val[6],Cold.val[7],Cold.val[8],
//							Cold.val[9],Cold.val[10],Cold.val[11],Cold.val[12],Cold.val[13],
//					})),new Page(Type.HANDLER, new PageLiquidFuel(new FluidStack[]{
//							Cold.coolant[14],
//							Cold.coolant[15],
//							Cold.coolant[16],
//							Cold.coolant[17],
//							Cold.coolant[18],
//							Cold.coolant[19],
//							Cold.coolant[20],
//							Cold.coolant[21],
//							Cold.coolant[22],
//							Cold.coolant[23],
//							Cold.coolant[24],
//							Cold.coolant[25],
//							Cold.coolant[26],
//							Cold.coolant[27],
//					}, new int[]{
//							Cold.val[14],Cold.val[15],Cold.val[16],Cold.val[17],Cold.val[18],Cold.val[19],Cold.val[20],Cold.val[21],
//							Cold.val[22],Cold.val[23],Cold.val[24],Cold.val[25],Cold.val[26],Cold.val[27],
//					}))
//				},
//				{
//					new Page(Type.HANDLER, new PageLiquidFuel(new FluidStack[]{
//							Reacting.reacting[0],
//							Reacting.reacting[1],
//							Reacting.reacting[2],
//							Reacting.reacting[3],
//							Reacting.reacting[4],
//							Reacting.reacting[5],
//							Reacting.reacting[6],
//							Reacting.reacting[7],
//							Reacting.reacting[8],
//							Reacting.reacting[9],
//							Reacting.reacting[10],
//							Reacting.reacting[11],
//							Reacting.reacting[12],
//							Reacting.reacting[13],
//					}, new int[]{
//							Reacting.val[0],Reacting.val[1],Reacting.val[2],Reacting.val[3],Reacting.val[4],Reacting.val[5],Reacting.val[6],
//							Reacting.val[7],Reacting.val[8],Reacting.val[9],Reacting.val[10],Reacting.val[11],Reacting.val[12],Reacting.val[13],
//					})),new Page(Type.HANDLER, new PageLiquidFuel(new FluidStack[]{
//							Reacting.reacting[14],
//							Reacting.reacting[15],
//							Reacting.reacting[16],
//							Reacting.reacting[17],
//							Reacting.reacting[18],
//							Reacting.reacting[19],
//							Reacting.reacting[20],
//							Reacting.reacting[21],
//							Reacting.reacting[22],
//							Reacting.reacting[23],
//							Reacting.reacting[24],
//							Reacting.reacting[25],
//							Reacting.reacting[26],
//							Reacting.reacting[27],
//					}, new int[]{
//							Reacting.val[14],Reacting.val[15],Reacting.val[16],Reacting.val[17],Reacting.val[18],Reacting.val[19],
//							Reacting.val[20],Reacting.val[21],
//							Reacting.val[22],Reacting.val[23],Reacting.val[24],Reacting.val[25],Reacting.val[26],Reacting.val[27],
//					}))
//				},
//				{
//					new Page(Type.HANDLER, new PageFuel(new ItemStack[]{
//							Reactant.reactants[0],
//							Reactant.reactants[1],
//							Reactant.reactants[2],
//							Reactant.reactants[3],
//							Reactant.reactants[4],
//							Reactant.reactants[5],
//							Reactant.reactants[6],
//							Reactant.reactants[7],
//							Reactant.reactants[8],
//							Reactant.reactants[9],
//							Reactant.reactants[10],
//							Reactant.reactants[11],
//							Reactant.reactants[12],
//							Reactant.reactants[13],
//					}, new int[]{
//							Reactant.val[0],Reactant.val[1],Reactant.val[2],Reactant.val[3],Reactant.val[4],Reactant.val[5],Reactant.val[6],
//							Reactant.val[7],Reactant.val[8],Reactant.val[9],Reactant.val[10],Reactant.val[11],Reactant.val[12],Reactant.val[13],
//					})),new Page(Type.HANDLER, new PageFuel(new ItemStack[]{
//							Reactant.reactants[14],
//							Reactant.reactants[15],
//							Reactant.reactants[16],
//							Reactant.reactants[17],
//							Reactant.reactants[18],
//							Reactant.reactants[19],
//							Reactant.reactants[20],
//							Reactant.reactants[21],
//							Reactant.reactants[22],
//							Reactant.reactants[23],
//							Reactant.reactants[24],
//							Reactant.reactants[25],
//							Reactant.reactants[26],
//							Reactant.reactants[27],
//					}, new int[]{
//							Reactant.val[14],Reactant.val[15],Reactant.val[16],Reactant.val[17],Reactant.val[18],Reactant.val[19],
//							Reactant.val[20],Reactant.val[21],
//							Reactant.val[22],Reactant.val[23],Reactant.val[24],Reactant.val[25],Reactant.val[26],Reactant.val[27],
//					}))
//				}
				
		};
		
		ButtonEntry but[][] = {
				{
					new ButtonEntry(30, 12, "Introduction", 0, pagesA[0]),
					new ButtonEntry(30, 20, "Crystal Blocks", 1, pagesA[1]),
					new ButtonEntry(30, 28, "Catalysts", 2, pagesA[2]),
				},
				{
					new ButtonEntry(30, 12, "Cave In", 0, pagesB[0]),
					new ButtonEntry(30, 20, "Black Hole", 1, pagesB[1]),
					new ButtonEntry(30, 28, "Water", 2, pagesB[2])
				}
		};
		
		tabs = new ButtonTab[2];
		for(int i=0; i<tabs.length; i++){
			tabs[i] = new ButtonTab(9, i*16+9, 0, i*16, i, but[i], getTabFromId(i));
		}
	}
	
	public static String getTabFromId(int id){
		switch(id){
		case 0:
			return "Rituals 101";
		case 1:
			return "Rituals";
		}
		return "";
	}
}
