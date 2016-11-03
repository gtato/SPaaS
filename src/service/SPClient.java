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
		Random r = new Random();
		Gson gson = new Gson();
		
		int size = 5;
		Node[] nodes = new Node[5];
		ArrayList<Edge> edgesList = new ArrayList<Edge>();
		for(int i = 0; i < size; i++)
			nodes[i] = new Node(i+"", r.nextInt(100), r.nextInt(100));

		for(int i = 0; i < size; i++)
			for(int j = i+1; j < size-1; j++){
				if(r.nextDouble() > 0.2)
					edgesList.add(new Edge(nodes[i].getId(), nodes[j].getId()));
			}
		
		
		Edge[] edges = new Edge[edgesList.size()];
		edges = edgesList.toArray(edges);
		Body b = new Body();
		b.setNodes(nodes);
		b.setEdges(edges);
		Request req = new Request("uploadGraph", b);
		String data = gson.toJson(req);
//		System.out.println(data);
		Document doc;
		try {
			doc = Jsoup.connect("http://localhost:8080")
					.header("Accept", "application/xml")
					.data("data", data)
					.post();
			System.out.println(doc.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	} 
}
