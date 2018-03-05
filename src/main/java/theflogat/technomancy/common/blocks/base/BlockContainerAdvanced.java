package theflogat.technomancy.common.blocks.base;

import java.util.ArrayList;

import cofh.redstoneflux.api.IEnergyHandler;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.lwjgl.input.Keyboard;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import theflogat.technomancy.util.ToolWrench;
import theflogat.technomancy.util.helpers.WorldHelper;

public abstract class BlockContainerAdvanced extends BlockContainerRedstone{

	public TileEntity dummy;

	public BlockContainerAdvanced() {
		super();
		dummy = createNewTileEntity(null, 0);
	}

	NBTTagCompound comp = new NBTTagCompound();

	@Override
	public void breakBlock(World w, BlockPos pos, IBlockState state) {
		TileEntity tile = w.getTileEntity(pos);
		comp = new NBTTagCompound();
		if(tile != null) {
			tile.writeToNBT(comp);
			w.removeTileEntity(pos);
		}
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ItemStack drop = new ItemStack(this, 1, getMetaFromState(state));
		drop.setTagCompound((NBTTagCompound)comp.copy());
		drops.add(drop);
	}

	@Override
	public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(stack != null && stack.hasTagCompound()) {
			stack.getTagCompound().setInteger("x", pos.getX());
			stack.getTagCompound().setInteger("y", pos.getY());
			stack.getTagCompound().setInteger("z", pos.getZ());
			if(w.getTileEntity(pos)!=null){
				w.getTileEntity(pos).readFromNBT(stack.getTagCompound());
			}
		}
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = w.getTileEntity(pos);
		ItemStack items = player.inventory.mainInventory.get(player.inventory.currentItem);
		if(te instanceof IWrenchable){
			if (ToolWrench.isWrench(items)){
				((IWrenchable)te).onWrenched(player.isSneaking());
				return true;
			}
		}
		if(te instanceof IUpgradable){
			if(items==null && player.isSneaking()){
				if(((IUpgradable)te).getBoost()){
					if(!w.isRemote) {
						((IUpgradable)te).toggleBoost();
						WorldHelper.dropBoost(w, pos.getX(), pos.getY(), pos.getZ());
						w.notifyBlockUpdate(pos, state, state, 3);
					}
					return true;
				}
			}
		}
		return super.onBlockActivated(w, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}

	public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta){
		dummy.readFromNBT(comp);
		if(dummy instanceof IEnergyHandler){
			l.add("Energy: " + ((IEnergyHandler)dummy).getEnergyStored(null) + "/" + ((IEnergyHandler)dummy).getMaxEnergyStored(null));
		}
		if(dummy instanceof IUpgradable){
			if(((IUpgradable)dummy).getBoost()){
				l.add("Potency Gem Installed");
			}
		}
		if(dummy instanceof IRedstoneSensitive){
			RedstoneSet set = RedstoneSet.load(comp);
			l.add("Redstone Setting: " + set.toString());
		}
		if(dummy instanceof IInventory){
			if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
				NBTTagList list = comp.getTagList("Items", 10);
				for (int i = 0; i < list.tagCount(); i++){
					NBTTagCompound stack = list.getCompoundTagAt(i);
					byte slot = stack.getByte("Slot");
					if ((slot >= 0) && (slot < ((IInventory)dummy).getSizeInventory())) {
						ItemStack it = new ItemStack(stack);
						l.add(it.getDisplayName() + " " + it.getCount());
					}
				}
			}else{
				l.add(ChatFormatting.WHITE.toString() + ChatFormatting.ITALIC + I18n.format("info.techno:ctrl"));
			}
		}
		if(dummy instanceof IFluidTank) {
			FluidTankInfo infoTanks = ((IFluidTank) dummy).getInfo();
			if (infoTanks.fluid != null) {
				l.add(infoTanks.fluid.getLocalizedName() + ": " + infoTanks.fluid.amount + "/" + infoTanks.capacity);
			}

		}
	}
}
