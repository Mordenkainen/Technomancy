package theflogat.technomancy.client.models;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelExistenceBurner extends ModelBase{
	
	ArrayList<ModelRenderer> core;
	ModelRenderer burner;
	ModelRenderer cube;
	
	public ModelExistenceBurner() {
		ModelRenderer m;
		core = new ArrayList<ModelRenderer>();
		
		burner = new ModelRenderer(this, 0, 0);
		burner.addBox(-4, 0, -4, 8, 1, 8);
		
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-1, 1, -1, 2, 6, 2);
		core.add(m);
		
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-4, 7, -4, 8, 1, 8);
		core.add(m);
		
		cube = new ModelRenderer(this, 0, 0);
		cube.addBox(-2, 10, -2, 4, 4, 4);
	}
	
	public void render(){
		for(ModelRenderer m : core){
			m.render(1F/16F);
		}
	}
	
	public void renderCube(boolean burn){
		GL11.glColor3f(burn ? 0.5F : 0, 0.7F, 0);
		cube.render(1F/16F);
		GL11.glColor3f(1, 1, 1);
	}
	
	public void renderBottom() {
		burner.render(1F/16F);
	}
}
