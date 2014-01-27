package thebombzen.emfieldhockey;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import thebombzen.emfieldhockey.object.ElectricFieldContributor;
import thebombzen.emfieldhockey.object.ElectricFieldReceiver;
import thebombzen.emfieldhockey.object.MagneticFieldContributor;
import thebombzen.emfieldhockey.object.MagneticFieldReceiver;
import thebombzen.emfieldhockey.object.MovingObject;
import thebombzen.emfieldhockey.object.PositionedObject;
import thebombzen.emfieldhockey.object.RenderPassComparator;
import thebombzen.emfieldhockey.object.RenderableObject;

public class World {

	private static final World world = new World();

	public static final Random random = new Random();

	public static World getInstance() {
		return world;
	}

	private List<RenderableObject> renderableObjects = new ArrayList<RenderableObject>();
	private List<PositionedObject> positionedObjects = new ArrayList<PositionedObject>();
	private List<MovingObject> movingObjects = new ArrayList<MovingObject>();
	private List<ElectricFieldContributor> electricFieldContributors = new ArrayList<ElectricFieldContributor>();
	private List<ElectricFieldReceiver> electricFieldReceivers = new ArrayList<ElectricFieldReceiver>();
	private List<MagneticFieldContributor> magneticFieldContributors = new ArrayList<MagneticFieldContributor>();
	private List<MagneticFieldReceiver> magneticFieldReceivers = new ArrayList<MagneticFieldReceiver>();

	private World() {

	}

	public synchronized void addObject(Object object) {
		if (object instanceof RenderableObject) {
			renderableObjects.add((RenderableObject) object);
		}
		if (object instanceof PositionedObject) {
			positionedObjects.add((PositionedObject) object);
		}
		if (object instanceof MovingObject) {
			movingObjects.add((MovingObject) object);
		}
		if (object instanceof ElectricFieldContributor) {
			electricFieldContributors.add((ElectricFieldContributor) object);
		}
		if (object instanceof ElectricFieldReceiver) {
			electricFieldReceivers.add((ElectricFieldReceiver) object);
		}
		if (object instanceof MagneticFieldContributor) {
			magneticFieldContributors.add((MagneticFieldContributor) object);
		}
		if (object instanceof MagneticFieldReceiver) {
			magneticFieldReceivers.add((MagneticFieldReceiver) object);
		}

	}

	public synchronized MovingObject[] getAllMovingObjects() {
		return movingObjects.toArray(new MovingObject[movingObjects.size()]);
	}

	public synchronized PositionedObject[] getAllPositionedObjects() {
		return positionedObjects.toArray(new PositionedObject[positionedObjects
				.size()]);
	}

	public synchronized RenderableObject[] getAllRenderableObjects() {
		return renderableObjects.toArray(new RenderableObject[renderableObjects
				.size()]);
	}

	public synchronized ElectricFieldContributor[] getElectricFieldContributors() {
		return electricFieldContributors
				.toArray(new ElectricFieldContributor[electricFieldContributors
						.size()]);
	}

	private Vector getForce(MovingObject movingObject) {
		Vector force = Vector.ZERO;

		if (movingObject instanceof ElectricFieldReceiver) {
			Vector electricField = Vector.ZERO;
			for (ElectricFieldContributor contributor : electricFieldContributors) {
				if (contributor instanceof PositionedObject) {
					double radius = ((PositionedObject) contributor)
							.getRadius() + movingObject.getRadius();
					if (((PositionedObject) contributor).getPosition()
							.subtract(movingObject.getPosition())
							.getNormSquared() <= radius * radius) {
						continue;
					}
				}
				electricField = electricField.add(contributor
						.getElectricField(movingObject.getPosition()));
			}
			force = force.add(((ElectricFieldReceiver) movingObject)
					.getElectricForce(electricField));
		}

		if (movingObject instanceof MagneticFieldReceiver) {
			double magneticField = 0D;
			for (MagneticFieldContributor contributor : magneticFieldContributors) {
				if (contributor instanceof PositionedObject) {
					double radius = ((PositionedObject) contributor)
							.getRadius() + movingObject.getRadius();
					if (((PositionedObject) contributor).getPosition()
							.subtract(movingObject.getPosition())
							.getNormSquared() <= radius * radius) {
						continue;
					}
				}
				magneticField += contributor.getMagneticField(movingObject
						.getPosition());
			}
			force = force.add(((MagneticFieldReceiver) movingObject)
					.getMagneticForce(magneticField));
		}

		return force;

	}

	public synchronized boolean isPositionInsideRenderableObjects(
			Vector position) {
		for (RenderableObject renderableObject : renderableObjects) {
			if (renderableObject.isPositionInsideObject(position)) {
				return true;
			}
		}
		return false;
	}

