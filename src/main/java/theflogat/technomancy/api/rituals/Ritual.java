package theflogat.technomancy.api.rituals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theflogat.technomancy.common.player.PlayerData;
import theflogat.technomancy.common.player.PlayerData.Affinity;
import theflogat.technomancy.util.helpers.RitualHelper;

public abstract class Ritual {

	public enum Type{
		EARTH(0),FIRE(1),WATER(2),LIGHT(3),DARK(4);

		int id;
		static Type[] allTypes = {EARTH,FIRE,WATER,LIGHT,DARK};

		private Type(int id) {
			this.id = id;
		}
		
		public static Type getType(int id) {
			for(Type t : allTypes){
				if(t.id==id){
					return t;
				}
			}
			return null;
		}
		
		public Affinity getAffinity(){
			return Affinity.getAffinity(id);
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
		return w.getBlockState(new BlockPos(x, y, z)).getBlock().getMetaFromState(w.getBlockState(new BlockPos(x, y, z))) == core.id;
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
	
	public void addAffinity(World w, String playerName){
		EntityPlayer player = w.getPlayerEntityByName(playerName);
		if(player==null){
			return;
		}
		PlayerData.addAffinity(w, player, core.getAffinity(), 5);
		for(Type t : frame){
			if(t!=null){
				PlayerData.addAffinity(w, player, t.getAffinity(), 1);
			}
		}
		int j = 0;
		for(int i=0;i<frame.length;i++){
			if(frame[i]!=null){
				j++;
			}
		}
		for(int i=0;i<=25*j;i++){
			PlayerData.addExistencePower(w.rand, player);
		}
	}

	public abstract boolean canApplyEffect(World w, int x, int y, int z);

	public abstract void applyEffect(World w, int x, int y, int z);
}