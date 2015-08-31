package theflogat.technomancy.client.models;

import java.util.ArrayList;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

public class ModelExistencePylon extends ModelBase{
	
	ModelRenderer cube;
	ArrayList<ModelRenderer> pylon;
	
	public ModelExistencePylon() {
		ModelRenderer m;
		pylon = new ArrayList<ModelRenderer>();
		
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-4, 0, -4, 8, 1, 8);
		pylon.add(m);
		
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-2, 1F, -2, 4, 9, 4);
		pylon.add(m);
		
		m = new ModelRenderer(this, 0, 0);
		m.addBox(2, 9F, -1, 1, 4, 2);
		pylon.add(m);
		
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-3, 9F, -1, 1, 4, 2);
		pylon.add(m);
		
		cube = new ModelRenderer(this, 0, 0);
		cube.addBox(-1.5F, 12F, -1.5F, 3, 3, 3);
		cube.render(1F/16F);
	}
	
	public void render() {
		for(ModelRenderer m:pylon){
			m.render(1F/16F);
		}
	}
	
	public void renderCube(int meta){
		switch(meta){
		case 0:
			GL11.glColor3f(0, 0, 0.7F);
			break;
		case 1:
			GL11.glColor3f(0, 0.5F, 0.5F);
			break;
		case 2:
			GL11.glColor3f(0, 0.7F, 0);
			break;
		}
		cube.render(1F/16F);
		GL11.glColor3f(1, 1, 1);
	}
	
}
