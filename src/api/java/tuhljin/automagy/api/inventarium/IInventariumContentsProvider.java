package tuhljin.automagy.api.inventarium;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IInventariumContentsProvider {

	/** Return whether this provider should handle the contents for the given tile entity. The other two methods here
	 *  won't be called for the tile entity unless this returned true.
	 *  
	 *  All of the methods here may be called frequently, but especially this one, so make it efficient. */
	public boolean canHandleTile(TileEntity te);

	/** Return the number of slots in the inventory. This is used to determine how many slots of the maximum allowed
	 *  are being examined by the Inventarium. This should normally be the maximum number of slots the container
	 *  provides, not the number of slots that are currently occupied.
	 *  
	 *  setID is used to help providers that need to know whether two calls to the method are part of the same series/set
	 *  of calls. If they are, the setIDs will match. If a tick has passed or a different Inventarium (or, in the case of
	 *  local contents, Unseen Scribe) is checking things, then the identifier will change. This is useful if, for
	 *  example, a single tile entity will cover the contents for multiple tiles and so these other tiles shouldn't add
	 *  any contents when the identifier is the same. All calls in a series will be handled one after another, with no
	 *  other series of calls mixed in, so in the example situation, only the previous identifier will need to be
	 *  remembered.
	 *  
	 *  If its return is less than 1, then getContents will not be called for the given tile entity during this set of
	 *  calls. */
	public int getSlotCount(TileEntity te, int setID);

	/** Return an array of item stacks specifying the contents of the container. All contents specified will be counted,
	 *  even if the return from getSizeInventory is smaller than the length of this array. Stack sizes larger than the
	 *  normal maximum are allowed. Any items of the same type will be added together automatically (so giving two
	 *  stacks of 64 cobblestone will result in 128 cobblestone being recorded).
	 *  
	 *  See the getSlotCount method's description of setID for information on that parameter. */
	public ItemStack[] getContents(TileEntity te, int setID);

}
