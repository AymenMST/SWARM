package visualizer;

import java.awt.Color;

import driver.DataPoint;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import graph.Edge;
import graph.Node;

public class NodeColor implements Renderer.Vertex<Node, Edge> {

	@Override
	public void paintVertex(RenderContext<Node, Edge> rc, Layout<Node, Edge> layout, Node node) {
		GraphicsDecorator graphicsContext = rc.getGraphicsContext();
		graphicsContext.setPaint(getColorFromOutputs(node.getDataPoint()));

	}

	public Color getColorFromOutputs(DataPoint d) {
		int totalColors = d.getOutputs().size();
		Color thisColor = null;
		int stepCounter = 255 / totalColors;
		int colorGradient = 0;
		for (int i = 0; i < d.getClassIndex(); i++) {
			colorGradient += stepCounter;
		}
		thisColor = new Color(colorGradient, colorGradient, colorGradient);
		return thisColor;
	}

}
