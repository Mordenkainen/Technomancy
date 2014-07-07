package thaumcraft.api.aspects;

public abstract interface IAspectContainer
{
  public abstract AspectList getAspects();
  
  public abstract void setAspects(AspectList paramAspectList);
  
  public abstract boolean doesContainerAccept(Aspect paramAspect);
  
  public abstract int addToContainer(Aspect paramAspect, int paramInt);
  
  public abstract boolean takeFromContainer(Aspect paramAspect, int paramInt);
  
  @Deprecated
  public abstract boolean takeFromContainer(AspectList paramAspectList);
  
  public abstract boolean doesContainerContainAmount(Aspect paramAspect, int paramInt);
  
  @Deprecated
  public abstract boolean doesContainerContain(AspectList paramAspectList);
  
  public abstract int containerContains(Aspect paramAspect);
}
