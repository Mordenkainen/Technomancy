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
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.RedstoneSet;
import theflogat.technomancy.util.ToolWrench;
import vazkii.botania.api.mana.IPoolOverlayProvider;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Optional.Interface(iface = "vazkii.botania.api.mana.IPoolOverlayProvider", modid = "Botania")
public class BlockManaExchanger extends BlockContainer implements IPoolOverlayProvider {
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public BlockManaExchanger() {
		super(Material.rock);
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

	@Optional.Method(modid = "Botania")
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
	
	@Override
	public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
		TileManaExchanger tile = (TileManaExchanger) w.getTileEntity(x, y, z);
		dropCurrentModifier(tile, w, x, y, z);
		super.breakBlock(w, x, y, z, block, meta);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		TileManaExchanger tile = (TileManaExchanger)world.getTileEntity(x, y, z);
		if (player.getHeldItem() != null) {
			if (ToolWrench.isWrench(player.getHeldItem())) {
				tile.mode = !tile.mode;
				world.markBlockForUpdate(x, y, z);
				return true;
			}else if(player.getHeldItem().getItem()==Items.gunpowder && tile.set != RedstoneSet.NONE){
				if(--player.inventory.mainInventory[player.inventory.currentItem].stackSize == 0) {
					player.inventory.mainInventory[player.inventory.currentItem] = null;
				}
				if(!world.isRemote) {
					dropCurrentModifier(tile, world, x, y, z);
				}
				tile.set = RedstoneSet.NONE;
				tile.modified = true;
				return true;
			} else if(player.getHeldItem().getItem()==Items.redstone) {
				if(tile.set != RedstoneSet.HIGH) {
					if(--player.inventory.mainInventory[player.inventory.currentItem].stackSize == 0) {
						player.inventory.mainInventory[player.inventory.currentItem] = null;
					}
					if(!world.isRemote) {
						dropCurrentModifier(tile, world, x, y, z);
					}
					tile.set = RedstoneSet.HIGH;
					tile.modified = true;
				}
				return true;
			}else if(player.getHeldItem().getItem()==Item.getItemFromBlock(Blocks.redstone_torch)){
				if(tile.set != RedstoneSet.LOW) {
					if(--player.inventory.mainInventory[player.inventory.currentItem].stackSize == 0) {
						player.inventory.mainInventory[player.inventory.currentItem] = null;
					}
					if(!world.isRemote) {
						dropCurrentModifier(tile, world, x, y, z);
					}
					tile.set = RedstoneSet.LOW;
					tile.modified = true;
				}
				return true;
			}
		} else if (player.isSneaking()) {
			if(tile.modified) {
				if(!world.isRemote) {
					dropCurrentModifier(tile, world, x, y, z);
				}
				tile.modified = false;
				tile.set = RedstoneSet.HIGH;
				return true;
			}
		}
		return false;
	}

	protected void dropCurrentModifier(TileManaExchanger tile, World w, int x, int y, int z) {
		if(tile.modified) {
			ItemStack item = null;
			switch (tile.set) {
			case LOW:
				item = new ItemStack(Item.getItemFromBlock(Blocks.redstone_torch), 1);
				break;
			case HIGH:
				item = new ItemStack(Items.redstone, 1);
				break;
			case NONE:
				item = new ItemStack(Items.gunpowder, 1);
				break;
			}
			InvHelper.spawnEntItem(w, x, y, z, item);
		}
	}
}