package democretes.items;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.api.wands.IWandFocus;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.Technomancy;
import democretes.compat.Thaumcraft;
import democretes.lib.Ref;

public class ItemFusionFocus extends ItemBase implements IWandFocus {

	public ItemFusionFocus(int id) {
		super(id);
		setCreativeTab(Technomancy.tabsTM);
	}

	AspectList aspects = new AspectList();
	NodeType type;
	NodeModifier mod;
	boolean cost = true;

	public Icon iconFocus;

	@Override
	public void registerIcons(IconRegister icon) {
		this.iconFocus = icon.registerIcon(Ref.TEXTURE_PREFIX + "focusFusion");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int par1) {
		return iconFocus;
	}

	@Override
	public boolean isItemTool(ItemStack par1ItemStack) {
		return true;
	}	

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public int getFocusColor() {
		return Aspect.FIRE.getColor();
	}

	@Override
	public WandFocusAnimation getAnimation() {
		return WandFocusAnimation.WAVE;
	}

	@Override
	public AspectList getVisCost() {			
		return new AspectList().add(Aspect.AIR, 3000).add(Aspect.FIRE, 3000).add(Aspect.WATER, 3000).add(Aspect.EARTH, 3000).add(Aspect.ORDER, 3000).add(Aspect.ENTROPY, 3000);		
	}

	@Override
	public boolean isVisCostPerTick() {
		return false;
	}

	@Override
	public ItemStack onFocusRightClick(ItemStack stack, World world, EntityPlayer player, MovingObjectPosition mop) {
		if(mop != null && stack != null) {
			if(this.type != null && this.mod != null && this.aspects != null) {
				this.cost = false;
				try {
					Thaumcraft.createNodeAt.invoke(null, world, mop.blockX, mop.blockY, mop.blockZ, type, mod, aspects);
				} catch (Exception e) {
					e.printStackTrace();
				}					
			}
		}
		return stack;
	}

	@Override
	public void onUsingFocusTick(ItemStack itemstack, EntityPlayer player, int count) {}

	@Override
	public String getSortingHelper(ItemStack itemstack) {
		Map<Integer,Integer> ench = EnchantmentHelper.getEnchantments(itemstack);
		String out="";
		for (Integer lvl:ench.values()) {
			out = out + lvl + "";
		}
		return out;
	}

	@Override
	public void onPlayerStoppedUsingFocus(ItemStack itemstack, World world,	EntityPlayer player, int count) {}

	@Override
	public boolean onFocusBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		try{
			TileEntity entity = player.worldObj.getBlockTileEntity(x, y, z);
			if(stack != null && entity != null) {
				if(Thaumcraft.TileNode.isInstance(entity) && Thaumcraft.ItemWandCasting.isInstance(stack.getItem())) {
					if(Thaumcraft.getAspects.invoke(entity) != null) {
						AspectList al = (AspectList) Thaumcraft.getAspects.invoke(entity);
						for(int i = 0; i < al.size(); i++) {
							if((mod == null || this.mod == Thaumcraft.getNodeModifier.invoke(entity)) &&
									(type == null || type == Thaumcraft.getNodeType.invoke(entity))){
								aspects.add(al.getAspects()[i], al.getAmount(al.getAspects()[i]));
								mod = (NodeModifier) Thaumcraft.getNodeModifier.invoke(entity);
								type =	(NodeType) Thaumcraft.getNodeType.invoke(entity);
							}
						}
						entity.blockType.breakBlock(player.worldObj, x, y, z, entity.blockType.blockID, entity.blockMetadata);
					}
				}
			}
		}catch(Exception e){e.printStackTrace();}
		return false;
	}

		@Override
		public boolean acceptsEnchant(int id) {
			if(id == Thaumcraft.enchantFrugal) {
				return true;
			}
			return false;
		}

		@Override
		public void addInformation(ItemStack stack,EntityPlayer player, List list, boolean par4) {
			AspectList al = this.getVisCost();
			if (al!=null && al.size()>0) {
				list.add(StatCollector.translateToLocal(isVisCostPerTick()?"item.Focus.cost2":"item.Focus.cost1"));
				for (Aspect aspect:al.getAspectsSorted()) {
					DecimalFormat myFormatter = new DecimalFormat("#####.##");
					String amount = myFormatter.format(al.getAmount(aspect)/100f);
					list.add(" \u00A7"+aspect.getChatcolor()+aspect.getName()+"\u00A7r x "+ amount);

				}
			}
		}

		@Override
		public int getItemEnchantability() {
			return 5;
		}

		@Override
		public EnumRarity getRarity(ItemStack itemstack)    {
			return EnumRarity.rare;
		}

		@Override
		public Icon getFocusDepthLayerIcon() {
			return null;
		}

		@Override
		public Icon getOrnament() {
			return null;
		}
	}