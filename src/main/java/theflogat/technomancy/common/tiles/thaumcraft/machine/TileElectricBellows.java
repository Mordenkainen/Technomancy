package theflogat.technomancy.common.tiles.thaumcraft.machine;

import net.minecraft.block.BlockFurnace;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.AspectList;
import theflogat.technomancy.common.tiles.base.TileMachineBase;
import theflogat.technomancy.lib.Rate;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class TileElectricBellows extends TileMachineBase {

	public byte facing = 2;	
	public float inflation = 1.0F;
	boolean firstrun = false;;
	boolean direction = false;
	public int baseCost = Rate.bellowsCost;
	private int delay = 0;

	public TileElectricBellows() {
		super(Rate.bellowsCost * 40);
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
		
		try{
			ForgeDirection dir = ForgeDirection.getOrientation(facing);
			Object furnace = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
			if(Thaumcraft.TileAlchemyFurnace.isInstance(furnace)) {
				furnace = Thaumcraft.TileAlchemyFurnace.cast(worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ));
			}else if(Thaumcraft.TileArcaneFurnace.isInstance(worldObj.getTileEntity(xCoord + (dir.offsetX *2), yCoord, zCoord + (dir.offsetZ *2)))) {
				furnace = Thaumcraft.TileArcaneFurnace.cast(worldObj.getTileEntity(xCoord + (dir.offsetX *2), yCoord, zCoord + (dir.offsetZ *2)));
			}			
			if(furnace != null) {
				if(Thaumcraft.TileAlchemyFurnace.isInstance(furnace)) {
					if(extractEnergy(baseCost * 6, true) == baseCost * 6) {
						if(((ISidedInventory)furnace).getStackInSlot(0) == null || ((ISidedInventory)furnace).getStackInSlot(1) != null) {
							return;
						}
						AspectList al = (AspectList) Thaumcraft.getObjectTags.invoke(null, ((ISidedInventory)furnace).getStackInSlot(0));
						al = (AspectList) Thaumcraft.getBonusTags.invoke(null,((ISidedInventory)furnace).getStackInSlot(0), al);
						if(al == null || al.size() == 0) {
							return;
						}
						if((Thaumcraft.TileAlchemyFurnace.getField("furnaceBurnTime").getInt(furnace)) <= 2 &&
								((AspectList)Thaumcraft.TileAlchemyFurnace.getField("aspects").get(furnace)).visSize() + al.visSize() < 50) {
							Thaumcraft.TileAlchemyFurnace.getField("furnaceBurnTime").set(furnace, 80);
							extractEnergy(baseCost * 6, false);
						}					
					}
				}
				if(Thaumcraft.TileArcaneFurnace.isInstance(furnace)) {
					if(extractEnergy(baseCost, true) == baseCost) {
						if((Thaumcraft.TileArcaneFurnace.getField("furnaceCookTime").getInt(furnace)) > 6) {
							Thaumcraft.TileArcaneFurnace.getField("furnaceCookTime").setInt(furnace, 
									(Thaumcraft.TileArcaneFurnace.getField("furnaceCookTime").getInt(furnace)) - 6);
							extractEnergy(baseCost, false);
						}
					}
				}
				if(furnace instanceof TileEntityFurnace) {
					if(extractEnergy(baseCost * 6, true) == baseCost * 6) {
						if(((ISidedInventory)furnace).getStackInSlot(0) == null) {
							return;
						}
						if(FurnaceRecipes.smelting().getSmeltingResult(((ISidedInventory)furnace).getStackInSlot(0)) != null &&
								((TileEntityFurnace)furnace).furnaceBurnTime <= 2) {
							((TileEntityFurnace)furnace).furnaceBurnTime = 80;
							BlockFurnace.updateFurnaceBlockState(true, this.worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
							extractEnergy(baseCost * 6, false);
						}
						delay++;
						if(delay >= 2) {
							delay = 0;
							if (((TileEntityFurnace)furnace).furnaceCookTime > 0 && ((TileEntityFurnace)furnace).furnaceCookTime < 199) {
								System.out.println(((TileEntityFurnace)furnace).furnaceCookTime);
								((TileEntityFurnace)furnace).furnaceCookTime += 1;
					        }
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
