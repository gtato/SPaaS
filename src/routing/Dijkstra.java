package routing;




import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Map.Entry;

import routing.internals.Graph;
import routing.internals.Node;
import routing.internals.ShortestPath;
import routing.internals.Utils;


public class Dijkstra extends ShortestPathAlgorithm
{
	private final Graph graph;
	    

    public Dijkstra (Graph graphAStar) {
        this.graph = graphAStar;
        
    }
    
    class DijsktraNodeComparator implements Comparator<Node>{

		public int compare(Node arg0, Node arg1) {
			// TODO Auto-generated method stub
			return 0;
		}
    	
    }
	    
    public void computePaths(Node source)
    {
        source.setDistance(0);
        PriorityQueue<Node> vertexQueue = new PriorityQueue<Node>(new DijsktraNodeComparator());
	    vertexQueue.add(source);
	
	    while (!vertexQueue.isEmpty()) {
	    	Node u = vertexQueue.poll();
	    	
	    	for (Entry<String, Double> neighborEntry : graph.edgesFrom(u.getNodeId()).entrySet()) {
                Node v = graph.getNodeData(neighborEntry.getKey()) ;

                double weight = neighborEntry.getValue();
                double distanceThroughU = u.getDistance() + weight;
                distanceThroughU = Utils.round(distanceThroughU);
		        if (distanceThroughU < v.getDistance()) {
		            vertexQueue.remove(v);
		
		            v.setDistance(distanceThroughU);
		            v.setPrevious(u);
		            vertexQueue.add(v);
		        }
            }
        }
    }

    public ShortestPath shortestPath(String src, String destination)
    {
    	Node source = graph.getNodeData(src);
    	Node target = graph.getNodeData(destination);
    	computePaths(source);
    	
        List<String> path = new ArrayList<String>();
        for (Node vertex = target; vertex != null; vertex = vertex.getPrevious())
            path.add(vertex.getNodeId());

        Collections.reverse(path);
        
        return new ShortestPath(path, target.getDistance());
    }
   
    
    
    
    

}



