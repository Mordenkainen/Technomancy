package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFluxLamp extends ModelBase
{
  //fields
    ModelRenderer Core;
    ModelRenderer TopPost;
    ModelRenderer TopFlat;
    ModelRenderer BottomPost;
    ModelRenderer BottomFlat;
    ModelRenderer FrontPost;
    ModelRenderer RightPost;
    ModelRenderer LeftPost;
    ModelRenderer BackPost;
    ModelRenderer FrontFlat;
    ModelRenderer RightFlat;
    ModelRenderer LeftFlat;
    ModelRenderer BackFlat;
  
  public ModelFluxLamp()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Core = new ModelRenderer(this, 0, 12);
      Core.addBox(-4F, -6F, -4F, 8, 12, 8);
      Core.setRotationPoint(0F, 16F, 0F);
      Core.setTextureSize(64, 32);
      Core.mirror = true;
      setRotation(Core, 0F, 0F, 0F);
      TopPost = new ModelRenderer(this, 25, 0);
      TopPost.addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
      TopPost.setRotationPoint(0F, 9F, 0F);
      TopPost.setTextureSize(64, 32);
      TopPost.mirror = true;
      setRotation(TopPost, 0F, 0F, 0F);
      TopFlat = new ModelRenderer(this, 3, 6);
      TopFlat.addBox(-2F, 0F, -2F, 4, 1, 4);
      TopFlat.setRotationPoint(0F, 8F, 0F);
      TopFlat.setTextureSize(64, 32);
      TopFlat.mirror = true;
      setRotation(TopFlat, 0F, 0F, 0F);
      BottomPost = new ModelRenderer(this, 25, 0);
      BottomPost.addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
      BottomPost.setRotationPoint(0F, 22F, 0F);
      BottomPost.setTextureSize(64, 32);
      BottomPost.mirror = true;
      setRotation(BottomPost, 0F, 0F, 0F);
      BottomFlat = new ModelRenderer(this, 0, 0);
      BottomFlat.addBox(-2.5F, 0F, -2.5F, 5, 1, 5);
      BottomFlat.setRotationPoint(0F, 23F, 0F);
      BottomFlat.setTextureSize(64, 32);
      BottomFlat.mirror = true;
      setRotation(BottomFlat, 0F, 0F, 0F);
      FrontPost = new ModelRenderer(this, 24, 0);
      FrontPost.addBox(-2F, -2F, 0F, 4, 4, 3);
      FrontPost.setRotationPoint(0F, 16F, -7F);
      FrontPost.setTextureSize(64, 32);
      FrontPost.mirror = true;
      setRotation(FrontPost, 0F, 0F, 0F);
      RightPost = new ModelRenderer(this, 24, 0);
      RightPost.addBox(-2F, -2F, 0F, 4, 4, 3);
      RightPost.setRotationPoint(4F, 16F, 0F);
      RightPost.setTextureSize(64, 32);
      RightPost.mirror = true;
      setRotation(RightPost, 0F, 1.570796F, 0F);
      LeftPost = new ModelRenderer(this, 24, 0);
      LeftPost.addBox(-2F, -2F, 0F, 4, 4, 3);
      LeftPost.setRotationPoint(-7F, 16F, 0F);
      LeftPost.setTextureSize(64, 32);
      LeftPost.mirror = true;
      setRotation(LeftPost, 0F, 1.570796F, 0F);
      BackPost = new ModelRenderer(this, 24, 0);
      BackPost.addBox(-2F, -2F, 0F, 4, 4, 3);
      BackPost.setRotationPoint(0F, 16F, 4F);
      BackPost.setTextureSize(64, 32);
      BackPost.mirror = true;
      setRotation(BackPost, 0F, 0F, 0F);
      FrontFlat = new ModelRenderer(this, 0, 0);
      FrontFlat.addBox(-2.5F, 0F, -2.5F, 5, 1, 5);
      FrontFlat.setRotationPoint(0F, 16F, -8F);
      FrontFlat.setTextureSize(64, 32);
      FrontFlat.mirror = true;
      setRotation(FrontFlat, 1.570796F, 0F, 0F);
      RightFlat = new ModelRenderer(this, 0, 0);
      RightFlat.addBox(-2.5F, 0F, -2.5F, 5, 1, 5);
      RightFlat.setRotationPoint(7F, 16F, 0F);
      RightFlat.setTextureSize(64, 32);
      RightFlat.mirror = true;
      setRotation(RightFlat, 1.570796F, 1.570796F, 0F);
      LeftFlat = new ModelRenderer(this, 0, 0);
      LeftFlat.addBox(-2.5F, 0F, -2.5F, 5, 1, 5);
      LeftFlat.setRotationPoint(-8F, 16F, 0F);
      LeftFlat.setTextureSize(64, 32);
      LeftFlat.mirror = true;
      setRotation(LeftFlat, 1.570796F, 1.570796F, 0F);
      BackFlat = new ModelRenderer(this, 0, 0);
      BackFlat.addBox(-2.5F, 0F, -2.5F, 5, 1, 5);
      BackFlat.setRotationPoint(0F, 16F, 7F);
      BackFlat.setTextureSize(64, 32);
      BackFlat.mirror = true;
      setRotation(BackFlat, 1.570796F, 0F, 0F);
  }
  
  final float f5 = 1F/16F;
  
  public void renderCore() {	
    Core.render(f5);
  }
  
  public void renderTop() {
	  TopPost.render(f5);
	  TopFlat.render(f5);	    
  }
  
  public void renderBottom() {
	  BottomPost.render(f5);
	  BottomFlat.render(f5);
  }
  
  public void renderLeft() {
	  LeftPost.render(f5);
	  LeftFlat.render(f5);
  }
  
  public void renderRight() {
	  RightPost.render(f5);
	  RightFlat.render(f5);	  
  }
  
  public void renderBack() {
	    BackPost.render(f5);
	    BackFlat.render(f5);
  }
  
  public void renderFront() {
	  FrontPost.render(f5);
	  FrontFlat.render(f5);
  }
  
  
  private void setRotation(ModelRenderer model, float x, float y, float z)  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  } 


}
