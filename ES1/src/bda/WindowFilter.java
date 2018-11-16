package bda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	private ArrayList<JPanel> panels;
	private JComboBox<String> comboFilters;
	private ReadXMLfile rXML;
	private JFrame windowDBA;

	// CONSTRUCTOR
	public WindowFilter(JFrame windowDBA) {
		this.windowDBA = windowDBA;
		comboFilters = new JComboBox<String>(); 	
		rXML = new ReadXMLfile();
		windowFilter = new JFrame("Filters");
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
		
		JButton btNadd = new JButton("Add");
		JButton btNcancel = new JButton("Cancel");
		JButton btNremove = new JButton("Remove");
		
		panelCenter.add(comboFilters);
		fillFilters();

		panels.get(0).add(btNadd);
		panels.get(0).add(btNremove);
		panels.get(0).add(btNcancel);
		
		// BUTTON ADD
		btNadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filter = JOptionPane.showInputDialog(null, "Insira o filtro a adicionar:");
				if(filter != null) {
					if(!filter.isEmpty()) {
						if(!rXML.validateFilter(filter)) {
							WriteXMLfile.addFilter(filter);
							JOptionPane.showMessageDialog(null, "Filtro " + filter + " foi adicionado.");
							comboFilters.addItem(filter);
						} else {
							JOptionPane.showMessageDialog(null, "Filtro já existente.");
						}
					}
				}
			}
		});
		
		// BUTTON REMOVE
		btNremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemSelected = comboFilters.getSelectedItem().toString();
				WriteXMLfile.removeFilter(itemSelected);
				JOptionPane.showMessageDialog(null, "Filtro " + itemSelected + " foi removido.");
				comboFilters.removeItem(itemSelected);
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
		windowFilter.setSize(250, 120);
		windowFilter.setLocationRelativeTo(null);
		windowFilter.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		windowFilter.setResizable(false);
		windowFilter.validate();
		windowFilter.setVisible(true);

	}

	public JFrame getWindowFrame() {
		return windowFilter;
	}
	

	public void fillFilters() {
		comboFilters.removeAllItems();
		for(Attributes filter: rXML.readFiltersXMLfile()) {
			comboFilters.addItem(filter.getKeyword());
		}
	}
	
	public JComboBox<String> getComboBox() {
		return comboFilters;
	}
}
