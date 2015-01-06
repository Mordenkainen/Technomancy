package theflogat.technomancy.common.tiles.thaumcraft.machine;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.api.wands.IWandable;
import theflogat.technomancy.common.tiles.base.TileMachineBase;
import theflogat.technomancy.handlers.compat.Thaumcraft;
import theflogat.technomancy.handlers.util.MathHelper;
import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileNodeGenerator extends TileMachineBase implements IEssentiaTransport, IAspectContainer{

	private Aspect aspect;
	public int amount = 0;
	private int maxAmount = 256;
	public boolean active = false;
	private boolean canSpawn = false;
	private boolean addNode = false;
	public byte facing = 2;
	private int energyCalc;
	private Aspect aspectSuction;
	public int rotation = 0;

	public TileNodeGenerator() {
		super(50000000);
	}
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			int xx = xCoord;
			int zz = zCoord;
			if(!active) {
				switch (facing) {
				case 2:
					zz -= 6;break;
				case 3: 
					zz += 6;break;
				case 4:
					xx -= 6;break;
				case 5:
					xx += 6;
				}
				if(worldObj.getTileEntity(xx, yCoord, zz) instanceof TileNodeGenerator) {
					active = true;
				}
			}
			if(active == true) {
				rotation += 1;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				switch(facing) {
				case 2:
					zz -= 3;break;
				case 3: 
					zz += 3;break;
				case 4:
					xx -= 3;break;
				case 5:
					xx += 3;break;
				}
				TileEntity entity = worldObj.getTileEntity(xx, yCoord + 1, zz);
				if(entity == null || worldObj.isAirBlock(xx, yCoord, zz)) {
					canSpawn = true;
					addNode = false;
				}
				if(entity instanceof INode) {
					aspectSuction = null;
					INode node = (INode)entity;
					addNode = true;					
					if(this.amount > 0 && this.aspect != null && this.getEnergyStored() > 1000) {	
						extractEnergy(1000, false);
						node.addToContainer(this.aspect, 1);
						this.takeFromContainer(this.aspect, 1);
						worldObj.markBlockForUpdate(xx, this.yCoord + 1, zz);
					}
					canSpawn = false;
				}							
			}
		}
		//shootLightning();
		fill();
	}	

	//Just using as a reference
	//NORMAL, UNSTABLE, DARK, TAINTED, HUNGRY, PURE

	void generateNode(int aurum, int taint) {
		NodeType type = NodeType.NORMAL;
		NodeModifier mod = null;
		AspectList al = new AspectList();
		Aspect ra = null;
		try {
			ra = (Aspect) Thaumcraft.getRandomBiomeTag.invoke(null, worldObj.getBiomeGenForCoords(xCoord, yCoord).biomeID, worldObj.rand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		al.add(ra, (aurum + taint)/ 2);			
		if (aurum == taint && (aurum + taint == 122 || aurum + taint == 152 || aurum + taint == 218 || aurum + taint == 510)) {
			type = NodeType.PURE;
		}else if (aurum + taint > 256 ) {
			type = NodeType.HUNGRY;
		}else if (aurum - 64 > taint) {
			type = NodeType.UNSTABLE;
		}else if (taint - 64 > aurum) {
			if (taint > 96) {
				type = NodeType.TAINTED;
			}else{
				type = NodeType.DARK;
			}				
		}
		if(aurum + taint < 80) {
			mod = NodeModifier.FADING;
		}else if((aurum + taint > 200 && aurum + taint < 256) || (aurum + taint == 510)) {
			mod = NodeModifier.BRIGHT;
		}else if(aurum + taint > 350 && aurum + taint != 510) {
			mod = NodeModifier.PALE;
		}
		int xx = xCoord;
		int zz = zCoord;
		switch (facing) {
		case 2:
			zz -= 3;break;
		case 3: 
			zz += 3;break;
		case 4:
			xx -= 3;break;
		case 5:
			xx += 3;break;
		}	
		try {
			Thaumcraft.createNodeAt.invoke(null, getWorldObj(), xx, yCoord + 1, zz, type, mod, al);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if (worldObj.isRemote) {
//			for (int a = 0; a < 6; a++) {
//				for (int b = 0; b < 6; b++) {
//					float fx = (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.5F;
//					float fy = (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.5F;
//					float fz = (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.5F;
//					Thaumcraft.wispFX3(worldObj, xx + fx, yCoord + 1 + fy, zz + fz, xx + fx * 10.0F, yCoord + 1 + fy * 10.0F, zz + fz * 10.0F, 0.4F, b, true, 0.05F);
//				}
//			}
//		}		
	}

	void fill() {
		TileEntity te = null;
		IEssentiaTransport ic = null;
		for (int y = 0; y <= 1; y++) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if ((dir != ForgeDirection.getOrientation(facing)) && (dir != ForgeDirection.DOWN) && ((y != 0) || (dir != ForgeDirection.UP))) {
					te = Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord + y, zCoord, dir);
					if (te != null) {
						ic = (IEssentiaTransport)te;
						Aspect ta = ic.getEssentiaType(dir.getOpposite());
						if ((ic.getEssentiaAmount(dir.getOpposite()) > 0) && (ic.getSuctionAmount(dir.getOpposite()) < getSuctionAmount(null)) &&
								(getSuctionAmount(null) >= ic.getMinimumSuction())) {
							addToContainer(ta, ic.takeEssentia(ta, 1, dir.getOpposite()));
							return;
						}
					}
				}
			}
		}
	}

	void shootLightning() {
		int xx = this.xCoord;
		int zz = this.zCoord;
		int xOffset = 0;
		int zOffset = 0;
		switch (this.facing) {
		case 2:
			zz -= 3;
			xOffset += .5;break;			
		case 3: 
			zz += 3;
			xOffset -= .5;break;
		case 4:
			xx -= 3;
			zOffset += .5;break;
		case 5:
			xx += 3;
			zOffset -= .5;break;
		}		

		try{
			EntityFX bolt = Thaumcraft.FXLightningBoltConst.newInstance(worldObj, xCoord + xOffset, yCoord + 2, zCoord + zOffset, xx, yCoord + 2, zz,
					worldObj.rand.nextLong(), 6, 0.5F, 5);
			Thaumcraft.defaultFractal.invoke(bolt);
			Thaumcraft.setType.invoke(bolt, 2);
			Thaumcraft.setWidth.invoke(bolt, 0.125F);
			Thaumcraft.finalizeBolt.invoke(bolt);
		}catch (Exception e){e.printStackTrace();}
	}
	
	public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
		if(active && canSpawn) {
			int xx = xCoord;
			int zz = zCoord;
			switch (facing) {
			case 2:
				zz -= 6;break;
			case 3: 
				zz += 6;break;
			case 4:
				xx -= 6;break;
			case 5:
				xx += 6;
			}
			TileEntity tile = worldObj.getTileEntity(xx, yCoord, zz);
			if(tile == null) {
				return 0;
			}
			if((amount + ((TileNodeGenerator)tile).amount > 64)) {				
				energyCalc =  MathHelper.round((Math.pow(((this.amount + ((TileNodeGenerator)tile).amount))/2, 2))*762.939453125);
				if(getEnergyStored() >= this.energyCalc) {
					if(aspect == Aspect.AURA) {					
						generateNode(amount, ((TileNodeGenerator)tile).amount);
						takeFromContainer(aspect, amount);
						((TileNodeGenerator)tile).takeFromContainer(((TileNodeGenerator)tile).aspect, ((TileNodeGenerator)tile).amount);
						extractEnergy(energyCalc, false);
						return 0;
					}else{
						generateNode((((TileNodeGenerator)tile).amount), amount);
						takeFromContainer(aspect, amount);
						((TileNodeGenerator)tile).takeFromContainer(((TileNodeGenerator)tile).aspect, ((TileNodeGenerator)tile).amount);
						extractEnergy(energyCalc, false);
						return 0;					
					}	
				}
			}	
		}
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		if (this.facing == 1 || this.facing == 3) {
			return AxisAlignedBB.getBoundingBox(this.xCoord - 1.0F, this.yCoord, this.zCoord, this.xCoord + 2.0F, this.yCoord + 3.0F, this.zCoord + 1.0F);
		}else{
			return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord - 1.0F, this.xCoord + 1.0F, this.yCoord + 3.0F, this.zCoord + 2.0F);
		}
	}


	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
		this.aspect = Aspect.getAspect(compound.getString("Aspect"));
		this.amount = compound.getShort("Amount");
		this.active = compound.getBoolean("Active");
		this.canSpawn = compound.getBoolean("Spawn");
		this.facing = compound.getByte("Facing");

	}

	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		if (this.aspect != null) {
			compound.setString("Aspect", this.aspect.getTag());
		}
		compound.setShort("Amount", (short)this.amount);
		compound.setBoolean("Active", this.active);
		compound.setBoolean("Spawn", this.canSpawn);
		compound.setByte("Facing", this.facing);
	}

	@Override
	public AspectList getAspects()  {
		AspectList al = new AspectList();
		if ((this.aspect != null) && (this.amount > 0)) {
			al.add(this.aspect, this.amount);
		}
		return al;
	}

	@Override
	public int addToContainer(Aspect tag, int amount)  {
		if (amount == 0) {
			return amount;
		}
		if (((this.amount < this.maxAmount) && (tag == this.aspect)) || (this.amount == 0)) {
			this.aspect = tag;
			int added = Math.min(amount, this.maxAmount - this.amount);
			this.amount += added;
			amount -= added;
		}
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		return amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount)  {
		if(this.amount >= amount && this.aspect == tag) {
			this.amount -= amount;
			if(this.amount <= 0) {
				this.aspect = null;
				this.amount = 0;					
			}
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			return true;			
		}
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amt) {
		if ((this.amount >= amt) && (tag == this.aspect)) {
			return true;
		}
		return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		return 0;
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return (face != ForgeDirection.getOrientation(this.facing));
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return (face != ForgeDirection.getOrientation(this.facing));
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection dir) {
		return takeFromContainer(aspect, amount) ? amount : 0;
	}

	@Override
	public int getMinimumSuction() {
		return 128;
	}

	@Override
	public boolean renderExtendedTube() {
		return true;
	}


	@Override
	public void setAspects(AspectList aspects) {	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		if (!this.addNode){ 
			if((this.facing == 2 || this.facing == 4) && this.active) {
				this.aspectSuction = Aspect.AURA;
			}else{
				this.aspectSuction = Aspect.TAINT;
			}		
		}
		return this.aspectSuction;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		if (this.amount < this.maxAmount){
			return 128;
		}
		return 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection dir) {
		return amount - addToContainer(aspect, amount);
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return this.aspect;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return this.amount;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return false;
	}

}
