package GUI;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import XML.WriteXMLfile;

/** 
 * Unsubscribe window for registered users.
 * Existing user and respective profile information will be deleted from the XML file.
 * @author GROUP 91
 * @version 1.0 
 * @since September 2018 
 */

public class WindowUnsubscribe extends WindowGUI {
	// VARIABLES
	private JFrame windowLogin;

	/** 
	 * Constructor
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param windowLogin a JFrame
	 */
	public WindowUnsubscribe(JFrame windowLogin) {
		this.windowLogin = windowLogin;
		getWindowFrame().setTitle("Unsubscribe");
		configWindow();
	}


	/** 
	 * Construction of the main window structure
	 */
	private void configWindow() {

		// COMPONENTS CONFIGURATION
		JPanel panelCenter = new JPanel(new FlowLayout());
		getWindowFrame().add(panelCenter);

		JLabel labInfoRegister = new JLabel("Unsubscribe account: ");

		JLabel labInfoUser = new JLabel("Username: ");
		JLabel labInfoPw = new JLabel("Password: ");
		JLabel labEmail = new JLabel("@iscte-iul.pt");

		JTextField userName = new JTextField();
		userName.setFont(getTextFont());
		userName.setPreferredSize(new Dimension(130,20));

		JPasswordField passWord = new JPasswordField();
		passWord.setFont(getTextFont());
		passWord.setPreferredSize(new Dimension(204,20));

		JButton btNunsub = new JButton("Unsubscribe");
		JButton btNcancel = new JButton("Cancel");

		getPanels().get(3).add(labInfoRegister);
		panelCenter.add(labInfoUser);
		panelCenter.add(userName);
		panelCenter.add(labEmail);
		panelCenter.add(labInfoPw);
		panelCenter.add(passWord);

		getPanels().get(0).add(btNunsub);
		getPanels().get(0).add(btNcancel);

		// WINDOW FRAME CONFIGURATION
		getWindowFrame().setSize(320, 150);
		getWindowFrame().setLocationRelativeTo(null);
		getWindowFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(userName.getText().isEmpty() || passWord.getText().toString().isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are empty fields.");
				} else {
					if(getReadXML().validateUserRegister(userName.getText().trim())== true) { // IF USER IS IN XML FILE
						WriteXMLfile.removeUser(userName.getText().trim());
						JOptionPane.showMessageDialog(null, "User removed.");				
						getWindowFrame().setVisible(false);
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
				getWindowFrame().setVisible(false);
				windowLogin.setVisible(true);
			}
		});
	}
}
