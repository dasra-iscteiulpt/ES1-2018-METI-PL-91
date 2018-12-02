package GUI;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

public class WindowLoading extends Thread  {
		
	private JFrame window;
	private JPanel panel;
	
	/** 
	 * Constructor
	 * Construction of the loading window
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	public WindowLoading() {
		window = new JFrame();
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		
		JLabel label = new JLabel();
		ImageIcon ii = new ImageIcon(getClass().getResource("wedges-loading.gif"));
		label.setIcon(ii);
		
		panel.add(label);
		window.add(panel);
		
		window.setUndecorated(true);
		window.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		window.validate();
		window.setSize(250, 200);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setUndecorated(true);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
	}
	
	@Override
	public void run() {	
		while(!isInterrupted()) {
			//Active waiting...
		}
		this.window.setVisible(false);
	}
	
}