package routing.internals;

import java.util.ArrayList;
import java.util.HashMap;



public class Heuristic {
	Graph graph;
	double distanceCoef; 
	public Heuristic(Graph g){
		this.graph = g;
	}
//	private HashMap<String, Double> heuristic = new HashMap<String, Double>();
	
//	public void addHeuristic(NodeData src, NodeData dst, double h){
//		addHeuristic(src.getNodeId(), dst.getNodeId(), h);
//	}
	
//	public void addHeuristic(String src, String dst, double h){
//		String id = getId(src, dst);
//		heuristic.put(id, h);
//	}
	
	public double getHeuristic(Node src, Node dst){
		return Utils.getEuclidianDistance(src, dst)*graph.getDWRatio();
		
	}
	
//	public double getHeuristic(String src, String dst){
//		String id = getId(src, dst);
//		if(heuristic.containsKey(id))
//			return heuristic.get(id);
//		return Double.MAX_VALUE;
//	}
	
	
//	private String getId(String src, String dst){
//		String id = src.concat(dst);
//		if (src.compareToIgnoreCase(dst) > 0)
//			id = dst.concat(src);
//		return id;
//	}
	
	
	
	
	public void setHeuristic(Node src, Node dst){
		double h = getHeuristic(src, dst);
		src.setHeuristic(h+src.getDistance());
	}
	

}
