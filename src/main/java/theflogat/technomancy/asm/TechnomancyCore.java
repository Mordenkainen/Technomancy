package theflogat.technomancy.asm;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import theflogat.technomancy.lib.Ref;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod.EventHandler;

public class TechnomancyCore extends DummyModContainer {
	public TechnomancyCore() {
		super(new ModMetadata());
		ModMetadata metadata = getMetadata();
		metadata.modId = Ref.MOD_ID + "Core";
		metadata.name = Ref.MOD_NAME + " Core";
		metadata.version = Ref.MOD_VERSION;
		metadata.authorList.add("Democretes");
		metadata.authorList.add("theflogat");
		metadata.authorList.add("Mordenkainen");
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}
	
	@Subscribe
	public void modConstruction(FMLConstructionEvent event) {
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}
