package theflogat.technomancy.common.items.technom;

import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemGlasses extends ItemArmor{

	public ItemGlasses() {
		super(Technomancy.existencePower, 0, 0);
		setUnlocalizedName(Ref.getId(Names.itemGlasses));
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return Ref.MOD_ID + ":textures/armor/" + Names.itemGlasses + ".png";
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(Ref.getAsset(Names.itemGlasses));
	}
	
}