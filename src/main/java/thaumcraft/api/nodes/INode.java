package thaumcraft.api.nodes;

import thaumcraft.api.aspects.IAspectContainer;

public abstract interface INode
  extends IAspectContainer
{
  public abstract String getId();
  
  public abstract NodeType getNodeType();
  
  public abstract void setNodeType(NodeType paramNodeType);
  
  public abstract void setNodeModifier(NodeModifier paramNodeModifier);
  
  public abstract NodeModifier getNodeModifier();
  
  public abstract int getNodeVisBase();
  
  public abstract void setNodeVisBase(short paramShort);
}
