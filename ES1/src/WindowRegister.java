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
	private int tentativesLog;
	
	// CONSTRUTOR
	public WindowRegister(JFrame windowLogin) {
		tentativesLog = 0;
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

				panels.get(0).add(btNreg);
				panels.get(0).add(btNcancel);
				
				// CONFIGURAÇÃO WINDOW FRAME
				windowRegister.setSize(320, 200);
				windowRegister.setLocationRelativeTo(null);
				windowRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
