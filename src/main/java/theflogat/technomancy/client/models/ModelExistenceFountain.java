package theflogat.technomancy.client.models;

import java.util.ArrayList;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

public class ModelExistenceFountain extends ModelBase {

	ArrayList<ModelRenderer> fountain;
	ArrayList<ModelRenderer> liquid;

	public ModelExistenceFountain() {
		ModelRenderer m;
		
		fountain = new ArrayList<ModelRenderer>();
		//S1
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-10, -8, 15, 1, 8, 2);
		fountain.add(m);

		m = new ModelRenderer(this, 2, 2);
		m.addBox(-11, -8, 13, 1, 8, 2);
		fountain.add(m);

		m = new ModelRenderer(this, 2, 2);
		m.addBox(-11, -8, 17, 1, 8, 2);
		fountain.add(m);

		//S2
		m = new ModelRenderer(this, 10, 0);
		m.addBox(-23, -8, 15, 1, 8, 2);
		fountain.add(m);

		m = new ModelRenderer(this, 12, 0);
		m.addBox(-22, -8, 13, 1, 8, 2);
		fountain.add(m);

		m = new ModelRenderer(this, 12, 0);
		m.addBox(-22, -8, 17, 1, 8, 2);
		fountain.add(m);

		//S3
		m = new ModelRenderer(this, 4, 0);
		m.addBox(-17, -8, 22, 2, 8, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 6, 0);
		m.addBox(-19, -8, 21, 2, 8, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 8, 0);
		m.addBox(-15, -8, 21, 2, 8, 1);
		fountain.add(m);

		//S4
		m = new ModelRenderer(this, 4, 0);
		m.addBox(-17, -8, 9, 2, 8, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 6, 0);
		m.addBox(-19, -8, 10, 2, 8, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 8, 0);
		m.addBox(-15, -8, 10, 2, 8, 1);
		fountain.add(m);

		//C1
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-20, -8, 11, 1, 8, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-21, -8, 12, 1, 8, 1);
		fountain.add(m);

		//C2
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-13, -8, 11, 1, 8, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-12, -8, 12, 1, 8, 1);
		fountain.add(m);

		//C3
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-13, -8, 20, 1, 8, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-12, -8, 19, 1, 8, 1);
		fountain.add(m);

		//C4
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-20, -8, 20, 1, 8, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-21, -8, 19, 1, 8, 1);
		fountain.add(m);

		//B1
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-20, 0, 12, 8, 1, 8);
		fountain.add(m);

		//ST
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-19, 0, 11, 6, 1, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-19, 0, 20, 6, 1, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-12, 0, 13, 1, 1, 6);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-21, 0, 13, 1, 1, 6);
		fountain.add(m);

		//EN
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-17, 0, 10, 2, 1, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-17, 0, 21, 2, 1, 1);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-11, 0, 15, 1, 1, 2);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-22, 0, 15, 1, 1, 2);
		fountain.add(m);

		//FT
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-17, -1, 15, 2, 6, 2);
		fountain.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-20, 7, 12, 8, 1, 8);
		fountain.add(m);
		

		liquid = new ArrayList<ModelRenderer>();
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-20, -8, 12, 8, 1, 8);
		liquid.add(m);
		//ST
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-19, -8, 11, 6, 1, 1);
		liquid.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-19, -8, 20, 6, 1, 1);
		liquid.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-12, -8, 13, 1, 1, 6);
		liquid.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-21, -8, 13, 1, 1, 6);
		liquid.add(m);

		//EN
		m = new ModelRenderer(this, 0, 0);
		m.addBox(-17, -8, 10, 2, 1, 1);
		liquid.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-17, -8, 21, 2, 1, 1);
		liquid.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-11, -8, 15, 1, 1, 2);
		liquid.add(m);

		m = new ModelRenderer(this, 0, 0);
		m.addBox(-22, -8, 15, 1, 1, 2);
		liquid.add(m);
	}

	public void render() {
		for(ModelRenderer m:fountain){
			m.render(1/16F);
		}
	}

	public void renderLiquid(float amt, float maxAmt) {
		GL11.glColor4f(0, 0.2F, amt/maxAmt, 0.5F);
		for(ModelRenderer m:liquid){
			m.render(1/16F);
		}
		GL11.glColor4f(1, 1, 1, 0);
	}
}