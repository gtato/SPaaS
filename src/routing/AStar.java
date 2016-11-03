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
import java.util.Set;

import routing.internals.Graph;
import routing.internals.Heuristic;
import routing.internals.Node;

public class AStar {

    private final Graph graph;
    private Heuristic heuristic;

    public AStar (Graph graphAStar, Heuristic h) {
        this.graph = graphAStar;
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
     * @param source        the source nodeid
     * @param destination   the destination nodeid
     * @return              the path from source to destination
     */
    public List<String> shortestPath(String source, String destination) {
        /**
         * http://stackoverflow.com/questions/20344041/why-does-priority-queue-has-default-initial-capacity-of-11
         */
        final Queue<Node> openQueue = new PriorityQueue<Node>(11, new AStrarNodeComparator()); 

        Node sourceNodeData = graph.getNodeData(source);
        sourceNodeData.setDistance(0);
        heuristic.setHeuristic(sourceNodeData, graph.getNodeData(destination));
        openQueue.add(sourceNodeData);

        final Map<String, String> path = new HashMap<String, String>();
        final Set<Node> closedList = new HashSet<Node>();

        while (!openQueue.isEmpty()) {
            final Node nodeData = openQueue.poll();

            if (nodeData.getNodeId().equals(destination)) { 
                return path(path, destination);
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


    
}