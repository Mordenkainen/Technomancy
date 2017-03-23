package tuhljin.automagy.api.inventarium;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class DefaultInventariumContentsProvider implements IInventariumContentsProvider {

	public static final int PRIORITY = 500;

	@Override
	public boolean canHandleTile(TileEntity te) {
		return te instanceof IInventory;
	}

	@Override
	public int getSlotCount(TileEntity te, int setID) {
		return ((IInventory) te).getSizeInventory();
	}

	@Override
	public ItemStack[] getContents(TileEntity te, int setID) {
		IInventory inv = (IInventory) te;
		int size = inv.getSizeInventory();
		ItemStack stacks[] = new ItemStack[size];
		for (int i = 0; i < size; i++) {
			stacks[i] = inv.getStackInSlot(i);
		}
		return stacks;
	}

}
