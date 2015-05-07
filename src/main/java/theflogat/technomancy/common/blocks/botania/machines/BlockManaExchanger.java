package theflogat.technomancy.common.blocks.botania.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.ToolWrench;
import vazkii.botania.api.mana.IPoolOverlayProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockManaExchanger extends BlockContainerAdvanced implements IPoolOverlayProvider {
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public BlockManaExchanger() {
		setHardness(2.0F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(Technomancy.tabsTM);
		setBlockName(Ref.MOD_PREFIX + Names.manaExchanger);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		icons = new IIcon[5];
		icons[0] = icon.registerIcon(Ref.getAsset(Names.manaExchanger + "Bottom"));
		icons[1] = icon.registerIcon(Ref.getAsset(Names.manaExchanger + "Out"));
		icons[2] = icon.registerIcon(Ref.getAsset(Names.manaExchanger + "In"));
		icons[3] = icon.registerIcon(Ref.getAsset(Names.manaExchanger + "TopActive"));
		icons[4] = icon.registerIcon(Ref.getAsset(Names.manaExchanger + "TopInactive"));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 1) {
			return icons[4];
		}
		if(side > 1) {
			return icons[2];
		}
		return icons[0];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		TileManaExchanger tile = (TileManaExchanger) access.getTileEntity(x, y, z);
		if(side == 1) {
			return icons[4];
		}
		if(side > 1) {
			if(tile.mode) {
				return icons[1];
			} else {
				return icons[2];
			}
		}		
		return icons[0];		
	}

	@Override
	public IIcon getIcon(World world, int x, int y, int z) {
		TileManaExchanger tile = (TileManaExchanger) world.getTileEntity(x, y, z);
		if(tile.active) {
			return icons[3];
		} else {
			return icons[4];
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileManaExchanger();
	}
}