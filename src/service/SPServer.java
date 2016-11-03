package service;



import routing.AStar;
import routing.Dijkstra;
import routing.internals.Graph;
import routing.internals.Heuristic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.gson.Gson;

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
        	Request req = gson.fromJson(params, Request.class);
        	System.out.println(req.getMethod());
        	return "yoyo";
        }
        
    }
	
//	public static void main(String[] args) {
//        Heuristic heuristic = new Heuristic();
//        
//        heuristic.addHeuristic("A", "A", 0);
//        heuristic.addHeuristic("B", "B", 0);
//        heuristic.addHeuristic("C", "C", 0);
//        heuristic.addHeuristic("E", "E", 0);
//        heuristic.addHeuristic("F", "F", 0);
//        
//        heuristic.addHeuristic("A", "B", 10);
//        heuristic.addHeuristic("A", "C", 20);
//        heuristic.addHeuristic("A", "E", 100);
//        heuristic.addHeuristic("A", "F", 110);
//        
//        heuristic.addHeuristic("B", "C", 10);
//        heuristic.addHeuristic("B", "E", 25);
//        heuristic.addHeuristic("B", "F", 40);
//        
//        heuristic.addHeuristic("C", "E", 10);
//        heuristic.addHeuristic("C", "F", 30);
//        
//        heuristic.addHeuristic("E", "F", 10);
//        
//
//        Graph graph = new Graph();
//        graph.addNode("A");
//        graph.addNode("B");
//        graph.addNode("C");
//        graph.addNode("E");
//        graph.addNode("F");
//
//        graph.addEdge("A", "B",  10);
//        graph.addEdge("A", "E", 100);
//        graph.addEdge("B", "C", 10);
//        graph.addEdge("C", "E", 10);
//        graph.addEdge("C", "F", 30);
//        graph.addEdge("E", "F", 10);
//
//        AStar aStar = new AStar(graph, heuristic);
//        Dijkstra dijkstra = new Dijkstra(graph);
//
//        System.out.println(aStar.shortestPath("A", "F"));
//        graph.resetNodes();
//        System.out.println(dijkstra.shortestPath("A", "F"));
//        
//    }
}