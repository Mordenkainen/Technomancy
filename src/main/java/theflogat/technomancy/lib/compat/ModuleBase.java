package theflogat.technomancy.lib.compat;

import theflogat.technomancy.lib.handlers.EventRegister;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class ModuleBase implements IModModule {
	
	protected void registerItem(Item item, String name) {
		if(item!=null) {
			GameRegistry.registerItem(item, name);
		}
	}
	
	protected void registerBlock(Block block, String name) {
		if(block!=null) {
			GameRegistry.registerBlock(block, name);
		}
	}
	
	protected void registerBlock(Block block, String name, Class<? extends ItemBlock> itemclass) {
		if(block!=null) {
			GameRegistry.registerBlock(block, itemclass, name);
		}
	}
	
	protected void registerTileEntity(Block block, Class<? extends TileEntity> teClass, String name) {
		if(block!=null) {
			GameRegistry.registerTileEntity(teClass, name);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected IRecipe oreDictRecipe(ItemStack res, Object[] params) {
		IRecipe rec = new ShapedOreRecipe(res, params);
		CraftingManager.getInstance().getRecipeList().add(rec);
		return rec;
	}
	
	protected void registerBucket(Fluid fluid, Block fluidBlock, Item bucket) {
		if(bucket!=null) {
			FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(bucket), new ItemStack(Items.bucket));
	    	EventRegister.buckets.put(fluidBlock, bucket);
		}
	}
}
