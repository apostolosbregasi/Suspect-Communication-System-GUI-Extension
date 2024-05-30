import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SuspectSearchFrame extends JFrame{
	
	private JPanel searchSuspectPanel;
	private JTextField searchSuspectField;
	private JButton searchSuspectButton;
	private JButton visualizeNetworkButton;
	private SuspectPageFrame spf;
	private ArrayList<Suspect> sus = new ArrayList<Suspect>();
	private Registry regist;
	
	public SuspectSearchFrame(Registry registry) {
		
		regist = registry;
		sus = registry.getAllSuspects();
		
		// Create panel to hold search components
		searchSuspectPanel = new JPanel();
		
		// Text field for user to enter suspect's name
		searchSuspectField = new JTextField("Please enter suspect's name");
		
		// Button to initiate suspect search
		searchSuspectButton = new JButton("Find");
		
		// Button to visualize communication network
		visualizeNetworkButton = new JButton("Visualize Network");

		// Add components to the search panel
		searchSuspectPanel.add(searchSuspectField);
		searchSuspectPanel.add(searchSuspectButton);
		searchSuspectPanel.add(visualizeNetworkButton);
		
		this.setContentPane(searchSuspectPanel);
		
		ButtonListener bl = new ButtonListener();
		searchSuspectButton.addActionListener(bl);
		visualizeNetworkButton.addActionListener(bl);
		
		this.setVisible(true);
		this.setSize(350, 175);
		this.setTitle("Find Suspect");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String suspectName = searchSuspectField.getText();
			Suspect susp = null;
			
			// Handle search button click
			if(e.getSource().equals(searchSuspectButton)) {
				// Search for the suspect with the given name
				for(Suspect s: sus) {
					if(s.getName().equals(suspectName)){
						susp = s;
					}
				}
				// If suspect is found, open a page with suspect details
				if(sus.contains(susp)) {		
					spf = new SuspectPageFrame(susp, regist);
					searchSuspectField.setText("Please enter suspect's name");
				}else {
					// If suspect is not found, display an error message
					JOptionPane.showMessageDialog(null, "Suspect " + suspectName + " Not Found");
				}
			}else {
				// Handle visualize network button click by opening network visualization frame
				NetworkFrame nf = new NetworkFrame(sus);
			}
		}	
	}
}
