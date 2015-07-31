package tuhljin.automagy.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.block.Block;
import tuhljin.automagy.api.inventarium.IInventariumContentsProvider;


/** Automagy API
 *  Author: Tuhljin
 *  Revision: 2
 *  */

public class AutomagyAPI {

	/** Blocks in this list will be ignored by Unseen Scribes, preventing their use by the Inventarium.
	 *  (Initially populated based on Automagy's config file but you can add to it directly if needed.) */
	public static ArrayList<Block> invContainerBlacklist = new ArrayList<Block>();


	public static Map<Integer, List<IInventariumContentsProvider>> invContentsProviders = new TreeMap<Integer, List<IInventariumContentsProvider>>();


	/** Use this to register your provider. Providers registered with a lower value for priority will be checked before
	 *  those using a higher number. If a provider returns true when its canHandleTile method is called, then no other
	 *  providers will be queried about the tile entity in question, so it is important to use a priority value that will
	 *  result in the correct class taking on the provider role.
	 *  
	 *  To give an example, here is the call Automagy uses to handle standard inventories:
	 *  AutomagyAPI.registerInventariumContentsProvider(new DefaultInventariumContentsProvider(), DefaultInventariumContentsProvider.PRIORITY);
	 *  */
	public static void registerInventariumContentsProvider(IInventariumContentsProvider provider, int priority) {
		if (!invContentsProviders.containsKey(priority)) {
			invContentsProviders.put(priority, new ArrayList<IInventariumContentsProvider>());
		}
		invContentsProviders.get(priority).add(provider);
	}

}
