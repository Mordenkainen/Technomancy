package thaumcraft.api.aspects;

import net.minecraftforge.common.ForgeDirection;

public abstract interface IEssentiaTransport
{
  public abstract boolean isConnectable(ForgeDirection paramForgeDirection);
  
  public abstract boolean canInputFrom(ForgeDirection paramForgeDirection);
  
  public abstract boolean canOutputTo(ForgeDirection paramForgeDirection);
  
  public abstract void setSuction(Aspect paramAspect, int paramInt);
  
  public abstract Aspect getSuctionType(ForgeDirection paramForgeDirection);
  
  public abstract int getSuctionAmount(ForgeDirection paramForgeDirection);
  
  public abstract int takeVis(Aspect paramAspect, int paramInt);
  
  public abstract int addVis(Aspect paramAspect, int paramInt);
  
  public abstract Aspect getEssentiaType(ForgeDirection paramForgeDirection);
  
  public abstract int getEssentiaAmount(ForgeDirection paramForgeDirection);
  
  public abstract int getMinimumSuction();
  
  public abstract boolean renderExtendedTube();
}
