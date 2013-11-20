package visualizer;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import graph.Edge;
import graph.Node;

/**
 * @author cauthon
 */
public class JungHandler {

	private Layout<Node, Edge> layout;
	private BasicVisualizationServer<Node, Edge> viewer;
	private JFrame frame;

	public JungHandler(Forest<Node, Edge> forest) {
		layout = new TreeLayout<>(forest);

		viewer = new BasicVisualizationServer<>(layout);

		frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(viewer);
		frame.pack();
		frame.setVisible(true);

	}

}
