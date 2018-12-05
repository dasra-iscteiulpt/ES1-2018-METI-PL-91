package BDA;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** 
* GUI Window construction
* @author GROUP 91
* @version 1.0
* @since September 2018 
*/

public class WindowGUI {
	
	private JFrame windowFrame;
	private ArrayList<JPanel> panels;
	private Font textFont;
	private ReadXMLfile rXML;

	/** 
	 * Constructor
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	public WindowGUI() {
		windowFrame = new JFrame();
		textFont = new Font("Calibri", Font.BOLD, 12);
		rXML = new ReadXMLfile();
		addPanels();
		configWindow();
	}
	
	/** 
	 * Utility method to add JPanels
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void addPanels() {
		panels = new ArrayList<>();
		panels.add(new JPanel()); // 0 SOUTH
		panels.add(new JPanel()); // 1 WEST
		panels.add(new JPanel()); // 2 EAST
		panels.add(new JPanel()); // 3 NORTH
	}
	
	/** 
	 * Construction of the main window structure
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void configWindow() {
		// PANELS CONFIGURATION
		windowFrame.add(panels.get(0), BorderLayout.SOUTH);
		windowFrame.add(panels.get(1), BorderLayout.WEST);
		windowFrame.add(panels.get(2), BorderLayout.EAST);
		windowFrame.add(panels.get(3), BorderLayout.NORTH);
		
		// FINAL CONFIGURATION
		windowFrame.setResizable(false);
		windowFrame.validate();
		windowFrame.setVisible(true);
	}
	
	/** 
	 * Utility method to get the JFrame
	 * @return windowFrame, a JFrame
	 */
	public JFrame getWindowFrame() {
		return windowFrame;
	}
	
	/** 
	 * Utility method to get the JPanels
	 * @return panels, an arrayList of JPanel
	 */
	public ArrayList<JPanel> getPanels() {
		return panels;
	}
	
	/** 
	 * Utility method to get the text font
	 * @return texFont, a font
	 */
	public Font getTextFont() {
		return textFont;
	}
	
	/** 
	 * Utility method to get a ReadXMLfile
	 * @return rXML, a ReadXMLFile
	 */
	public ReadXMLfile getReadXML() {
		return rXML;
	}
	
}