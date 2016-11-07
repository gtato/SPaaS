package routing;

import java.util.List;

import routing.internals.ShortestPath;

public abstract class ShortestPathAlgorithm {
	public abstract ShortestPath shortestPath(String source, String destination);
}
