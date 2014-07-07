package democretes.renderer.models;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNodeGenerator extends ModelBase
{
  //fields
    ModelRenderer OuterBoard;
    ModelRenderer MiddleBoard;
    ModelRenderer BackBoard;
    ModelRenderer Base;
    ModelRenderer Pylon4;
    ModelRenderer Pylon3;
    ModelRenderer Pylon1;
    ModelRenderer Pylon2;
    ModelRenderer CenterPiece1;
  
  public ModelNodeGenerator()  {
    textureWidth = 256;
    textureHeight = 128;
    
      OuterBoard = new ModelRenderer(this, 176, 52);
      OuterBoard.addBox(0F, -19F, -19F, 2, 38, 38);
      OuterBoard.setRotationPoint(-8F, 0F, 0F);
      OuterBoard.setTextureSize(256, 128);
      OuterBoard.mirror = true;
      setRotation(OuterBoard, 0F, 0F, 0F);
      MiddleBoard = new ModelRenderer(this, 0, 40);
      MiddleBoard.addBox(0F, -22F, -22F, 2, 44, 44);
      MiddleBoard.setRotationPoint(-6F, 0F, 0F);
      MiddleBoard.setTextureSize(256, 128);
      MiddleBoard.mirror = true;
      setRotation(MiddleBoard, 0F, 0F, 0F);
      BackBoard = new ModelRenderer(this, 92, 52);
      BackBoard.addBox(0F, -19F, -19F, 4, 38, 38);
      BackBoard.setRotationPoint(-4F, 0F, 0F);
      BackBoard.setTextureSize(256, 128);
      BackBoard.mirror = true;
      setRotation(BackBoard, 0F, 0F, 0F);
      Base = new ModelRenderer(this, 200, 0);
      Base.addBox(-6F, -8F, -8F, 12, 32, 16);
      Base.setRotationPoint(2F, 0F, 0F);
      Base.setTextureSize(256, 128);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Pylon4 = new ModelRenderer(this, 0, 0);
      Pylon4.addBox(-4F, -2F, -2F, 8, 4, 4);
      Pylon4.setRotationPoint(-12F, -16F, 16F);
      Pylon4.setTextureSize(256, 128);
      Pylon4.mirror = true;
      setRotation(Pylon4, 0F, 0F, 0F);
      Pylon3 = new ModelRenderer(this, 0, 0);
      Pylon3.addBox(-4F, -2F, -2F, 8, 4, 4);
      Pylon3.setRotationPoint(-12F, 16F, 16F);
      Pylon3.setTextureSize(256, 128);
      Pylon3.mirror = true;
      setRotation(Pylon3, 0F, 0F, 0F);
      Pylon1 = new ModelRenderer(this, 0, 0);
      Pylon1.addBox(-4F, -2F, -2F, 8, 4, 4);
      Pylon1.setRotationPoint(-12F, -16F, -16F);
      Pylon1.setTextureSize(256, 128);
      Pylon1.mirror = true;
      setRotation(Pylon1, 0F, 0F, 0F);
      Pylon2 = new ModelRenderer(this, 0, 0);
      Pylon2.addBox(-4F, -2F, -2F, 8, 4, 4);
      Pylon2.setRotationPoint(-12F, 16F, -16F);
      Pylon2.setTextureSize(256, 128);
      Pylon2.mirror = true;
      setRotation(Pylon2, 0F, 0F, 0F);
      CenterPiece1 = new ModelRenderer(this, 144, 0);
      CenterPiece1.addBox(-1F, -13F, -13F, 2, 26, 26);
      CenterPiece1.setRotationPoint(-7.5F, 0F, 0F);
      CenterPiece1.setTextureSize(256, 128);
      CenterPiece1.mirror = true;
      setRotation(CenterPiece1, 0.7853982F, 0F, 0F);
  }
  
  
  final float f5 = 1F/16F;
  
  public void renderCore() {
	  CenterPiece1.render(f5);
  }
  
  public void render()  {	
    OuterBoard.render(f5);
    MiddleBoard.render(f5);
    BackBoard.render(f5);
    Base.render(f5);
    Pylon4.render(f5);
    Pylon3.render(f5);
    Pylon1.render(f5);
    Pylon2.render(f5);    
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
