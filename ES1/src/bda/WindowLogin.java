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

public class WindowLogin {

	// VARIABLES
	private JFrame windowLogin;
	private ArrayList<JPanel> panels;
	ReadXMLfile r = new ReadXMLfile();
	private int tentativesLog;
	
	// CONSTRUCTOR
	public WindowLogin(String title) {
		tentativesLog = 0;
		windowLogin = new JFrame(title);
		configWindow();
	}

	// GETTERS
	public JFrame getFrame() {
		return windowLogin;
	}

	public ArrayList<JPanel> getPanels() {
		return panels;
	}

	//AUXILIARY METHODS
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
		// JPANEL CONFIGURATION IN THE WINDOWLOGIN
		windowLogin.add(panels.get(0), BorderLayout.SOUTH);
		windowLogin.add(panels.get(1), BorderLayout.WEST);
		windowLogin.add(panels.get(2), BorderLayout.EAST);
		windowLogin.add(panels.get(3), BorderLayout.NORTH);

		// COMPONENTS CONFIGURATION
		JPanel panelCenter = new JPanel(new FlowLayout());
		windowLogin.add(panelCenter);
		
		JLabel labInfoLog = new JLabel("Do you have an account? Please login.");
		JLabel labInfoRegister = new JLabel("Not registered? Please sign up.");
		
		JLabel labInfoUser = new JLabel("Username: ");
		JLabel labInfoPw = new JLabel("Password: ");
		
		JTextField userName = new JTextField();
		userName.setPreferredSize(new Dimension(130,20));
		JPasswordField passWord = new JPasswordField();
		passWord.setPreferredSize(new Dimension(204,20));
		JLabel labEmail = new JLabel("@iscte-iul.pt");
		JButton btNlog = new JButton("Login");
		JButton btNreg = new JButton("Register");

		panels.get(3).add(labInfoLog);
		panelCenter.add(labInfoUser);
		panelCenter.add(userName);
		panelCenter.add(labEmail);

		panelCenter.add(labInfoPw);
		panelCenter.add(passWord);
		
		panelCenter.add(labInfoRegister);

		panels.get(0).add(btNlog);
		panels.get(0).add(btNreg);

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
				WindowRegister windReg = new WindowRegister(windowLogin);
				windowLogin.setVisible(false);
				windReg.getWindowFrame().setVisible(true);
				
			}
		});
		
		// CLICK ON BUTTON LOGIN
		btNlog.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(userName.getText().isEmpty() || passWord.getText().toString().isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are empty fields.");
				} else {
					// CASO USER E PASSWORD CONSTEM NO FICHEIRO XML E SERVI�O � "BDA", LOGIN � EFETUADO C/ SUCESSO
					if(r.validateUserBDA(userName.getText().trim(),passWord.getText().toString().trim()) == true) {
					JOptionPane.showMessageDialog(null, "Login successfully completed.");
					@SuppressWarnings("unused")
					WindowDBA w = new WindowDBA("Good Morning Academy!");
					windowLogin.setVisible(false);
					} else {
						tentativesLog++;
						if(tentativesLog == 3) {
							windowLogin.setVisible(false);
							JOptionPane.showMessageDialog(null, "Login attempts exceeded.");
						} else {
							JOptionPane.showMessageDialog(null, "Incorrect login. Please review the access data.");
						}
					}
				}
			}
		});

		// CONFIGURA��O WINDOW FRAME
		windowLogin.setSize(330, 180);
		windowLogin.setLocationRelativeTo(null);
		windowLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowLogin.setResizable(false);
		windowLogin.validate();
		windowLogin.setVisible(true);
	}
}