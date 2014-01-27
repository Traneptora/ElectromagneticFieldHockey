package thebombzen.emfieldhockey.object;

import thebombzen.emfieldhockey.Vector;

public interface ElectricFieldReceiver extends PositionedObject {
	public Vector getElectricForce(Vector electricField);
}
