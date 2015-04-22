package theflogat.technomancy.common.items.base;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import thaumcraft.api.wands.WandRod;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.bloodmagic.ItemBMMaterial;
import theflogat.technomancy.common.items.botania.ItemBOMaterial;
import theflogat.technomancy.common.items.botania.ItemManaBucket;
import theflogat.technomancy.common.items.technom.ItemBoost;
import theflogat.technomancy.common.items.technom.ItemProcessedOre;
import theflogat.technomancy.common.items.thaumcraft.ElectricWandUpdate;
import theflogat.technomancy.common.items.thaumcraft.ItemFusionFocus;
import theflogat.technomancy.common.items.thaumcraft.ItemPen;
import theflogat.technomancy.common.items.thaumcraft.ItemTHMaterial;
import theflogat.technomancy.common.items.thaumcraft.ItemWandCores;
import theflogat.technomancy.common.items.tome.ItemRitualTome;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.handlers.EventRegister;
import theflogat.technomancy.util.Ore;
import cpw.mods.fml.common.registry.GameRegistry;

public class TMItems {

	// Item Instances
    public static Item essentiaCannon;
    public static Item itemMaterial;
    public static Item itemPen;
    public static Item itemWandCores;
    public static Item itemFusionFocus;
    public static Item itemBoost;
    public static Item ritualTome;
    
    public static WandRod WAND_ROD_ELECTRIC;
    
    public static void initTechnomancy(){
        itemBoost = Ids.itemBoost ? new ItemBoost() : null;
        ritualTome = Ids.ritualTome ? new ItemRitualTome() : null;
        
        registerItem(itemBoost, Names.itemBoost);
        registerItem(ritualTome, Names.ritualTome);
    }
    
    public static void initPureOres(){
    	for (Ore ore : Ore.ores) {
    		if (ore.getEnabled()) {
				ore.setPure(new ItemProcessedOre(ore));
				GameRegistry.registerItem(ore.getPure(), "pure" + ore.oreName().substring(3));
    		}
	}
    }
    
    public static void initThaumcraft() {

        // Item Initializations
        //essentiaCannon = new ItemEssentiaCannon(ItemIds.idESSENTIA_CANNON);
        itemMaterial = Ids.itemMaterial ? new ItemTHMaterial() : null;
        itemPen = Ids.pen ? new ItemPen() : null;
        itemWandCores = Ids.wandCores ? new ItemWandCores() : null;
        itemFusionFocus = Ids.focusFusion ? new ItemFusionFocus() : null;
        
        //Registry
        //GameRegistry.registerItem(essentiaCannon, LibNames.ESSENTIA_CANNON_NAME);
        registerItem(itemMaterial, Names.itemMaterial);
        registerItem(itemPen, Names.pen);
        registerItem(itemFusionFocus, Names.fusionFocus);
        
        registerItem(itemWandCores, Names.wandCores);
        if(Ids.wandCores){
        	WAND_ROD_ELECTRIC = new WandRod("electric", 25, new ItemStack(itemWandCores, 1, 0), 9, new ElectricWandUpdate(), new ResourceLocation("technom", "textures/models/electricWand.png"));
        }
    }
    
    //Instances
    public static Item itemBM;
    
    public static void initBloodMagic() {

    	//Initializations
        itemBM = Ids.matBM ? new ItemBMMaterial() : null;        
        
        //Registry
        registerItem(itemBM, Names.itemBM);
    }
    
    //Instances
    public static Item itemBO;
    public static Item manaBucket;
    
    public static void initBotania() {
    	//Initializations
    	itemBO = Ids.matBO ? new ItemBOMaterial() : null;
    	manaBucket = Ids.manaFluid ? new ItemManaBucket(TMBlocks.manaFluidBlock) : null;
    	
    	//Registry
    	registerItem(itemBO, Names.itemBO);
    	registerItem(manaBucket, Names.manaBucket);
    	registerBucket(TMBlocks.manaFluid, TMBlocks.manaFluidBlock, manaBucket);
    }

    private static void registerItem(Item item, String name) {
		if(item!=null) {
			GameRegistry.registerItem(item, name);
		}
	}
    
    private static void registerBucket(Fluid fluid, Block fluidBlock, Item bucket) {
		if(bucket!=null) {
			FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(bucket), new ItemStack(Items.bucket));
	    	EventRegister.buckets.put(fluidBlock, bucket);
		}
	}
}
