package graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	List<Edge> edges = new ArrayList<Edge>();
	
	public Node() {
		
	}
	
	protected void addEdge(Edge edge) {
		edges.add(edge);
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
	
	public List<Node> getConnected() {
		List<Node> connected = new ArrayList<Node>();
		for (Edge edge : edges)
			connected.add(edge.getConnected(this));
		return connected;
	}

}
