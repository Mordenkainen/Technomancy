package theflogat.technomancy.lib.compat.waila;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;

public class WailaProvider {
	public static void callbackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new DynamoHUDHandler(), TileDynamoBase.class);
		
		registrar.registerBodyProvider(new CatalystHUDHandler(), TileCatalyst.class);
		
		registrar.registerBodyProvider(new ProcessorHUDHandler(), TileProcessorBase.class);
		
		if(CompatibilityHandler.bo) {
			IWailaDataProvider manaExchanger = new ManaExchangerHUDHandler();
			registrar.registerBodyProvider(manaExchanger, TileManaExchanger.class);
			registrar.registerNBTProvider(manaExchanger, TileManaExchanger.class);
		}
		
		if(CompatibilityHandler.bm) {
			registrar.registerBodyProvider(new BloodFabricatorHUDHandler(), TileBloodFabricator.class);
		}
    }
}
