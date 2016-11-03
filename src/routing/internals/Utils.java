package routing.internals;

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
	
	public static Graph convertRequestToGraph(Request req){
		Graph graph = new Graph();
		for(containers.Node cnode : req.getBody().getNodes()){
			graph.addNode(convertCNodeToNode(cnode));
		}
		
		for(containers.Edge cedge : req.getBody().getEdges()){
			graph.addEdge(cedge.getSource(), cedge.getDestination());
		}
		
		
		return graph;
	}
	
	public static Node convertCNodeToNode(containers.Node cnode){
		Node node = new Node(cnode.getId());
		node.setCoords(cnode.getX(), cnode.getY());
		return node;
	}
}
