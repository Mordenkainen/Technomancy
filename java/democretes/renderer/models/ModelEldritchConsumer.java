package democretes.renderer.models;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelEldritchConsumer extends ModelBase{

	private ArrayList<ModelRenderer> parts = new ArrayList<ModelRenderer>();
	private ModelRenderer[][] pieces = new ModelRenderer[4][5];
	ModelRenderer rotator;

	public ModelEldritchConsumer() {
		textureHeight = 64;
		textureWidth = 64;

		ModelRenderer base = new ModelRenderer(this, 0, 0);
		base.addBox(0, 0, 0, 16, 6, 16);
		base.setRotationPoint(-16, -12, 0);
		parts.add(base);


		int l = 5;
		for(int i = 0; i<l; i++){
			ModelRenderer piece = new ModelRenderer(this, 0, 0);
			piece.addBox(-1, -0.5F, -1.5F, 2, 1, 3);
			piece.setRotationPoint(-8, -5.5F + i, 2.5F + i);
			pieces[0][i] = piece;
		}

		for(int i = 0; i<l; i++){
			ModelRenderer piece = new ModelRenderer(this, 0, 0);
			piece.addBox(-1, -0.5F, -1.5F, 2, 1, 3);
			piece.setRotationPoint(-8, -5.5F + i, 13.5F - i);
			pieces[1][i] = piece;
		}

		for(int i = 0; i<l; i++){
			ModelRenderer piece = new ModelRenderer(this, 0, 0);
			piece.addBox(-1, -0.5F, -1.5F, 2, 1, 3);
			piece.setRotationPoint(-2.5F - i, -5.5F + i, 8F);
			pieces[2][i] = piece;
		}

		for(int i = 0; i<l; i++){
			ModelRenderer piece = new ModelRenderer(this, 0, 0);
			piece.addBox(-1, -0.5F, -1.5F, 2, 1, 3);
			piece.setRotationPoint(-13.5F + i, -5.5F + i, 8F);
			pieces[3][i] = piece;
		}

		{
			ModelRenderer piece = new ModelRenderer(this, 0, 0);
			piece.addBox(-1, -0.5F, -1.5F, 2, 1, 3);
			piece.setRotationPoint(-1.5F, -13.5F, 8F);
			piece.rotateAngleY = (float) (Math.PI/2);
			piece.rotateAngleX = (float) (Math.PI/2.2);
			parts.add(piece);
		}{
			ModelRenderer piece = new ModelRenderer(this, 0, 0);
			piece.addBox(-1, -0.5F, -1.5F, 2, 1, 3);
			piece.setRotationPoint(-14.5F, -13.5F, 8F);
			piece.rotateAngleY = (float) (Math.PI/2);
			piece.rotateAngleX = (float) (Math.PI/1.8);
			parts.add(piece);
		}{
			ModelRenderer piece = new ModelRenderer(this, 0, 0);
			piece.addBox(-1, -0.5F, -1.5F, 2, 1, 3);
			piece.setRotationPoint(-8F, -13.5F, 1.5F);
			piece.rotateAngleX = (float) (Math.PI/1.8);
			parts.add(piece);
		}{
			ModelRenderer piece = new ModelRenderer(this, 0, 0);
			piece.addBox(-1, -0.5F, -1.5F, 2, 1, 3);
			piece.setRotationPoint(-8F, -13.5F, 14.5F);
			piece.rotateAngleX = (float) (Math.PI/2.2);
			parts.add(piece);
		}
		rotator = new ModelRenderer(this, 0, 0);
		rotator.addBox(-1, -0.5F, -1.5F, 2, 1, 3);
		rotator.setRotationPoint(-8F, -14F, 8F);
		{
			ModelRenderer piece = new ModelRenderer(this, 48, 0);
			piece.addBox(-1, -3F, -1F, 2, 6, 2);
			piece.setRotationPoint(-1F, -3F, 1F);
			piece.rotateAngleY = (float) Math.PI;
			parts.add(piece);
		}{
			ModelRenderer piece = new ModelRenderer(this, 48, 0);
			piece.addBox(-1, -3F, -1F, 2, 6, 2);
			piece.setRotationPoint(-15F, -3F, 1F);
			piece.rotateAngleY -= (float) Math.PI/2;
			parts.add(piece);
		}{
			ModelRenderer piece = new ModelRenderer(this, 48, 0);
			piece.addBox(-1, -3F, -1F, 2, 6, 2);
			piece.setRotationPoint(-15F, -3F, 15F);
			parts.add(piece);
		}{
			ModelRenderer piece = new ModelRenderer(this, 48, 0);
			piece.addBox(-1, -3F, -1F, 2, 6, 2);
			piece.setRotationPoint(-1F, -3F, 15F);
			piece.rotateAngleY = (float) Math.PI/2;
			parts.add(piece);
		}
	}




	public void render(float mult, float rot, boolean go) {
		for(ModelRenderer part : parts){
			part.render(mult);
		}
		
		if(go){
			rotator.rotateAngleY = (System.currentTimeMillis()/16) % 7;
		}
		rotator.render(mult);

		for(int i = 0; i<pieces.length; i++){
			for(ModelRenderer piece : pieces[i]){
				switch(i){
				case 0:
					piece.rotateAngleX = rot;
				case 1:
					piece.rotateAngleX = rot;
				case 2:
					piece.rotateAngleX = rot;
				case 3:
					piece.rotateAngleX = rot;
				}
				piece.render(mult);
			}
		}
	}
}
