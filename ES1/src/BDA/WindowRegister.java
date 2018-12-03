package BDA;
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

/** 
 * Registration window for new users
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

public class WindowRegister extends WindowGUI {
	// VARIABLES
	private JFrame windowLogin;

	/** 
	 * Constructor
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param windowLogin, a JFrame
	 */
	public WindowRegister(JFrame windowLogin) {
		this.windowLogin = windowLogin;
		getWindowFrame().setTitle("Register");
		configWindow();
	}

	/** 
	 * Construction of the main window structure
	 */
	private void configWindow() {

		// COMPONENTS CONFIGURATION
		JPanel panelCenter = new JPanel(new FlowLayout());
		getWindowFrame().add(panelCenter);

		JLabel labInfoRegister = new JLabel("Create your account: ");

		JLabel labInfoUser = new JLabel("Username: ");
		JLabel labInfoPw = new JLabel("Password: ");
		JLabel labInfoEmail = new JLabel("E-mail: ");
		JLabel labInfoEmailPw = new JLabel("E-mail pass: ");
		JLabel labEmail = new JLabel("@iscte-iul.pt");
		JLabel labInfo1TokenT = new JLabel("   Token TW: ");
		JLabel labInfo2TokenT = new JLabel("   Token TW: ");
		JLabel labInfo3TokenT = new JLabel("   Token TW: ");
		JLabel labInfo4TokenT = new JLabel("   Token TW: ");
		JLabel labInfo1TokenF = new JLabel("    Token FB: ");

		getReadXML().setupRegister("dasra");

		JTextField userName = new JTextField();
		userName.setFont(getTextFont());
		userName.setPreferredSize(new Dimension(130,20));
		
		JPasswordField passWord = new JPasswordField();
		passWord.setFont(getTextFont());
		passWord.setPreferredSize(new Dimension(204,20));

		JTextField email = new JTextField();
		email.setFont(getTextFont());
		email.setText(ReadXMLfile.userData[0]);
		email.setPreferredSize(new Dimension(180,20));
		//email.setEditable(false);

		JPasswordField emailPassWord = new JPasswordField();
		emailPassWord.setFont(getTextFont());
		emailPassWord.setText(ReadXMLfile.userData[1]);
		emailPassWord.setPreferredSize(new Dimension(210,20));
		//emailPassWord.setEditable(false);

		JTextField oneTokenTwitter = new JTextField();
		oneTokenTwitter.setFont(getTextFont());
		oneTokenTwitter.setText(ReadXMLfile.twitterData[0]);
		oneTokenTwitter.setPreferredSize(new Dimension(210,20));
		//oneTokenTwitter.setEditable(false);

		JTextField twoTokenTwitter = new JTextField();
		twoTokenTwitter.setFont(getTextFont());
		twoTokenTwitter.setText(ReadXMLfile.twitterData[1]);
		twoTokenTwitter.setPreferredSize(new Dimension(210,20));
		//twoTokenTwitter.setEditable(false);

		JTextField threeTokenTwitter = new JTextField();
		threeTokenTwitter.setFont(getTextFont());
		threeTokenTwitter.setText(ReadXMLfile.twitterData[2]);
		threeTokenTwitter.setPreferredSize(new Dimension(210,20));
		//threeTokenTwitter.setEditable(false);

		JTextField fourTokenTwitter = new JTextField();
		fourTokenTwitter.setFont(getTextFont());
		fourTokenTwitter.setText(ReadXMLfile.twitterData[3]);
		fourTokenTwitter.setPreferredSize(new Dimension(210,20));
		//fourTokenTwitter.setEditable(false);

		JTextField oneTokenFacebook = new JTextField();
		oneTokenFacebook.setFont(getTextFont());
		oneTokenFacebook.setText(ReadXMLfile.facebookData);
		oneTokenFacebook.setPreferredSize(new Dimension(210,20));
		//oneTokenFacebook.setEditable(false);

		JButton btNreg = new JButton("Register");
		JButton btNcancel = new JButton("Cancel");

		getPanels().get(3).add(labInfoRegister);
		panelCenter.add(labInfoUser);
		panelCenter.add(userName);
		panelCenter.add(labEmail);
		panelCenter.add(labInfoPw);
		panelCenter.add(passWord);
		panelCenter.add(labInfoEmail);
		panelCenter.add(email);
		panelCenter.add(labInfoEmailPw);
		panelCenter.add(emailPassWord);
		panelCenter.add(labInfo1TokenT);
		panelCenter.add(oneTokenTwitter);
		panelCenter.add(labInfo2TokenT);
		panelCenter.add(twoTokenTwitter);
		panelCenter.add(labInfo3TokenT);
		panelCenter.add(threeTokenTwitter);
		panelCenter.add(labInfo4TokenT);
		panelCenter.add(fourTokenTwitter);
		panelCenter.add(labInfo1TokenF);
		panelCenter.add(oneTokenFacebook);

		getPanels().get(0).add(btNreg);
		getPanels().get(0).add(btNcancel);

		// WINDOW FRAME CONFIGURATION
		getWindowFrame().setSize(320, 350);
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

		// CLICK ON BUTTON REGISTER
		btNreg.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(userName.getText().isEmpty() || passWord.getText().toString().isEmpty() || email.getText().isEmpty() || emailPassWord.getText().toString().isEmpty() ) {
					JOptionPane.showMessageDialog(null, "There are empty fields.");
				} else {
					if(getReadXML().validateUserRegister(userName.getText().trim())== true) { // IF USER EXISTS IN XML FILE
						JOptionPane.showMessageDialog(null, "This user altready exists. Please try again.");
					} else {
						WriteXMLfile.addUser(userName.getText().trim(), passWord.getText().trim(), email.getText().trim(), emailPassWord.getText().trim(), oneTokenTwitter.getText().trim(), twoTokenTwitter.getText().trim(), threeTokenTwitter.getText().trim(), fourTokenTwitter.getText().trim(), oneTokenFacebook.getText().trim());
						JOptionPane.showMessageDialog(null, "Successful registration.");
						getWindowFrame().setVisible(false);
						windowLogin.setVisible(true);
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