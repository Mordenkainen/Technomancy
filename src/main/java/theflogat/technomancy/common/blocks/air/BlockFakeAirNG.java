package theflogat.technomancy.common.blocks.air;

import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.Coords;
import theflogat.technomancy.util.InvHelper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFakeAirNG extends BlockContainer{

	public BlockFakeAirNG() {
		super(Material.carpet);
		setBlockUnbreakable();
		setBlockName(Ref.getId(Names.fakeAirNG));
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Ref.getAsset(Names.fakeAirNG));
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileFakeAirNG();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (player.getHeldItem() == null && player.isSneaking()) {
			TileFakeAirNG fakeAir = (TileFakeAirNG)world.getTileEntity(x, y, z);
			Coords nodeGen = fakeAir.getMain();
			TileNodeGenerator tile = (TileNodeGenerator)nodeGen.w.getTileEntity(nodeGen.x, nodeGen.y, nodeGen.z);
			if(tile.boost) {
				if(!world.isRemote) {
					InvHelper.spawnEntItem(nodeGen.w, nodeGen.x, nodeGen.y, nodeGen.z, new ItemStack(TMItems.itemBoost, 1));
				}
				tile.setBoost(false);
				return true;
			}
		}
		return false;
	}

}
