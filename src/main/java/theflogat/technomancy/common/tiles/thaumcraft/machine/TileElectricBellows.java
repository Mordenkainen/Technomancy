package theflogat.technomancy.common.tiles.thaumcraft.machine;

import net.minecraft.block.BlockFurnace;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileAlchemyFurnace;
import thaumcraft.common.tiles.TileArcaneFurnace;
import theflogat.technomancy.common.tiles.base.TileMachineBase;
import theflogat.technomancy.lib.handlers.Rate;

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
		
		ForgeDirection dir = ForgeDirection.getOrientation(facing);
		Object furnace = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
		if(worldObj.getTileEntity(xCoord + (dir.offsetX *2), yCoord, zCoord + (dir.offsetZ *2)) instanceof TileArcaneFurnace) {
			furnace = worldObj.getTileEntity(xCoord + (dir.offsetX *2), yCoord, zCoord + (dir.offsetZ *2));
		}			
		if(furnace != null) {
			if(furnace instanceof TileAlchemyFurnace) {
				if(extractEnergy(baseCost * 6, true) == baseCost * 6) {
					if(((ISidedInventory)furnace).getStackInSlot(0) == null || ((ISidedInventory)furnace).getStackInSlot(1) != null) {
						return;
					}
					AspectList al = ThaumcraftCraftingManager.getObjectTags(((ISidedInventory)furnace).getStackInSlot(0));
					al = ThaumcraftCraftingManager.getBonusTags(((ISidedInventory)furnace).getStackInSlot(0), al);
					if(al == null || al.size() == 0) {
						return;
					}
					if(((TileAlchemyFurnace)furnace).furnaceBurnTime <= 2 && ((TileAlchemyFurnace)furnace).aspects.visSize() + al.visSize() < 50) {
						((TileAlchemyFurnace)furnace).furnaceBurnTime = 80;
						extractEnergy(baseCost * 6, false);
					}					
				}
			}
			if(furnace instanceof TileArcaneFurnace) {
				if(extractEnergy(baseCost, true) == baseCost) {
					if(((TileArcaneFurnace)furnace).furnaceCookTime > 6) {
						((TileArcaneFurnace)furnace).furnaceCookTime -= 6;
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
	}

}
