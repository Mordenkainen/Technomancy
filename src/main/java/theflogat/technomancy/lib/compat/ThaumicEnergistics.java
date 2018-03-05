package theflogat.technomancy.lib.compat;

/**
import thaumicenergistics.api.IThETransportPermissions;
import thaumicenergistics.api.ThEApi;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaFusor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileCreativeJar;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import theflogat.technomancy.lib.Ids;

public class ThaumicEnergistics implements IModModule{
	
	private static ThaumicEnergistics thaumicEnergistics;
	
	public static ThaumicEnergistics getInstance(){
		if(thaumicEnergistics==null){
			thaumicEnergistics = new ThaumicEnergistics();
		}
		return thaumicEnergistics;
	}

	@Override
	public void Init() {}

	@Override
	public void PostInit() {
		IThETransportPermissions perms = ThEApi.instance().transportPermissions();
		
		if(Ids.fluxLamp) {
			perms.addAspectContainerTileToInjectPermissions(TileFluxLamp.class, 32);
		}
		
		if(Ids.contEssentia) {
			perms.addAspectContainerTileToBothPermissions(TileEssentiaContainer.class, 640);
			perms.addAspectContainerTileToExtractPermissions(TileCreativeJar.class, 320);
		}
		
		if(Ids.dynEssentia) {
			perms.addAspectContainerTileToInjectPermissions(TileEssentiaDynamo.class, 64);
		}
		
		if(Ids.fusor) {
			perms.addAspectContainerTileToBothPermissions(TileEssentiaFusor.class, 64);
		}
		
		if(Ids.nodeGen) {
			perms.addAspectContainerTileToBothPermissions(TileNodeGenerator.class, 256);
			perms.addAspectContainerTileToBothPermissions(TileFakeAirNG.class, 256);
		}
		
		if(Ids.condenser) {
			perms.addAspectContainerTileToExtractPermissions(TileCondenser.class, 64);
		}
		
		if(Ids.eldrichConsumer) {
			perms.addAspectContainerTileToExtractPermissions(TileEldritchConsumer.class, 0);
		}
		
		if(Ids.processorTC) {
			perms.addAspectContainerTileToInjectPermissions(TileTCProcessor.class, 64);
		}
	}

	@Override
	public void RegisterItems() {
		
	}

	@Override
	public void RegisterBlocks() {
		
	}

	@Override
	public void RegisterRecipes() {
		
	}
}
 */