package vazkii.botania.api.internal;

import java.util.List;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public abstract interface IManaNetwork
{
  public abstract void clear();
  
  public abstract TileEntity getClosestCollector(ChunkCoordinates paramChunkCoordinates, int paramInt1, int paramInt2);
  
  public abstract TileEntity getClosestPool(ChunkCoordinates paramChunkCoordinates, int paramInt1, int paramInt2);
  
  public abstract List<TileEntity> getAllCollectorsInWorld(int paramInt);
  
  public abstract List<TileEntity> getAllPoolsInWorld(int paramInt);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.internal.IManaNetwork
 * JD-Core Version:    0.7.0.1
 */