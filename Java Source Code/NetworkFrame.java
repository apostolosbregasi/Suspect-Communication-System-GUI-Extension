import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

// Class for visualizing the network

public class NetworkFrame extends JFrame{

	private JPanel networkFramePanel;
	private ArrayList<Suspect> suspects;
	private Graph<String, String> networkGraph;
	private JTextField diameterTextField;
	private BorderLayout networkLayout;
	
	public NetworkFrame(ArrayList<Suspect> sus) {
		
		networkFramePanel = new JPanel();
		networkLayout = new BorderLayout();
		suspects = sus;
		
		networkFramePanel.setLayout(networkLayout);
		
		networkGraph = new SparseGraph<String, String>();
		
		// Create a vertex for each suspect based on their code name
		for(Suspect s: suspects) {
			networkGraph.addVertex(s.getCodeName());
		}
		
		// Connect the vertices if there are connections between suspects
		int i = 0;
		for(Suspect s: suspects) {
			for(Suspect suspect: suspects) {
				if(s.isConnectedTo(suspect)) {
					networkGraph.addEdge( "Edge " + i, s.getCodeName(), suspect.getCodeName());
					i++;
				}
			}
		}
		
		// Create VisualizationViewer to place the graph layout on the JPanel
		VisualizationViewer<String, String> networkFrameVisualizationViewer =new VisualizationViewer<String, String>(new CircleLayout<String, String>(networkGraph), new Dimension(600, 600));
		
		// Display labels
		networkFrameVisualizationViewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
		
		// Calculate diameter
		Double networkDistance = DistanceStatistics.diameter(networkGraph);
		
		diameterTextField =  new JTextField("Diameter: " + networkDistance);
		
		networkFramePanel.add(networkFrameVisualizationViewer);
		networkFramePanel.add(diameterTextField, BorderLayout.PAGE_END);
		
		this.setContentPane(networkFramePanel);
		
		this.setVisible(true);
		this.setSize(750, 725);
		this.setTitle("Suspects Network");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
}
