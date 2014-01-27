package thebombzen.emfieldhockey;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class TickExecutor implements Runnable {

	private static final TickExecutor tickExecutor = new TickExecutor();

	public static TickExecutor getInstance() {
		return tickExecutor;
	}

	private long excess = 0L;

	private TickExecutor() {

	}

	private void render(World world) {
		Canvas canvas = ElectromagneticFieldHockey.getInstance().getCanvas();
		BufferedImage image = new BufferedImage(Constants.WIDTH,
				Constants.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		g2.setColor(Color.BLACK);
		g2.drawRect(0, 0, Constants.WIDTH - 1, Constants.HEIGHT - 1);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		world.renderObjects(g2);

		g2.dispose();
		Graphics canvasGraphics = canvas.getGraphics();
		canvasGraphics.drawImage(image, 0, 0, null);
		canvasGraphics.dispose();
	}

	@Override
	public void run() {

		long beforeTime = System.nanoTime();

		World world = World.getInstance();

		try {
			world.updateObjects(Constants.TICK_TIME_STEP * 0.75D);
			render(world);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (Error e) {
			e.printStackTrace();
			throw new Error(e);
		}

		long afterTime = System.nanoTime();

		long should = Constants.TICK_TIME_STEP_NANOS;
		long did = afterTime - beforeTime;
		if (did >= should) {
			excess += did - should;
			if (excess > Constants.TICK_TIME_STEP_NANOS) {
				excess -= Constants.TICK_TIME_STEP_NANOS;
				world.updateObjects(Constants.TICK_TIME_STEP);
			}
		} else {
			long millis = (should - did) / 1000000L;
			int nanos = (int) ((should - did) % 1000000L);
			try {
				Thread.sleep(millis, nanos);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}

	}

}
