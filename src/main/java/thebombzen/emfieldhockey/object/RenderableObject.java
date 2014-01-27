package thebombzen.emfieldhockey.object;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import thebombzen.emfieldhockey.Vector;

public interface RenderableObject {

	public int getRenderPass();

	public boolean isPositionInsideObject(Vector position);

	/**
	 * Returns true if the action was successful and final.
	 * 
	 * @return success
	 */
	public boolean onClick(MouseEvent event);

	public void render(Graphics2D g2);

}
