package service;



import routing.AStar;
import routing.Dijkstra;
import routing.ShortestPathAlgorithm;
import routing.internals.Graph;
import routing.internals.Heuristic;
import routing.internals.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.gson.Gson;

import containers.Body;
import containers.Edge;
import containers.Node;
import containers.Request;


public class SPServer {
	public static void main( String[] args ) throws Exception
    {
    	
		Server server = new Server(8080);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        handler.addServletWithMapping(SPServlet.class, "/*");
        server.start();
        server.join();
    }
	
	@SuppressWarnings("serial")
    public static class SPServlet extends HttpServlet
    {
    	Gson gson = new Gson();
    	Graph g = null; ShortestPathAlgorithm spa;
    	@Override
        protected void doGet( HttpServletRequest request,
                              HttpServletResponse response ) throws ServletException,
                                                            IOException
        {
            response.setContentType("application/xml");
            response.setStatus(HttpServletResponse.SC_OK);
            String resp = onRequest(request.getParameter("data"));
            response.getWriter().println(resp);
        }
        
        @Override
        protected void doPost( HttpServletRequest request,
                              HttpServletResponse response ) throws ServletException,
                                                            IOException
        {
            response.setContentType("application/xml");
            response.setStatus(HttpServletResponse.SC_OK);
            String resp = onRequest(request.getParameter("data"));
            response.getWriter().println(resp);
        
        }
        
        private String onRequest(String params){
        	String reply = "ok";
        	Request req = gson.fromJson(params, Request.class);
        	String method = req.getMethod();
        	System.out.println("request: " + method + " " + req.getParameters());
        	
        	if(method.equals("uploadGraph")){
        		uploadGraph(req);
        	}else if(method.equals("sp")){
        		reply  = shortestPath(req);
        	}else if(method.equals("minmax")){
        		reply  = minmax();
        	} 
        	
        	System.out.println("replied: " + reply);
        	return reply;
        }
        
        private void uploadGraph(Request req){
        	g = Utils.convertBodyToGraph(req.getBody());
        	String params = req.getParameters();
        	if(params != null && params.equalsIgnoreCase("astar"))
        		spa = new AStar(g, new Heuristic(g));
        	else 
        		spa = new  Dijkstra(g);
        }
        
        private String shortestPath(Request req){
        	String src = req.getBody().getPath()[0];
        	String dst = req.getBody().getPath()[1];
        	System.out.println("sp: " + src + " " + dst );
        	Body body = Utils.convertSPtoBody(spa.shortestPath(src, dst));
        	
        	return gson.toJson(body);
        }
        
        private String minmax(){
        	double[] minmax = spa.minmaxPath();
        	return gson.toJson(minmax);
        }
        
        
    }
	
//	public static void main(String[] args) {
//		Random r = new Random(123);
//		
//		
//		
//		String[] ids = new String[]{"A", "B", "C", "D", "E"};
//		
//		Node[] nodes = new Node[5];
//		ArrayList<Edge> edgesList = new ArrayList<Edge>();
//		
//		for(int i = 0; i < ids.length; i++)
//			nodes[i] = new Node(ids[i], r.nextDouble(), r.nextDouble());
//
//		Edge[] edges = new Edge[6];
//		edges[0] = new Edge("A", "B");
//		edges[1] = new Edge("A", "E");
//		edges[2] = new Edge("B", "C");
//		edges[3] = new Edge("C", "E");
//		edges[4] = new Edge("C", "D");
//		edges[5] = new Edge("E", "D");
//     
//		Body body = new Body();
//		body.setNodes(nodes);
//		body.setEdges(edges);
//		Graph g = Utils.convertBodyToGraph(body);
//		ShortestPathAlgorithm spa;
//		spa = new AStar(g, new Heuristic(g));
////		spa = new  Dijkstra(g);
//		
//		System.out.println(spa.shortestPath("A", "C"));
//		
//		double[] minmax = spa.minmaxPath();
//		System.out.println(minmax[0] +" "+ minmax[1]);
//		
//    }
}
