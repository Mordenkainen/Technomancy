package thaumcraft.api.wands;

import java.util.LinkedHashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WandRod{
	private String tag;
	private int craftCost;
	int capacity;
	protected ResourceLocation texture;
	ItemStack item;
	IWandRodOnUpdate onUpdate;
	boolean glow;
	public static LinkedHashMap<String, WandRod> rods = new LinkedHashMap();

	public WandRod(String tag, int capacity, ItemStack item, int craftCost, ResourceLocation texture) {
		setTag(tag);
		this.capacity = capacity;
		this.texture = texture;
		this.item = item;
		setCraftCost(craftCost);
		rods.put(tag, this);
	}

	public WandRod(String tag, int capacity, ItemStack item, int craftCost, IWandRodOnUpdate onUpdate, ResourceLocation texture) {
		setTag(tag);
		this.capacity = capacity;
		this.texture = texture;
		this.item = item;
		setCraftCost(craftCost);
		rods.put(tag, this);
		this.onUpdate = onUpdate;
	}

	public WandRod(String tag, int capacity, ItemStack item, int craftCost) {
		setTag(tag);
		this.capacity = capacity;
		texture = new ResourceLocation("thaumcraft", "textures/models/wand_rod_" + getTag() + ".png");
		this.item = item;
		setCraftCost(craftCost);
		rods.put(tag, this);
	}

	public WandRod(String tag, int capacity, ItemStack item, int craftCost, IWandRodOnUpdate onUpdate) {
		setTag(tag);
		this.capacity = capacity;
		texture = new ResourceLocation("thaumcraft", "textures/models/wand_rod_" + getTag() + ".png");
		this.item = item;
		setCraftCost(craftCost);
		rods.put(tag, this);
		this.onUpdate = onUpdate;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	public void setTexture(ResourceLocation texture) {
		this.texture = texture;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public int getCraftCost() {
		return craftCost;
	}

	public void setCraftCost(int craftCost) {
		this.craftCost = craftCost;
	}

	public IWandRodOnUpdate getOnUpdate() {
		return onUpdate;
	}

	public void setOnUpdate(IWandRodOnUpdate onUpdate) {
		this.onUpdate = onUpdate;
	}

	public boolean isGlowing() {
		return glow;
	}

	public void setGlowing(boolean hasGlow) {
		glow = hasGlow;
	}
}