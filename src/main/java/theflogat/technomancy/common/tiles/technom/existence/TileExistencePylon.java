package theflogat.technomancy.common.tiles.technom.existence;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import theflogat.technomancy.common.tiles.base.TileTechnomancyRedstone;

public class TileExistencePylon extends TileTechnomancyRedstone implements IExistenceTransmitter{

	public enum Type implements IStringSerializable {
		BASIC(5, 0) {
			@Override
			public String getName() {
				return "basic";
			}
		},
		ADVANCED(25, 1){
			@Override
			public String getName() {
				return "advanced";
			}
		},
		COMPLEX(125, 2){
			@Override
			public String getName() {
				return "complex";
			}
		};

		public int id;
		public int tRate;
		public static final Type[] allTypes = {BASIC,ADVANCED,COMPLEX};

		private Type(int tRate, int id) {
			this.tRate = tRate;
			this.id = id;
		}

		public static Type getTypeFromId(int id){
			for(Type t: allTypes){
				if(t.id == id){
					return t;
				}
			}
			return Type.BASIC;
		}
	}

	public Type transferRate;
	public int power;

	public TileExistencePylon() {
		super(RedstoneSet.LOW);
	}

	@Override
	public void update() {
		if(transferRate==null){
			transferRate = Type.getTypeFromId(getBlockMetadata());
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}

		if(power<transferRate.tRate){
			input();
		}
		if(power>0){
			output();
			if(power>0){
				transfert();
			}
		}
	}

	private void input() {
		for(int xx =-7; xx<=7; xx++){
			for(int zz =-7; zz<=7; zz++){
				for(int yy =-1; yy<=1; yy++){
					TileEntity te = world.getTileEntity(new BlockPos(pos.getX() + xx, pos.getY() + yy, pos.getZ() + zz));
					if(te!=null && te instanceof IExistenceProducer && !(te instanceof TileExistencePylon)){
						IExistenceProducer ex = (IExistenceProducer)te;
						if(ex.canOutput()){
							int t = Math.min(transferRate.tRate - power, Math.min(ex.getMaxRate(), ex.getPower()));
							if(t>0){
								ex.addPower(-t);
								power += t;
							}
						}
					}
				}
			}
		}
	}

	private void transfert(){
		for(int xx =-7; xx<=7; xx++){
			for(int zz =-7; zz<=7; zz++){
				for(int yy =-1; yy<=1; yy++){
					TileEntity te = world.getTileEntity(new BlockPos(pos.getX() + xx, pos.getY() + yy, pos.getZ() + zz));
					if(te!=null && te instanceof TileExistencePylon){
						TileExistencePylon ex = (TileExistencePylon)te;
						int t = Math.min(power, Math.min(ex.getMaxRate(), ex.getPowerCap() - ex.getPower()));
						if(t>0){
							ex.addPower(t);
							power -= t;
						}
					}
				}
			}
		}
	}

	private void output() {
		for(int xx =-7; xx<=7; xx++){
			for(int zz =-7; zz<=7; zz++){
				for(int yy =-1; yy<=1; yy++){
					TileEntity te = world.getTileEntity(new BlockPos(pos.getX() + xx, pos.getY() + yy, pos.getZ() + zz));
					if(te!=null && te instanceof IExistenceConsumer){
						IExistenceConsumer ex = (IExistenceConsumer)te;
						if(ex.canInput()){
							int t = Math.min(power, Math.min(ex.getMaxRate(), ex.getPowerCap() - ex.getPower()));
							if(t>0){
								ex.addPower(t);
								power -= t;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		set.save(comp);
		comp.setInteger("power", power);
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		set = RedstoneSet.load(comp);
		power = comp.getInteger("power");
	}

	@Override
	public int getMaxRate() {
		if(transferRate==null){
			transferRate = Type.getTypeFromId(getBlockMetadata());
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
		return transferRate.tRate;
	}

	public int getPower() {
		return power;
	}

	public int getPowerCap() {
		if(transferRate==null){
			transferRate = Type.getTypeFromId(getBlockMetadata());
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
		return transferRate.tRate;
	}

	public void addPower(int val) {
		power += val;
	}
}