package routing.internals;

import containers.Body;
import containers.Request;

public class Utils {
	public static double getEuclidianDistance(Node first, Node second) {
        double x1 = first.getX();
        double x2 = second.getX();
        double y1 = first.getY();
        double y2 = second.getY();
        double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return distance; 
    }
	
	public static Graph convertBodyToGraph(Body body){
		Graph graph = new Graph();
		for(containers.Node cnode : body.getNodes()){
			graph.addNode(convertCNodeToNode(cnode));
		}
		
		for(containers.Edge cedge : body.getEdges()){
			graph.addEdge(cedge);
		}
		
		
		return graph;
	}
	
	public static Node convertCNodeToNode(containers.Node cnode){
		Node node = new Node(cnode.getId());
		node.setCoords(cnode.getX(), cnode.getY());
		return node;
	}
	
	public static Body convertSPtoBody(ShortestPath sp){
		Body body = new Body();
		body.setLatency(sp.getLatency());
		String[] path = new String[sp.getPath().size()];
		path = sp.getPath().toArray(path);
		body.setPath(path);
		return body;
	}
	
	
	public static double round(double tr){
		for(double i=100.0; i <= 100000.0; i*=10){
			double ret = Math.round(tr * i ) / i;
			if(ret != 0)
				return ret;
		}
		return 0;
	}
	
	public static String concat(String src, String dst){
		String id = src.concat(dst);
		if (src.compareToIgnoreCase(dst) > 0)
			id = dst.concat(src);
		return id;
	}
}
