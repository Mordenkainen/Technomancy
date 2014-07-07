package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEssentiaContainer extends ModelBase{
    ModelRenderer Core;
    ModelRenderer Lid;
  
  public ModelEssentiaContainer()  {
    textureWidth = 64;
    textureHeight = 32;
    
      Core = new ModelRenderer(this, 0, 10);
      Core.addBox(-5F, -6F, -5F, 10, 12, 10);
      Core.setRotationPoint(0F, 18F, 0F);
      Core.setTextureSize(64, 32);
      Core.mirror = true;
      setRotation(Core, 0F, 0F, 0F);
      Lid = new ModelRenderer(this, 0, 2);
      Lid.addBox(-3F, -1F, -3F, 6, 2, 6);
      Lid.setRotationPoint(0F, 11F, 0F);
      Lid.setTextureSize(64, 32);
      Lid.mirror = true;
      setRotation(Lid, 0F, 0F, 0F);
      
      
  }
  
  public void render() {
	final float f5 = 1F/16F;
    Core.render(f5);
    Lid.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
