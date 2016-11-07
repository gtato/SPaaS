package routing.internals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import containers.Edge;



/**
 * The graph represents an undirected graph. 
 * 
 * @author SERVICE-NOW\ameya.patil
 *
 * @param <T>
 */
public class Graph implements Iterable<String> {
    /*
     * A map from the nodeId to outgoing edge.
     * An outgoing edge is represented as a tuple of NodeData and the edge length
     */
    private final Map<String, Map<String, Double>> graph;
    /*
     * A map of heuristic from a node to each other node in the graph.
     */
//    private final Map<String, Map<String, Double>> heuristicMap;
    /*
     * A map between nodeId and nodedata.
     */
    private final Map<String, Node> nodeIdNodeData;
    
    private double totalDistance  = 0;
    private double totalWeight = 0;
    private double dwratio;
    public Graph() {
        graph = new HashMap<String, Map<String, Double>>();
        nodeIdNodeData = new HashMap<String, Node>();
    } 

    public double getDWRatio(){
    	return dwratio;
    }
    /**
     * Adds a new node to the graph.
     * Internally it creates the nodeData and populates the heuristic map concerning input node into node data.
     * 
     * @param nodeId the node to be added
     */
    public void addNode(Node node) {
        if (node == null) throw new NullPointerException("The node cannot be null");


        graph.put(node.getNodeId(), new HashMap<String, Double>());
        nodeIdNodeData.put(node.getNodeId(), node);
    }

    /**
     * Adds an edge from source node to destination node.
     * There can only be a single edge from source to node.
     * Adding additional edge would overwrite the value
     * 
     * @param nodeIdFirst   the first node to be in the edge
     * @param nodeIdSecond  the second node to be second node in the edge
     * @param length        the length of the edge.
     */
    public void addEdge(Edge edge) {
        if (edge.getSource()== null || edge.getDestination() == null) throw new NullPointerException("The first nor second node can be null.");

//        if (!graph.containsKey(nodeIdFirst) || !graph.containsKey(nodeIdSecond)) {
//            throw new NoSuchElementException("Source and Destination both should be part of the part of graph");
//        }
        Node src = getNodeData(edge.getSource());
        Node dst = getNodeData(edge.getDestination());
        double distance =  Utils.getEuclidianDistance(src, dst);
        double weight = edge.getWeight() == 0? distance : edge.getWeight(); 
        graph.get(edge.getSource()).put(edge.getDestination(), weight);
        graph.get(edge.getDestination()).put(edge.getSource(), weight);
//        System.out.println(nodeIdFirst +  " -> " + nodeIdSecond + ": " + distance );
        totalDistance += distance;
        totalWeight += weight;
        dwratio = totalDistance/totalWeight;
    }

    /**
     * Returns immutable view of the edges
     * 
     * @param nodeId    the nodeId whose outgoing edge needs to be returned
     * @return          An immutable view of edges leaving that node
     */
    public Map<String, Double> edgesFrom (String nodeId) {
        if (nodeId == null) throw new NullPointerException("The input node should not be null.");
//        if (!graph.containsKey(nodeId)) throw new NoSuchElementException("The node should not be null.");

        return Collections.unmodifiableMap(graph.get(nodeId));
    }

    /**
     * The nodedata corresponding to the current nodeId.
     * 
     * @param nodeId    the nodeId to be returned
     * @return          the nodeData from the 
     */ 
    public Node getNodeData (String nodeId) {
        if (nodeId == null) { throw new NullPointerException("The nodeid should not be empty"); }
//        if (!nodeIdNodeData.containsKey(nodeId))  { throw new NoSuchElementException("The nodeId does not exist"); }
        return nodeIdNodeData.get(nodeId);
    }

    /**
     * Returns an iterator that can traverse the nodes of the graph
     * 
     * @return an Iterator.
     */
	public Iterator<String> iterator() {
		return graph.keySet().iterator();
	}

	 
    public void resetNodes()
    {
    	for(Node v : nodeIdNodeData.values()){
    		v.setDistance(Double.POSITIVE_INFINITY);
    		v.setPrevious(null);
    	}
    }
	

}
