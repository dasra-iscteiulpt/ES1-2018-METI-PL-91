package GUI;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import XML.ReadXMLfile;

public class WindowGUI {
	
	private JFrame windowFrame;
	private ArrayList<JPanel> panels;
	private Font textFont;
	private ReadXMLfile rXML;

	public WindowGUI() {
		windowFrame = new JFrame();
		textFont = new Font("Calibri", Font.BOLD, 12);
		rXML = new ReadXMLfile();
		addPanels();
		configWindow();
	}
	
	private void addPanels() {
		panels = new ArrayList<>();
		panels.add(new JPanel()); // 0 SOUTH
		panels.add(new JPanel()); // 1 WEST
		panels.add(new JPanel()); // 2 EAST
		panels.add(new JPanel()); // 3 NORTH
	}
	
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
	
	// GETTERS
	public JFrame getWindowFrame() {
		return windowFrame;
	}
	
	public ArrayList<JPanel> getPanels() {
		return panels;
	}
	
	public Font getTextFont() {
		return textFont;
	}
	
	public ReadXMLfile getReadXML() {
		return rXML;
	}
	
}