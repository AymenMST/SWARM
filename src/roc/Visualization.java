package roc;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import graph.Edge;
import graph.Node;

public class Visualization {

	private Layout<Node, Edge> layout;
	private BasicVisualizationServer<Node, Edge> viewer;
	private JFrame frame;

	public Visualization() {
		frame = new JFrame("Simple Graph View");
		layout = new TreeLayout<>(graph);
		viewer = new BasicVisualizationServer<>(layout);
		
		for (Node n : graph.getVertices()) {
			layout.setLocation(n, n.getLocation());
		}
		
		frame.add(viewer, BorderLayout.CENTER);
	}

}
