package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;



public class ModelReconstructor extends ModelBase {
  //fields
    ModelRenderer Base;
    ModelRenderer Base2;
    ModelRenderer Post1;
    ModelRenderer Post2;
    ModelRenderer Post3;
    ModelRenderer Post4;
    ModelRenderer TopBase;
    ModelRenderer TopBase2;
    ModelRenderer TopBase3;
  
  public ModelReconstructor()  {
    textureWidth = 64;
    textureHeight = 32;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(-8F, 0F, -8F, 16, 2, 16);
      Base.setRotationPoint(0F, 22F, 0F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Base2 = new ModelRenderer(this, 0, 18);
      Base2.addBox(-4F, 0F, -4F, 8, 2, 8);
      Base2.setRotationPoint(0F, 22F, 0F);
      Base2.setTextureSize(64, 32);
      Base2.mirror = true;
      setRotation(Base2, 0F, 0F, 3.141593F);
      Post1 = new ModelRenderer(this, 0, 0);
      Post1.addBox(-1F, 0F, -1F, 2, 10, 2);
      Post1.setRotationPoint(7F, 12F, 7F);
      Post1.setTextureSize(64, 32);
      Post1.mirror = true;
      setRotation(Post1, 0F, 3.141593F, 0F);
      Post2 = new ModelRenderer(this, 0, 0);
      Post2.addBox(-1F, 0F, -1F, 2, 10, 2);
      Post2.setRotationPoint(-7F, 12F, 7F);
      Post2.setTextureSize(64, 32);
      Post2.mirror = true;
      setRotation(Post2, 0F, 1.570796F, 0F);
      Post3 = new ModelRenderer(this, 0, 0);
      Post3.addBox(-1F, 0F, -1F, 2, 10, 2);
      Post3.setRotationPoint(7F, 12F, -7F);
      Post3.setTextureSize(64, 32);
      Post3.mirror = true;
      setRotation(Post3, 0F, -1.570796F, 0F);
      Post4 = new ModelRenderer(this, 0, 0);
      Post4.addBox(-1F, 0F, -1F, 2, 10, 2);
      Post4.setRotationPoint(-7F, 12F, -7F);
      Post4.setTextureSize(64, 32);
      Post4.mirror = true;
      setRotation(Post4, 0F, 0F, 0F);
      TopBase = new ModelRenderer(this, 0, 0);
      TopBase.addBox(-8F, 0F, -8F, 16, 2, 16);
      TopBase.setRotationPoint(0F, 10F, 0F);
      TopBase.setTextureSize(64, 32);
      TopBase.mirror = true;
      setRotation(TopBase, 0F, 1.570796F, 0.0F);
      TopBase2 = new ModelRenderer(this, 0, 18);
      TopBase2.addBox(-4F, 0F, -4F, 8, 2, 8);
      TopBase2.setRotationPoint(0F, 12F, 0F);
      TopBase2.setTextureSize(64, 32);
      TopBase2.mirror = true;
      setRotation(TopBase2, 0F, 0F, 0F);
      TopBase3 = new ModelRenderer(this, 0, 18);
      TopBase3.addBox(-4F, 0F, -4F, 8, 1, 8);
      TopBase3.setRotationPoint(0F, 9F, 0F);
      TopBase3.setTextureSize(64, 32);
      TopBase3.mirror = true;
      setRotation(TopBase3, 0F, 0F, 0F);
  }
  
  public void render()  {

	final float f5 = 1F/16.0F;
    Base.render(f5);
    Base2.render(f5);
    Post1.render(f5);
    Post2.render(f5);
    Post3.render(f5);
    Post4.render(f5);
    TopBase.render(f5);
    TopBase2.render(f5);
    TopBase3.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}
