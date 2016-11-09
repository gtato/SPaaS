package routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import routing.internals.Graph;
import routing.internals.Heuristic;
import routing.internals.Node;
import routing.internals.ShortestPath;
import routing.internals.Utils;

public class AStar extends ShortestPathAlgorithm {

    
    private Heuristic heuristic;

    public AStar (Graph g, Heuristic h) {
        super(g);
        this.heuristic = h;
    }

    class AStrarNodeComparator implements Comparator<Node>{

		public int compare(Node nodeFirst, Node nodeSecond) {
			if (nodeFirst.getHeuristic() > nodeSecond.getHeuristic()) return 1;
	        if (nodeSecond.getHeuristic() > nodeFirst.getHeuristic()) return -1;
	        return 0;
		}
    	
    }

    /**
     * Implements the A-star algorithm and returns the path from source to destination
     * 
     * @param source        the source id
     * @param destination   the destination id
     * @return              the path from source to destination
     */
    public ShortestPath shortestPath(String source, String destination) {
    	graph.resetNodes();
    	final Queue<Node> openQueue = new PriorityQueue<Node>(11, new AStrarNodeComparator()); 

        Node sourceNodeData = graph.getNodeData(source);
        sourceNodeData.setDistance(0);
        heuristic.setHeuristic(sourceNodeData, graph.getNodeData(destination));
        openQueue.add(sourceNodeData);

        final Map<String, String> path = new HashMap<String, String>();
        double weight = 0;
        final Set<Node> closedList = new HashSet<Node>();

        while (!openQueue.isEmpty()) {
            final Node nodeData = openQueue.poll();
            if (nodeData.getDistance() > max) max = nodeData.getDistance();
			if (nodeData.getDistance()  < min && !nodeData.getNodeId().equals(source)) min = nodeData.getDistance();


            if (nodeData.getNodeId().equals(destination)) { 
            	List<String> p = path(path, destination);
            	return new ShortestPath(p, Utils.round(nodeData.getDistance()));
            }

            closedList.add(nodeData);

            for (Entry<String, Double> neighborEntry : graph.edgesFrom(nodeData.getNodeId()).entrySet()) {
                Node neighbor = graph.getNodeData(neighborEntry.getKey()) ;

                if (closedList.contains(neighbor)) continue;

                double distanceBetweenTwoNodes = neighborEntry.getValue();
                double tentativeG = distanceBetweenTwoNodes + nodeData.getDistance();
                
                if (tentativeG < neighbor.getDistance()) {
                    neighbor.setDistance(tentativeG);
                    heuristic.setHeuristic(neighbor, graph.getNodeData(destination));

                    path.put(neighbor.getNodeId(), nodeData.getNodeId());
                    weight += neighbor.getDistance(); weight = Utils.round(weight);
                    		
                    if (!openQueue.contains(neighbor)) {
                        openQueue.add(neighbor);
                    }
                }
            }
        }

        return null;
    }


    private List<String> path(Map<String, String> path, String destination) {
        assert path != null;
        assert destination != null;

        final List<String> pathList = new ArrayList<String>();
        pathList.add(destination);
        while (path.containsKey(destination)) {
            destination = path.get(destination);
            pathList.add(destination);
        }
        Collections.reverse(pathList);
        return pathList;
    }


//	@Override
//	public double[] minmaxPath() {
//		Random r = new Random(5);
//		ArrayList<String> rands = graph.getRandomNodes();
//		ArrayList<String> tested = new ArrayList<String>();
//		double totalMin = Double.MAX_VALUE;
//		double totalMax = -1;
//		int trials = 3;
//		while(trials >= 0){
//			String src = rands.get(r.nextInt(rands.size()));
//			String dest = rands.get(r.nextInt(rands.size()));
//			String concat = Utils.concat(src, dest); 
//			if(src == null || dest==null || src.equals(dest) || tested.contains(concat)) 
//				continue;
//			
//			tested.add(concat);
//			resetMinMax();
//			graph.resetNodes();
//			shortestPath(src, dest);
//			if(min < totalMin) totalMin = min;
//			if(max > totalMax) totalMax = max;
//			trials--;
//		}
//		
//		return new double[]{Utils.round(totalMin), Utils.round(totalMax)};
//	}

    
    @Override
	public double[] minmaxPath() {
		ArrayList<Node> sqrNodes = graph.getVertexNodes();
		double totalMin = Double.MAX_VALUE;
		double totalMax = -1;
		for(int i = 0 ; i < 2; i++){
			String src = sqrNodes.get(i).getNodeId();
			String dest = sqrNodes.get(i+2).getNodeId();
			resetMinMax();
			graph.resetNodes();
			shortestPath(src, dest);
			if(min < totalMin) totalMin = min;
			if(max > totalMax) totalMax = max;
		}
				
		return new double[]{Utils.round(totalMin), Utils.round(totalMax)};
	}

    
}