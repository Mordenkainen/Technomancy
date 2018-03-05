package theflogat.technomancy.client.models;

import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCatalyst extends ModelBase {
	
	ModelRenderer main;
	
	public ModelCatalyst() {
		textureHeight = 32;
		textureWidth = 32;
		
		
	}
	
	public void render(int meta){
		Color c = ModelCrystal.getColor(meta);
		GL11.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		main = new ModelRenderer(this, 0, 0);
		main.addBox(-8, 8, -8, 16, 16, 16);
		main.render(0.0625F);
	}

}
