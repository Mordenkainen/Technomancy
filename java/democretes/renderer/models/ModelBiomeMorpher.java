package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBiomeMorpher extends ModelBase
{
  //fields
    ModelRenderer Bottom;
    ModelRenderer Top;
    ModelRenderer Post1;
    ModelRenderer Post2;
    ModelRenderer Post3;
    ModelRenderer Post4;
    ModelRenderer Side1;
    ModelRenderer Side2;
    ModelRenderer Side3;
    ModelRenderer Side4;
    /*ModelRenderer CenterTop1;
    ModelRenderer CenterTop2;
    ModelRenderer CenterTop3;
    ModelRenderer CenterTop4;
    ModelRenderer CenterBottom1;
    ModelRenderer CenterBottome2;
    ModelRenderer CenterBottom3;
    ModelRenderer CenterBottome4;*/
    ModelRenderer Glass1;
    ModelRenderer Glass2;
    ModelRenderer Glass3;
    ModelRenderer Glass4;
  
  public ModelBiomeMorpher()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Bottom = new ModelRenderer(this, 0, 15);
      Bottom.addBox(-8F, -2F, -8F, 16, 1, 16);
      Bottom.setRotationPoint(0F, 25F, 0F);
      Bottom.setTextureSize(64, 32);
      Bottom.mirror = true;
      setRotation(Bottom, 0F, 0F, 0F);
      Top = new ModelRenderer(this, 0, 15);
      Top.addBox(-8F, 0F, -8F, 16, 1, 16);
      Top.setRotationPoint(0F, 8F, 0F);
      Top.setTextureSize(64, 32);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 3.141593F);
      Post1 = new ModelRenderer(this, 0, 14);
      Post1.addBox(0F, -8F, 0F, 1, 16, 1);
      Post1.setRotationPoint(-8F, 16F, 7F);
      Post1.setTextureSize(64, 32);
      Post1.mirror = true;
      setRotation(Post1, 0F, 0F, 0F);
      Post2 = new ModelRenderer(this, 0, 14);
      Post2.addBox(0F, -8F, 0F, 1, 16, 1);
      Post2.setRotationPoint(-8F, 16F, -8F);
      Post2.setTextureSize(64, 32);
      Post2.mirror = true;
      setRotation(Post2, 0F, 0F, 0F);
      Post3 = new ModelRenderer(this, 0, 14);
      Post3.addBox(0F, -8F, 0F, 1, 16, 1);
      Post3.setRotationPoint(7F, 15F, -8F);
      Post3.setTextureSize(64, 32);
      Post3.mirror = true;
      setRotation(Post3, 0F, 0F, 0F);
      Post4 = new ModelRenderer(this, 0, 14);
      Post4.addBox(0F, -8F, 0F, 1, 16, 1);
      Post4.setRotationPoint(7F, 15F, 7F);
      Post4.setTextureSize(64, 32);
      Post4.mirror = true;
      setRotation(Post4, 0F, 0F, 0F);
      Side1 = new ModelRenderer(this, 16, 0);
      Side1.addBox(0F, -2F, -2F, 1, 4, 4);
      Side1.setRotationPoint(-8F, 16F, 0F);
      Side1.setTextureSize(64, 32);
      Side1.mirror = true;
      setRotation(Side1, 0F, 0F, 0F);
      Side2 = new ModelRenderer(this, 16, 0);
      Side2.addBox(0F, -2F, -2F, 1, 4, 4);
      Side2.setRotationPoint(0F, 16F, -8F);
      Side2.setTextureSize(64, 32);
      Side2.mirror = true;
      setRotation(Side2, 0F, -1.570796F, 0F);
      Side3 = new ModelRenderer(this, 16, 0);
      Side3.addBox(0F, -2F, -2F, 1, 4, 4);
      Side3.setRotationPoint(7F, 16F, 0F);
      Side3.setTextureSize(64, 32);
      Side3.mirror = true;
      setRotation(Side3, 0F, 0F, 0F);
      Side4 = new ModelRenderer(this, 16, 0);
      Side4.addBox(0F, -2F, -2F, 1, 4, 4);
      Side4.setRotationPoint(0F, 16F, 8F);
      Side4.setTextureSize(64, 32);
      Side4.mirror = true;
      setRotation(Side4, 0F, 1.570796F, 0F);
      /*CenterTop1 = new ModelRenderer(this, 0, 0);
      CenterTop1.addBox(-2F, -0.5F, -0.5F, 5, 1, 1);
      CenterTop1.setRotationPoint(-4F, 13F, 0F);
      CenterTop1.setTextureSize(64, 32);
      CenterTop1.mirror = true;
      setRotation(CenterTop1, -3.141593F, 0F, -0.7853982F);
      CenterTop2 = new ModelRenderer(this, 0, 0);
      CenterTop2.addBox(-2F, -0.5F, -0.5F, 5, 1, 1);
      CenterTop2.setRotationPoint(0F, 13F, -4F);
      CenterTop2.setTextureSize(64, 32);
      CenterTop2.mirror = true;
      setRotation(CenterTop2, 0F, -1.570796F, -0.7853982F);
      CenterTop3 = new ModelRenderer(this, 0, 0);
      CenterTop3.addBox(-2F, -0.5F, -0.5F, 5, 1, 1);
      CenterTop3.setRotationPoint(4F, 13F, 0F);
      CenterTop3.setTextureSize(64, 32);
      CenterTop3.mirror = true;
      setRotation(CenterTop3, 0F, -3.141593F, -0.7853982F);
      CenterTop4 = new ModelRenderer(this, 0, 0);
      CenterTop4.addBox(-2F, -0.5F, -0.5F, 5, 1, 1);
      CenterTop4.setRotationPoint(0F, 13F, 4F);
      CenterTop4.setTextureSize(64, 32);
      CenterTop4.mirror = true;
      setRotation(CenterTop4, -3.141593F, 1.570796F, -0.7853982F);
      CenterBottom1 = new ModelRenderer(this, 0, 0);
      CenterBottom1.addBox(-2F, -0.5F, -0.5F, 5, 1, 1);
      CenterBottom1.setRotationPoint(-4F, 19F, 0F);
      CenterBottom1.setTextureSize(64, 32);
      CenterBottom1.mirror = true;
      setRotation(CenterBottom1, 0F, 0F, 0.7853982F);
      CenterBottome2 = new ModelRenderer(this, 0, 0);
      CenterBottome2.addBox(-2F, -0.5F, -0.5F, 5, 1, 1);
      CenterBottome2.setRotationPoint(0F, 19F, -4F);
      CenterBottome2.setTextureSize(64, 32);
      CenterBottome2.mirror = true;
      setRotation(CenterBottome2, 0F, -1.570796F, 0.7853982F);
      CenterBottom3 = new ModelRenderer(this, 0, 0);
      CenterBottom3.addBox(-2F, -0.5F, -0.5F, 5, 1, 1);
      CenterBottom3.setRotationPoint(4F, 19F, 0F);
      CenterBottom3.setTextureSize(64, 32);
      CenterBottom3.mirror = true;
      setRotation(CenterBottom3, 0F, 3.141593F, 0.7853982F);
      CenterBottome4 = new ModelRenderer(this, 0, 0);
      CenterBottome4.addBox(-2F, -0.5F, -0.5F, 5, 1, 1);
      CenterBottome4.setRotationPoint(0F, 19F, 4F);
      CenterBottome4.setTextureSize(64, 32);
      CenterBottome4.mirror = true;
      setRotation(CenterBottome4, 0F, 1.570796F, 0.7853982F);*/
      Glass1 = new ModelRenderer(this, 30, -14);
      Glass1.addBox(0.5F, -7F, -7F, 0, 15, 14);
      Glass1.setRotationPoint(-8F, 15F, 0F);
      Glass1.setTextureSize(64, 32);
      Glass1.mirror = true;
      setRotation(Glass1, 0F, 0F, 0F);
      Glass2 = new ModelRenderer(this, 30, -14);
      Glass2.addBox(-0.5F, -7F, -7F, 0, 15, 14);
      Glass2.setRotationPoint(0F, 15F, -7F);
      Glass2.setTextureSize(64, 32);
      Glass2.mirror = true;
      setRotation(Glass2, 0F, -1.570796F, 0F);
      Glass3 = new ModelRenderer(this, 30, -14);
      Glass3.addBox(-0.5F, -7F, -7F, 0, 15, 14);
      Glass3.setRotationPoint(7F, 15F, 0F);
      Glass3.setTextureSize(64, 32);
      Glass3.mirror = true;
      setRotation(Glass3, 0F, 3.141593F, 0F);
      Glass4 = new ModelRenderer(this, 30, -14);
      Glass4.addBox(0.5F, -7F, -7F, 0, 15, 14);
      Glass4.setRotationPoint(0F, 15F, 8F);
      Glass4.setTextureSize(64, 32);
      Glass4.mirror = true;
      setRotation(Glass4, 0F, 1.570796F, 0F);
  }
  
  public void render()  {
	final float f5 = 1F/16F;
    Bottom.render(f5);
    Top.render(f5);
    Post1.render(f5);
    Post2.render(f5);
    Post3.render(f5);
    Post4.render(f5);
    Side1.render(f5);
    Side2.render(f5);
    Side3.render(f5);
    Side4.render(f5);
   /*CenterTop1.render(f5);
    CenterTop2.render(f5);
    CenterTop3.render(f5);
    CenterTop4.render(f5);
    CenterBottom1.render(f5);
    CenterBottome2.render(f5);
    CenterBottom3.render(f5);
    CenterBottome4.render(f5);*/
    Glass1.render(f5);
    Glass2.render(f5);
    Glass3.render(f5);
    Glass4.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
