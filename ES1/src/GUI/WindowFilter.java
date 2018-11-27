package GUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Others.Attributes;
import XML.ReadXMLfile;
import XML.WriteXMLfile;

/** 
 * Content filters window for registered users.
 * Here a user is able to edit content filters from its own profile by writing and adding them or by removing existing ones.
 * Filters will decide which information will be shown in the content list.
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

public class WindowFilter {
	
	// VARIABLES
	private JFrame windowFilter;
	private ArrayList<JPanel> panels;
	private JList<String> listFilters;
	private DefaultListModel listModel;

	private ReadXMLfile rXML;
	private JFrame windowDBA;

	// CONSTRUCTOR
	public WindowFilter(JFrame windowDBA) {
		this.windowDBA = windowDBA;
		listFilters = new JList<String>();
		listModel = new DefaultListModel();
		listFilters.setModel(listModel);
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
		
		panelCenter.add(listFilters);
		fillFilters();

		panels.get(0).add(btNadd);
		panels.get(0).add(btNremove);
		panels.get(0).add(btNcancel);
		
		// BUTTON ADD
		btNadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filter = JOptionPane.showInputDialog(null, "Please add the filter:");
				if(filter != null) {
					if(!filter.isEmpty() && listModel.size() < 10) {
						if(!rXML.validateFilter(filter)) {
							WriteXMLfile.addFilter(filter);
							JOptionPane.showMessageDialog(null, "Filter " + filter + " added.");
							listModel.addElement(filter);
						} else {
							JOptionPane.showMessageDialog(null, "The filter already exists.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Filtro vazio ou tamanho máximo atingido!");
					}
				}
			}
		});
		
		// BUTTON REMOVE
		btNremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemSelected = listFilters.getSelectedValue();
				WriteXMLfile.removeFilter(itemSelected);
				JOptionPane.showMessageDialog(null, "Filter " + itemSelected + " removed.");
				listModel.removeElement(itemSelected);
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
		windowFilter.setSize(250, 270);
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
		listModel.removeAllElements();
		for(Attributes filter: rXML.readFiltersXMLfile()) {
			listModel.addElement(filter.getKeyword());
		}
	}
	
	public DefaultListModel getListModel() {
		return listModel;
	}
}
