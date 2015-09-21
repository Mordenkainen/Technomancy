package theflogat.technomancy.client.models;

import java.awt.Color;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

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
		Color c = getColor(meta);
		GL11.glColor4f(c.getRed(), c.getGreen(), c.getBlue(), alpha);
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
		GL11.glColor4f(1, 1, 1, 0);
	}
	
	public static Color getColor(int meta){
		switch(meta){
		case 0:
			return new Color(0x00DD00);
		case 1:
			return new Color(0xDD0000);
		case 2:
			return new Color(0x0000DD);
		case 3:
			return new Color(0x111111);
		case 4:
			return new Color(0xDDDDDD);
		}
		return new Color(0x2266AA);
	}
}