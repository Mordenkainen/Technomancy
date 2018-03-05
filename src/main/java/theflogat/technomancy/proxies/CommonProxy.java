package theflogat.technomancy.proxies;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import theflogat.technomancy.client.gui.container.ContainerBMProcessor;
import theflogat.technomancy.client.gui.container.ContainerBOProcessor;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBMProcessor;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import theflogat.technomancy.lib.Ref;

public class CommonProxy implements IGuiHandler {

    public void initSounds() {

    }

    public void preInit(FMLPreInitializationEvent e) {

	}

    public void initRenderers() {

    }
    
    @Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) {
			case 0: 
				return null;
			case 1:
				return new ContainerBMProcessor(player.inventory, ((TileBMProcessor)world.getTileEntity(pos)));
			case 2:
				return new ContainerBOProcessor(player.inventory, ((TileBOProcessor)world.getTileEntity(pos)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public void registerWithMapper(Block block) {
	}

	public void registerMapper(Block block) {
	}

	public void registerWithMapper(Item item, String... types) {
	}
}
