package theflogat.technomancy.common.items.tome;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemRitualTome extends Item{
	
	public ItemRitualTome() {
		setCreativeTab(Technomancy.tabsTM);
		setUnlocalizedName(Ref.getId(Names.ritualTome));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		player.openGui(Technomancy.instance, 3, worldIn, 0, 0, 0);
		return super.onItemRightClick(worldIn, player, handIn);
	}

	/*
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(Ref.getAsset(Names.ritualTome));
	}
	*/

	public static class Res{
		public static final String E = ".png";
		
		public static final String BH1 = "blackHoleT1"+E;
		public static final String BH2 = "blackHoleT2"+E;
		public static final String BH3 = "blackHoleT3"+E;
		
		public static final String CI1 = "caveInT1"+E;
		public static final String CI2 = "caveInT2"+E;
		public static final String CI3 = "caveInT3"+E;
		
		public static final String PU1 = "purificationT1"+E;
		public static final String PU2 = "purificationT2"+E;
		public static final String PU3 = "purificationT3"+E;
		
		public static final String F1 = "fireT1"+E;
		public static final String F2 = "fireT2"+E;
		public static final String F3 = "fireT3"+E;
		
		public static final String W1 = "waterT1"+E;
		public static final String W2 = "waterT2"+E;
		public static final String W3 = "waterT3"+E;
		
		public static final String FT = "fountain"+E;
		public static final String ET = "extract"+E;
	}
	
}
