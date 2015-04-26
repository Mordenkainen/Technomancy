package theflogat.technomancy.proxies;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import theflogat.technomancy.client.renderers.blocks.BlockAdvDeconTableRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockBiomeMorpherRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockBloodDynamoRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockBloodFabricatorRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockCatalystRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockCreativeJarRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockCrystalRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockEldritchConsumerRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockElectricBellowsRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockEssentiaContainerRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockEssentiaDynamoRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockEssentiaTransmitterRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockFlowerDynamoRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockFluxLampRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockItemTransmitterRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockManaFabricatorRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockNodeDynamoRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockNodeGeneratorRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockReconstructorRenderer;
import theflogat.technomancy.client.renderers.gui.GuiProcessorBM;
import theflogat.technomancy.client.renderers.gui.GuiProcessorBO;
import theflogat.technomancy.client.renderers.gui.GuiProcessorTC;
import theflogat.technomancy.client.renderers.gui.GuiRitualTome;
import theflogat.technomancy.client.renderers.tiles.TileAdvDeconTableRenderer;
import theflogat.technomancy.client.renderers.tiles.TileBiomeMorpherRenderer;
import theflogat.technomancy.client.renderers.tiles.TileBloodDynamoRenderer;
import theflogat.technomancy.client.renderers.tiles.TileBloodFabricatorRenderer;
import theflogat.technomancy.client.renderers.tiles.TileCatalystRenderer;
import theflogat.technomancy.client.renderers.tiles.TileCreativeJarRenderer;
import theflogat.technomancy.client.renderers.tiles.TileCrystalRenderer;
import theflogat.technomancy.client.renderers.tiles.TileEldritchConsumerRenderer;
import theflogat.technomancy.client.renderers.tiles.TileElectricBellowsRenderer;
import theflogat.technomancy.client.renderers.tiles.TileEssentiaContainerRenderer;
import theflogat.technomancy.client.renderers.tiles.TileEssentiaDynamoRenderer;
import theflogat.technomancy.client.renderers.tiles.TileEssentiaTransmitterRenderer;
import theflogat.technomancy.client.renderers.tiles.TileFlowerDynamoRenderer;
import theflogat.technomancy.client.renderers.tiles.TileFluxLampRenderer;
import theflogat.technomancy.client.renderers.tiles.TileItemTransmitterRenderer;
import theflogat.technomancy.client.renderers.tiles.TileManaFabricatorRenderer;
import theflogat.technomancy.client.renderers.tiles.TileNodeDynamoRenderer;
import theflogat.technomancy.client.renderers.tiles.TileNodeGeneratorRenderer;
import theflogat.technomancy.client.renderers.tiles.TileReconstructorRenderer;
import theflogat.technomancy.common.tiles.bloodmagic.dynamos.TileBloodDynamo;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBMProcessor;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import theflogat.technomancy.common.tiles.botania.dynamos.TileFlowerDynamo;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.common.tiles.botania.machines.TileManaFabricator;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.common.tiles.technom.TileCrystal;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileNodeDynamo;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileAdvDeconTable;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBiomeMorpher;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileElectricBellows;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileReconstructor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileCreativeJar;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.IGuiHandler;

public class ClientProxy extends CommonProxy implements IGuiHandler{

    @Override
    public void initSounds() {

    }

