package thebombzen.emfieldhockey.object.other;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import thebombzen.emfieldhockey.Constants;
import thebombzen.emfieldhockey.Vector;
import thebombzen.emfieldhockey.World;
import thebombzen.emfieldhockey.object.ElectricFieldContributor;
import thebombzen.emfieldhockey.object.RenderableObject;

public class ElectricFieldRenderer implements RenderableObject {

	@Override
	public int getRenderPass() {
		return -1;
	}

	@Override
	public boolean isPositionInsideObject(Vector position) {
		return false;
	}

	@Override
	public boolean onClick(MouseEvent event) {
		return false;
	}

	@Override
	public void render(Graphics2D g2) {
		for (int x = 10; x <= Constants.WIDTH; x += 20) {
			for (int y = 10; y <= Constants.HEIGHT; y += 20) {

				Vector electricField = Vector.ZERO;
				for (ElectricFieldContributor contributor : World.getInstance()
						.getElectricFieldContributors()) {
					electricField = electricField.add(contributor
							.getElectricField(new Vector(x, y)));
				}

				double logNorm = Math.log(electricField.getNormSquared()) - 23D;

				if (Double.isInfinite(logNorm)) {
					continue;
				}

				double max = 10D;

				if (logNorm < 0D) {
					continue;
				}

				if (logNorm > max) {
					logNorm = max;
				}

				Vector direction = electricField.setLength(8D);

				double angle = Math.atan2(direction.getY(), direction.getX());

				Vector arm1 = new Vector(Math.cos(angle + Math.PI / 6),
						Math.sin(angle + Math.PI / 6)).multiply(6D); // already
																		// normalized
				Vector arm2 = new Vector(Math.cos(angle - Math.PI / 6),
						Math.sin(angle - Math.PI / 6)).multiply(6D); // already
																		// normalized

				g2.setColor(new Color(Color.HSBtoRGB(0.1F,
						(float) (logNorm / max), 1F)));
				g2.drawLine(x - (int) (direction.getX()),
						y - (int) (direction.getY()),
						x + (int) (direction.getX()),
						y + (int) (direction.getY()));
				g2.drawLine(x + (int) (direction.getX()),
						y + (int) (direction.getY()),
						x + (int) (direction.getX() - arm1.getX()), y
								+ (int) (direction.getY() - arm1.getY()));
				g2.drawLine(x + (int) (direction.getX()),
						y + (int) (direction.getY()),
						x + (int) (direction.getX() - arm2.getX()), y
								+ (int) (direction.getY() - arm2.getY()));

			}
		}
	}

}
