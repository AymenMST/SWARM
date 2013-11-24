package visualizer;

import java.awt.Dimension;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import graph.Edge;
import graph.Graph;
import graph.Node;

public class JungHandler {

	private Layout<Node, Edge> layout;
	private BasicVisualizationServer<Node, Edge> viewer;
	private JFrame frame = new JFrame("Simple Graph View");

	public JungHandler(Forest<Node, Edge> forest) {
		//forest = sortByLayer(forest);
		layout = new TreeLayout<>(forest);
		viewer = new BasicVisualizationServer<>(layout);
		
		// Map nodes to their internally stored colors
        Transformer<Node,Paint> vertexColor = new Transformer<Node,Paint>() {
			public Paint transform(Node node) {
				return node.getColor();
			}
        };
		viewer.getRenderContext().setVertexFillPaintTransformer(vertexColor);
		
		for (Node n : forest.getVertices()) {
			layout.setLocation(n, n.getLocation());
		}

		frame.add(viewer);
	}
	
	public void draw() {
		frame.setSize(new Dimension(1000, 1000));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * A bubble sort method performed on each node's layer index.
	 * Does not work with edges, must implement if necessary.
	 * 
	 * @return 
	 */
	public Forest<Node, Edge> sortByLayer(Forest<Node, Edge> forest) {
		List<Node> nodes = new ArrayList<Node>(forest.getVertices());
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size() - 1 - i; j++) {
				if (nodes.get(j).getLayer() > nodes.get(j + 1).getLayer()) {
					Node temp = nodes.get(j);
					nodes.set(j, nodes.get(j + 1));
					nodes.set(j + 1, temp);
				}
			}
		}
		Forest<Node, Edge> newForest = new Graph();
		for (Node node : nodes) {
			newForest.addVertex(node);
		}
		return newForest;
	}

}
