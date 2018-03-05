package theflogat.technomancy.asm;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import theflogat.technomancy.lib.Ref;

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
		metadata.authorList.add("abused_master");
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
	
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}
