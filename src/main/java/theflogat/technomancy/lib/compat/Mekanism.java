package theflogat.technomancy.lib.compat;

import java.util.ArrayList;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Mekanism implements IModModule{
	
	private static Mekanism mekanism;
	
	public static ItemStack energyCube;
	public static ItemStack steelCasing;
	public static ItemStack energyTablet;
	public static ItemStack enrichedAlloy;
	public static ItemStack lithiumDust;
	
	public static Mekanism getInstance(){
		if(mekanism==null){
			mekanism = new Mekanism();
		}
		return mekanism;
	}

	@Override
	public void Init() {
		energyCube = new ItemStack(GameRegistry.findItem("Mekanism", "EnergyCube"), 1);
		setEnergyCubeTier(BaseTier.ADVANCED, energyCube);
		Item BasicBlock = GameRegistry.findItem("Mekanism", "BasicBlock");
		ArrayList<ItemStack> stackBBlocks = new ArrayList<ItemStack>();
		BasicBlock.getSubItems(BasicBlock, CreativeTabs.tabAllSearch, stackBBlocks);
		for(ItemStack items : stackBBlocks){
			if(items.getUnlocalizedName().toLowerCase().contains("steel")){
				steelCasing = items;
			}
		}
		energyTablet = GameRegistry.findItemStack("Mekanism", "EnergyTablet", 1);
		enrichedAlloy = GameRegistry.findItemStack("Mekanism", "EnrichedAlloy", 1);
		Item OtherDust = GameRegistry.findItem("Mekanism", "OtherDust");
		ArrayList<ItemStack> stackODusts = new ArrayList<ItemStack>();
		OtherDust.getSubItems(OtherDust, CreativeTabs.tabAllSearch, stackODusts);
		for(ItemStack items : stackODusts){
			if(items.getUnlocalizedName().toLowerCase().contains("lithium")){
				lithiumDust = items;
			}
		}
		if(energyCube != null && steelCasing != null && energyTablet != null && enrichedAlloy != null &&
				lithiumDust != null) {
			Technomancy.logger.info("Mekanism compatibility module loaded.");
		} else {
			Technomancy.logger.warn("Mekanism compatibility module failed to load.");
			CompatibilityHandler.mk = false;
		}
	}
	
	public static enum BaseTier{
		BASIC("Basic"),
		ADVANCED("Advanced"),
		ELITE("Elite"),
		ULTIMATE("Ultimate"),
		CREATIVE("Creative");
		
		public String getName(){
			return name;
		}
		
		String name;
		
		private BaseTier(String s){
			name = s;
		}
	}
	
	public void setEnergyCubeTier(BaseTier tier, ItemStack items){
		if(tier==BaseTier.BASIC){
			items.stackTagCompound = null;
		}else{
			items.stackTagCompound = new NBTTagCompound();
			items.stackTagCompound.setString("tier", tier.name);
		}
	}

	@Override
	public void PostInit() {
		
	}

	@Override
	public void RegisterItems() {
		
	}

	@Override
	public void RegisterBlocks() {
		
	}

	@Override
	public void RegisterRecipes() {
		
	}
}