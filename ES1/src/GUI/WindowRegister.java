package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

import XML.ReadXMLfile;
import XML.WriteXMLfile;

/** 
 * Registration window for new users
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

public class WindowRegister {
	// VARIABLES
	private JFrame windowRegister;
	private JFrame windowLogin;
	private ArrayList<JPanel> panels;
	private Font textFont;
	ReadXMLfile r = new ReadXMLfile();

	// CONSTRUCTOR
	public WindowRegister(JFrame windowLogin) {
		this.windowLogin = windowLogin;
		textFont = new Font("Calibri", Font.BOLD, 12);
		windowRegister = new JFrame("Register");
		configWindow();
	}

	// GETTERS
	public JFrame getFrame() {
		return windowRegister;
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
		windowRegister.add(panels.get(0), BorderLayout.SOUTH);
		windowRegister.add(panels.get(1), BorderLayout.WEST);
		windowRegister.add(panels.get(2), BorderLayout.EAST);
		windowRegister.add(panels.get(3), BorderLayout.NORTH);

		// COMPONENTS CONFIGURATION
		JPanel panelCenter = new JPanel(new FlowLayout());
		windowRegister.add(panelCenter);

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

		r.setupRegister("dasra");

		JTextField userName = new JTextField();
		userName.setFont(textFont);
		userName.setPreferredSize(new Dimension(130,20));
		
		JPasswordField passWord = new JPasswordField();
		passWord.setFont(textFont);
		passWord.setPreferredSize(new Dimension(204,20));

		JTextField email = new JTextField();
		email.setFont(textFont);
		email.setText(ReadXMLfile.userData[0]);
		email.setPreferredSize(new Dimension(180,20));
		//email.setEditable(false);

		JPasswordField emailPassWord = new JPasswordField();
		emailPassWord.setFont(textFont);
		emailPassWord.setText(ReadXMLfile.userData[1]);
		emailPassWord.setPreferredSize(new Dimension(210,20));
		//emailPassWord.setEditable(false);

		JTextField oneTokenTwitter = new JTextField();
		oneTokenTwitter.setFont(textFont);
		oneTokenTwitter.setText(ReadXMLfile.twitterData[0]);
		oneTokenTwitter.setPreferredSize(new Dimension(210,20));
		//oneTokenTwitter.setEditable(false);

		JTextField twoTokenTwitter = new JTextField();
		twoTokenTwitter.setFont(textFont);
		twoTokenTwitter.setText(ReadXMLfile.twitterData[1]);
		twoTokenTwitter.setPreferredSize(new Dimension(210,20));
		//twoTokenTwitter.setEditable(false);

		JTextField threeTokenTwitter = new JTextField();
		threeTokenTwitter.setFont(textFont);
		threeTokenTwitter.setText(ReadXMLfile.twitterData[2]);
		threeTokenTwitter.setPreferredSize(new Dimension(210,20));
		//threeTokenTwitter.setEditable(false);

		JTextField fourTokenTwitter = new JTextField();
		fourTokenTwitter.setFont(textFont);
		fourTokenTwitter.setText(ReadXMLfile.twitterData[3]);
		fourTokenTwitter.setPreferredSize(new Dimension(210,20));
		//fourTokenTwitter.setEditable(false);

		JTextField oneTokenFacebook = new JTextField();
		oneTokenFacebook.setFont(textFont);
		oneTokenFacebook.setText(ReadXMLfile.facebookData);
		oneTokenFacebook.setPreferredSize(new Dimension(210,20));
		//oneTokenFacebook.setEditable(false);

		JButton btNreg = new JButton("Register");
		JButton btNcancel = new JButton("Cancel");

		panels.get(3).add(labInfoRegister);
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

		panels.get(0).add(btNreg);
		panels.get(0).add(btNcancel);

		// WINDOW FRAME CONFIGURATION
		windowRegister.setSize(320, 350);
		windowRegister.setLocationRelativeTo(null);
		windowRegister.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		windowRegister.setResizable(false);
		windowRegister.validate();
		windowRegister.setVisible(true);

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
					if(r.validateUserRegister(userName.getText().trim())== true) { // IF USER EXISTS IN XML FILE
						JOptionPane.showMessageDialog(null, "This user altready exists. Please try again.");
					} else {
						WriteXMLfile.addUser(userName.getText().trim(), passWord.getText().trim(), email.getText().trim(), emailPassWord.getText().trim(), oneTokenTwitter.getText().trim(), twoTokenTwitter.getText().trim(), threeTokenTwitter.getText().trim(), fourTokenTwitter.getText().trim(), oneTokenFacebook.getText().trim());
						JOptionPane.showMessageDialog(null, "Successful registration.");
						windowRegister.setVisible(false);
						windowLogin.setVisible(true);
					}
				}

			}
		});

		// CLICK ON BUTTON CANCEL
		btNcancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowRegister.setVisible(false);
				windowLogin.setVisible(true);
			}
		});
	}

	/** 
	 * Utility method to get the JFrame
	 * @return A JFrame
	 */
	public JFrame getWindowFrame() {
		return windowRegister;
	}
}
