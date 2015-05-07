package theflogat.technomancy.common.rituals.e;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.api.tiles.IMovingHandler;
import theflogat.technomancy.api.tiles.MovableTileRegistry;
import theflogat.technomancy.common.blocks.technom.BlockCrystal;
import theflogat.technomancy.util.RitualHelper;

public class RitualCaveInT1 extends Ritual{

	public RitualCaveInT1() {
		super(new Type[]{Type.EARTH}, Type.EARTH);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		for(int yy = 0; yy<y; yy++){
			for(int xx=-1; xx<=1; xx++){
				for(int zz=-1; zz<=1; zz++){
					if(w.getTileEntity(x + xx, yy, z + zz)!=null){
						if(!MovableTileRegistry.allowed.contains(w.getTileEntity(x + xx, yy, z + zz).getClass())){
							if(!MovableTileRegistry.specialHandler.containsKey(w.getTileEntity(x + xx, yy, z + zz).getClass())){
								System.out.println("Tile forbids dislocation");
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		w.setBlockToAir(x, y, z);
		removeFrame(w, x, y, z);

		for(int yy = 1; yy<y; yy++){
			for(int xx=-1; xx<=1; xx++){
				for(int zz=-1; zz<=1; zz++){
					if(!w.isAirBlock(x + xx, yy, z + zz)){
						if(w.getTileEntity(x + xx, yy, z + zz)==null){
							Block b = w.getBlock(x + xx, yy, z + zz);
							for(int i=0; i<yy; i++){
								if(w.isAirBlock(x + xx, i, z + zz) || w.getBlock(x + xx, i, z + zz).canReplace(w, x + xx, i, z + zz,
										w.getBlockMetadata(x + xx, i, z + zz), new ItemStack(b))){
									w.setBlock(x + xx, i, z + zz, b);
									w.setBlockToAir(x + xx, yy, z + zz);
									break;
								}
							}
						} else {
							if(MovableTileRegistry.allowed.contains(w.getTileEntity(x + xx, yy, z + zz).getClass())){
								NBTTagCompound comp = new NBTTagCompound();
								TileEntity te = w.getTileEntity(x + xx, yy, z + zz);
								te.writeToNBT(comp);
								for(int i=0; i<yy; i++){
									if(w.isAirBlock(x + xx, i, z + zz) || w.getBlock(x + xx, i, z + zz).canReplace(w, x + xx, i, z + zz,
											te.blockMetadata, new ItemStack(te.getBlockType()))){
										w.setBlock(x + xx, i, z + zz, te.getBlockType());
										te.yCoord = i;
										w.addTileEntity(te);
										w.getTileEntity(x + xx, i, z + zz).readFromNBT(comp);
										w.setBlockToAir(x + xx, yy, z + zz);
										break;
									}
								}
							}else if(MovableTileRegistry.specialHandler.containsKey(w.getTileEntity(x + xx, yy, z + zz).getClass())){
								TileEntity te = w.getTileEntity(x + xx, yy, z + zz);
								IMovingHandler mov = MovableTileRegistry.specialHandler.get(te.getClass());
								if(mov.canMove(w, x + xx, yy, z + zz)){
									Object[] data = mov.save(w, x + xx, yy, z + zz);
									for(int i=0; i<yy; i++){
										if(w.isAirBlock(x + xx, i, z + zz) || w.getBlock(x + xx, i, z + zz).canReplace(w, x + xx, i, z + zz,
												te.blockMetadata, new ItemStack(te.getBlockType()))){
											w.setBlock(x + xx, i, z + zz, te.getBlockType());
											te.yCoord = i;
											w.addTileEntity(te);
											mov.load(w, x + xx, i, z + zz, data);
											w.setBlockToAir(x + xx, yy, z + zz);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}