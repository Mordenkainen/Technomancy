package democretes.blocks.machines.tiles;

import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import cofh.api.energy.EnergyStorage;
import democretes.blocks.TMBlocks;
import democretes.compat.Thaumcraft;


public class TileBiomeMorpher extends TileMachineBase implements INode{	
	
	private AspectList aspects = new AspectList();
	private int amount = 35;
	
	public TileBiomeMorpher() {
		capacity = 800000;
		maxReceive = 20000;
		energyStorage = new EnergyStorage(capacity);
	}
	@Override
	public void updateEntity() {
		if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			if (getEnergyStored() > 20000) {
				alterBiome();
				alterBiome();
				energyStorage.extractEnergy(20000, false);
			}
		}
	}		
	
	void alterBiome() {
		if (worldObj.isRemote) {
			return;
		}
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		BiomeGenBase bm = biomeForMeta(meta);
		int xx = xCoord + worldObj.rand.nextInt(100) - worldObj.rand.nextInt(100);
		int zz = zCoord + worldObj.rand.nextInt(100) - worldObj.rand.nextInt(100);
		BiomeGenBase bg = worldObj.getBiomeGenForCoords(xx, zz);
		if (bg.biomeID != bm.biomeID) {
			try {
				Class Utils = Class.forName("thaumcraft.common.lib.Utils");
				for(Method method : Utils.getMethods()){
					if(method.getName().equalsIgnoreCase("setBiomeAt")){
						method.invoke(null, worldObj, xx, zz, bm);
					}
				}
			} catch (Exception e) {e.printStackTrace();}		
		} else {
			alterBiome2();
		}
		worldObj.markBlockRangeForRenderUpdate(xx - 1, xx + 1, 1, 256, zz - 1, zz + 1);
	}
	
	void alterBiome2() {
		if (worldObj.isRemote) {
			return;
		}
		int meta = worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		BiomeGenBase bm = biomeForMeta(meta);
		int xx = xCoord + worldObj.rand.nextInt(100) - worldObj.rand.nextInt(100);
		int zz = zCoord + worldObj.rand.nextInt(100) - worldObj.rand.nextInt(100);
		BiomeGenBase bg = this.worldObj.getBiomeGenForCoords(xx, zz);
		if (bg.biomeID != bm.biomeID) {
			try {
				Class Utils = Class.forName("thaumcraft.common.lib.Utils");
				for(Method method : Utils.getMethods()){
					if(method.getName().equalsIgnoreCase("setBiomeAt")){
						method.invoke(null, worldObj, xx, zz, bm);
					}
				}
			} catch (Exception e) {e.printStackTrace();}		
		}
		worldObj.markBlockRangeForRenderUpdate(xx - 1, xx + 1, 1, 256, zz - 1, zz + 1);
	}
	
	BiomeGenBase checkBiome() {
		if (!this.worldObj.isRemote) {
			BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord);
			if (biome.biomeID == Thaumcraft.biomeTaint.biomeID || biome.biomeID == Thaumcraft.biomeEerie.biomeID ||
					biome.biomeID == Thaumcraft.biomeMagicalForest.biomeID) {
				return biome;
			}			
		}
		return null;
	}
	
	BiomeGenBase biomeForMeta(int meta) {
		BiomeGenBase biome = null;
		if (meta == 0) {
			biome = Thaumcraft.biomeMagicalForest;
		}else if (meta == 1) {
			biome = Thaumcraft.biomeEerie;
		}else if (meta == 2) {
			biome = Thaumcraft.biomeTaint;
		}
		return biome;
	}

	@Override
	public AspectList getAspects() {		
		return this.aspects;
	}

	@Override
	public void setAspects(AspectList aspects) {
		this.aspects = aspects.copy();
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		return 0;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		if (this.aspects.getAmount(tag) >= amount) {
			return true;
		}
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		for (Aspect tt : ot.getAspects()) {
			if (this.aspects.getAmount(tt) < ot.getAmount(tt)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int containerContains(Aspect tag) {
		return this.aspects.getAmount(tag);
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public NodeType getNodeType() {
		switch (this.blockMetadata) {
		case 0:
			return NodeType.PURE;
		case 1:
			return NodeType.DARK;
		case 2:
			return NodeType.TAINTED;
		}
		return null;
	}

	@Override
	public void setNodeType(NodeType nodeType) {	
	}

	@Override
	public void setNodeModifier(NodeModifier nodeModifier) {
	}

	@Override
	public NodeModifier getNodeModifier() {
		return null;
	}

	@Override
	public int getNodeVisBase() {
		return 35;
	}

	@Override
	public void setNodeVisBase(short nodeVisBase) {
	}
	
	
	

}
