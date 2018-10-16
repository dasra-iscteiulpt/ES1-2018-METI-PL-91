
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WindowMessage {

		private JFrame windowFrame;
		private JLabel dateM;
		private JLabel canalM;
		private JLabel fromM;
		private JLabel titleM;
		private JTextField contentM;
		private JButton sendM;
		private ArrayList<JPanel> panels;

		public WindowMessage(String date, String canal, String from, String title, String content) {
			windowFrame = new JFrame();
			dateM = new JLabel("Date: " + date);
			canalM = new JLabel("Channel: " + canal);
			fromM = new JLabel("From: " + from);
			titleM = new JLabel("Subject: " + title);
			contentM = new JTextField(content);
			sendM = new JButton("Reply");
			configWindow();
			endConfigWindow();
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
			windowFrame.setSize(400, 300);
			
			// CONFIGURA��O JPANEL NA WINDOWFRAME
			windowFrame.add(panels.get(0), BorderLayout.SOUTH);
			windowFrame.add(panels.get(1), BorderLayout.WEST);
			windowFrame.add(panels.get(2), BorderLayout.EAST);
			windowFrame.add(panels.get(3), BorderLayout.NORTH);
			
			// CONFIGURA��O DOS COMPONENTES
			panels.get(3).add(dateM);
			panels.get(3).add(canalM);
			panels.get(3).add(fromM);
			panels.get(3).add(titleM);
			windowFrame.add(contentM, BorderLayout.CENTER);
			panels.get(0).add(sendM);
		}
		
		private void endConfigWindow() {
			// CONFIGURA��O WINDOW FRAME
			windowFrame.setLocationRelativeTo(null);
			windowFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			windowFrame.setResizable(false);
			windowFrame.validate();
			windowFrame.setVisible(true);
			
			
			sendM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					// JOPTION PANE
					JTextField emailTo = new JTextField();
					JTextField contentTo = new JTextField();
					Object[] f = {"E-mail to: ", emailTo, "Comments: ", contentTo};
					int okOrCancel = JOptionPane.showConfirmDialog(null, f, "This is a header", JOptionPane.OK_CANCEL_OPTION);

					String toEmail = emailTo.getText().toString();
					String toContent = contentTo.getText().toString();

					try {
						if(okOrCancel == JOptionPane.CANCEL_OPTION) {
							windowFrame.setVisible(false);
						} else {
							if(!toEmail.isEmpty() && !toContent.isEmpty()) {
								JOptionPane.showMessageDialog(null, "E-mail sent to: " + toEmail + " and content is " + toContent);
							} else {
								JOptionPane.showMessageDialog(null, "Campos por preencher");
								sendM.doClick();
							}
						}
					} catch (Exception excep) {
						System.out.println("Button cancel." + excep);
					}
				}
		});
		}
		
	}