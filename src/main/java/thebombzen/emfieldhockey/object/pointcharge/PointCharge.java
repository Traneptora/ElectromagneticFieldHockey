package thebombzen.emfieldhockey.object.pointcharge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import thebombzen.emfieldhockey.Constants;
import thebombzen.emfieldhockey.Vector;
import thebombzen.emfieldhockey.World;
import thebombzen.emfieldhockey.object.ElectricFieldContributor;
import thebombzen.emfieldhockey.object.PositionedObject;

public class PointCharge implements PositionedObject, ElectricFieldContributor {

	private double charge;
	private Vector position;

	public static final PointCharge DUMMY = new PointCharge(null, 0D);

	protected PointCharge(Vector position, double charge) {
		this.position = position;
		this.charge = charge;
	}

	public double getCharge() {
		return charge;
	}

	@Override
	public Vector getElectricField(Vector position) {
		Vector r = position.subtract(getPosition());
		return r.multiply(Constants.K * getCharge() / r.getNormCubed());
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	@Override
	public double getRadius() {
		return 10D;
	}

	@Override
	public int getRenderPass() {
		return 0;
	}

	@Override
	public boolean isPositionInsideObject(Vector position) {
		return position.subtract(getPosition()).getNormSquared() <= getRadius()
				* getRadius();
	}

	@Override
	public boolean onClick(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON2) {
			World.getInstance().removeObject(this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void render(Graphics2D g2) {
		if (charge >= 0) {
			g2.setColor(Color.RED);
		} else {
			g2.setColor(Color.BLUE);
		}
		g2.fillOval(getPosition().getIntegerX() - 10, getPosition()
				.getIntegerY() - 10, 20, 20);
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	@Override
	public void setPosition(Vector position) {
		this.position = position;
	}

}
