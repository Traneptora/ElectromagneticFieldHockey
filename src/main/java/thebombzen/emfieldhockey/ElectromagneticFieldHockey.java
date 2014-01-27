package thebombzen.emfieldhockey;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import thebombzen.emfieldhockey.object.other.ConstantMagneticField;
import thebombzen.emfieldhockey.object.other.ElectricFieldRenderer;
import thebombzen.emfieldhockey.object.other.PlacementSelector;

public class ElectromagneticFieldHockey extends JPanel {

	private static final ElectromagneticFieldHockey instance = new ElectromagneticFieldHockey();

	private static final long serialVersionUID = -5566156782717789895L;

	public static ElectromagneticFieldHockey getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBackground(Color.WHITE);

		frame.add(getInstance());

		frame.pack();
		frame.setTitle("Electromagnetic Field Hockey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

		frame.setLocation((size.width - frame.getWidth()) / 2,
				(size.height - frame.getHeight()) / 2);
		frame.setVisible(true);

		getInstance().start();

	}

	private CanvasMouseListener listener = new CanvasMouseListener();
	private Canvas canvas = new Canvas();
	private ScheduledExecutorService service = Executors
			.newSingleThreadScheduledExecutor();
	private boolean started = false;

	private ElectromagneticFieldHockey() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		canvas.setSize(Constants.WIDTH, Constants.HEIGHT);
		canvas.setBackground(Color.WHITE);
		canvas.addMouseListener(listener);
		this.add(canvas);

		World.getInstance().addObject(PlacementSelector.getInstance());
		World.getInstance().addObject(new ConstantMagneticField());
		World.getInstance().addObject(new ElectricFieldRenderer());

	}

	public Canvas getCanvas() {
		return canvas;
	}

	private void start() {
		if (!started) {
			started = true;
			service.scheduleAtFixedRate(TickExecutor.getInstance(), 0,
					Constants.TICK_TIME_STEP_NANOS, TimeUnit.NANOSECONDS);
		}
	}

}
