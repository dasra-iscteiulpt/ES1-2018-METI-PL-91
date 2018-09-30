import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class WindowLogin {
	
	private JFrame windowLogin;
	private ArrayList<JPanel> panels;
		
	public WindowLogin(String title) {
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
	
	// M�TODOS AUXILIARES
	private void addPanels() {
		panels = new ArrayList<>();
		panels.add(new JPanel()); // 0 SOUTH
		panels.add(new JPanel()); // 1 WEST
		panels.add(new JPanel()); // 2 EAST
		panels.add(new JPanel()); // 3 NORTH
	}

	private void configWindow() {
		addPanels();
		
		// CONFIGURA��O JPANEL NA WINDOWLOGIN
		windowLogin.add(panels.get(0), BorderLayout.SOUTH);
		windowLogin.add(panels.get(1), BorderLayout.WEST);
		windowLogin.add(panels.get(2), BorderLayout.EAST);
		windowLogin.add(panels.get(3), BorderLayout.CENTER);

		// CONFIGURA��O DOS COMPONENTES
		JPanel panelCenter = new JPanel(new FlowLayout());
		windowLogin.add(panelCenter);
		
		JTextField userName = new JTextField("Username");
		JPasswordField passWord = new JPasswordField("Password");
		JLabel labEmail = new JLabel("@iscte-iul.pt");
		JLabel labPass = new JLabel("Password");
		JButton btNlog = new JButton("Login");
		
		panelCenter.add(userName);
		panelCenter.add(labEmail);
		panelCenter.add(passWord);
		panelCenter.add(labPass);
		panelCenter.add(btNlog);
		
		btNlog.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(userName.getText().isEmpty() || passWord.getText().toString().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Existem campos por preencher.");
				} else {
					// FALTA VERIFICAR CAMPOS ATRAV�S DE XML
					JOptionPane.showMessageDialog(null, "Login efetuado c/ sucesso.");
					windowLogin.setVisible(false);
					@SuppressWarnings("unused")
					WindowDBA w = new WindowDBA("Bom dia Academia!");
				}
			}
		});
		
		// CONFIGURA��O WINDOW FRAME
		windowLogin.setSize(190, 125);
		windowLogin.setLocationRelativeTo(null);
		windowLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowLogin.setResizable(false);
		windowLogin.validate();
		windowLogin.setVisible(true);
	}
}