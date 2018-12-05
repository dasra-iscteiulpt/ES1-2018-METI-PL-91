package BDA;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/** 
* Login window construction
* @author GROUP 91
* @version 1.0
* @since September 2018 
*/

public class WindowLogin extends WindowGUI {

	// VARIABLES
	private String userDBA;
	private JCheckBox chkOffline;
	
	/** 
	 * Constructor
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	public WindowLogin() {
		chkOffline = new JCheckBox("Offline");
		getWindowFrame().setTitle("Login");
		configWindow();
	}
	
	/** 
	* Construction of the main window structure
	* @author GROUP 91
	* @version 1.0
	* @since September
	*/
	private void configWindow() {

		// COMPONENTS CONFIGURATION
		JPanel panelCenter = new JPanel(new FlowLayout());
		getWindowFrame().add(panelCenter);
		
		JLabel labInfoLog = new JLabel("Do you have an account? Please login.");
		JLabel labInfoRegister = new JLabel("Not registered? Please sign up.");
		
		JLabel labInfoUser = new JLabel("Username: ");
		JLabel labInfoPw = new JLabel("Password: ");
		
		JTextField userName = new JTextField();	
		userName.setFont(getTextFont());
		userName.setPreferredSize(new Dimension(130,20));
		
		JPasswordField passWord = new JPasswordField();
		passWord.setFont(getTextFont());
		passWord.setPreferredSize(new Dimension(204,20));
		JLabel labEmail = new JLabel("@iscte-iul.pt");
		JButton btNlog = new JButton("Login");
		JButton btNreg = new JButton("Register");
		JButton btNunsub = new JButton("Unsubscribe");
		
		getPanels().get(3).add(labInfoLog);
		panelCenter.add(labInfoUser);
		panelCenter.add(userName);
		panelCenter.add(labEmail);

		panelCenter.add(labInfoPw);
		panelCenter.add(passWord);
		panelCenter.add(labInfoRegister);

		getPanels().get(0).add(btNlog);
		getPanels().get(0).add(btNreg);
		getPanels().get(0).add(btNunsub);
		getPanels().get(0).add(chkOffline);

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
        
		// CLICK ON BUTTON REGISTER
		btNreg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowGUI windReg = new WindowRegister(getWindowFrame());
				getWindowFrame().setVisible(false);
				windReg.getWindowFrame().setVisible(true);
				
			}
		});
		
		// CLICK ON BUTTON UNSUBSCRIBE
		btNunsub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowGUI windUnsub = new WindowUnsubscribe(getWindowFrame());
				getWindowFrame().setVisible(false);
				windUnsub.getWindowFrame().setVisible(true);
			}
		});
		
		// CLICK ON BUTTON LOGIN
		btNlog.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(userName.getText().isEmpty() || passWord.getText().toString().isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are empty fields.");
				} else {
					// IF USER AND PASSWORD ARE IN THE XML FILE, THE LOGIN IS SUCCESSFUL
					if(getReadXML().validateUserBDA(userName.getText().trim(),passWord.getText().toString().trim()) == true) {
						JOptionPane.showMessageDialog(null, "Login successful.");
						userDBA = userName.getText();
						
						getWindowFrame().setVisible(false);
						
						WindowLoading windowloading = new WindowLoading();						
						windowloading.start();
						
						@SuppressWarnings("unused")
						WindowDBA w = new WindowDBA(chkOffline.isSelected(), userDBA, windowloading);
						w.start();
																				
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect login. Please review the access data.");
					}
				}
			}
		});

		// CONFIGURATION WINDOW FRAME
		getWindowFrame().setSize(350, 170);
		getWindowFrame().setLocationRelativeTo(null);
		getWindowFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}