package democretes.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.WandRod;
import cpw.mods.fml.common.registry.GameRegistry;
import democretes.lib.Ids;
import democretes.lib.Names;

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
    
    public static void initThaumcraft() { 	

        // Item Initializations
        //essentiaCannon = new ItemEssentiaCannon(ItemIds.idESSENTIA_CANNON);
        itemMaterial = new ItemMaterial(Ids.itemMaterial);
        itemPen = new ItemPen(Ids.pen);
        itemWandCores = new ItemWandCores(Ids.wandCores);
        itemFusionFocus = new ItemFusionFocus(Ids.focusFusion);        
        processedIron = new ItemProcessedOre.ItemProcessedIron(Ids.procIron);
        processedGold = new ItemProcessedOre.ItemProcessedGold(Ids.procGold);
        processedCopper = new ItemProcessedOre.ItemProcessedCopper(Ids.procCopp);
        processedTin = new ItemProcessedOre.ItemProcessedTin(Ids.procTin);
        processedSilver = new ItemProcessedOre.ItemProcessedSilver(Ids.procSilver);
        processedLead = new ItemProcessedOre.ItemProcessedLead(Ids.procLead);
        processedNickel = new ItemProcessedOre.ItemProcessedNickel(Ids.procNickel);
        
        //Registry
        //GameRegistry.registerItem(essentiaCannon, LibNames.ESSENTIA_CANNON_NAME);
        GameRegistry.registerItem(itemMaterial, Names.itemMaterial);
        GameRegistry.registerItem(itemPen, Names.pen);
        GameRegistry.registerItem(itemFusionFocus, Names.fusionFocus);
        GameRegistry.registerItem(processedIron, Names.pureIron);
        
        
        GameRegistry.registerItem(itemWandCores, Names.wandCores);
        WAND_ROD_ELECTRIC = new WandRod("electric", 25, new ItemStack(itemWandCores, 1, 0), 9, new ElectricWandUpdate(), new ResourceLocation("technom", "textures/models/electricWand.png"));
    }
    
    //Instances
    public static Item itemBM;
    
    public static void initBloodMagic() {

    	//Initializations
        itemBM = new ItemBMMaterial(Ids.matBM);        
        
        //Registry
        GameRegistry.registerItem(itemBM, Names.itemBM);
    }
    
    //Instances
    public static Item itemBO;
    
    public static void initBotania() {
    	//Initializations
    	itemBO = new ItemBOMaterial(Ids.matBO);
    	
    	//Registry
    	GameRegistry.registerItem(itemBO, Names.itemBO);
    }

    

}
