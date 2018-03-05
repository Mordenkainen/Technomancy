package theflogat.technomancy.proxies;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import theflogat.technomancy.client.gui.GuiProcessorBM;
import theflogat.technomancy.client.gui.GuiProcessorBO;
import theflogat.technomancy.client.gui.GuiRitualTome;
import theflogat.technomancy.client.tiles.*;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.bloodmagic.dynamos.TileBloodDynamo;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBMProcessor;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import theflogat.technomancy.common.tiles.botania.dynamos.TileFlowerDynamo;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.common.tiles.botania.machines.TileManaFabricator;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.common.tiles.technom.TileCrystal;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceBurner;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceDynamicBurner;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceFountain;
import theflogat.technomancy.common.tiles.technom.existence.TileExistencePylon;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.IModModule;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;

import java.util.Map;

public class ClientProxy extends CommonProxy implements IGuiHandler {

    @Override
    public void initSounds() {

    }

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		TMItems.registerRenders();
		TMBlocks.registerRenders();
		TMItems.initPureOresRender();
		for(IModModule mod : CompatibilityHandler.mods) {
			mod.preInit();
		}
	}

	@Override
    public void initRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new TileCrystalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCatalyst.class, new TileCatalystRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileItemTransmitter.class, new TileItemTransmitterRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileExistenceFountain.class, new TileExistenceFountainRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileExistencePylon.class, new TileExistencePylonRenderer());
		TileExistenceBurnerRenderer rendBurner = new TileExistenceBurnerRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileExistenceBurner.class, rendBurner);
		ClientRegistry.bindTileEntitySpecialRenderer(TileExistenceDynamicBurner.class, rendBurner);
    	
    	if(CompatibilityHandler.bm) {
    		ClientRegistry.bindTileEntitySpecialRenderer(TileBloodDynamo.class, new TileBloodDynamoRenderer());

    		ClientRegistry.bindTileEntitySpecialRenderer(TileBloodFabricator.class, new TileBloodFabricatorRenderer());
    	}
    	
    	if(CompatibilityHandler.bo) {
    		ClientRegistry.bindTileEntitySpecialRenderer(TileManaFabricator.class, new TileManaFabricatorRenderer());
    		ClientRegistry.bindTileEntitySpecialRenderer(TileFlowerDynamo.class, new TileFlowerDynamoRenderer());
    	}
    	  	
    }

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if(world instanceof WorldClient) {
			switch(ID) {
				case 0: 
					return null;
				case 1:
					return new GuiProcessorBM(player.inventory, ((TileBMProcessor)world.getTileEntity(pos)));
				case 2:
					return new GuiProcessorBO(player.inventory, ((TileBOProcessor)world.getTileEntity(pos)));
				case 3:
					return new GuiRitualTome();
			}
		}
		return null;
	}

	@Override
	public void registerWithMapper(Block block) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient() && block != null) {
			final String resourcePath = String.format("%s:%s", Ref.MOD_ID, block.getRegistryName().getResourcePath());

			ModelLoader.setCustomStateMapper(block, new DefaultStateMapper() {
				@Override
				protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
					return new ModelResourceLocation(resourcePath, getPropertyString(state.getProperties()));
				}
			});

			NonNullList<ItemStack> subBlocks = NonNullList.create();
			block.getSubBlocks(null, (NonNullList<ItemStack>) subBlocks);

			for (ItemStack stack : subBlocks) {
				IBlockState state = block.getStateFromMeta(stack.getMetadata());
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), stack.getMetadata(), new ModelResourceLocation(resourcePath, this.getPropertyString(state.getProperties())));
			}
		}
	}

	@Override
	public void registerWithMapper(Item item, String... types) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			for (int i = 0; i < types.length; i++) {
				ResourceLocation location = item.getRegistryName();
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(location, "type=" + types[i]));
			}
		}
	}

	public static String getPropertyString(Map<IProperty<?>, Comparable<?>> values, String... extrasArgs) {
		StringBuilder stringbuilder = new StringBuilder();

		for (Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet()) {
			if (stringbuilder.length() != 0) {
				stringbuilder.append(",");
			}

			IProperty<?> iproperty = (IProperty) entry.getKey();
			stringbuilder.append(iproperty.getName());
			stringbuilder.append("=");
			stringbuilder.append(getPropertyName(iproperty, (Comparable) entry.getValue()));
		}

		if (stringbuilder.length() == 0) {
			stringbuilder.append("inventory");
		}

		for (String args : extrasArgs) {
			if (stringbuilder.length() != 0)
				stringbuilder.append(",");
			stringbuilder.append(args);
		}

		return stringbuilder.toString();
	}

	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> comparable) {
		return property.getName((T) comparable);
	}
}
