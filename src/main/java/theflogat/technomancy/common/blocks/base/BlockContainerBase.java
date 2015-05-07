package theflogat.technomancy.common.blocks.base;

import java.util.HashMap;
import java.util.Map;

import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.ToolWrench;
import theflogat.technomancy.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockContainerBase extends BlockContainer {
	
	public BlockContainerBase() {
		super(Material.iron);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
	}
	
}