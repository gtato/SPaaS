package routing;

import java.util.List;

import routing.internals.Graph;
import routing.internals.ShortestPath;

public abstract class ShortestPathAlgorithm {
	protected final Graph graph;
	double max = -1;
	double min = Double.MAX_VALUE;
	
	public ShortestPathAlgorithm(Graph g){
		this.graph = g;
	}
	
	public void resetMinMax(){
		max = -1;
		min = Double.MAX_VALUE;
	}
	
	public abstract ShortestPath shortestPath(String source, String destination);
	public abstract double[] minmaxPath();
	
}
