package thebombzen.emfieldhockey.object;

import thebombzen.emfieldhockey.Vector;

public interface MagneticFieldReceiver extends PositionedObject {
	public Vector getMagneticForce(double magneticField);
}
