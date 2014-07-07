package democretes.renderer.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelElectricBellows  extends ModelBase{
	
	public ModelRenderer BottomPlank;
    public ModelRenderer MiddlePlank;
    public ModelRenderer TopPlank;
    public ModelRenderer Bag;
    public ModelRenderer Nozzle;
  
  public ModelElectricBellows()  {
    this.textureWidth = 128;
    this.textureHeight = 64;
    
    this.BottomPlank = new ModelRenderer(this, 0, 0);
    this.BottomPlank.addBox(-6.0F, 0.0F, -6.0F, 12, 2, 12);
    this.BottomPlank.setRotationPoint(0.0F, 22.0F, 0.0F);
    this.BottomPlank.setTextureSize(128, 64);
    this.BottomPlank.mirror = true;
    setRotation(this.BottomPlank, 0.0F, 0.0F, 0.0F);
    
    this.MiddlePlank = new ModelRenderer(this, 0, 0);
    this.MiddlePlank.addBox(-6.0F, -1.0F, -6.0F, 12, 2, 12);
    this.MiddlePlank.setRotationPoint(0.0F, 16.0F, 0.0F);
    this.MiddlePlank.setTextureSize(128, 64);
    this.MiddlePlank.mirror = true;
    setRotation(this.MiddlePlank, 0.0F, 0.0F, 0.0F);
    
    this.TopPlank = new ModelRenderer(this, 0, 0);
    this.TopPlank.addBox(-6.0F, 0.0F, -6.0F, 12, 2, 12);
    this.TopPlank.setRotationPoint(0.0F, 8.0F, 0.0F);
    this.TopPlank.setTextureSize(128, 64);
    this.TopPlank.mirror = true;
    setRotation(this.TopPlank, 0.0F, 0.0F, 0.0F);
    
    this.Bag = new ModelRenderer(this, 48, 0);
    this.Bag.addBox(-10.0F, -12.03333F, -10.0F, 20, 24, 20);
    this.Bag.setRotationPoint(0.0F, 16.0F, 0.0F);
    this.Bag.setTextureSize(64, 32);
    this.Bag.mirror = true;
    setRotation(this.Bag, 0.0F, 0.0F, 0.0F);
    
    this.Nozzle = new ModelRenderer(this, 0, 36);
    this.Nozzle.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 2);
    this.Nozzle.setRotationPoint(0.0F, 16.0F, 6.0F);
    this.Nozzle.setTextureSize(128, 64);
    this.Nozzle.mirror = true;
    setRotation(this.Nozzle, 0.0F, 0.0F, 0.0F);
  }
  
  public void render()  {
    this.MiddlePlank.render(0.0625F);
    this.Nozzle.render(0.0625F);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
}

