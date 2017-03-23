package theflogat.technomancy.common.tiles.thaumcraft.dynamos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.INode;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;

public class TileNodeDynamo extends TileDynamoBase {

    public static final int maxAmount = 32;

    public int amount = 0;
    public boolean draining = false;
    public int sourceX, sourceY, sourceZ;
    public int color;
    private int counter = 0;

    @Override
    public int extractFuel(int ener) {
        float ratio = (ener) / 80F;
        if (ratio > amount) {
            return 0;
        }
        amount -= Math.ceil(ratio);
        return 30;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (counter++ == 20) {
            draining = false;
            if (amount < maxAmount) {
                takeAspectsFromNodes();
            }
            counter = 0;
        }
    }

    public void takeAspectsFromNodes() {
        ArrayList<TileEntity> l = getNodes();
        if (!l.isEmpty()) {
            Collections.shuffle(l);
            for (TileEntity te : l) {
                if (te != null && te instanceof INode) {
                    INode node = (INode) te;
                    Aspect[] as = node.getAspects().getAspects();
                    Collections.shuffle(Arrays.asList(as));
                    for (int i = 0; i < as.length; i++) {
                        Aspect aspect = as[i];
                        if (node.getAspects().getAmount(aspect) > 1 && amount < 16) {
                            if (node.takeFromContainer(aspect, 1)) {
                                amount += 1;
                                draining = true;
                                sourceX = te.xCoord;
                                sourceY = te.yCoord;
                                sourceZ = te.zCoord;
                                color = aspect.getColor();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public ArrayList<TileEntity> getNodes() {
        ArrayList<TileEntity> l = new ArrayList<TileEntity>();
        for (int xx = -4; xx <= 4; xx++) {
            for (int yy = -4; yy <= 4; yy++) {
                for (int zz = -4; zz <= 4; zz++) {
                    TileEntity te = worldObj.getTileEntity(xCoord + xx, yCoord + yy, zCoord + zz);
                    if (te instanceof INode) {
                        l.add(te);
                    }
                }
            }
        }
        return l;
    }

    @Override
    public void writeSyncData(NBTTagCompound comp) {
        super.writeSyncData(comp);
        comp.setShort("Amount", (short) amount);
    }

    @Override
    public void readSyncData(NBTTagCompound comp) {
        super.readSyncData(comp);
        this.amount = comp.getShort("Amount");
    }
}