	public synchronized void removeObject(Object object) {
		if (object instanceof RenderableObject) {
			renderableObjects.remove(object);
		}
		if (object instanceof PositionedObject) {
			positionedObjects.remove(object);
		}
		if (object instanceof MovingObject) {
			movingObjects.remove(object);
		}
		if (object instanceof ElectricFieldContributor) {
			electricFieldContributors.remove(object);
		}
		if (object instanceof ElectricFieldReceiver) {
			electricFieldReceivers.remove(object);
		}
		if (object instanceof MagneticFieldContributor) {
			magneticFieldContributors.remove(object);
		}
		if (object instanceof MagneticFieldReceiver) {
			magneticFieldReceivers.remove(object);
		}
	}

	public synchronized void renderObjects(Graphics2D g2) {
		Collections.sort(renderableObjects,
				RenderPassComparator.renderPassComparator);
		for (RenderableObject o : renderableObjects) {
			o.render(g2);
		}
	}

	public synchronized void updateObjects(double timestep) {

		for (int i = 0; i < movingObjects.size(); i++) {
			MovingObject o1 = movingObjects.get(i);
			for (int j = i + 1; j < movingObjects.size(); j++) {

				MovingObject o2 = movingObjects.get(j);

				double radius = o1.getRadius() + o2.getRadius();

				if (o1.getPosition().subtract(o2.getPosition())
						.getNormSquared() <= radius * radius) {
					o1.collide(o2);
				}

			}
			for (int j = 0; j < positionedObjects.size(); j++) {
				PositionedObject o2 = positionedObjects.get(j);
				if (o2 instanceof MovingObject) {
					continue;
				}

				double radius = o1.getRadius() + o2.getRadius();
				if (o1.getPosition().subtract(o2.getPosition())
						.getNormSquared() <= radius * radius) {
					o1.collide(o2);
				}
			}
		}

		MovingObject[] movings = getAllMovingObjects();

		Vector[] positions = new Vector[movings.length];
		Vector[][] velocities = new Vector[movings.length][4];
		Vector[][] accelerations = new Vector[movings.length][4];

		// set starting conditions and k1: y0 is position and velocity and k1 is
		// the derivative
		for (int i = 0; i < movings.length; i++) {
			positions[i] = movings[i].getPosition();
			velocities[i][0] = movings[i].getVelocity();
			accelerations[i][0] = getForce(movings[i]).divide(
					movings[i].getMass());
		}

		// get k2
		for (int i = 0; i < movings.length; i++) {
			Vector velocity = velocities[i][0];
			Vector acceleration = accelerations[i][0];

			movings[i].setPosition(positions[i].add(velocity
					.multiply(timestep * 0.5D)));
			movings[i].setVelocity(velocities[i][0].add(acceleration
					.multiply(timestep * 0.5D)));
		}
		for (int i = 0; i < movings.length; i++) {
			velocities[i][1] = movings[i].getVelocity();
			accelerations[i][1] = getForce(movings[i]).divide(
					movings[i].getMass());
		}

		// get k3
		for (int i = 0; i < movings.length; i++) {
			Vector velocity = velocities[i][1];
			Vector acceleration = accelerations[i][1];

			movings[i].setPosition(positions[i].add(velocity
					.multiply(timestep * 0.5D)));
			movings[i].setVelocity(velocities[i][0].add(acceleration
					.multiply(timestep * 0.5D)));
		}
		for (int i = 0; i < movings.length; i++) {
			velocities[i][2] = movings[i].getVelocity();
			accelerations[i][2] = getForce(movings[i]).divide(
					movings[i].getMass());
		}

		// get k4
		for (int i = 0; i < movings.length; i++) {
			Vector velocity = velocities[i][2];
			Vector acceleration = accelerations[i][2];

			movings[i].setPosition(positions[i].add(velocity
					.multiply(timestep * 0.5D)));
			movings[i].setVelocity(velocities[i][0].add(acceleration
					.multiply(timestep * 0.5D)));
		}
		for (int i = 0; i < movings.length; i++) {
			velocities[i][3] = movings[i].getVelocity();
			accelerations[i][3] = getForce(movings[i]).divide(
					movings[i].getMass());
		}

		// finalize
		for (int i = 0; i < movings.length; i++) {
			Vector position = positions[i].add(velocities[i][0]
					.add(velocities[i][1].add(velocities[i][2]).multiply(2D))
					.add(velocities[i][3]).multiply(timestep / 6D));
			Vector velocity = velocities[i][0].add(accelerations[i][0]
					.add(accelerations[i][1].add(accelerations[i][2]).multiply(
							2D)).add(accelerations[i][3])
					.multiply(timestep / 6D));
			movings[i].setPosition(position);
			movings[i].setVelocity(velocity);
		}

		for (PositionedObject positionedObject : getAllPositionedObjects()) {
			if (positionedObject.getPosition().getX() < -50D
					|| positionedObject.getPosition().getX() > 50D + Constants.WIDTH
					|| positionedObject.getPosition().getY() < -50D
					|| positionedObject.getPosition().getY() > 50D + Constants.HEIGHT) {
				removeObject(positionedObject);
			}
		}

	}

}
