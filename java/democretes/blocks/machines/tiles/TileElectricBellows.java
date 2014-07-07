package democretes.blocks.machines.tiles;

import cofh.api.energy.EnergyStorage;
import democretes.compat.Thaumcraft;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.AspectList;


public class TileElectricBellows extends TileMachineBase {

	public byte facing = 2;	
	public float inflation = 1.0F;
	boolean firstrun = false;;
	boolean direction = false;

	public TileElectricBellows() {
		this.capacity = 20000;
		this.maxReceive = 5000;
		this.energyStorage = new EnergyStorage(capacity);
	}
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setByte("Facing", this.facing);
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.facing = compound.getByte("Facing");
	}

	@Override
	public void updateEntity() {
		try{
			ForgeDirection dir = ForgeDirection.getOrientation(facing);
			Object furnace = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
			if(Thaumcraft.TileAlchemyFurnace.isInstance(furnace)) {
				furnace = Thaumcraft.TileAlchemyFurnace.cast(worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ));
			}else if(Thaumcraft.TileArcaneFurnace.isInstance(worldObj.getBlockTileEntity(xCoord + (dir.offsetX *2), yCoord, zCoord + (dir.offsetZ *2)))) {
				furnace = Thaumcraft.TileArcaneFurnace.cast(worldObj.getBlockTileEntity(xCoord + (dir.offsetX *2), yCoord, zCoord + (dir.offsetZ *2)));
			}			
			if(furnace != null) {
				if(Thaumcraft.TileAlchemyFurnace.isInstance(furnace)) {
					if(this.energyStorage.extractEnergy(3000, true) == 3000) {
						if(((ISidedInventory)furnace).getStackInSlot(0) == null || ((ISidedInventory)furnace).getStackInSlot(1) != null) {
							return;
						}
						AspectList al = (AspectList) Thaumcraft.getObjectTags.invoke(null, ((ISidedInventory)furnace).getStackInSlot(0));
						al = (AspectList) Thaumcraft.getBonusTags.invoke(null,((ISidedInventory)furnace).getStackInSlot(0), al);
						if(al == null || al.size() == 0) {
							return;
						}
						if(((int)Thaumcraft.TileAlchemyFurnace.getField("furnaceBurnTime").getInt(furnace)) <= 2 &&
								((AspectList)Thaumcraft.TileAlchemyFurnace.getField("aspects").get(furnace)).visSize() + al.visSize() < 50) {
							Thaumcraft.TileAlchemyFurnace.getField("furnaceBurnTime").set(furnace, 80);
							energyStorage.extractEnergy(3000, false);
						}					
					}
				}
				if(Thaumcraft.TileArcaneFurnace.isInstance(furnace)) {
					if(this.energyStorage.extractEnergy(500, true) == 500) {
						if(((int)Thaumcraft.TileArcaneFurnace.getField("furnaceCookTime").getInt(furnace)) > 6) {
							Thaumcraft.TileAlchemyFurnace.getField("furnaceBurnTime").set(furnace, 
									((int)Thaumcraft.TileAlchemyFurnace.getField("furnaceBurnTime").getInt(furnace))-6);
							this.energyStorage.extractEnergy(500, false);
						}
					}
				}
			}


			if (firstrun) {
				inflation = (0.35F + this.worldObj.rand.nextFloat() * 0.55F);
			}
			this.firstrun = false;
			if ((inflation > 0.35F) && (!direction)) {
				inflation -= 0.075F;
			}
			if ((inflation <= 0.35F) && (!direction)) {
				direction = true;
			}
			if ((inflation < 1.0F) && (direction)) {
				inflation += 0.025F;
			}
			if ((inflation >= 1.0F) && (direction)) {
				direction = false;
				worldObj.playSound(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "mob.ghast.fireball", 0.01F, 0.5F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F, false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
