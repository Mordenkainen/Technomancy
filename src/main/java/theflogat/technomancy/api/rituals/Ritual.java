package theflogat.technomancy.api.rituals;

import theflogat.technomancy.util.helpers.RitualHelper;
import net.minecraft.world.World;

public abstract class Ritual {

	public enum Type{
		DARK(0),LIGHT(1),FIRE(2),EARTH(3),WATER(4);

		int id;
		static Type[] allTypes = {DARK,LIGHT,FIRE,EARTH,WATER};

		private Type(int mid) {
			id = mid;
		}
	}

	protected Type[] frame = {null,null,null};
	protected Type core;

	public Ritual(Type[] frame, Type core) {
		for(int i=0;i<Math.min(frame.length, this.frame.length);i++){
			this.frame[i] = frame[i];
		}
		this.core = core;
	}

	protected int id = 0;

	protected void setId(int id){
		this.id = id;
	}

	public boolean isCoreComplete(World w, int x, int y, int z){
		return w.getBlockMetadata(x, y, z)==core.id;
	}

	public boolean isFrameComplete(World w, int x, int y, int z){
		for(int i=0;i<frame.length;i++){
			if(frame[i]==null){
				if(!checkEmpty(w, x, y, z, i)){
					return false;
				}
			}else{
				if(!RitualHelper.checkForT(w, x, y, z, frame[i].id, i)){
					return false;
				}
			}
		}
		return true;
	}
	
	protected void removeFrame(World w, int x, int y, int z){
		for(int i=0;i<frame.length;i++){
			if(frame[i]!=null){
				RitualHelper.removeT(w, x, y, z, i);
			}
		}
	}

	protected boolean checkEmpty(World w, int x, int y, int z, int tier){
		for(Type t:Type.allTypes){
			if(RitualHelper.checkForT(w, x, y, z, t.id, tier)){
				return false;
			}
		}
		return true;
	}

	public abstract boolean canApplyEffect(World w, int x, int y, int z);

	public abstract void applyEffect(World w, int x, int y, int z);
}