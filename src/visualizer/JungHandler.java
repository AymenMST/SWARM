package visualizer;

import java.awt.Color;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.Vertex;
import graph.Edge;
import graph.Node;

/**
 * @author cauthon
 */
public class JungHandler {

	private Layout<Node, Edge> layout;
	private BasicVisualizationServer<Node, Edge> viewer;
	private JFrame frame;
	private NodeColor nodeColor;

	public JungHandler(Forest<Node, Edge> forest) {
		layout = new TreeLayout<>(forest);

		viewer = new BasicVisualizationServer<>(layout);
		
		nodeColor = new NodeColor();
		
		for (Node n : forest.getVertices()){
			layout.setLocation(n, n.getLocation());
			//viewer.getRenderer().setVertexRenderer(nodeColor);
		}

		
		
		
		frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(viewer);
		frame.pack();
		frame.setVisible(true);

	}

}
