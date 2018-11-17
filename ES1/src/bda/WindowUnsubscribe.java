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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/** 
 * Unsubscribe window for registered users.
 * Existing user and respective profile information will be deleted from the XML file.
 * @author GROUP 91
 * @version 1.0 
 * @since September 2018
 */

public class WindowUnsubscribe {
	// VARIABLES
	private JFrame windowUnsubscribe;
	private JFrame windowLogin;
	private ArrayList<JPanel> panels;
	ReadXMLfile r = new ReadXMLfile();

	// CONSTRUCTOR
	public WindowUnsubscribe(JFrame windowLogin) {
		this.windowLogin = windowLogin;
		windowUnsubscribe = new JFrame("Unsubscribe");
		configWindow();
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
		windowUnsubscribe.add(panels.get(0), BorderLayout.SOUTH);
		windowUnsubscribe.add(panels.get(1), BorderLayout.WEST);
		windowUnsubscribe.add(panels.get(2), BorderLayout.EAST);
		windowUnsubscribe.add(panels.get(3), BorderLayout.NORTH);

		// COMPONENTS CONFIGURATION
		JPanel panelCenter = new JPanel(new FlowLayout());
		windowUnsubscribe.add(panelCenter);

		JLabel labInfoRegister = new JLabel("Unsubscribe account: ");

		JLabel labInfoUser = new JLabel("Username: ");
		JLabel labInfoPw = new JLabel("Password: ");
		JLabel labEmail = new JLabel("@iscte-iul.pt");

		JTextField userName = new JTextField();
		userName.setPreferredSize(new Dimension(130,20));

		JPasswordField passWord = new JPasswordField();
		passWord.setPreferredSize(new Dimension(204,20));

		JButton btNunsub = new JButton("Unsubscribe");
		JButton btNcancel = new JButton("Cancel");

		panels.get(3).add(labInfoRegister);
		panelCenter.add(labInfoUser);
		panelCenter.add(userName);
		panelCenter.add(labEmail);
		panelCenter.add(labInfoPw);
		panelCenter.add(passWord);

		panels.get(0).add(btNunsub);
		panels.get(0).add(btNcancel);

		// WINDOW FRAME CONFIGURATION
		windowUnsubscribe.setSize(320, 150);
		windowUnsubscribe.setLocationRelativeTo(null);
		windowUnsubscribe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		windowUnsubscribe.setResizable(false);
		windowUnsubscribe.validate();
		windowUnsubscribe.setVisible(true);

		// CLICK ON TEXTFIELD USERNAME
		userName.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				userName.setText("");
			}
		});

		// CLICK ON TEXTFIELD PASSWORD
		passWord.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				passWord.setText("");
			}
		});

		
		// CLICK ON BUTTON UNBSUBSCRIBE
		btNunsub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userName.getText().isEmpty() || passWord.getText().toString().isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are empty fields.");
				} else {
					if(r.validateUserRegister(userName.getText().trim())== true) { // IF USER IS IN XML FILE
						WriteXMLfile.removeUser(userName.getText().trim());
						JOptionPane.showMessageDialog(null, "User removed.");				
						windowUnsubscribe.setVisible(false);
						windowLogin.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "This user doesn't exist.");
					}
				}

			}
		});
		
		
		
		// CLICK ON BUTTON CANCEL
		btNcancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowUnsubscribe.setVisible(false);
				windowLogin.setVisible(true);
			}
		});
	}

	public JFrame getWindowFrame() {
		return windowUnsubscribe;
	}
}
