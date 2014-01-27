package thebombzen.emfieldhockey.object.pointcharge;

import java.awt.Color;
import java.awt.Graphics2D;

import thebombzen.emfieldhockey.Vector;

public class StationaryPointCharge extends PointCharge {

	public StationaryPointCharge(Vector position, double charge) {
		super(position, charge);
	}

	@Override
	public void render(Graphics2D g2) {
		super.render(g2);
		g2.setColor(Color.BLACK);
		g2.fillOval(getPosition().getIntegerX() - 5, getPosition()
				.getIntegerY() - 5, 10, 10);
	}

	@Override
	public String toString() {
		return "StationaryPointCharge [getPosition()=" + getPosition()
				+ ", getCharge()=" + getCharge() + "]";
	}

}
