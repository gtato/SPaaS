package service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;


import containers.Body;
import containers.Edge;
import containers.Node;
import containers.Request;


public class SPClient {
	
	public static void main(String[] args){
		Random r = new Random(123);
		Gson gson = new Gson();
		
		
		String[] ids = new String[]{"A", "B", "C", "D", "E"};
		
		Node[] nodes = new Node[5];
		ArrayList<Edge> edgesList = new ArrayList<Edge>();
		
		for(int i = 0; i < ids.length; i++)
			nodes[i] = new Node(ids[i], r.nextInt(100), r.nextInt(100));

		Edge[] edges = new Edge[6];
		edges[0] = new Edge("A", "B");
		edges[1] = new Edge("A", "E");
		edges[2] = new Edge("B", "C");
		edges[3] = new Edge("C", "E");
		edges[4] = new Edge("C", "D");
		edges[5] = new Edge("E", "D");
		
//		for(int i = 0; i < ids.length; i++)
//			for(int j = i+1; j < ids.length-1; j++){
//				if(r.nextDouble() > 0.2)
//					edgesList.add(new Edge(nodes[i].getId(), nodes[j].getId()));
//			}
		
		
//		Edge[] edges = new Edge[edgesList.size()];
//		edges = edgesList.toArray(edges);
		Body b = new Body();
		b.setNodes(nodes);
		b.setEdges(edges);
		Request req = new Request("uploadGraph", b);
		String data = gson.toJson(req);
//		System.out.println(data);
		String reply = post(data);
		System.out.println(reply);
		
		b = new Body();
		b.setPath(new String[]{"B", "D"});
		req = new Request("sp", b);
//		req.setParameters("dijkstra");
//		req.setParameters("astar");
		data = gson.toJson(req);
		
		reply = post(data);
		System.out.println(reply);
	} 
	
	public static String post(String data){
		Document doc;
		try {
			doc = Jsoup.connect("http://localhost:8080")
					.header("Accept", "application/xml")
					.data("data", data)
					.post();
			return doc.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
