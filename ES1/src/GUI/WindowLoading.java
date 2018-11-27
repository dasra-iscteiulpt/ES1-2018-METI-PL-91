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
	
	public WindowLoading() {
				
		window = new JFrame();
		panel  = new JPanel();
		panel.setBackground(Color.WHITE);
		
		JLabel label = new JLabel();
		ImageIcon ii = new ImageIcon(getClass().getResource("loading_img.gif"));
		label.setIcon(ii);
		
		panel.add(label);
		window.add(panel);
		
		window.setUndecorated(true);
		window.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		window.validate();
		window.setSize(441, 291);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	
	/*
	public static void main(String[] args) {
		new WindowLoading();
	}
	*/
	
	
}
