package theflogat.technomancy.lib.compat;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.items.base.ItemAdvancedBase;
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
import net.minecraftforge.oredict.ShapedOreRecipe;

public abstract class ModuleBase implements IModModule {

	public static void regItem(Item item) {
		if(item != null) {
			ModelResourceLocation res = new ModelResourceLocation(item.getRegistryName().toString(), "inventory");
			ModelLoader.setCustomModelResourceLocation(item, 0, res);
		}
	}

	public static void regBlock(Block block) {
		if(block != null) {
			ModelResourceLocation res = new
					ModelResourceLocation(block.getRegistryName().toString(), "inventory");
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, res);
		}
	}
	
	protected void registerItem(Item item, String name) {
		if(item!=null) {
			ForgeRegistries.ITEMS.register(item.setRegistryName(name));
		}
	}

	protected void registerItem(Item item) {
		if(item!=null) {
			ForgeRegistries.ITEMS.register(item);
		}
	}
	
	public static void registerBlock(Block block, String name) {
		if(block instanceof BlockContainerAdvanced){
			ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
			ForgeRegistries.ITEMS.register(new ItemAdvancedBase(block).setRegistryName(block.getRegistryName()));

			return;
		} else {
			if(block!=null) {
				ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
				ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			}
		}
	}

	public static void registerBlock(Block block) {
		if(block!=null) {
			ForgeRegistries.BLOCKS.register(block);
			ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
	
	protected void registerBlock(Block block, String name, Class<? extends ItemBlock> itemclass) {
		if(block!=null) {
			ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
			ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
	
	protected void registerTileEntity(Block block, Class<? extends TileEntity> teClass, String name) {
		if(block!=null) {
			GameRegistry.registerTileEntity(teClass, name);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected IRecipe oreDictRecipe(ItemStack res, Object[] params) {
		IRecipe rec = new ShapedOreRecipe(null, res, params);
		GameRegistry.addShapedRecipe(res.getItem().getRegistryName(), null, res, params);
		return rec;
	}
	
	protected void registerBucket(Fluid fluid, Block fluidBlock, Item bucket) {
		if(bucket!=null) {
			FluidRegistry.addBucketForFluid(fluid);
	    	EventRegister.buckets.put(fluidBlock, bucket);
		}
	}
}
