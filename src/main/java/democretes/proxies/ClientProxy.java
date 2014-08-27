package democretes.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.IGuiHandler;
import democretes.compat.BloodMagic;
import democretes.compat.Botania;
import democretes.compat.Thaumcraft;
import democretes.lib.RenderIds;
import democretes.renderers.blocks.BlockBiomeMorpherRenderer;
import democretes.renderers.blocks.BlockBloodDynamoRenderer;
import democretes.renderers.blocks.BlockBloodFabricatorRenderer;
import democretes.renderers.blocks.BlockCreativeJarRenderer;
import democretes.renderers.blocks.BlockEldritchConsumerRenderer;
import democretes.renderers.blocks.BlockElectricBellowsRenderer;
import democretes.renderers.blocks.BlockEssentiaContainerRenderer;
import democretes.renderers.blocks.BlockEssentiaDynamoRenderer;
import democretes.renderers.blocks.BlockFlowerDynamoRenderer;
import democretes.renderers.blocks.BlockFluxLampRenderer;
import democretes.renderers.blocks.BlockManaFabricatorRenderer;
import democretes.renderers.blocks.BlockNodeDynamoRenderer;
import democretes.renderers.blocks.BlockNodeGeneratorRenderer;
import democretes.renderers.blocks.BlockReconstructorRenderer;
import democretes.renderers.blocks.BlockTeslaCoilRenderer;
import democretes.renderers.gui.GuiProcessorBM;
import democretes.renderers.gui.GuiProcessorBO;
import democretes.renderers.gui.GuiProcessorTC;
import democretes.renderers.tiles.TileBiomeMorpherRenderer;
import democretes.renderers.tiles.TileBloodDynamoRenderer;
import democretes.renderers.tiles.TileBloodFabricatorRenderer;
import democretes.renderers.tiles.TileCreativeJarRenderer;
import democretes.renderers.tiles.TileEldritchConsumerRenderer;
import democretes.renderers.tiles.TileElectricBellowsRenderer;
import democretes.renderers.tiles.TileEssentiaContainerRenderer;
import democretes.renderers.tiles.TileEssentiaDynamoRenderer;
import democretes.renderers.tiles.TileFlowerDynamoRenderer;
import democretes.renderers.tiles.TileFluxLampRenderer;
import democretes.renderers.tiles.TileManaFabricatorRenderer;
import democretes.renderers.tiles.TileNodeDynamoRenderer;
import democretes.renderers.tiles.TileNodeGeneratorRenderer;
import democretes.renderers.tiles.TileReconstructorRenderer;
import democretes.renderers.tiles.TileTeslaCoilRenderer;
import democretes.tiles.dynamos.TileBloodDynamo;
import democretes.tiles.dynamos.TileEssentiaDynamo;
import democretes.tiles.dynamos.TileFlowerDynamo;
import democretes.tiles.dynamos.TileNodeDynamo;
import democretes.tiles.thaumcraft.machine.TileBMProcessor;
import democretes.tiles.thaumcraft.machine.TileBOProcessor;
import democretes.tiles.thaumcraft.machine.TileBiomeMorpher;
import democretes.tiles.thaumcraft.machine.TileBloodFabricator;
import democretes.tiles.thaumcraft.machine.TileEldritchConsumer;
import democretes.tiles.thaumcraft.machine.TileElectricBellows;
import democretes.tiles.thaumcraft.machine.TileFluxLamp;
import democretes.tiles.thaumcraft.machine.TileManaFabricator;
import democretes.tiles.thaumcraft.machine.TileNodeGenerator;
import democretes.tiles.thaumcraft.machine.TileReconstructor;
import democretes.tiles.thaumcraft.machine.TileTCProcessor;
import democretes.tiles.thaumcraft.machine.TileTeslaCoil;
import democretes.tiles.thaumcraft.storage.TileCreativeJar;
import democretes.tiles.thaumcraft.storage.TileEssentiaContainer;

public class ClientProxy extends CommonProxy implements IGuiHandler{

    @Override
    public void initSounds() {

    }

    @Override
    public void initRenderers() {
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
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileTeslaCoil.class, new TileTeslaCoilRenderer());
    		RenderIds.idTeslaCoil = RenderingRegistry.getNextAvailableRenderId(); 
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileElectricBellows.class, new TileElectricBellowsRenderer());
    		RenderIds.idElectricBellows = RenderingRegistry.getNextAvailableRenderId();
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileCreativeJar.class, new TileCreativeJarRenderer());
    		RenderIds.idCreativeJar = RenderingRegistry.getNextAvailableRenderId(); 
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileReconstructor.class, new TileReconstructorRenderer());
    		RenderIds.idReconstructor = RenderingRegistry.getNextAvailableRenderId();
    		
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
    	}
    	
    	if(BloodMagic.bm) {
    		ClientRegistry.bindTileEntitySpecialRenderer(TileBloodDynamo.class, new TileBloodDynamoRenderer());
    		RenderIds.idBloodDynamo = RenderingRegistry.getNextAvailableRenderId();
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileBloodFabricator.class, new TileBloodFabricatorRenderer());
    		RenderIds.idBloodFabricator = RenderingRegistry.getNextAvailableRenderId();    		

        	RenderingRegistry.registerBlockHandler(new BlockBloodDynamoRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockBloodFabricatorRenderer());
    	}
    	
    	if(Botania.bo) {
    		ClientRegistry.bindTileEntitySpecialRenderer(TileManaFabricator.class, new TileManaFabricatorRenderer());
    		RenderIds.idManaFabricator = RenderingRegistry.getNextAvailableRenderId();
    	
    		ClientRegistry.bindTileEntitySpecialRenderer(TileFlowerDynamo.class, new TileFlowerDynamoRenderer());
    		RenderIds.idFlowerDynamo = RenderingRegistry.getNextAvailableRenderId();
    		
        	RenderingRegistry.registerBlockHandler(new BlockManaFabricatorRenderer());
        	RenderingRegistry.registerBlockHandler(new BlockFlowerDynamoRenderer());  
    	}	    	
    	  	
    }
    
    
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
					return new GuiProcessorTC(player.inventory, ((TileTCProcessor)world.getBlockTileEntity(x, y, z)));
				case 1:
					return new GuiProcessorBM(player.inventory, ((TileBMProcessor)world.getBlockTileEntity(x, y, z)));
				case 2:
					return new GuiProcessorBO(player.inventory, ((TileBOProcessor)world.getBlockTileEntity(x, y, z)));
			}
		}
		return null;
	}

}
