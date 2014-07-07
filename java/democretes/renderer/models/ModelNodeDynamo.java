package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNodeDynamo extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer UpperBase;
    ModelRenderer SmallPost1;
    ModelRenderer SmallPost2;
    ModelRenderer SmallPost3;
    ModelRenderer SmallPost4;
    ModelRenderer Arm1;
    ModelRenderer Arm2;
    ModelRenderer Arm3;
    ModelRenderer Arm4;
    ModelRenderer SmallArm1;
    ModelRenderer SmallArm2;
    ModelRenderer SmallArm3;
    ModelRenderer SmallArm4;
    ModelRenderer Pannel1;
    ModelRenderer Pannel2;
    ModelRenderer Pannel3;
    ModelRenderer Pannel4;
    ModelRenderer Post1;
    ModelRenderer Post2;
    ModelRenderer Post3;
    ModelRenderer Post4;
  
  public ModelNodeDynamo()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Base = new ModelRenderer(this, 0, 8);
      Base.addBox(-8F, -4F, -8F, 16, 8, 16);
      Base.setRotationPoint(0F, 20F, 0F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      UpperBase = new ModelRenderer(this, 24, 1);
      UpperBase.addBox(-3F, -0.5F, -3F, 6, 1, 6);
      UpperBase.setRotationPoint(0F, 13F, 0F);
      UpperBase.setTextureSize(64, 32);
      UpperBase.mirror = true;
      setRotation(UpperBase, 0F, 0.7853982F, -3.141593F);
      SmallPost1 = new ModelRenderer(this, 4, 20);
      SmallPost1.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
      SmallPost1.setRotationPoint(2.8F, 14.5F, 0F);
      SmallPost1.setTextureSize(64, 32);
      SmallPost1.mirror = true;
      setRotation(SmallPost1, 0F, 0.7853982F, 0F);
      SmallPost2 = new ModelRenderer(this, 4, 20);
      SmallPost2.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
      SmallPost2.setRotationPoint(0F, 14.5F, 2.8F);
      SmallPost2.setTextureSize(64, 32);
      SmallPost2.mirror = true;
      setRotation(SmallPost2, 0F, 0.7853982F, 0F);
      SmallPost3 = new ModelRenderer(this, 4, 20);
      SmallPost3.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
      SmallPost3.setRotationPoint(-2.8F, 14.5F, 0F);
      SmallPost3.setTextureSize(64, 32);
      SmallPost3.mirror = true;
      setRotation(SmallPost3, 0F, 0.7853982F, 0F);
      SmallPost4 = new ModelRenderer(this, 4, 20);
      SmallPost4.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
      SmallPost4.setRotationPoint(0F, 14.5F, -2.8F);
      SmallPost4.setTextureSize(64, 32);
      SmallPost4.mirror = true;
      setRotation(SmallPost4, 0F, 0.7853982F, 0F);
      Arm1 = new ModelRenderer(this, 0, 19);
      Arm1.addBox(-0.5F, -2F, -0.5F, 1, 4, 1);
      Arm1.setRotationPoint(4.8F, 12.1F, 0F);
      Arm1.setTextureSize(64, 32);
      Arm1.mirror = true;
      setRotation(Arm1, 0F, 0F, -2.007129F);
      Arm2 = new ModelRenderer(this, 0, 19);
      Arm2.addBox(-0.5F, -2F, -0.5F, 1, 4, 1);
      Arm2.setRotationPoint(0F, 12.1F, 4.8F);
      Arm2.setTextureSize(64, 32);
      Arm2.mirror = true;
      setRotation(Arm2, 2.007129F, 0F, 0F);
      Arm3 = new ModelRenderer(this, 0, 19);
      Arm3.addBox(-0.5F, -2F, -0.5F, 1, 4, 1);
      Arm3.setRotationPoint(-4.8F, 12.1F, 0F);
      Arm3.setTextureSize(64, 32);
      Arm3.mirror = true;
      setRotation(Arm3, 0F, 0F, 2.007129F);
      Arm4 = new ModelRenderer(this, 0, 19);
      Arm4.addBox(-0.5F, -2F, -0.5F, 1, 4, 1);
      Arm4.setRotationPoint(0F, 12.1F, -4.8F);
      Arm4.setTextureSize(64, 32);
      Arm4.mirror = true;
      setRotation(Arm4, -2.007129F, 0F, 0F);
      SmallArm1 = new ModelRenderer(this, 0, 19);
      SmallArm1.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
      SmallArm1.setRotationPoint(5.4F, 9.7F, 0F);
      SmallArm1.setTextureSize(64, 32);
      SmallArm1.mirror = true;
      setRotation(SmallArm1, 0F, 0F, -0.4363323F);
      SmallArm2 = new ModelRenderer(this, 0, 19);
      SmallArm2.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
      SmallArm2.setRotationPoint(0F, 9.7F, 5.4F);
      SmallArm2.setTextureSize(64, 32);
      SmallArm2.mirror = true;
      setRotation(SmallArm2, 0.4363323F, 0F, 0F);
      SmallArm3 = new ModelRenderer(this, 0, 19);
      SmallArm3.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
      SmallArm3.setRotationPoint(-5.4F, 9.7F, 0F);
      SmallArm3.setTextureSize(64, 32);
      SmallArm3.mirror = true;
      setRotation(SmallArm3, 0F, 0F, 0.4363323F);
      SmallArm4 = new ModelRenderer(this, 0, 19);
      SmallArm4.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
      SmallArm4.setRotationPoint(0F, 9.7F, -5.4F);
      SmallArm4.setTextureSize(64, 32);
      SmallArm4.mirror = true;
      setRotation(SmallArm4, -0.4363323F, 0F, 0F);
      Pannel1 = new ModelRenderer(this, 54, 0);
      Pannel1.addBox(-0.5F, -2F, -2F, 1, 11, 4);
      Pannel1.setRotationPoint(8F, 15F, 0F);
      Pannel1.setTextureSize(64, 32);
      Pannel1.mirror = true;
      setRotation(Pannel1, 0F, -3.141593F, 0F);
      Pannel2 = new ModelRenderer(this, 54, 0);
      Pannel2.addBox(-0.5F, -2F, -2F, 1, 11, 4);
      Pannel2.setRotationPoint(0F, 15F, 8F);
      Pannel2.setTextureSize(64, 32);
      Pannel2.mirror = true;
      setRotation(Pannel2, 0F, 1.570796F, 0F);
      Pannel3 = new ModelRenderer(this, 54, 0);
      Pannel3.addBox(-0.5F, -2F, -2F, 1, 11, 4);
      Pannel3.setRotationPoint(-8F, 15F, 0F);
      Pannel3.setTextureSize(64, 32);
      Pannel3.mirror = true;
      setRotation(Pannel3, 0F, 0F, 0F);
      Pannel4 = new ModelRenderer(this, 54, 0);
      Pannel4.addBox(-0.5F, -2F, -2F, 1, 11, 4);
      Pannel4.setRotationPoint(0F, 15F, -8F);
      Pannel4.setTextureSize(64, 32);
      Pannel4.mirror = true;
      setRotation(Pannel4, 0F, -1.570796F, 0F);
      Post1 = new ModelRenderer(this, 0, 9);
      Post1.addBox(-1F, -1F, -2F, 2, 2, 5);
      Post1.setRotationPoint(6F, 16F, 0F);
      Post1.setTextureSize(64, 32);
      Post1.mirror = true;
      setRotation(Post1, 2.356194F, -1.570796F, 0F);
      Post2 = new ModelRenderer(this, 0, 9);
      Post2.addBox(-1F, -1F, -2F, 2, 2, 5);
      Post2.setRotationPoint(0F, 16F, 6F);
      Post2.setTextureSize(64, 32);
      Post2.mirror = true;
      setRotation(Post2, 0.7853982F, 0F, 0F);
      Post3 = new ModelRenderer(this, 0, 9);
      Post3.addBox(-1F, -1F, -2F, 2, 2, 5);
      Post3.setRotationPoint(-6F, 16F, 0F);
      Post3.setTextureSize(64, 32);
      Post3.mirror = true;
      setRotation(Post3, 2.356194F, 1.570796F, 0F);
      Post4 = new ModelRenderer(this, 0, 9);
      Post4.addBox(-1F, -1F, -2F, 2, 2, 5);
      Post4.setRotationPoint(0F, 16F, -6F);
      Post4.setTextureSize(64, 32);
      Post4.mirror = true;
      setRotation(Post4, 0.7853982F, 3.141593F, 0F);
  }
  
  public void render()  {
    final float f5 = 1F/16F;
    Base.render(f5);
    UpperBase.render(f5);
    SmallPost1.render(f5);
    SmallPost2.render(f5);
    SmallPost3.render(f5);
    SmallPost4.render(f5);
    Arm1.render(f5);
    Arm2.render(f5);
    Arm3.render(f5);
    Arm4.render(f5);
    SmallArm1.render(f5);
    SmallArm2.render(f5);
    SmallArm3.render(f5);
    SmallArm4.render(f5);
    Pannel1.render(f5);
    Pannel2.render(f5);
    Pannel3.render(f5);
    Pannel4.render(f5);
    Post1.render(f5);
    Post2.render(f5);
    Post3.render(f5);
    Post4.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
