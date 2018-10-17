import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.mail.Message;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class WindowDBA {

	private JFrame windowFrame;
	private ArrayList<JPanel> panels;
	private ArrayList<Message> messagesMail;
	private JComboBox<String> chkDate;
	private DefaultTableModel modelTable;
	private int indicatorFilters = 0;

	public WindowDBA(String title) {
		windowFrame = new JFrame(title);
		startConfigWindow();
		endConfigWindow();
	}

	// GETTERS
	public JFrame getFrame() {
		return windowFrame;
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
	private void startConfigWindow() {
		addPanels();
		windowFrame.setSize(500, 500);
		// CONFIGURAÇÃO JPANEL NA WINDOWFRAME
		windowFrame.add(panels.get(0), BorderLayout.SOUTH);
		windowFrame.add(panels.get(1), BorderLayout.WEST);
		windowFrame.add(panels.get(2), BorderLayout.EAST);
		windowFrame.add(panels.get(3), BorderLayout.CENTER);

		// CONFIGURAÇÃO DO MENU
		JMenuBar generalMenu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Sort");
		JMenu aboutMenu = new JMenu("More");

		JMenuItem workOnline = new JMenuItem("Work online");
		workOnline.setEnabled(false);
		JMenuItem workOffline = new JMenuItem("Work offline");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem moreRecent = new JMenuItem("More recent");
		JMenuItem moreOlder = new JMenuItem("Older");
		JMenuItem about = new JMenuItem("About");
		JMenuItem help = new JMenuItem("Help");

		fileMenu.add(workOnline);
		fileMenu.add(workOffline);
		fileMenu.add(exit);
		editMenu.add(moreRecent);
		editMenu.add(moreOlder);
		aboutMenu.add(about);
		aboutMenu.add(help);

		generalMenu.add(fileMenu);
		generalMenu.add(editMenu);
		generalMenu.add(aboutMenu);
		windowFrame.add(generalMenu, BorderLayout.NORTH);

		// CONFIGURAÇÃO DOS RADIO BUTTON & COMBOBOX
		JRadioButton sortOne = new JRadioButton("More Recent");
		JRadioButton sortTwo = new JRadioButton("Older");

		chkDate = new JComboBox<String>();
		chkDate.addItem("All");
		chkDate.addItem("Last 24h");
		chkDate.addItem("Last 48h");
		chkDate.addItem("Last 7 days");
		chkDate.addItem("Last 30 days");

		ButtonGroup sortOptions = new ButtonGroup();
		sortOptions.add(sortOne);
		sortOptions.add(sortTwo);

		panels.get(3).add(sortOne);
		panels.get(3).add(sortTwo);
		panels.get(3).add(chkDate);

		// CONFIGURAÇÃO DA TABELA
		JTable tableContent = new JTable(0,6);
		panels.get(3).add(tableContent);

		modelTable = (DefaultTableModel) tableContent.getModel();
		modelTable.addRow(new String[]{"Id", "Date", "Channel", "From", "Subject", "Content"});

		getAndFillNewsOnTable(modelTable);
		//filterEmailsByDate(modelTable);
		buttonsMenuConfig(generalMenu, sortOne, sortTwo, tableContent);
	}

	/** 
	 * Construction of the main window structure
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void endConfigWindow() {
		// CONFIGURAÇÃO WINDOW FRAME
		windowFrame.setLocationRelativeTo(null);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setResizable(false);
		windowFrame.validate();
		windowFrame.setVisible(true);
	}

	/** 
	 * Setting the menu and various buttons
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param GM is the general bar, MR and MO are the radioButtons and TC is the table with news
	 */
	private void buttonsMenuConfig(JMenuBar gM, JRadioButton MR, JRadioButton MO, JTable TC) {
		// WORKONLINE BUTTON ACTION
		gM.getMenu(0).getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "New sync started");
				gM.getMenu(0).getItem(0).setEnabled(false);
				gM.getMenu(0).getItem(1).setEnabled(true);
			}
		});

		// WORKOFFLINE BUTTON ACTION
		gM.getMenu(0).getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Limited access to local content");
				gM.getMenu(0).getItem(0).setEnabled(true);
				gM.getMenu(0).getItem(1).setEnabled(false);
			}
		});

		// EXIT BUTTON ACTION
		gM.getMenu(0).getItem(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowFrame.setVisible(false);
			}
		});

		// MORE RECENT ACTION
		gM.getMenu(1).getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( !MR.isSelected() ) {
					MR.setSelected(true);
				}
			}
		});

		// MORE OLD ACTION
		gM.getMenu(1).getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( !MO.isSelected() ) {
					MO.setSelected(true);
									}
			}
		});

		// ABOUT BUTTON ACTION
		gM.getMenu(2).getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lineSep = System.lineSeparator();
				String infoUC = "Software Engineering I - Teacher Vitor Basto Fernandes";
				String numberGroup = "Group 91 - Constituted by:" + lineSep;
				String infoGroup = "68092 - Diana Salvador" + lineSep + "69980 - Diogo Reis" + lineSep + "65799 - Ricardo Ferreira" + lineSep + "73422 - Ivo Carvalho";
				String toolsProj = "Tools used: Git, Trello.";
				JOptionPane.showMessageDialog(null, infoUC + lineSep + numberGroup + infoGroup + lineSep + toolsProj);
			}
		});

		// HELP BUTTON ACTION
		gM.getMenu(2).getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lineSep = System.lineSeparator();
				String infoHelp = "Maintenance page" + lineSep + "You will soon have support content (SWING, Java DOCS, etc).";
				JOptionPane.showMessageDialog(null, infoHelp);
			}
		});

		// CLICK TABLE ACTION
		TC.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					if( !(TC.getSelectedRow() == 0) ) {
						String dateM = TC.getModel().getValueAt(TC.getSelectedRow(),0).toString();
						String canalM = TC.getModel().getValueAt(TC.getSelectedRow(),1).toString();
						String fromM = TC.getModel().getValueAt(TC.getSelectedRow(),2).toString();
						String titleM = TC.getModel().getValueAt(TC.getSelectedRow(),3).toString();
						String contentM = TC.getModel().getValueAt(TC.getSelectedRow(),4).toString();
						@SuppressWarnings("unused")
						WindowMessage windMess = new WindowMessage(dateM, canalM, fromM, titleM, contentM);
					}
				}
			}
		});
	}

	/** 
	 * Main method for collecting news
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void getAndFillNewsOnTable(DefaultTableModel modelTable) {

		ReadEmails rMails = new ReadEmails();
		ArrayList<Message> messagesMail = rMails.readMessages("imap.gmail.com", "imaps3", "diana.es.pl.91@gmail.com", "engenhariasoftware");

		System.out.println(messagesMail.size());
		int count = 1;
		try {

			for (Message m: messagesMail) {
				String dateM = m.getReceivedDate().toString();
				String channelM = "EM";
				String fromM = m.getFrom().toString();
				String subjectM = m.getSubject();
				String contentM = m.getContent().toString();
				modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
				count++;
			}
		} catch(Exception e) {
			System.out.print("Erro a ler e-mails: " + e.toString());
		}

	}

	private void filterEmailsByDate(DefaultTableModel modelTable) {
		ReadEmails rMails = new ReadEmails();
		ArrayList<Message> messagesMail = rMails.readMessages("imap.gmail.com", "imaps3", "diana.es.pl.91@gmail.com", "engenhariasoftware");
		Calendar c = Calendar.getInstance();
		System.out.println(messagesMail.size());
		int count = 1;
		try {

			for (Message m: messagesMail) {
				Object selection = chkDate.getSelectedItem();

				if(selection.toString().equals("Last 24h"))
				{
					c.setTime(new Date());
					c.add(Calendar.DATE, -1);
					Date d = c.getTime();
					if (m.getReceivedDate().after(d)) {
						String dateM = m.getReceivedDate().toString();
						String channelM = "EM";
						String fromM = m.getFrom().toString();
						String subjectM = m.getSubject();
						String contentM = m.getContent().toString();
						//colocar emails na tabela
						System.out.println("Emails from last 24h");
					}
				}
				if(selection.toString().equals("Last 48h"))
				{
					c.setTime(new Date());
					c.add(Calendar.DATE, -2);
					Date d = c.getTime();
					if (m.getReceivedDate().after(d)) {
						String dateM = m.getReceivedDate().toString();
						String channelM = "EM";
						String fromM = m.getFrom().toString();
						String subjectM = m.getSubject();
						String contentM = m.getContent().toString();
						//colocar emails na tabela
						System.out.println("Emails from last 48h");
					}
				}
				if(selection.toString().equals("Last week"))
				{
					c.setTime(new Date());
					c.add(Calendar.DATE, -7);
					Date d = c.getTime();
					if (m.getReceivedDate().after(d)) {
						String dateM = m.getReceivedDate().toString();
						String channelM = "EM";
						String fromM = m.getFrom().toString();
						String subjectM = m.getSubject();
						String contentM = m.getContent().toString();
						//colocar emails na tabela
						System.out.println("Emails from last week");
					}
				}
				if(selection.toString().equals("Last month"))
				{
					c.setTime(new Date());
					c.add(Calendar.DATE, -30);
					Date d = c.getTime();
					if (m.getReceivedDate().after(d)) {
						String dateM = m.getReceivedDate().toString();
						String channelM = "EM";
						String fromM = m.getFrom().toString();
						String subjectM = m.getSubject();
						String contentM = m.getContent().toString();
						//colocar emails na tabela
						System.out.println("Emails from last month");
					}
				}
				count++;
			}
		} catch(Exception e) {
			System.out.print("Erro a filtrar por hora: " + e.toString());
		}
	}
	
	private void sortEmails(DefaultTableModel modelTable) {

		ReadEmails rMails = new ReadEmails();
		ArrayList<Message> messagesMail = rMails.readMessages("imap.gmail.com", "imaps3", "diana.es.pl.91@gmail.com", "engenhariasoftware");

		System.out.println(messagesMail.size());
		int count = 1;
		try {

			for (Message m: messagesMail) {
				String dateM = m.getReceivedDate().toString();
				String channelM = "EM";
				String fromM = m.getFrom().toString();
				String subjectM = m.getSubject();
				String contentM = m.getContent().toString();
				modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
				count++;
			}
		} catch(Exception e) {
			System.out.print("Erro a ler e-mails: " + e.toString());
		}

	}

}