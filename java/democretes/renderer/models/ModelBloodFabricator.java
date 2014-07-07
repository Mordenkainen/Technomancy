package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelBloodFabricator extends ModelBase {
  //fields
    ModelRenderer Base;
    ModelRenderer Post1;
    ModelRenderer Post2;
    ModelRenderer Post3;
    ModelRenderer Post4;
    ModelRenderer Shape1;
  
  public ModelBloodFabricator()  {
    textureWidth = 64;
    textureHeight = 64;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(-7F, 0.01F, -7F, 14, 3, 14);
      Base.setRotationPoint(0F, 21F, 0F);
      Base.setTextureSize(64, 64);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Post1 = new ModelRenderer(this, 0, 0);
      Post1.addBox(-2F, 0F, -2F, 4, 3, 2);
      Post1.setRotationPoint(7F, 21F, 7F);
      Post1.setTextureSize(64, 64);
      Post1.mirror = true;
      setRotation(Post1, 0.0017453F, 0.7853982F, 0F);
      Post2 = new ModelRenderer(this, 0, 0);
      Post2.addBox(-2F, 0.0001F, -2F, 4, 3, 2);
      Post2.setRotationPoint(-7F, 21F, 7F);
      Post2.setTextureSize(64, 64);
      Post2.mirror = true;
      setRotation(Post2, 0.0017453F, -0.7853982F, 0F);
      Post3 = new ModelRenderer(this, 0, 0);
      Post3.addBox(-2F, 0F, 0F, 4, 3, 2);
      Post3.setRotationPoint(-7F, 21F, -7F);
      Post3.setTextureSize(64, 64);
      Post3.mirror = true;
      setRotation(Post3, -0.0017453F, 0.7853982F, 0F);
      Post4 = new ModelRenderer(this, 0, 0);
      Post4.addBox(-2F, 0F, 0F, 4, 3, 2);
      Post4.setRotationPoint(7F, 21F, -7F);
      Post4.setTextureSize(64, 64);
      Post4.mirror = true;
      setRotation(Post4, -0.0017453F, -0.7853982F, 0F);
      Shape1 = new ModelRenderer(this, 0, 18);
      Shape1.addBox(-4F, 0F, -4F, 8, 13, 8);
      Shape1.setRotationPoint(0F, 8F, 0F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
  }
  
  public void render()  {
    final float f5 = 1F/16F;
    Base.render(f5);
    Post1.render(f5);
    Post2.render(f5);
    Post3.render(f5);
    Post4.render(f5);
    Shape1.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}
