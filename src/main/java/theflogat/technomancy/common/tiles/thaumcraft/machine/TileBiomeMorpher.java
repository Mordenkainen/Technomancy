package theflogat.technomancy.common.tiles.thaumcraft.machine;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import theflogat.technomancy.common.tiles.base.TileMachineRedstone;
import theflogat.technomancy.lib.handlers.Rate;

public class TileBiomeMorpher extends TileMachineRedstone {

    public static int cost = Rate.biomeMorpherCost;

    public TileBiomeMorpher() {
        super(Rate.biomeMorpherCost * 40, RedstoneSet.LOW);
    }

    @Override
    public void updateEntity() {
        if (set.canRun(this)) {
            if (getEnergyStored() >= cost) {
                alterBiome();
                alterBiome();
                extractEnergy(cost, false);
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
            Utils.setBiomeAt(worldObj, xx, zz, bm);
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
            Utils.setBiomeAt(worldObj, xx, zz, bm);
        }
        worldObj.markBlockRangeForRenderUpdate(xx - 1, xx + 1, 1, 256, zz - 1, zz + 1);
    }

    BiomeGenBase checkBiome() {
        if (!this.worldObj.isRemote) {
            BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord);
            if (biome.biomeID == ThaumcraftWorldGenerator.biomeTaint.biomeID || biome.biomeID == ThaumcraftWorldGenerator.biomeEerie.biomeID || biome.biomeID == ThaumcraftWorldGenerator.biomeMagicalForest.biomeID) {
                return biome;
            }
        }
        return null;
    }

    BiomeGenBase biomeForMeta(int meta) {
        BiomeGenBase biome = null;
        if (meta == 0) {
            biome = ThaumcraftWorldGenerator.biomeMagicalForest;
        } else if (meta == 1) {
            biome = ThaumcraftWorldGenerator.biomeEerie;
        } else if (meta == 2) {
            biome = ThaumcraftWorldGenerator.biomeTaint;
        }
        return biome;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }
}
