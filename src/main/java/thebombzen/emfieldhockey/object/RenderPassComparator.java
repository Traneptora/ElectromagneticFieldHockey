package thebombzen.emfieldhockey.object;

import java.util.Comparator;

public final class RenderPassComparator implements Comparator<RenderableObject> {

	public static final RenderPassComparator renderPassComparator = new RenderPassComparator();

	private RenderPassComparator() {

	}

	@Override
	public int compare(RenderableObject o1, RenderableObject o2) {
		return Integer.valueOf(o1.getRenderPass())
				.compareTo(o2.getRenderPass());
	}

}
