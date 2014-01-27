package thebombzen.emfieldhockey;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import thebombzen.emfieldhockey.object.PositionedObject;
import thebombzen.emfieldhockey.object.RenderableObject;
import thebombzen.emfieldhockey.object.other.PlacementSelector;
import thebombzen.emfieldhockey.object.pointcharge.MovingPointCharge;
import thebombzen.emfieldhockey.object.pointcharge.PointCharge;
import thebombzen.emfieldhockey.object.pointcharge.StationaryPointCharge;

public class CanvasMouseListener extends MouseAdapter {

	private Vector pressedLocation = null;

	@Override
	public void mousePressed(MouseEvent event) {

		boolean success = false;

		World world = World.getInstance();
		Vector location = new Vector(event.getX(), event.getY());
		for (RenderableObject renderableObject : world
				.getAllRenderableObjects()) {
			if (renderableObject.isPositionInsideObject(location)) {
				success |= renderableObject.onClick(event);
			}
		}

		if (!success) {
			pressedLocation = new Vector(event.getX(), event.getY());
		}

	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (pressedLocation == null) {
			return;
		}

		World world = World.getInstance();
		Vector location = new Vector(event.getX(), event.getY());

		if (world.isPositionInsideRenderableObjects(location)) {
			return;
		}

		for (PositionedObject object : world.getAllPositionedObjects()) {
			double radius = object.getRadius() + PointCharge.DUMMY.getRadius();
			if (object.getPosition().subtract(location).getNormSquared() <= radius
					* radius) {
				return;
			}
		}

		Vector r = location.subtract(pressedLocation);
		Vector v = r.getNormSquared() >= 2500 ? r.setLength(200D) : Vector.ZERO;

		if (event.getButton() == MouseEvent.BUTTON1
				|| event.getButton() == MouseEvent.BUTTON3) {
			switch (PlacementSelector.getInstance().getSelected()) {
			case 0:
				world.addObject(new MovingPointCharge(location, 2E-6D * (event
						.getButton() == MouseEvent.BUTTON1 ? 1 : -1), v, 1E-2D));
				break;
			case 1:
				world.addObject(new StationaryPointCharge(location,
						2E-6D * (event.getButton() == MouseEvent.BUTTON1 ? 1
								: -1)));
				break;
			}
		}

	}

}
