package theflogat.technomancy.client.renderers.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCatalyst extends ModelBase{
	
	ModelRenderer main;
	
	public ModelCatalyst() {
		textureHeight = 32;
		textureWidth = 32;
		
		
	}
	
	public void render(int meta){
		switch(meta){
		case 0:
			GL11.glColor3f(0.1F, 0.1F, 0.1F);
			break;
		case 1:
			GL11.glColor3f(0.9F, 0.9F, 0.9F);
			break;
		case 2:
			GL11.glColor3f(0.9F, 0, 0);
			break;
		case 3:
			GL11.glColor3f(0, 0.9F, 0);
			break;
		case 4:
			GL11.glColor3f(0, 0, 0.9F);
			break;
		}
		main = new ModelRenderer(this, 0, 0);
		main.addBox(-8, 8, -8, 16, 16, 16);
		main.render(0.0625F);
	}

}
