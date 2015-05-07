package theflogat.technomancy.util;

public class AreaProtocolBuilder {
	
	public class Area{
		
		int startX;int startY;int startZ;
		int endX;int endY;int endZ;
		
		public Area(int startX, int startY, int startZ, int endX, int endY, int endZ) {
			this.startX = startX;this.startY = startY;this.startZ = startZ;
			this.endX = endX;this.endY = endY;this.endZ = endZ;
		}
	}
	
	public AreaProtocolBuilder() {
		
	}
}
