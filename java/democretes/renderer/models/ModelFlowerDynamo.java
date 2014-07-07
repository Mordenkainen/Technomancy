package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFlowerDynamo extends ModelBase {
    ModelRenderer Shape1;
    ModelRenderer Shape2;
  
  public ModelFlowerDynamo()  {
    textureWidth = 64;
    textureHeight = 64;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(-8F, 0F, -8F, 16, 8, 16);
      Shape1.setRotationPoint(0F, 16F, 0F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 24);
      Shape2.addBox(-4F, 0F, -4F, 8, 8, 8);
      Shape2.setRotationPoint(0F, 8F, 0F);
      Shape2.setTextureSize(64, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
  }
  
  public void render()  {
	  final float f5 = 1F/16F;
	  Shape1.render(f5);
	  Shape2.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)  {
	  model.rotateAngleX = x;
	  model.rotateAngleY = y;
	  model.rotateAngleZ = z;
  }

}
