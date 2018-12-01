package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Email.SendEmail;
import Facebook.WriteComment;
import Twitter.Retweet;
import XML.ReadXMLfile;

public class WindowMessage {

	// VARIABLES
	private JFrame windowFrame;
	private JLabel dateM;
	private JLabel fromM;
	private JLabel titleM;
	private JTextArea contentM;
	private JButton sendM;
	private String userDBA;
	private Font textFont;
	private ArrayList<JPanel> panels;

	public WindowMessage(String date, String from, String title, String content, String canal, String userDBA, boolean workOffline) {
		windowFrame = new JFrame(canal);
		dateM = new JLabel("Date: " + date);
		fromM = new JLabel("From: " + from);
		titleM = new JLabel("Subject: " + title);
		contentM = new JTextArea(content);
		textFont = new Font("Calibri", Font.BOLD, 12);
		this.userDBA = userDBA;
		if(canal.equals("E-Mail")) {
			sendM = new JButton("Reply");
		} else if(canal.equals("Twitter")) {
			sendM = new JButton("Retweet");
		} else if(canal.equals("Facebook")) {
			sendM = new JButton("Comment");
		}
		if(workOffline) {
			sendM.setEnabled(false);
		}
		configWindow();
		endConfigWindow();
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
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void configWindow() {
		addPanels();
		windowFrame.setSize(700, 200);

		contentM.setPreferredSize(new Dimension(200,20));
		contentM.setLineWrap(true);
		contentM.setWrapStyleWord(true);
		contentM.setEditable(false);

		//JPANEL CONFIGURATION IN WINDOWFRAME
		windowFrame.add(panels.get(0), BorderLayout.SOUTH);
		windowFrame.add(panels.get(1), BorderLayout.WEST);
		windowFrame.add(panels.get(2), BorderLayout.EAST);
		windowFrame.add(panels.get(3), BorderLayout.NORTH);

		//COMPONENTS CONFIGURATION
		panels.get(3).add(dateM);
		panels.get(3).add(fromM);
		panels.get(3).add(titleM);

		windowFrame.add(contentM, BorderLayout.CENTER);

		panels.get(0).add(sendM);
	}

	/** 
	 * Construction of the main window structure
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void endConfigWindow() {
		//WINDOW FRAME CONFIGURATION
		windowFrame.setLocationRelativeTo(null);
		windowFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		windowFrame.setResizable(false);
		windowFrame.validate();
		windowFrame.setVisible(true);

		sendM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(windowFrame.getTitle().equals("E-Mail")) {		
					SendEmail sMail = new SendEmail();

					// JOPTION PANE
					JTextField emailTo = new JTextField();
					emailTo.setFont(textFont);
					JTextField contentTo = new JTextField();
					contentTo.setFont(textFont);

					String fromMOnlyEmail = fromM.getText().split("<")[1];
					String emailToFinal = fromMOnlyEmail.substring(0, fromMOnlyEmail.length()-1).toLowerCase();

					emailTo.setText(emailToFinal);
					emailTo.setEditable(false);
					Object[] f = {"E-mail to: ", emailTo, "Message: ", contentTo};
					int okOrCancel = JOptionPane.showConfirmDialog(null, f, "This is a header", JOptionPane.OK_CANCEL_OPTION);

					String toEmail = emailTo.getText().toString();
					String toContent = contentTo.getText().toString();

					try {
						if(okOrCancel == JOptionPane.CANCEL_OPTION) {
							windowFrame.setVisible(false);
						} else {
							if(!toEmail.isEmpty() && !toContent.isEmpty()) {
								String fromEmail = ReadXMLfile.userData[0];
								String fromPWEmail = ReadXMLfile.userData[1];
								int resultMail = sMail.senderMail(toEmail, fromEmail, fromPWEmail, toContent, titleM.getText());
								if(resultMail == 1) {
									JOptionPane.showMessageDialog(null, "E-mail sent to: " + toEmail + " and the message is " + toContent);
									windowFrame.setVisible(false);
								} else {
									JOptionPane.showMessageDialog(null, "The email couldn't be sent. Please try again.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "There are empty fields.");
								sendM.doClick();
							}
						}
					} catch (Exception excep) {
						System.out.println("Button cancel." + excep);
					}
				} else if(windowFrame.getTitle().equals("Twitter")) {
					JTextField retweet = new JTextField();
					retweet.setFont(textFont);
					Object[] f = {"Retweet message:", retweet};
					int okOrCancel = JOptionPane.showConfirmDialog(null, f, "This is a header", JOptionPane.OK_CANCEL_OPTION);
					String retweetText = retweet.getText().toString();

					if(okOrCancel == JOptionPane.CANCEL_OPTION) {
						windowFrame.setVisible(false);
					} else {	
						if(!retweetText.isEmpty()) {
							Retweet rTwitter = new Retweet();
							int sucessOrInsucess = rTwitter.retweet("iscteiul", ReadXMLfile.userData[2], Long.valueOf(titleM.getText().split(" ")[1]), retweetText);	
							if(sucessOrInsucess == 0) {
								JOptionPane.showMessageDialog(null, "Retweet successful.");
								windowFrame.setVisible(false);
							} else {
								JOptionPane.showMessageDialog(null, "Error in retweet. Please try again.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "There are empty fields.");
							sendM.doClick();
						}
					}
				} else if(windowFrame.getTitle().equals("Facebook")) {
					JTextField comment = new JTextField();
					comment.setFont(textFont);
					Object[] f = {"Comment: ", comment};
					int okOrCancel = JOptionPane.showConfirmDialog(null, f, "This is a header", JOptionPane.OK_CANCEL_OPTION);
					String commentText = comment.getText().toString();

					if(okOrCancel == JOptionPane.CANCEL_OPTION) {
						windowFrame.setVisible(false);
					} else {	
						if(!commentText.isEmpty()) {
							WriteComment commentFace = new WriteComment();
							int sucessOrInsucess = commentFace.writeComment(titleM.getText().split(" ")[1], userDBA, comment.getText());
							if(sucessOrInsucess == 1) {
								JOptionPane.showMessageDialog(null, "Comment successful.");
								windowFrame.setVisible(false);
							} else {
								JOptionPane.showMessageDialog(null, "Error in writing a comment. Please try again.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "There are empty fields.");
							sendM.doClick();
						}
					}
				}
			}
		});
	}

	/** 
	 * Utility method to get the JButton
	 * @return A JButton
	 */
	public JButton sendM() {
		return sendM;
	}

	/** 
	 * Utility method to get the JFrame
	 * @return A JFrame
	 */
	public JFrame getFrame() {
		return windowFrame;
	}
}