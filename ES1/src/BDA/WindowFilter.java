package BDA;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** 
 * Content filters window for registered users.
 * Here a user is able to edit content filters from its own profile by writing and adding them or by removing existing ones.
 * Filters will decide which information will be shown in the content list.
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

public class WindowFilter extends WindowGUI {
	
	// VARIABLES
	private JList<String> listFilters;
	private DefaultListModel listModel;
	private JFrame windowDBA;

	/** 
	 * Constructor
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param windowDBA, a JFrame
	 */
	public WindowFilter(JFrame windowDBA) {
		this.windowDBA = windowDBA;
		listFilters = new JList<String>();
		listModel = new DefaultListModel();
		listFilters.setModel(listModel);
		getWindowFrame().setTitle("Filters");
		configWindow();
	}

	/** 
	 * Utility method for construction of the main window structure
	 */
	private void configWindow() {

		// COMPONENTS CONFIGURATION
		JPanel panelCenter = new JPanel(new FlowLayout());
		getWindowFrame().add(panelCenter);
		
		JButton btNadd = new JButton("Add");
		JButton btNcancel = new JButton("Cancel");
		JButton btNremove = new JButton("Remove");
		
		panelCenter.add(listFilters);
		fillFilters();

		getPanels().get(0).add(btNadd);
		getPanels().get(0).add(btNremove);
		getPanels().get(0).add(btNcancel);
		
		// BUTTON ADD
		btNadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filter = JOptionPane.showInputDialog(null, "Please add the filter:");
				if(filter != null) {
					if(!filter.isEmpty() && listModel.size() < 10) {
						if(!getReadXML().validateFilter(filter)) {
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
				getWindowFrame().setVisible(false);
				windowDBA.setVisible(true);
			}
		});
		
		// WINDOW FRAME CONFIGURATION
		getWindowFrame().setSize(250, 270);
		getWindowFrame().setLocationRelativeTo(null);
		getWindowFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}
	
	/** 
	 * Utility method to fill the table with filters 
	 */
	public void fillFilters() {
		listModel.removeAllElements();
		for(Attributes filter: getReadXML().readFiltersXMLfile()) {
			listModel.addElement(filter.getKeyword());
		}
	}
	
	/** 
	 * Utility method to get the DefaultListModel
	 * @return listModel, A DefaultListModel
	 */
	public DefaultListModel getListModel() {
		return listModel;
	}
}
