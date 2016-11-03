package routing.internals;



public class Node { 

    private final String nodeId;
    
    private double d;  // d is distance from the source
    private double h;  // h is the heuristic of destination.
    private Node previous;

    public Node (String nodeId) {
        this.nodeId = nodeId;
        this.d = Double.MAX_VALUE; 
    }

    public String getNodeId() {
        return nodeId;
    }

    public double getDistance() {
        return d;
    }

    public void setDistance(double g) {
        this.d = g;
    }

    public double getHeuristic() {
        return h;
    }
    
    public void setHeuristic(double h) {
        this.h = h;
    }


	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

    
 }