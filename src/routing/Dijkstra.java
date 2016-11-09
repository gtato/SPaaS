package routing;




import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
	
    public Dijkstra (Graph g) {
       super(g);
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
    	graph.resetNodes();
    	Node source = graph.getNodeData(src);
    	Node target = graph.getNodeData(destination);
    	computePaths(source);
    	
        List<String> path = new ArrayList<String>();
        for (Node vertex = target; vertex != null; vertex = vertex.getPrevious())
            path.add(vertex.getNodeId());

        Collections.reverse(path);
        ShortestPath sp = new ShortestPath(path, target.getDistance());
        
        return sp;
    }

//	@Override
//	public double[] minmaxPath() {
//		resetMinMax();
//		graph.resetNodes();
//		Iterator<String> it = graph.iterator();
//		String id = it.next();
//		Node src = graph.getNodeData(id);
//		computePaths(src);
//		
//		
//		
//		while(it.hasNext()){
//			Node target = graph.getNodeData(it.next());
//			if(id.equals(target.getNodeId())) continue;
//			for (Node vertex = target; vertex != null; vertex = vertex.getPrevious())
//			{
//				if(id.equals(vertex.getNodeId())) continue;
//				if (vertex.getDistance() > max)
//					max = vertex.getDistance();
//				if (vertex.getDistance() < min){
//					min = vertex.getDistance();
//				}
//			}
//	            
//		}
//		
//		return new double[]{min, max};
//	}
   
	
	@Override
	public double[] minmaxPath() {
		resetMinMax();
		graph.resetNodes();
		Iterator<String> it = graph.iterator();
		String id = graph.getVertexNodes().get(0).getNodeId();
		Node src = graph.getNodeData(id);
		computePaths(src);
		
		
		
		while(it.hasNext()){
			Node target = graph.getNodeData(it.next());
			if(id.equals(target.getNodeId())) continue;
			for (Node vertex = target; vertex != null; vertex = vertex.getPrevious())
			{
				if(id.equals(vertex.getNodeId())) continue;
				if (vertex.getDistance() > max)
					max = vertex.getDistance();
				if (vertex.getDistance() - vertex.getPrevious().getDistance() < min){
					min = Utils.round(vertex.getDistance() - vertex.getPrevious().getDistance());
//					if(min == 0) System.out.println(vertex.getNodeId());
				}
			}
	            
		}
		
		return new double[]{min, max};
	}
    
    
    
    

}



