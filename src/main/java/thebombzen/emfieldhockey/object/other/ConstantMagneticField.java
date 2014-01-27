package thebombzen.emfieldhockey.object.other;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import thebombzen.emfieldhockey.Constants;
import thebombzen.emfieldhockey.Vector;
import thebombzen.emfieldhockey.object.MagneticFieldContributor;
import thebombzen.emfieldhockey.object.RenderableObject;

public class ConstantMagneticField implements RenderableObject,
		MagneticFieldContributor {

	private static boolean enabled = false;

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean enabled) {
		ConstantMagneticField.enabled = enabled;
	}

	@Override
	public double getMagneticField(Vector position) {
		return enabled ? 0.5E4D : 0D;
	}

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
		if (!enabled) {
			return;
		}
		g2.setColor(new Color(0x008000));
		for (int x = 0; x <= Constants.WIDTH; x += 20) {
			for (int y = 0; y <= Constants.HEIGHT; y += 20) {
				g2.drawLine(x - 2, y - 2, x + 2, y + 2);
				g2.drawLine(x - 2, y + 2, x + 2, y - 2);
			}
		}
	}

}
