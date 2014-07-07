package thaumcraft.api.aspects;

import java.io.Serializable;
import java.util.LinkedHashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import democretes.compat.Thaumcraft;

public class AspectList implements Serializable
{
  public LinkedHashMap<Aspect, Integer> aspects = new LinkedHashMap();
  




  public AspectList(int id, int meta)
  {
    try
    {
      AspectList temp = Thaumcraft.getObjectAspects(new ItemStack(id, 1, meta));
      if (temp != null) {
        for (Aspect tag : temp.getAspects()) {
          add(tag, temp.getAmount(tag));
        }
      }
    } catch (Exception e) {}
  }
  
  public AspectList() {}
  
  public AspectList copy() {
    AspectList out = new AspectList();
    for (Aspect a : getAspects())
      out.add(a, getAmount(a));
    return out;
  }
  


  public int size()
  {
    return aspects.size();
  }
  


  public int visSize()
  {
    int q = 0;
    
    for (Aspect as : aspects.keySet()) {
      q += getAmount(as);
    }
    
    return q;
  }
  


  public Aspect[] getAspects()
  {
    Aspect[] q = new Aspect[1];
    return (Aspect[])aspects.keySet().toArray(q);
  }
  


  public Aspect[] getPrimalAspects()
  {
    AspectList t = new AspectList();
    for (Aspect as : aspects.keySet()) {
      if (as.isPrimal()) {
        t.add(as, 1);
      }
    }
    Aspect[] q = new Aspect[1];
    return (Aspect[])aspects.keySet().toArray(q);
  }
  


  public Aspect[] getAspectsSorted()
  {
    Aspect[] out = (Aspect[])aspects.keySet().toArray(new Aspect[1]);
    boolean change = false;
    do {
      change = false;
      for (int a = 0; a < out.length - 1; a++) {
        Aspect e1 = out[a];
        Aspect e2 = out[(a + 1)];
        if ((e1 != null) && (e2 != null) && (e1.getTag().compareTo(e2.getTag()) > 0)) {
          out[a] = e2;
          out[(a + 1)] = e1;
          change = true;
          break;
        }
      }
    } while (change == true);
    return out;
  }
  


  public Aspect[] getAspectsSortedAmount()
  {
    Aspect[] out = (Aspect[])aspects.keySet().toArray(new Aspect[1]);
    boolean change = false;
    do {
      change = false;
      for (int a = 0; a < out.length - 1; a++) {
        int e1 = getAmount(out[a]);
        int e2 = getAmount(out[(a + 1)]);
        if ((e1 > 0) && (e2 > 0) && (e2 > e1)) {
          Aspect ea = out[a];
          Aspect eb = out[(a + 1)];
          out[a] = eb;
          out[(a + 1)] = ea;
          change = true;
          break;
        }
      }
    } while (change == true);
    return out;
  }
  



  public int getAmount(Aspect key)
  {
    return aspects.get(key) == null ? 0 : ((Integer)aspects.get(key)).intValue();
  }
  





  public boolean reduce(Aspect key, int amount)
  {
    if (getAmount(key) >= amount) {
      int am = getAmount(key) - amount;
      aspects.put(key, Integer.valueOf(am));
      return true;
    }
    return false;
  }
  






  public AspectList remove(Aspect key, int amount)
  {
    int am = getAmount(key) - amount;
    if (am <= 0) aspects.remove(key); else
      aspects.put(key, Integer.valueOf(am));
    return this;
  }
  





  public AspectList remove(Aspect key)
  {
    aspects.remove(key);
    return this;
  }
  






  public AspectList add(Aspect aspect, int amount)
  {
    if (aspects.containsKey(aspect)) {
      int oldamount = ((Integer)aspects.get(aspect)).intValue();
      amount += oldamount;
    }
    aspects.put(aspect, Integer.valueOf(amount));
    return this;
  }
  







  public AspectList merge(Aspect aspect, int amount)
  {
    if (aspects.containsKey(aspect)) {
      int oldamount = ((Integer)aspects.get(aspect)).intValue();
      if (amount < oldamount) { amount = oldamount;
      }
    }
    aspects.put(aspect, Integer.valueOf(amount));
    return this;
  }
  





  public void readFromNBT(NBTTagCompound nbttagcompound)
  {
    aspects.clear();
    NBTTagList tlist = nbttagcompound.getTagList("Aspects");
    for (int j = 0; j < tlist.tagCount(); j++) {
      NBTTagCompound rs = (NBTTagCompound)tlist.tagAt(j);
      if (rs.hasKey("key")) {
        add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
      }
    }
  }
  






  public void writeToNBT(NBTTagCompound nbttagcompound)
  {
    NBTTagList tlist = new NBTTagList();
    nbttagcompound.setTag("Aspects", tlist);
    for (Aspect aspect : getAspects()) {
      if (aspect != null) {
        NBTTagCompound f = new NBTTagCompound();
        f.setString("key", aspect.getTag());
        f.setInteger("amount", getAmount(aspect));
        tlist.appendTag(f);
      }
    }
  }
}
