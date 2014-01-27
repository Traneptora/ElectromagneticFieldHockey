package thebombzen.emfieldhockey.object;

import thebombzen.emfieldhockey.Vector;

public interface MovingObject extends PositionedObject {

	public void collide(PositionedObject object);

	public double getMass();

	public Vector getVelocity();

	public void setMass(double mass);

	public void setVelocity(Vector velocity);

}
