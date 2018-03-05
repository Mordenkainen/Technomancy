package theflogat.technomancy.api.renderers;

import net.minecraft.client.model.ModelBase;

public abstract class ModelCatalystSpecial extends ModelBase {
	public static final float scale = 0.625F;
	
	public abstract void render();
}
