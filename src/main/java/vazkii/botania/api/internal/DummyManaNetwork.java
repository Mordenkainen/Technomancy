package vazkii.botania.api.internal;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class DummyManaNetwork
  implements IManaNetwork
{
  public static final DummyManaNetwork instance = new DummyManaNetwork();
  
  public void clear() {}
  
  public TileEntity getClosestPool(ChunkCoordinates pos, int dimension, int limit)
  {
    return null;
  }
  
  public TileEntity getClosestCollector(ChunkCoordinates pos, int dimension, int limit)
  {
    return null;
  }
  
  public List<TileEntity> getAllCollectorsInWorld(int dim)
  {
    return new ArrayList();
  }
  
  public List<TileEntity> getAllPoolsInWorld(int dim)
  {
    return new ArrayList();
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.internal.DummyManaNetwork
 * JD-Core Version:    0.7.0.1
 */