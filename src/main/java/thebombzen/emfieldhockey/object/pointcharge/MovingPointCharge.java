package thebombzen.emfieldhockey.object.pointcharge;

import thebombzen.emfieldhockey.Vector;
import thebombzen.emfieldhockey.object.ElectricFieldReceiver;
import thebombzen.emfieldhockey.object.MagneticFieldReceiver;
import thebombzen.emfieldhockey.object.MovingObject;
import thebombzen.emfieldhockey.object.PositionedObject;

public class MovingPointCharge extends PointCharge implements MovingObject,
		ElectricFieldReceiver, MagneticFieldReceiver {

	private double mass;
	private Vector velocity;

	public MovingPointCharge(Vector position, double charge, Vector velocity,
			double mass) {
		super(position, charge);
		this.velocity = velocity;
		this.mass = mass;
	}

	@Override
	public void collide(PositionedObject object) {
		if (object instanceof MovingObject) {
			collideWithMoving((MovingObject) object);
		} else {
			collideWithStationary(object);
		}
	}

	private void collideWithMoving(MovingObject object) {

		Vector collisionDirection = object.getPosition()
				.subtract(getPosition());
		Vector collisionV1 = getVelocity().getComponent(collisionDirection);
		Vector collisionV2 = object.getVelocity().getComponent(
				collisionDirection);
		Vector keptV1 = getVelocity().subtract(collisionV1);
		Vector keptV2 = object.getVelocity().subtract(collisionV2);
		Vector outV1 = collisionV1.multiply(getMass() - object.getMass())
				.add(collisionV2.multiply(2D * object.getMass()))
				.divide(getMass() + object.getMass());
		Vector outV2 = collisionV2.multiply(object.getMass() - getMass())
				.add(collisionV1.multiply(2D * getMass()))
				.divide(getMass() + object.getMass());
		object.setVelocity(outV2.add(keptV2));
		setVelocity(outV1.add(keptV1));

	}

	private void collideWithStationary(PositionedObject object) {
		Vector collisionDirection = object.getPosition()
				.subtract(getPosition());
		Vector collisionV = getVelocity().getComponent(collisionDirection);
		setVelocity(getVelocity().subtract(collisionV.multiply(2D)));

	}

	@Override
	public Vector getElectricForce(Vector electricField) {
		return electricField.multiply(getCharge());
	}

	@Override
	public Vector getMagneticForce(double magneticField) {
		return getVelocity().multiply(getCharge()).cross(magneticField);
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public Vector getVelocity() {
		return velocity;
	}

	@Override
	public void setMass(double mass) {
		this.mass = mass;
	}

	@Override
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	@Override
	public String toString() {
		return "MovingPointCharge [getVelocity()=" + getVelocity()
				+ ", getMass()=" + getMass() + ", getPosition()="
				+ getPosition() + ", getCharge()=" + getCharge() + "]";
	}

}