    @Override
    public void initRenderers() {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new TileCrystalRenderer());
		RenderIds.idCrystal = RenderingRegistry.getNextAvailableRenderId();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileCatalyst.class, new TileCatalystRenderer());
		RenderIds.idCatalyst = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(new BlockCrystalRenderer());
		RenderingRegistry.registerBlockHandler(new BlockCatalystRenderer());
    	
    	if(CompatibilityHandler.th) {
    		ClientRegistry.bindTileEntitySpecialRenderer(TileNodeDynamo.class, new TileNodeDynamoRenderer());
    		RenderIds.idNodeDynamo = RenderingRegistry.getNextAvailableRenderId();
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileEssentiaDynamo.class, new TileEssentiaDynamoRenderer());
    		RenderIds.idEssentiaDynamo = RenderingRegistry.getNextAvailableRenderId();
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileEssentiaContainer.class, new TileEssentiaContainerRenderer());
    		RenderIds.idEssentiaContainer = RenderingRegistry.getNextAvailableRenderId();    	
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileBiomeMorpher.class, new TileBiomeMorpherRenderer());
    		RenderIds.idBiomeMorpher = RenderingRegistry.getNextAvailableRenderId(); 
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileNodeGenerator.class, new TileNodeGeneratorRenderer());
    		RenderIds.idNodeGenerator = RenderingRegistry.getNextAvailableRenderId(); 

    		ClientRegistry.bindTileEntitySpecialRenderer(TileFluxLamp.class, new TileFluxLampRenderer());
    		RenderIds.idFluxLamp = RenderingRegistry.getNextAvailableRenderId();
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileEssentiaTransmitter.class, new TileEssentiaTransmitterRenderer());
    		RenderIds.idEssentiaTransmitter = RenderingRegistry.getNextAvailableRenderId(); 
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileElectricBellows.class, new TileElectricBellowsRenderer());
    		RenderIds.idElectricBellows = RenderingRegistry.getNextAvailableRenderId();
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileCreativeJar.class, new TileCreativeJarRenderer());
    		RenderIds.idCreativeJar = RenderingRegistry.getNextAvailableRenderId(); 
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileReconstructor.class, new TileReconstructorRenderer());
    		RenderIds.idReconstructor = RenderingRegistry.getNextAvailableRenderId();
    		
    		ClientRegistry.bindTileEntitySpecialRenderer(TileAdvDeconTable.class, new TileAdvDeconTableRenderer());
    		RenderIds.idAdvDeconTable = RenderingRegistry.getNextAvailableRenderId();
    		
    		ClientRegistry.bindTileEntitySpecialRenderer(TileEldritchConsumer.class, new TileEldritchConsumerRenderer());
    		RenderIds.idEldrichConsumer = RenderingRegistry.getNextAvailableRenderId();
    		
    		ClientRegistry.bindTileEntitySpecialRenderer(TileItemTransmitter.class, new TileItemTransmitterRenderer());
    		RenderIds.idItemTransmitter = RenderingRegistry.getNextAvailableRenderId();
    		
    		RenderingRegistry.registerBlockHandler(new BlockNodeDynamoRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockEssentiaDynamoRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockEssentiaContainerRenderer());  	
        	RenderingRegistry.registerBlockHandler(new BlockBiomeMorpherRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockNodeGeneratorRenderer()); 
        	RenderingRegistry.registerBlockHandler(new BlockFluxLampRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockEssentiaTransmitterRenderer());  
        	RenderingRegistry.registerBlockHandler(new BlockElectricBellowsRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockCreativeJarRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockReconstructorRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockEldritchConsumerRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockAdvDeconTableRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockItemTransmitterRenderer());
    	}
    	
    	if(CompatibilityHandler.bm) {
    		ClientRegistry.bindTileEntitySpecialRenderer(TileBloodDynamo.class, new TileBloodDynamoRenderer());
    		RenderIds.idBloodDynamo = RenderingRegistry.getNextAvailableRenderId();
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileBloodFabricator.class, new TileBloodFabricatorRenderer());
    		RenderIds.idBloodFabricator = RenderingRegistry.getNextAvailableRenderId();    		

        	RenderingRegistry.registerBlockHandler(new BlockBloodDynamoRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockBloodFabricatorRenderer());
    	}
    	
    	if(CompatibilityHandler.bo) {
    		ClientRegistry.bindTileEntitySpecialRenderer(TileManaFabricator.class, new TileManaFabricatorRenderer());
    		RenderIds.idManaFabricator = RenderingRegistry.getNextAvailableRenderId();
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileFlowerDynamo.class, new TileFlowerDynamoRenderer());
    		RenderIds.idFlowerDynamo = RenderingRegistry.getNextAvailableRenderId();
    		
        	RenderingRegistry.registerBlockHandler(new BlockManaFabricatorRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockFlowerDynamoRenderer());  
    	}	    	
    	  	
    }

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if(world instanceof WorldClient) {
			switch(ID) {
				case 0: 
					return new GuiProcessorTC(player.inventory, ((TileTCProcessor)world.getTileEntity(x, y, z)));
				case 1:
					return new GuiProcessorBM(player.inventory, ((TileBMProcessor)world.getTileEntity(x, y, z)));
				case 2:
					return new GuiProcessorBO(player.inventory, ((TileBOProcessor)world.getTileEntity(x, y, z)));
				case 3:
					return new GuiRitualTome();
			}
		}
		return null;
	}
}
