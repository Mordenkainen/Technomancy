package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelManaFabricator extends ModelBase {
  //fields
    ModelRenderer PotBase;
    ModelRenderer PotTop;
    ModelRenderer Stem;
    ModelRenderer Leaf1;
    ModelRenderer Leaf2;
    ModelRenderer Bud;
    ModelRenderer Petal1;
    ModelRenderer Petal2;
    ModelRenderer Petal3;
    ModelRenderer Petal4;
    ModelRenderer Petal5;
    ModelRenderer Petal6;
    ModelRenderer Petal7;
    ModelRenderer Petal8;
  
  public ModelManaFabricator()  {
    textureWidth = 64;
    textureHeight = 32;
    
      PotBase = new ModelRenderer(this, 12, 9);
      PotBase.addBox(-2F, 0F, -2F, 4, 4, 4);
      PotBase.setRotationPoint(0F, 20F, 0F);
      PotBase.setTextureSize(64, 32);
      PotBase.mirror = true;
      setRotation(PotBase, 0F, 0F, 0F);
      PotTop = new ModelRenderer(this, 0, 0);
      PotTop.addBox(-3F, 0F, -3F, 6, 1, 6);
      PotTop.setRotationPoint(0F, 19F, 0F);
      PotTop.setTextureSize(64, 32);
      PotTop.mirror = true;
      setRotation(PotTop, 0F, 0F, 0F);
      Stem = new ModelRenderer(this, 24, 0);
      Stem.addBox(-0.5F, 0F, -0.5F, 1, 8, 1);
      Stem.setRotationPoint(0F, 11F, 0F);
      Stem.setTextureSize(64, 32);
      Stem.mirror = true;
      setRotation(Stem, 0F, 0F, 0F);
      Leaf1 = new ModelRenderer(this, 0, 12);
      Leaf1.addBox(0F, 0F, 0F, 3, 0, 3);
      Leaf1.setRotationPoint(0F, 15F, 0F);
      Leaf1.setTextureSize(64, 32);
      Leaf1.mirror = true;
      setRotation(Leaf1, 0.2617994F, 0F, -0.2617994F);
      Leaf2 = new ModelRenderer(this, 0, 12);
      Leaf2.addBox(0F, 0F, 0F, 3, 0, 3);
      Leaf2.setRotationPoint(0F, 17F, 0F);
      Leaf2.setTextureSize(64, 32);
      Leaf2.mirror = true;
      setRotation(Leaf2, 0.2617994F, 3.141593F, -0.2617994F);
      Bud = new ModelRenderer(this, 0, 15);
      Bud.addBox(-1F, -0.7F, -1F, 2, 1, 2);
      Bud.setRotationPoint(0F, 11F, 0F);
      Bud.setTextureSize(64, 32);
      Bud.mirror = true;
      setRotation(Bud, 0F, 0F, 0F);
      Petal1 = new ModelRenderer(this, 0, 8);
      Petal1.addBox(0.5F, 0F, 0.5F, 3, 0, 3);
      Petal1.setRotationPoint(0F, 11F, 0F);
      Petal1.setTextureSize(64, 32);
      Petal1.mirror = true;
      setRotation(Petal1, 0.5235988F, 0F, -0.5235988F);
      Petal2 = new ModelRenderer(this, 0, 8);
      Petal2.addBox(0.5F, 0F, 0.5F, 3, 0, 3);
      Petal2.setRotationPoint(0F, 12F, 0F);
      Petal2.setTextureSize(64, 32);
      Petal2.mirror = true;
      setRotation(Petal2, 0.5235988F, 1.570796F, -0.5235988F);
      Petal3 = new ModelRenderer(this, 0, 8);
      Petal3.addBox(0.5F, 0F, 0.5F, 3, 0, 3);
      Petal3.setRotationPoint(0F, 11F, 0F);
      Petal3.setTextureSize(64, 32);
      Petal3.mirror = true;
      setRotation(Petal3, 0.5235988F, 0.7853982F, -0.5235988F);
      Petal4 = new ModelRenderer(this, 0, 8);
      Petal4.addBox(0.5F, 0F, 0.5F, 3, 0, 3);
      Petal4.setRotationPoint(0F, 11F, 0F);
      Petal4.setTextureSize(64, 32);
      Petal4.mirror = true;
      setRotation(Petal4, 0.5235988F, 2.356194F, -0.5235988F);
      Petal5 = new ModelRenderer(this, 0, 8);
      Petal5.addBox(0.5F, 0F, 0.5F, 3, 0, 3);
      Petal5.setRotationPoint(0F, 11F, 0F);
      Petal5.setTextureSize(64, 32);
      Petal5.mirror = true;
      setRotation(Petal5, 0.5235988F, 3.141593F, -0.5235988F);
      Petal6 = new ModelRenderer(this, 0, 8);
      Petal6.addBox(0.5F, 0F, 0.5F, 3, 0, 3);
      Petal6.setRotationPoint(0F, 11F, 0F);
      Petal6.setTextureSize(64, 32);
      Petal6.mirror = true;
      setRotation(Petal6, 0.5235988F, 0.7853982F, -0.5235988F);
      Petal7 = new ModelRenderer(this, 0, 8);
      Petal7.addBox(0.5F, 0F, 0.5F, 3, 0, 3);
      Petal7.setRotationPoint(0F, 11F, 0F);
      Petal7.setTextureSize(64, 32);
      Petal7.mirror = true;
      setRotation(Petal7, 0.5235988F, -1.570796F, -0.5235988F);
      Petal8 = new ModelRenderer(this, 0, 8);
      Petal8.addBox(0.5F, 0F, 0.5F, 3, 0, 3);
      Petal8.setRotationPoint(0F, 11F, 0F);
      Petal8.setTextureSize(64, 32);
      Petal8.mirror = true;
      setRotation(Petal8, 0.5235988F, -2.356194F, -0.5235988F);
  }
  
  public void render()  {
	  final float f5 = 0.0625F;
	  PotBase.render(f5);
	  PotTop.render(f5);
	  Stem.render(f5);
	  Leaf1.render(f5);
	  Leaf2.render(f5);
	  Bud.render(f5);
	  Petal1.render(f5);
	  Petal2.render(f5);
	  Petal3.render(f5);
	  Petal4.render(f5);
	  Petal5.render(f5);
	  Petal6.render(f5);
	  Petal7.render(f5);
	  Petal8.render(f5);
	  
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}
