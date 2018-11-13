package bda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** 
 * Registration window for new users
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

public class WindowFilter {
	
	// VARIABLES
	private JFrame windowFilter;
	private JComboBox<String> comboFilters;
	private ArrayList<JPanel> panels;
	private String typeWindow;
	private JButton btNcancel;
	private JButton btNremove;
	private ReadXMLfile rXML;
	private JFrame windowDBA;

	// CONSTRUCTOR
	public WindowFilter(String typeWindow, JFrame windowDBA) {
		this.typeWindow = typeWindow;
		this.windowDBA = windowDBA;
		btNcancel = new JButton("Cancel");
		btNremove = new JButton("Remove");
		rXML = new ReadXMLfile();
		windowFilter = new JFrame(typeWindow);
		comboFilters = new JComboBox<String>();
		configWindow();
	}

	// GETTERS
	public JFrame getFrame() {
		return windowFilter;
	}

	public ArrayList<JPanel> getPanels() {
		return panels;
	}

	// AUXILIARY METHODS
	private void addPanels() {
		panels = new ArrayList<>();
		panels.add(new JPanel()); // 0 SOUTH
		panels.add(new JPanel()); // 1 WEST
		panels.add(new JPanel()); // 2 EAST
		panels.add(new JPanel()); // 3 NORTH
	}

	/** 
	 * Construction of the main window structure
	 */
	private void configWindow() {
		addPanels();

		// JPANEL CONFIGURATION IN WINDOWLOGIN
		windowFilter.add(panels.get(0), BorderLayout.SOUTH);
		windowFilter.add(panels.get(1), BorderLayout.WEST);
		windowFilter.add(panels.get(2), BorderLayout.EAST);
		windowFilter.add(panels.get(3), BorderLayout.NORTH);

		// COMPONENTS CONFIGURATION
		JPanel panelCenter = new JPanel(new FlowLayout());
		windowFilter.add(panelCenter);
		
		panelCenter.add(comboFilters);
		
		for(Attributes filter: rXML.readFiltersXMLfile()) {
			comboFilters.addItem(filter.getKeyword());
		}
		
		if(typeWindow.equals("Remove filter")) {
			panels.get(0).add(btNremove);
			panels.get(0).add(btNcancel);
		} else if(typeWindow.equals("View filter")) {
			panels.get(0).add(btNcancel);
		}
		
		// BUTTON REMOVE
		btNremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemSelected = comboFilters.getSelectedItem().toString();
				WriteXMLfile.removeFilter(itemSelected);
			}
		});
		
		
		// BUTTON CANCEL
		btNcancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowFilter.setVisible(false);
				windowDBA.setVisible(true);
			}
		});
		
		
		// WINDOW FRAME CONFIGURATION
		windowFilter.setSize(200, 120);
		windowFilter.setLocationRelativeTo(null);
		windowFilter.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		windowFilter.setResizable(false);
		windowFilter.validate();
		windowFilter.setVisible(true);

	}

	public JFrame getWindowFrame() {
		return windowFilter;
	}
}
