import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SuspectPageFrame extends JFrame{

    private JPanel suspectPanel;
    private JTextField suspectName;
    private JTextField suspectCodeName;
    private JTextArea suspectPhoneNumbers;
    private JTextField insertPhoneNumberField;
    private JTextArea dangerousMessagesArea;
    private JButton findDangerousMessagesButton;
    private JLabel partnersLabel;
    private JTextArea partnersArea;
    private JLabel suggestedPartnersLabel;
    private JTextArea suggestedPartnersArea;
    private JTextArea suspectCountry;
    private JButton returnToSuspectSearchButton;
    private GroupLayout gl;
    
    private Suspect chosenSuspect;
    private Registry r;
    private ArrayList<Suspect> allSuspects = new ArrayList<Suspect>();
    
    
    public SuspectPageFrame(Suspect s, Registry regist) { 
        
        // Initialize frame components and data
        chosenSuspect = s;
        r = regist;
        
        // Retrieve all suspects
        allSuspects = r.getAllSuspects();
        
        // Create panel to hold components
        suspectPanel = new JPanel();
        
        // Initialize text fields and text areas
        suspectName = new JTextField(chosenSuspect.getName());
        suspectCodeName = new JTextField(s.getCodeName());
        
        suspectPhoneNumbers = new JTextArea();
        
        // Populate suspect's phone numbers
        for(String phoneNumbers: chosenSuspect.getNumber()) {
            suspectPhoneNumbers.append(phoneNumbers + "\n"); 
        }
        
        // Initialize other components
        insertPhoneNumberField = new JTextField(10);
        dangerousMessagesArea = new JTextArea(5, 5);
        findDangerousMessagesButton = new JButton("Find SMS");
        
        // Set up labels and areas
        partnersLabel = new JLabel("Partners");
        
        partnersArea = new JTextArea(5, 5);
        
        // Add partners to display
        for(Suspect suspect: allSuspects) {
            if(chosenSuspect.getPartners().contains(suspect)) {
                partnersArea.append(suspect.getName() + ", " + suspect.getCodeName() + "\n");
            }
        }
        
        // Initialize suggested partners components
        suggestedPartnersLabel = new JLabel("Suggested Partners ====>");
        suggestedPartnersArea = new JTextArea(5, 5);
        
        // Add suggested partners to display
        for(Suspect suspects: allSuspects) {
            if(chosenSuspect.getSuggestedPartners().contains(suspects)) {
                suggestedPartnersArea.append(suspects.getName() + "\n");
            }
        }
        
        // Initialize country area
        suspectCountry = new JTextArea(5, 5);
        
        // Populate with suspects from chosen country
        suspectCountry.setText("Suspects from " + chosenSuspect.getCountry() + "\n");
        
        for(String suspectFromCountry: r.printSuspectsFromCountry(chosenSuspect.getCountry())) {
                suspectCountry.append(suspectFromCountry + "\n");
        }
        
        // Set up return button
        returnToSuspectSearchButton = new JButton("Return to Search Screen");
        
        // Set up GroupLayout for layout management
        gl = new GroupLayout(suspectPanel);
        gl.setAutoCreateGaps(true);  
        gl.setAutoCreateContainerGaps(true);  
        
        suspectPanel.setLayout(gl);
        
        // Add components to panel
        suspectPanel.add(suspectName);
        suspectPanel.add(suspectCodeName);
        suspectPanel.add(suspectPhoneNumbers);
        suspectPanel.add(insertPhoneNumberField);
        suspectPanel.add(dangerousMessagesArea);
        suspectPanel.add(findDangerousMessagesButton);
        suspectPanel.add(partnersLabel);
        suspectPanel.add(partnersArea);
        suspectPanel.add(suggestedPartnersLabel);
        suspectPanel.add(suggestedPartnersArea);
        suspectPanel.add(suspectCountry);
        suspectPanel.add(returnToSuspectSearchButton);
        
        // Set panel as content pane
        this.setContentPane(suspectPanel);
        
        // Set up GroupLayout horizontal layout
        gl.setHorizontalGroup(gl.createSequentialGroup()  
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(suspectName).addComponent(insertPhoneNumberField).addComponent(partnersLabel).addComponent(suggestedPartnersLabel))  
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(suspectCodeName).addComponent(dangerousMessagesArea).addComponent(partnersArea).addComponent(suggestedPartnersArea).addComponent(suspectCountry).addComponent(returnToSuspectSearchButton))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(suspectPhoneNumbers).addComponent(findDangerousMessagesButton)));  
        
        // Set up GroupLayout vertical layout
        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(suspectName, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(suspectCodeName, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(suspectPhoneNumbers, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(insertPhoneNumberField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(dangerousMessagesArea, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(findDangerousMessagesButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(partnersLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(partnersArea, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(suggestedPartnersLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(suggestedPartnersArea, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(suspectCountry, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(returnToSuspectSearchButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)));
        
        // Set frame properties
        this.setVisible(true);
        this.setSize(550, 550);
        this.setTitle("Suspects Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // Button listener for suspect page
    class suspectButtonListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            
            String partnersPhoneNumber = insertPhoneNumberField.getText();
            
            // Handle return button click
            if(e.getSource() == returnToSuspectSearchButton) {
                
                // Clear stored data related to chosen suspect and return to search screen
                r.printSuspectsFromCountry(chosenSuspect.getCountry()).clear();
                
                for(String phoneNumbers: chosenSuspect.getNumber()) {
                    r.getMessagesBetween(phoneNumbers, partnersPhoneNumber).clear();
                }
                
                JComponent suspectComponent = (JComponent) e.getSource();
                Window suspectPageWindow = SwingUtilities.getWindowAncestor(suspectComponent);
                suspectPageWindow.dispose();
                
            }else{
                
                // Find and display dangerous messages between chosen suspect and given number
                for(String phoneNumbers: chosenSuspect.getNumber()) {
                    for(SMS messages: r.getMessagesBetween(phoneNumbers, partnersPhoneNumber)) {
                        if(dangerousMessagesArea.getText().contains(messages.getText())) {
                          }else {
							dangerousMessagesArea.append(messages.getText() + "\n");	
						}
					}
					
					for(SMS messages: r.getMessagesBetween(partnersPhoneNumber, phoneNumbers)) {
						if(dangerousMessagesArea.getText().contains(messages.getText())) {
							
						}else {
							dangerousMessagesArea.append(messages.getText() + "\n");	
						}
					}
				}
			}
		}
	}
}
