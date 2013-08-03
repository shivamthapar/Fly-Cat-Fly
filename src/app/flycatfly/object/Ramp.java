package app.flycatfly.object;

import org.andengine.entity.primitive.Line;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Ramp {
	public final float[] xCoords = {40,300, 375, 430};
	public final float[] yCoords = {600, 160, 130, 148};
	
	private VertexBufferObjectManager vbo;
	
	public Ramp(VertexBufferObjectManager v){
		vbo=v;
	}
	
	public Line[] getLines(){
		int i=0;
		Line[] lines = new Line[xCoords.length-1];
		while(i<xCoords.length-1){
			lines[i]=new Line(xCoords[i], yCoords[i], xCoords[i+1], yCoords[i+1], vbo);
			i++;
		}
		return lines;
	}

}
