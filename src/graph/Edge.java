package graph;

public class Edge {
	
	Node node1;
	Node node2;
	public double weight = 1.0;
	public double pheromone = 0.0;
	
	public Edge(Node node1, Node node2) {
		this.node1 = node1;
		this.node2 = node2;
		this.node1.addEdge(this);
		this.node2.addEdge(this);
	}
	
	public Node getConnected(Node node) {
		Node connected = null;
		if (node == node1)
			connected = node2;
		else if (node == node2)
			connected = node1;
		else
			System.out.println("ERROR: Invalid connection request");
		return connected;
	}

}
