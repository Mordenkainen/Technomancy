package theflogat.technomancy.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelEssentiaFusor extends ModelBase {
	public ModelRenderer fusor;

    public ModelEssentiaFusor() {
        textureWidth = 64;
        textureHeight = 32;
        fusor = new ModelRenderer(this, 0, 0);
        fusor.setRotationPoint(0.0F, 0.0F, 0.0F);
        fusor.addBox(0.0F, 0.0F, 0.0F, 16, 12, 16, 0.0F);
    }

    public void render() {
    	final float scale = 1F/16F;
        fusor.render(scale);
    }
}
