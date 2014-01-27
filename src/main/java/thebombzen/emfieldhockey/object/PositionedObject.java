package thebombzen.emfieldhockey.object;

import thebombzen.emfieldhockey.Vector;

public interface PositionedObject extends RenderableObject {
	public Vector getPosition();

	public double getRadius();

	public void setPosition(Vector vector);

}
