package routing.internals;

import java.util.List;

public class ShortestPath {
	private List<String> path;
	private double latency;
	public ShortestPath(List<String> path, double latency) {
		super();
		this.path = path;
		this.latency = latency;
	}
	public List<String> getPath() {
		return path;
	}
	public void setPath(List<String> path) {
		this.path = path;
	}
	public double getLatency() {
		return latency;
	}
	public void setLatency(double latency) {
		this.latency = latency;
	}
	
	public String toString(){
		return "path: " + this.path.toString() + " latency: " + this.latency;
		
	}
}
