package theflogat.technomancy.common.tiles.technom.existence;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import theflogat.technomancy.common.tiles.base.TileTechnomancyRedstone;

public class TileExistencePylon extends TileTechnomancyRedstone implements IExistenceTransmitter{

	public enum Type {
		BASIC(5, 0),
		ADVANCED(25, 1),
		COMPLEX(125, 2);

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
	public void updateEntity() {
		if(transferRate==null){
			blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			transferRate = Type.getTypeFromId(blockMetadata);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
					TileEntity te = worldObj.getTileEntity(xCoord + xx, yCoord + yy, zCoord + zz);
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
					TileEntity te = worldObj.getTileEntity(xCoord + xx, yCoord + yy, zCoord + zz);
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
					TileEntity te = worldObj.getTileEntity(xCoord + xx, yCoord + yy, zCoord + zz);
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
			transferRate = Type.getTypeFromId(blockMetadata);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return transferRate.tRate;
	}

	public int getPower() {
		return power;
	}

	public int getPowerCap() {
		if(transferRate==null){
			transferRate = Type.getTypeFromId(blockMetadata);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return transferRate.tRate;
	}

	public void addPower(int val) {
		power += val;
	}
}