package thebombzen.emfieldhockey;

import java.awt.GraphicsEnvironment;

public final class Constants {

	/**
	 * Pixels per meter
	 */
	private static final double PPM = 100D;

	/**
	 * Physics area width, in pixels
	 */
	public static final int WIDTH = 720;

	/**
	 * Physics area height, in pixels
	 */
	public static final int HEIGHT = 540;

	/**
	 * K, in N m^2 / C^2 /. m->p
	 */
	public static final double K = 299792.458D * 29979.2458D * PPM * PPM * PPM;

	/**
	 * E0, in F / m /. m->p
	 */
	public static final double E0 = 1D / (4D * Math.PI * K);

	/**
	 * MU0, in H / m /. m->p
	 */
	public static final double MU0 = 4E-7D * Math.PI * PPM;

	/**
	 * framerate
	 */

	public static final int REFRESH_RATE = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getRefreshRate();

	public static final double TICK_TIME_STEP = 1D / REFRESH_RATE;
	public static final long TICK_TIME_STEP_NANOS = (long) (1E9D * TICK_TIME_STEP);

	private Constants() {

	}

}
