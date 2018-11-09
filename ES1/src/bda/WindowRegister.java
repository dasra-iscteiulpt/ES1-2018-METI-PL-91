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

public class WindowRegister {
	// ATRIBUTOS
	private JFrame windowRegister;
	private JFrame windowLogin;
	private ArrayList<JPanel> panels;
	ReadXMLfile r = new ReadXMLfile();
	
	// CONSTRUTOR
	public WindowRegister(JFrame windowLogin) {
		this.windowLogin = windowLogin;
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

	// MÉTODOS AUXILIARES
	private void addPanels() {
		panels = new ArrayList<>();
		panels.add(new JPanel()); // 0 SOUTH
		panels.add(new JPanel()); // 1 WEST
		panels.add(new JPanel()); // 2 EAST
		panels.add(new JPanel()); // 3 NORTH
	}

	/** 
	* Construction of the main window structure
	* @author GROUP 91
	* @version 1.0
	* @since September
	*/
	private void configWindow() {
		addPanels();

		// CONFIGURAÇÃO JPANEL NA WINDOWLOGIN
		windowRegister.add(panels.get(0), BorderLayout.SOUTH);
		windowRegister.add(panels.get(1), BorderLayout.WEST);
		windowRegister.add(panels.get(2), BorderLayout.EAST);
		windowRegister.add(panels.get(3), BorderLayout.NORTH);
		
		// CONFIGURAÇÃO DOS COMPONENTES
				JPanel panelCenter = new JPanel(new FlowLayout());
				windowRegister.add(panelCenter);
				
				JLabel labInfoRegister = new JLabel("Introduza os dados de registo:");
				
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
				JLabel labInfo2TokenF = new JLabel("    Token FB: ");

				JTextField userName = new JTextField();
				userName.setPreferredSize(new Dimension(130,20));
				
				JPasswordField passWord = new JPasswordField();
				passWord.setPreferredSize(new Dimension(204,20));

				JTextField email = new JTextField();
				email.setText("diana.es.pl.91@gmail.com");
				email.setPreferredSize(new Dimension(180,20));
				email.setEditable(false);
				
				JPasswordField emailPassWord = new JPasswordField();
				emailPassWord.setText("engenhariasoftware");
				emailPassWord.setPreferredSize(new Dimension(210,20));
				emailPassWord.setEditable(false);

				JTextField oneTokenTwitter = new JTextField();
				oneTokenTwitter.setText("dpthvBBPVVsWxWituNu1CBx9h");
				oneTokenTwitter.setPreferredSize(new Dimension(210,20));
				oneTokenTwitter.setEditable(false);
				
				JTextField twoTokenTwitter = new JTextField();
				twoTokenTwitter.setText("Nsuy5xBRdruPTaBms8GfQVtFsywI16zGyExf27THMHmmEm6d2W");
				twoTokenTwitter.setPreferredSize(new Dimension(210,20));
				twoTokenTwitter.setEditable(false);
				
				JTextField threeTokenTwitter = new JTextField();
				threeTokenTwitter.setText(">1056561597659914240-pHb2DY7rIzqtPTVyohg7zVZGEoqX6B");
				threeTokenTwitter.setPreferredSize(new Dimension(210,20));
				threeTokenTwitter.setEditable(false);
				
				JTextField fourTokenTwitter = new JTextField();
				fourTokenTwitter.setText("B2WrSZcGM9LqmyrUbvmHKesS5S7hDYuvlHKKhmVlJU38N");
				fourTokenTwitter.setPreferredSize(new Dimension(210,20));
				fourTokenTwitter.setEditable(false);
				
				JTextField oneTokenFacebook = new JTextField();
				oneTokenFacebook.setText("");
				oneTokenFacebook.setPreferredSize(new Dimension(210,20));
				oneTokenFacebook.setEditable(false);
				
				JTextField twoTokenFacebook = new JTextField();
				twoTokenFacebook.setText("");
				twoTokenFacebook.setPreferredSize(new Dimension(210,20));
				twoTokenFacebook.setEditable(false);
								
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
				panelCenter.add(labInfo2TokenF);
				panelCenter.add(twoTokenFacebook);

				panels.get(0).add(btNreg);
				panels.get(0).add(btNcancel);
				
				// CONFIGURAÇÃO WINDOW FRAME
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
					public void actionPerformed(ActionEvent e) {
						if(userName.getText().isEmpty() || passWord.getText().toString().isEmpty() || email.getText().isEmpty() || emailPassWord.getText().toString().isEmpty() ) {
							JOptionPane.showMessageDialog(null, "There are fields to fill.");
						} else {
							// if() { // CASO USER NÃO EXISTE NO XML
							
							// } else {
								JOptionPane.showMessageDialog(null, "User já existente no sistema.");
							// }
						}
						windowRegister.setVisible(false);
						windowLogin.setVisible(true);
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
	
	public JFrame getWindowFrame() {
		return windowRegister;
	}

}
