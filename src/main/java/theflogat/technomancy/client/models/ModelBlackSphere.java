package theflogat.technomancy.client.models;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import theflogat.technomancy.api.renderers.ModelCatalystSpecial;
import theflogat.technomancy.util.Loc;

public class ModelBlackSphere extends ModelCatalystSpecial{
	
	int sphereID;
	
	public ModelBlackSphere(float size, float trX, float trY, float trZ) {
		textureHeight = 32;
		textureWidth = 64;
		
		if(Loc.isClient()){
			Sphere sphere = new Sphere();
			sphere.setDrawStyle(GLU.GLU_FILL);
			sphere.setNormals(GLU.GLU_SMOOTH);
			sphere.setOrientation(GLU.GLU_OUTSIDE);
			sphereID = GL11.glGenLists(1);
        
			GL11.glNewList(sphereID, GL11.GL_COMPILE);
			GL11.glTranslatef(trX, trY, trZ);
			sphere.draw(size, 32, 32);
			GL11.glEndList();
		}
	}
	
	public void render(){
		GL11.glColor3f(0.1F, 0.1F, 0.1F);
		GL11.glCallList(sphereID);
	}
}
