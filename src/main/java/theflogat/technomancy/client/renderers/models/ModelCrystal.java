package theflogat.technomancy.client.renderers.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import theflogat.technomancy.common.tiles.technom.TileCrystal;

public class ModelCrystal extends ModelBase{
	
	private static ModelRenderer crystalBig;
	private static ModelRenderer crystalMed;
	private static ModelRenderer crystalSma;
	
	public ModelCrystal() {
		textureHeight = 64;
		textureWidth = 64;

		crystalBig = new ModelRenderer(this, 0, 0);
		crystalBig.addBox(-8, 8, -8, 16, 16, 16);
		
		crystalMed = new ModelRenderer(this, 0, 0);
		crystalMed.addBox(-4, 8, -4, 8, 16, 8);
		
		crystalSma = new ModelRenderer(this, 0, 0);
		crystalSma.addBox(-2, 8, -2, 4, 16, 4);
	}
	
	public void render(int stage, int meta) {
		float alpha = 0.6F;
		switch(meta){
		case 0:
			GL11.glColor4f(0.1F, 0.1F, 0.1F, alpha);
			break;
		case 1:
			GL11.glColor4f(0.9F, 0.9F, 0.9F, alpha);
			break;
		case 2:
			GL11.glColor4f(0.9F, 0, 0, alpha);
			break;
		case 3:
			GL11.glColor4f(0, 0.9F, 0, alpha);
			break;
		case 4:
			GL11.glColor4f(0, 0, 0.9F, alpha);
			break;
		}
		switch(stage){
		case 0:
			crystalBig.render(0.0625F);
			break;
		case 1:
			crystalMed.render(0.0625F);
			break;
		case 2:
			crystalSma.render(0.0625F);
			break;
		}
		GL11.glColor3f(1, 1, 1);
	}
}