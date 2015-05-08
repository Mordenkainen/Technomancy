package theflogat.technomancy.common.blocks.air;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.Coords;
import theflogat.technomancy.util.WorldHelper;

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
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (player.getHeldItem() == null && player.isSneaking()) {
			TileFakeAirNG fakeAir = (TileFakeAirNG)w.getTileEntity(x, y, z);
			Coords nodeGen = fakeAir.getMain();
			TileNodeGenerator tile = (TileNodeGenerator)nodeGen.w.getTileEntity(nodeGen.x, nodeGen.y, nodeGen.z);
			if(tile.boost) {
				if(!w.isRemote) {
					WorldHelper.dropBoost(w, x, y, z, player);
				}
				tile.setBoost(false);
				return true;
			}
		}
		return false;
	}

}
