
import java.awt.BorderLayout;
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

	private JFrame windowLogin;
	private ArrayList<JPanel> panels;
	ReadXMLfile r = new ReadXMLfile();
	private int tentativesLog;

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

	// MÉTODOS AUXILIARES
	private void addPanels() {
		panels = new ArrayList<>();
		panels.add(new JPanel()); // 0 SOUTH
		panels.add(new JPanel()); // 1 WEST
		panels.add(new JPanel()); // 2 EAST
		panels.add(new JPanel()); // 3 NORTH
	}

	private void configWindow() {
		addPanels();

		// CONFIGURAÇÃO JPANEL NA WINDOWLOGIN
		windowLogin.add(panels.get(0), BorderLayout.SOUTH);
		windowLogin.add(panels.get(1), BorderLayout.WEST);
		windowLogin.add(panels.get(2), BorderLayout.EAST);
		windowLogin.add(panels.get(3), BorderLayout.CENTER);

		// CONFIGURAÇÃO DOS COMPONENTES
		JPanel panelCenter = new JPanel(new FlowLayout());
		windowLogin.add(panelCenter);

		JTextField userName = new JTextField("                     ");
		JPasswordField passWord = new JPasswordField("*************");
		JLabel labEmail = new JLabel("@iscte-iul.pt");
		JLabel labPass = new JLabel("Password");
		JButton btNlog = new JButton("Login");

		panelCenter.add(userName);
		panelCenter.add(labEmail);
		panelCenter.add(passWord);
		panelCenter.add(labPass);
		panelCenter.add(btNlog);

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

		// CLICK ON BUTTON LOGIN
		btNlog.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(userName.getText().isEmpty() || passWord.getText().toString().isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are fields to fill.");
				} else {
					// CASO USER E PASSWORD CONSTAM NO FICHEIRO XML E SERVIÇO É "BDA", LOGIN É EFETUADO C/ SUCESSO
					if(r.validateUserBDA(userName.getText(),passWord.getText().toString()) == true) {
						JOptionPane.showMessageDialog(null, "Login successfully completed.");
						windowLogin.setVisible(false);
						@SuppressWarnings("unused")
						WindowDBA w = new WindowDBA("Good Morning Academy!");

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

		// CONFIGURAÇÃO WINDOW FRAME
		windowLogin.setSize(250, 120);
		windowLogin.setLocationRelativeTo(null);
		windowLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowLogin.setResizable(false);
		windowLogin.validate();
		windowLogin.setVisible(true);
	}
}