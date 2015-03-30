package theflogat.technomancy.proxies;

import net.minecraft.client.Minecraft;
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
import theflogat.technomancy.client.renderers.blocks.BlockFlowerDynamoRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockFluxLampRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockManaFabricatorRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockNodeDynamoRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockNodeGeneratorRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockReconstructorRenderer;
import theflogat.technomancy.client.renderers.blocks.BlockTeslaCoilRenderer;
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
import theflogat.technomancy.client.renderers.tiles.TileFlowerDynamoRenderer;
import theflogat.technomancy.client.renderers.tiles.TileFluxLampRenderer;
import theflogat.technomancy.client.renderers.tiles.TileManaFabricatorRenderer;
import theflogat.technomancy.client.renderers.tiles.TileNodeDynamoRenderer;
import theflogat.technomancy.client.renderers.tiles.TileNodeGeneratorRenderer;
import theflogat.technomancy.client.renderers.tiles.TileReconstructorRenderer;
import theflogat.technomancy.client.renderers.tiles.TileTeslaCoilRenderer;
import theflogat.technomancy.common.tiles.TileBOProcessor;
import theflogat.technomancy.common.tiles.bm.TileBMProcessor;
import theflogat.technomancy.common.tiles.dynamos.TileBloodDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileFlowerDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileNodeDynamo;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.common.tiles.technom.TileCrystal;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileAdvDeconTable;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBiomeMorpher;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBloodFabricator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileElectricBellows;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileManaFabricator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileReconstructor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileWirelessCoil;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileCreativeJar;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.lib.compat.Thaumcraft;
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
    	
    	if(Thaumcraft.th) {
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
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileWirelessCoil.class, new TileTeslaCoilRenderer());
    		RenderIds.idTeslaCoil = RenderingRegistry.getNextAvailableRenderId(); 
    	
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
    		
    		RenderingRegistry.registerBlockHandler(new BlockNodeDynamoRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockEssentiaDynamoRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockEssentiaContainerRenderer());  	
        	RenderingRegistry.registerBlockHandler(new BlockBiomeMorpherRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockNodeGeneratorRenderer()); 
        	RenderingRegistry.registerBlockHandler(new BlockFluxLampRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockTeslaCoilRenderer());  
        	RenderingRegistry.registerBlockHandler(new BlockElectricBellowsRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockCreativeJarRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockReconstructorRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockEldritchConsumerRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockAdvDeconTableRenderer());
    	}
    	
    	if(BloodMagic.bm) {
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
	public void essentiaTrail(World world, double x, double y, double z, double tx, double ty, double tz, int color) {
    	if(Minecraft.getMinecraft().renderViewEntity == null) {
    	      return;
    	}
    	if(Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer == false) {
    		return;
    	}
        try {
			Minecraft.getMinecraft().effectRenderer.addEffect(Thaumcraft.FXEssentiaTrailConst.newInstance(world, tx, ty, tz, 
					x, y, z, 25, color, 1.0F));
		} catch (Exception e) {
			e.printStackTrace();
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
