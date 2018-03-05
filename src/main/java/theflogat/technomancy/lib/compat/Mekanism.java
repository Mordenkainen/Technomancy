package theflogat.technomancy.lib.compat;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
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
	public void preInit() {

	}

	@Override
	public void Init() {
		energyCube = new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("mekanism:energycube")), 1);
		setEnergyCubeTier(BaseTier.ADVANCED, energyCube);
		steelCasing = new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("mekanism:basicblock")), 1, 8);
		energyTablet = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("mekanism:energytablet")), 1);
		enrichedAlloy = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("mekanism:enrichedalloy")), 1);
		lithiumDust = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("mekanism:otherdust")), 1, 4);
		if(energyCube != null && steelCasing != null && energyTablet != null && enrichedAlloy != null &&
				lithiumDust != null) {
			Technomancy.logger.info("Mekanism compatibility module loaded.");
		} else {
			Technomancy.logger.warn("Mekanism compatibility module failed to load.");
			CompatibilityHandler.mk = false;
		}
	}
	
	public static enum BaseTier {
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
			items.setTagCompound(null);
		}else{
			items.setTagCompound(new NBTTagCompound());
			items.getTagCompound().setString("tier", tier.name);
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