package theflogat.technomancy.common.blocks.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import org.lwjgl.input.Keyboard;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import theflogat.technomancy.util.ToolWrench;
import theflogat.technomancy.util.WorldHelper;
import theflogat.technomancy.util.helpers.InvHelper;
import cofh.api.energy.IEnergyHandler;

public abstract class BlockContainerAdvanced extends BlockContainerBase{

	public static HashMap<Item, RedstoneSet> map = new HashMap<Item, RedstoneSet>();
	public TileEntity dummy;

	public BlockContainerAdvanced() {
		map.put(Items.gunpowder, RedstoneSet.NONE);
		map.put(Items.redstone, RedstoneSet.HIGH);
		map.put(Item.getItemFromBlock(Blocks.redstone_torch), RedstoneSet.LOW);
		dummy = createNewTileEntity(null, 0);
	}

	public static Item getItem(RedstoneSet r){
		Iterator<Item> i = map.keySet().iterator();
		while(i.hasNext()){
			Item it = i.next();
			if(map.get(it)==r){
				return it;
			}
		}
		return null;
	}
	
	NBTTagCompound comp = new NBTTagCompound();

	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int meta) {
		w.getTileEntity(x, y, z).writeToNBT(comp);
		super.breakBlock(w, x, y, z, b, meta);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World w, int x, int y, int z, int metadata, int fortune) {
		ItemStack drop = new ItemStack(this, 1, metadata);
		drop.stackTagCompound = (NBTTagCompound) comp.copy();
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(drop);
		return drops;
	}

	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase player,	ItemStack items) {
		items.stackTagCompound.setInteger("x", x);
		items.stackTagCompound.setInteger("y", y);
		items.stackTagCompound.setInteger("z", z);
		if(w.getTileEntity(x, y, z)!=null){
			w.getTileEntity(x, y, z).readFromNBT(items.stackTagCompound);
		}
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = w.getTileEntity(x, y, z);
		ItemStack items = player.inventory.mainInventory[player.inventory.currentItem];
		if(te instanceof IWrenchable){
			if (ToolWrench.isWrench(items)){
				((IWrenchable)te).onWrenched();
				return true;
			}
		}
		if(te instanceof IRedstoneSensitive){
			if(items!=null && map.containsKey(items.getItem())){
				if(map.get(items.getItem())!=((IRedstoneSensitive)te).getCurrentSetting()){
					Item it = getItem(((IRedstoneSensitive)te).getCurrentSetting());
					if(!w.isRemote)
						WorldHelper.spawnEntItem(w, x, y, z, new ItemStack(it, 1));
					((IRedstoneSensitive)te).setNewSetting(map.get(items.getItem()));
					player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
					player.inventory.mainInventory[player.inventory.currentItem] = player.inventory.mainInventory[player.inventory.currentItem].stackSize==0 ? null:
						player.inventory.mainInventory[player.inventory.currentItem];
					return true;
				}
			}
		}
		if(te instanceof IUpgradable){
			if(player.getHeldItem()==null && player.isSneaking()){
				if(((IUpgradable)te).getBoost()){
					((IUpgradable)te).toggleBoost();
					if(!w.isRemote)
						WorldHelper.dropBoost(w, x, y, z, player);
					return true;
				}
			}
		}
		return false;
	}

	public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta){
		dummy.readFromNBT(comp);
		if(dummy instanceof IEnergyHandler){
			l.add("Energy:" + ((IEnergyHandler)dummy).getEnergyStored(null) + "/" + ((IEnergyHandler)dummy).getMaxEnergyStored(null));
		}
		if(CompatibilityHandler.th && dummy instanceof thaumcraft.api.aspects.IAspectContainer){
			if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				thaumcraft.api.aspects.AspectList al = ((thaumcraft.api.aspects.IAspectContainer)dummy).getAspects();
				for(thaumcraft.api.aspects.Aspect as: al.getAspects()){
					if(al.getAmount(as)>0){
						l.add(as.getChatcolor() + as.getName() + ":" + al.getAmount(as));
					}
				}
			}else{
				l.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("info.techno:a"));
			}
		}
		if(dummy instanceof IUpgradable){
			if(((IUpgradable)dummy).getBoost()){
				l.add("Potency Gem Installed");
			}
		}
		if(dummy instanceof IRedstoneSensitive){
			RedstoneSet set = RedstoneSet.load(comp);
			l.add("Redstone Setting:" + set.toString());
		}
		if(dummy instanceof IInventory){
			if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
				NBTTagList list = comp.getTagList("Items", 10);
				for (int i = 0; i < list.tagCount(); i++){
					NBTTagCompound stack = list.getCompoundTagAt(i);
					byte slot = stack.getByte("Slot");
					if ((slot >= 0) && (slot < ((IInventory)dummy).getSizeInventory())) {
						ItemStack it = ItemStack.loadItemStackFromNBT(stack);
						l.add(it.getDisplayName() + " " + it.stackSize);
					}
				}
			}else{
				l.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("info.techno:ctrl"));
			}
		}
		if(dummy instanceof IFluidHandler){
			FluidTankInfo[] infoTanks = ((IFluidHandler)dummy).getTankInfo(null);
			for(FluidTankInfo info:infoTanks)
				l.add(info.fluid.getLocalizedName() + ":" + info.fluid.amount + "/" + info.capacity);
		}
	}
}
