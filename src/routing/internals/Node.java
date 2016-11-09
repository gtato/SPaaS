package routing.internals;



public class Node { 

    private final String nodeId;
    
    private double d;  // d is distance from the source
    private double h;  // h is the heuristic of destination.
    private Node previous;

    private double x;
    private double y;
    
    public Node (String nodeId) {
        this.nodeId = nodeId;
        this.d = Double.MAX_VALUE; 
    }

    public Node (double x, double y) {
        this.nodeId = "";
    	this.x = x;
        this.y = y;
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

	public void setCoords(double x, double y){
		this.x = x; this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
    
	
 }