package thebombzen.emfieldhockey.object.other;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import thebombzen.emfieldhockey.Vector;
import thebombzen.emfieldhockey.object.RenderableObject;

public class PlacementSelector implements RenderableObject {

	private int selected = 0;

	private static final PlacementSelector placementSelector = new PlacementSelector();

	public static PlacementSelector getInstance() {
		return placementSelector;
	}

	private PlacementSelector() {

	}

	@Override
	public int getRenderPass() {
		return 1;
	}

	public int getSelected() {
		return selected;
	}

	@Override
	public boolean isPositionInsideObject(Vector position) {
		if (position.getIntegerX() < 150 && position.getIntegerY() < 50) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onClick(MouseEvent event) {
		if (event.getX() < 100) {
			setSelected(event.getX() / 50);
		} else if (event.getX() < 150) {
			ConstantMagneticField
					.setEnabled(!ConstantMagneticField.isEnabled());
		}
		return true;
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 150, 50);

		g2.setColor(Color.BLACK);
		for (int i = 0; i < 3; i++) {
			g2.drawRect(50 * i, 0, 50, 50);
		}

		g2.setColor(Color.ORANGE);
		g2.drawRect(50 * getSelected() + 1, 1, 48, 48);
		g2.setColor(Color.BLACK);
		g2.drawRect(50 * getSelected() + 2, 2, 46, 46);

		g2.setColor(Color.RED);
		g2.fillOval(15, 15, 20, 20);
		g2.fillOval(65, 15, 20, 20);
		g2.setColor(Color.BLACK);
		g2.fillOval(70, 20, 10, 10);

		g2.setColor(new Color(0x008000));
		g2.drawLine(110, 10, 140, 40);
		g2.drawLine(140, 10, 110, 40);

		if (ConstantMagneticField.isEnabled()) {
			g2.setColor(Color.ORANGE);
			g2.drawRect(101, 1, 48, 48);
			g2.setColor(Color.BLACK);
			g2.drawRect(102, 2, 46, 46);
		}

	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

}
