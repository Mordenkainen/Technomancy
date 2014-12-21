package theflogat.technomancy.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.WandRod;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TMItems {

	// Item Instances
    public static Item essentiaCannon;
    public static Item itemMaterial;
    public static Item itemPen;
    public static Item itemWandCores;
    public static Item itemFusionFocus;
    public static Item processedIron;
    public static Item processedGold;
    public static Item processedCopper;
    public static Item processedTin;
    public static Item processedSilver;
    public static Item processedLead;
    public static Item processedNickel;
    
    public static WandRod WAND_ROD_ELECTRIC;
    
    public static void initTechnomancy(){
        processedIron = Ids.procIron ? new ItemProcessedOre(14211288, Names.pureIron) : null;
        processedGold = Ids.procGold ? new ItemProcessedOre(14605824, Names.pureGold) : null;
        processedCopper = Ids.procCopp ? new ItemProcessedOre(16758834, Names.pureCopper) : null;
        processedTin = Ids.procTin ? new ItemProcessedOre(12243942, Names.pureTin) : null;
        processedSilver = Ids.procSilver ? new ItemProcessedOre(13163770, Names.pureSilver) : null;
        processedLead = Ids.procLead ? new ItemProcessedOre(8163006, Names.pureLead) : null;
        processedNickel = Ids.procNickel ? new ItemProcessedOre(16053453, Names.pureNickel) : null;
        

        registerItem(processedIron, Names.pureIron);
        registerItem(processedGold, Names.pureGold);
        registerItem(processedCopper, Names.pureCopper);
        registerItem(processedTin, Names.pureTin);
        registerItem(processedSilver, Names.pureSilver);
        registerItem(processedLead, Names.pureLead);
        registerItem(processedNickel, Names.pureNickel);
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
    
    public static void initBotania() {
    	//Initializations
    	itemBO = Ids.matBO ? new ItemBOMaterial() : null;
    	
    	//Registry
    	registerItem(itemBO, Names.itemBO);
    }

    
    private static void registerItem(Item item, String name) {
		if(item!=null)
			GameRegistry.registerItem(item, name);
	}
}
