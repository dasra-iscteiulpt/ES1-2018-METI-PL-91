package bda;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

	// CONSTRUCTOR
	public WindowFilter() {
		comboFilters = new JComboBox<String>();
		windowFilter = new JFrame("Remove filterr");
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
		
		comboFilters.addItem("Teste");
		comboFilters.addItem("Teste");
		comboFilters.addItem("Teste");
		comboFilters.addItem("Teste");

		panelCenter.add(comboFilters);
		
		JButton btNremove = new JButton("Remove");

		panels.get(0).add(btNremove);

		// WINDOW FRAME CONFIGURATION
		windowFilter.setSize(320, 100);
		windowFilter.setLocationRelativeTo(null);
		windowFilter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFilter.setResizable(false);
		windowFilter.validate();
		windowFilter.setVisible(true);

		// CLICK ON BUTTON REGISTER
		btNremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

			}
		});

	}

	public JFrame getWindowFrame() {
		return windowFilter;
	}
}
