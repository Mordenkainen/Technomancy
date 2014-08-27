package vazkii.botania.api.mana;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.EventBus;

public class ManaNetworkEvent
  extends Event
{
  public final TileEntity tile;
  public final ManaBlockType type;
  public final Action action;
  
  public ManaNetworkEvent(TileEntity tile, ManaBlockType type, Action action)
  {
    this.tile = tile;
    this.type = type;
    this.action = action;
  }
  
  public static void addCollector(TileEntity tile)
  {
    ManaNetworkEvent event = new ManaNetworkEvent(tile, ManaBlockType.COLLECTOR, Action.ADD);
    MinecraftForge.EVENT_BUS.post(event);
  }
  
  public static void removeCollector(TileEntity tile)
  {
    ManaNetworkEvent event = new ManaNetworkEvent(tile, ManaBlockType.COLLECTOR, Action.REMOVE);
    MinecraftForge.EVENT_BUS.post(event);
  }
  
  public static void addPool(TileEntity tile)
  {
    ManaNetworkEvent event = new ManaNetworkEvent(tile, ManaBlockType.POOL, Action.ADD);
    MinecraftForge.EVENT_BUS.post(event);
  }
  
  public static void removePool(TileEntity tile)
  {
    ManaNetworkEvent event = new ManaNetworkEvent(tile, ManaBlockType.POOL, Action.REMOVE);
    MinecraftForge.EVENT_BUS.post(event);
  }
  
  public static enum ManaBlockType
  {
    POOL,  COLLECTOR;
    
    private ManaBlockType() {}
  }
  
  public static enum Action
  {
    REMOVE,  ADD;
    
    private Action() {}
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.mana.ManaNetworkEvent
 * JD-Core Version:    0.7.0.1
 */