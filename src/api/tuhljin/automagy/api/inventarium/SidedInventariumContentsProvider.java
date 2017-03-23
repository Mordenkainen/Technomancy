package tuhljin.automagy.api.inventarium;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class SidedInventariumContentsProvider implements IInventariumContentsProvider {

	public static final int PRIORITY = 499;
	public static int ACCESS_SIDE = ForgeDirection.UP.ordinal();

	@Override
	public boolean canHandleTile(TileEntity te) {
		return te instanceof ISidedInventory;
	}

	@Override
	public int getSlotCount(TileEntity te, int setID) {
		int[] slots = ((ISidedInventory) te).getAccessibleSlotsFromSide(ACCESS_SIDE);
		return slots == null ? 0 : slots.length;
	}

	@Override
	public ItemStack[] getContents(TileEntity te, int setID) {
		ISidedInventory inv = (ISidedInventory) te;
		int[] slots = inv.getAccessibleSlotsFromSide(ACCESS_SIDE);
		if (slots == null)  return null;
		int size = slots.length;
		ItemStack stacks[] = new ItemStack[size];
		int i = 0;
		for (int slot : slots) {
			ItemStack stack = inv.getStackInSlot(slot);
			if (inv.canExtractItem(slot, stack, ACCESS_SIDE)) {
				stacks[i] = stack;
				i++;
			}
		}
		return stacks;
	}

}
