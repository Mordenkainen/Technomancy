package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTeslaCoil extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer Post;
    ModelRenderer Core;
    ModelRenderer TopCore;
    ModelRenderer RightCore;
    ModelRenderer LeftCore;
    ModelRenderer BackCore;
    ModelRenderer FrontCore;
    ModelRenderer BottomCore;
    ModelRenderer TopRing;
    ModelRenderer BottomRing;
  
  public ModelTeslaCoil()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Base = new ModelRenderer(this, 0, 25);
      Base.addBox(-3F, 0F, -3F, 6, 1, 6);
      Base.setRotationPoint(0F, 23F, 0F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Post = new ModelRenderer(this, 12, 2);
      Post.addBox(-0.5F, 0F, -0.5F, 1, 10, 1);
      Post.setRotationPoint(0F, 13F, 0F);
      Post.setTextureSize(64, 32);
      Post.mirror = true;
      setRotation(Post, 0F, 0F, 0F);
      Core = new ModelRenderer(this, 0, 7);
      Core.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
      Core.setRotationPoint(0F, 12F, 0F);
      Core.setTextureSize(64, 32);
      Core.mirror = true;
      setRotation(Core, 0F, 0F, 0F);
      TopCore = new ModelRenderer(this, 0, 0);
      TopCore.addBox(-1F, -1F, -1F, 2, 1, 2);
      TopCore.setRotationPoint(0F, 11F, 0F);
      TopCore.setTextureSize(64, 32);
      TopCore.mirror = true;
      setRotation(TopCore, 0F, 0F, 0F);
      RightCore = new ModelRenderer(this, 0, 0);
      RightCore.addBox(-1F, 0F, -1F, 2, 2, 2);
      RightCore.setRotationPoint(1F, 11F, 0F);
      RightCore.setTextureSize(64, 32);
      RightCore.mirror = true;
      setRotation(RightCore, 0F, 0F, 0F);
      LeftCore = new ModelRenderer(this, 0, 0);
      LeftCore.addBox(-1F, 0F, -1F, 2, 2, 2);
      LeftCore.setRotationPoint(-1F, 11F, 0F);
      LeftCore.setTextureSize(64, 32);
      LeftCore.mirror = true;
      setRotation(LeftCore, 0F, 0F, 0F);
      BackCore = new ModelRenderer(this, 0, 0);
      BackCore.addBox(-1F, 0F, -1F, 2, 2, 2);
      BackCore.setRotationPoint(0F, 11F, 1F);
      BackCore.setTextureSize(64, 32);
      BackCore.mirror = true;
      setRotation(BackCore, 0F, 0F, 0F);
      FrontCore = new ModelRenderer(this, 0, 0);
      FrontCore.addBox(-1F, 0F, -1F, 2, 2, 2);
      FrontCore.setRotationPoint(0F, 12F, -2F);
      FrontCore.setTextureSize(64, 32);
      FrontCore.mirror = true;
      setRotation(FrontCore, 1.570796F, 0F, 0F);
      BottomCore = new ModelRenderer(this, 0, 0);
      BottomCore.addBox(-1F, 0F, -1F, 2, 1, 2);
      BottomCore.setRotationPoint(0F, 13F, 0F);
      BottomCore.setTextureSize(64, 32);
      BottomCore.mirror = true;
      setRotation(BottomCore, 0F, 0F, 0F);
      TopRing = new ModelRenderer(this, 0, 13);
      TopRing.addBox(-2F, 0F, -2F, 4, 1, 4);
      TopRing.setRotationPoint(0F, 16F, 0F);
      TopRing.setTextureSize(64, 32);
      TopRing.mirror = true;
      setRotation(TopRing, 0F, 0F, 0F);
      BottomRing = new ModelRenderer(this, 0, 18);
      BottomRing.addBox(-3F, 0F, -3F, 6, 1, 6);
      BottomRing.setRotationPoint(0F, 19F, 0F);
      BottomRing.setTextureSize(64, 32);
      BottomRing.mirror = true;
      setRotation(BottomRing, 0F, 0F, 0F);
  }
  
 public void render()  {
	    final float f5 = 1.0F/16.0F;
	    Base.render(f5);
	    Post.render(f5);
	    Core.render(f5);
	    TopCore.render(f5);
	    RightCore.render(f5);
	    LeftCore.render(f5);
	    BackCore.render(f5);
	    FrontCore.render(f5);
	    BottomCore.render(f5);
 }
 public void renderRings() {
	 final float f5 = 1.0F/16.0F;

	    TopRing.render(f5);
		BottomRing.render(f5);
 }
 
private void setRotation(ModelRenderer model, float x, float y, float z) {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }

}
