package theflogat.technomancy.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelExistenceFountain extends ModelBase{
	
	ModelRenderer m;

	public void render() {
		m = new ModelRenderer(this, 0, 0);
		m.addBox(0, 0, 0, 1, 4, 2);
		m.render(1F/16F);
	}
}
