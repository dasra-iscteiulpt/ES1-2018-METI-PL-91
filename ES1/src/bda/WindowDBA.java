package bda;

import javax.swing.JFrame;
import javax.swing.JMenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/** 
 * "Bom dia Academia" content window called after a successful login
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

public class WindowDBA {

	// VARIABLES
	private JFrame windowFrame;
	private ArrayList<JPanel> panels;
	private static ArrayList<GenericMessage> genericMessages;
	private ArrayList<GenericMessage> messagesMail;
	private ArrayList<GenericMessage> messagesTwitter;
	private ArrayList<GenericMessage> messagesFacebook;
	private DefaultTableModel modelTable;
	private int indicatorFilters = 0;
	private boolean workOffline;
	private String userDBA;
	private JRadioButton sortOne; // NEWEST
	private JRadioButton sortTwo; // OLDEST
	private Font textFont;

	// CONSTRUCTOR
	public WindowDBA(boolean workOffline, String userDBA) {
		windowFrame = new JFrame("Good Morning Academy!");
		genericMessages = new ArrayList<GenericMessage>();
		this.workOffline = workOffline;
		this.userDBA = userDBA;
		textFont = new Font("Calibri", Font.BOLD, 12);
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
	private void startConfigWindow() {
		addPanels();
		windowFrame.setSize(500, 530);
		// JPANEL CONFIGURATION IN THE WINDOW FRAME
		windowFrame.add(panels.get(0), BorderLayout.SOUTH);
		windowFrame.add(panels.get(1), BorderLayout.WEST);
		windowFrame.add(panels.get(2), BorderLayout.EAST);
		windowFrame.add(panels.get(3), BorderLayout.CENTER);

		// MENU CONFIGURATION
		JMenuBar generalMenu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu filterMenu = new JMenu("Filters");
		JMenu servicesMenu = new JMenu("Services");
		JMenu aboutMenu = new JMenu("More");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem editFilters = new JMenuItem("Edit");
		JCheckBoxMenuItem chkboxMail = new JCheckBoxMenuItem("E-Mail");
		JCheckBoxMenuItem chkboxTwitter = new JCheckBoxMenuItem("Twitter");
		JCheckBoxMenuItem chkboxFacebook = new JCheckBoxMenuItem("Facebook");
		JMenuItem about = new JMenuItem("About");
		JMenuItem help = new JMenuItem("Help");

		fileMenu.add(exit);
		filterMenu.add(editFilters);
		servicesMenu.add(chkboxMail);
		chkboxMail.setSelected(true);
		servicesMenu.add(chkboxTwitter);
		chkboxTwitter.setSelected(true);
		servicesMenu.add(chkboxFacebook);
		chkboxFacebook.setSelected(true);
		aboutMenu.add(about);
		aboutMenu.add(help);
		generalMenu.add(fileMenu);
		generalMenu.add(filterMenu);
		generalMenu.add(servicesMenu);
		generalMenu.add(aboutMenu);
		windowFrame.add(generalMenu, BorderLayout.NORTH);

		// RADIO BUTTON, COMBOBOX & CHECKBOX CONFIGURATION		
		sortOne = new JRadioButton("Newest");
		sortTwo = new JRadioButton("Oldest");

		JComboBox<String> chkDate = new JComboBox<String>();

		chkDate.addItem("All");
		chkDate.addItem("Last 24 hours");
		chkDate.addItem("Last 48 hors");
		chkDate.addItem("Last 7 days");
		chkDate.addItem("Last 30 days");

		ButtonGroup sortOptions = new ButtonGroup();
		sortOptions.add(sortOne);
		sortOptions.add(sortTwo);

		panels.get(3).add(sortOne);
		panels.get(3).add(sortTwo);
		panels.get(3).add(chkDate);

		// TABLE CONFIGURATION
		JTable tableContent = new JTable(0,6);
	
		//tableContent.setAutoscrolls(true);
		tableContent.setFont(textFont);
		panels.get(3).add(tableContent);
		JScrollPane scroll = new JScrollPane(tableContent);
		panels.get(3).add(scroll);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        
		modelTable = (DefaultTableModel) tableContent.getModel();
		modelTable.addRow(new String[]{"Id", "Date", "Channel", "From", "Subject", "Content"});

		if(workOffline == false) { // CHECKBOX NOT SELECTED
			try {
				getAndFillNewsOnTable(generalMenu, modelTable);
			} catch (Exception e) {
				getNewsWorkingOffline();
			}
		} else { // CHECKBOX SELECTED
			getNewsWorkingOffline();
		}
		buttonsMenuConfig(generalMenu, sortOne, sortTwo, chkDate, modelTable, tableContent);
	}

	/** 
	 * Construction of the main window structure
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void endConfigWindow() {
		// WINDOW FRAME CONFIGURATION
		windowFrame.setLocationRelativeTo(null);
		windowFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		windowFrame.setResizable(false);
		windowFrame.validate();
		windowFrame.setVisible(true);
	}

	/** 
	 * Setting the menu and different buttons
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param gM, is the general bars
	 * @param MR, is the radio button
	 * @param MO, is the radio button
	 * @param TC, is the table with news
	 * @param MT, is the JTABLE that contains the messages
	 * @param CB, is the combo box with filters date
	 */
	private void buttonsMenuConfig(JMenuBar gM, JRadioButton MR, JRadioButton MO, JComboBox<String> CB, DefaultTableModel MT, JTable TC) {

		// EXIT BUTTON ACTION
		gM.getMenu(0).getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowFrame.setVisible(false);
			}
		});

		// FILTERS
		gM.getMenu(1).getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				WindowFilter windFilter = new WindowFilter(windowFrame);
			}
		});

		// SERVICE MAIL BUTTON ACTION
		gM.getMenu(2).getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeRows(modelTable);
				fillOnTable(gM);
			}
		});

		// SERVICE TWITTER BUTTON ACTION
		gM.getMenu(2).getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeRows(modelTable);
				fillOnTable(gM);
			}
		});

		// SERVICE FACEBOOK BUTTON ACTION
		gM.getMenu(2).getItem(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeRows(modelTable);
				fillOnTable(gM);			
			}
		});

		// ABOUT BUTTON ACTION
		gM.getMenu(3).getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lineSep = System.lineSeparator();
				String infoUC = "Software Engineering I - Teacher Vitor Basto Fernandes";
				String numberGroup = "Group 91 members:" + lineSep;
				String infoGroup = "68092 - Diana Salvador" + lineSep + "69980 - Diogo Reis" + lineSep + "65799 - Ricardo Ferreira" + lineSep + "73422 - Ivo Carvalho";
				String toolsProj = "Tools: GitHub, Trello and Eclipse.";
				JOptionPane.showMessageDialog(null, infoUC + lineSep + numberGroup + infoGroup + lineSep + toolsProj);
			}
		});

		// HELP BUTTON ACTION
		gM.getMenu(3).getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lineSep = System.lineSeparator();
				String infoHelp = "Help page:" + lineSep + "Please check the JavaDoc documentation for further details about the software.";
				JOptionPane.showMessageDialog(null, infoHelp);
			}
		});

		// CLICK TABLE ACTION
		TC.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					if( !(TC.getSelectedRow() == 0)) {
						String dateM = TC.getModel().getValueAt(TC.getSelectedRow(),1).toString();
						String canalM = TC.getModel().getValueAt(TC.getSelectedRow(),2).toString();
						String fromM = TC.getModel().getValueAt(TC.getSelectedRow(),3).toString();
						String titleM = TC.getModel().getValueAt(TC.getSelectedRow(),4).toString();
						String contentM = TC.getModel().getValueAt(TC.getSelectedRow(),5).toString();
						@SuppressWarnings("unused")
						WindowMessage windMess = new WindowMessage(dateM, fromM, titleM, contentM, canalM, userDBA);
					}
				}
			}
		});

		// RADIO BUTTON OLDEST
		MR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MR.setSelected(true);
				sortByNewest(modelTable, gM);
			}
		});

		// RADIO BUTTON NEWEST
		MO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MO.setSelected(true);
				sortByOldest(modelTable, gM);
			}
		});

		// COMBO BOX FILTER DATE
		CB.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				switch (CB.getSelectedIndex()) {
				case 0:
					System.out.println("All");
					filterEmailsAll(MT);
					break;
				case 1:
					System.out.println("Last 24H");
					filterEmailsLast24Hours(MT);
					break;
				case 2:
					System.out.println("Last 48H");
					filterEmailsLast48Hours(MT);
					break;
				case 3:
					System.out.println("Last week");
					filterEmailsLastWeek(MT);
					break;
				default:
					System.out.println("Last month");
					filterEmailsLastMonth(MT);
					break;             
				} 
			}
		});
	}

	/** 
	 * Main method for collecting messages
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable, is the JTABLE that contains the messages
	 */
	private void getAndFillNewsOnTable(JMenuBar generalMenu, DefaultTableModel modelTable) {
		ReadEmails rMails = new ReadEmails();
		ReadTweets rTweets = new ReadTweets();
		ReadPosts rPosts = new ReadPosts();

		messagesMail = GenericMessage.receiveMailReturnMessage(rMails.readMessages("imap.gmail.com", "imaps3", ReadXMLfile.userData[0], ReadXMLfile.userData[1]));
		messagesTwitter = GenericMessage.receiveTweetsReturnMessage(rTweets.readTweets(ReadXMLfile.userData[2]));
		messagesFacebook = GenericMessage.receivePostsReturnMessage(rPosts.readPosts(ReadXMLfile.userData[2]));

		fillOnTable(generalMenu);
		indicatorFilters = genericMessages.size();
	}

	/** 
	 * Method to filter e-mails from the last 24 hours
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 */
	public void filterEmailsLast24Hours(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		indicatorFilters = 0;
		int count = 1;
		try {
			for (GenericMessage m: genericMessages) {
				c.setTime(new Date());
				c.add(Calendar.DATE, -1);
				Date d = c.getTime();
				if (df.parse(m.getDateM()).after(d)) {
					String dateM = m.getDateM();
					String channelM = m.getCanalM();
					String fromM = m.getFromM();
					String subjectM = m.getTitleM();
					String contentM = m.getContentM();
					modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
					count++;
					indicatorFilters++;
				}
			}
			System.out.println("Emails from last 24h");
		} catch (Exception e) {
			System.out.print("Error in filtering by hour: " + e.toString());
		}
		sortOne.setEnabled(false);
		sortTwo.setEnabled(false);
	}

	/** 
	 * Method to filter e-mails from the last 48 hours
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 */
	public void filterEmailsLast48Hours(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();	
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		indicatorFilters = 0;
		int count = 1;

		try {
			System.out.println(genericMessages.size());
			for (GenericMessage m: genericMessages) {
				System.out.println("INSIDE FOR");
				c.setTime(new Date());
				c.add(Calendar.DATE, -2);
				Date d = c.getTime();
				if (df.parse(m.getDateM()).after(d)) {
					String dateM = m.getDateM();
					String channelM = m.getCanalM();
					String fromM = m.getFromM();
					String subjectM = m.getTitleM();
					String contentM = m.getContentM();
					modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
					count++;
					indicatorFilters++;
				}
			}
			System.out.println("Emails from last 48h");
		} catch (Exception e) {
			System.out.print("Error in filtering by hour: " + e.toString());
		}
		sortOne.setEnabled(false);
		sortTwo.setEnabled(false);
	}

	/** 
	 * Method to filter e-mails from the last Week
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 */
	public void filterEmailsLastWeek(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		indicatorFilters = 0;
		int count = 1;
		try {
			for (GenericMessage m: genericMessages) {
				c.setTime(new Date());
				c.add(Calendar.DATE, -7);
				Date d = c.getTime();
				System.out.println(df.parse(m.getDateM()));
				if (df.parse(m.getDateM()).after(d)) {
					String dateM = m.getDateM();
					String channelM = m.getCanalM();
					String fromM = m.getFromM();
					String subjectM = m.getTitleM();
					String contentM = m.getContentM();
					modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
					count++;
					indicatorFilters++;
				}
			}
			System.out.println("Emails from last week");

		} catch (Exception e) {
			System.out.print("Error in filtering by hour: " + e.toString());
		}
		sortOne.setEnabled(false);
		sortTwo.setEnabled(false);
	}

	/** 
	 * Method to filter e-mails from the last Month
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 */
	public void filterEmailsLastMonth(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		indicatorFilters = 0;
		int count = 1;

		try {
			for (GenericMessage m: genericMessages) {
				c.setTime(new Date());
				c.add(Calendar.DATE, -30);
				Date d = c.getTime();
				if (df.parse(m.getDateM()).after(d)) {
					String dateM = m.getDateM();
					String channelM = m.getCanalM();
					String fromM = m.getFromM();
					String subjectM = m.getTitleM();
					String contentM = m.getContentM();
					modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
					count++;
					indicatorFilters++;
				}
			}
			System.out.println("Emails from last month");
		} catch (Exception e) {

			System.out.print("Error in filtering by hour: " + e.toString());
		}
		sortOne.setEnabled(false);
		sortTwo.setEnabled(false);
	}

	/** 
	 * Method to show all messages
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 */
	public void filterEmailsAll(DefaultTableModel modelTable) {
		removeRows(modelTable);
		int count = 1;

		indicatorFilters = 0;

		try {
			for (GenericMessage m: genericMessages) {
				String dateM = m.getDateM();
				String channelM = m.getCanalM();
				String fromM = m.getFromM();
				String subjectM = m.getTitleM();
				String contentM = m.getContentM();
				modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
				count++;
				indicatorFilters++;
			}
			System.out.println("All Emails");
		} catch (Exception e) {
			System.out.print("Error in filtering by hour: " + e.toString());
		}
		sortOne.setEnabled(true);
		sortTwo.setEnabled(true);
	}

	/** 
	 * Sort messages, from the oldest to the newest
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param gM
	 */
	public void sortByOldest(DefaultTableModel modelTable, JMenuBar gM) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<Date> dateArray = new ArrayList<Date>();
		int count = 1;
		Date date = new Date();

		System.out.println("Hello + " + genericMessages.size());
		try {
			for(GenericMessage m: genericMessages) {
				date = df.parse(m.getDateM());
				if(!dateArray.contains(date)) {
					dateArray.add(date);
				}
			}
			Collections.sort(dateArray);
			System.out.println("Size" + dateArray.size());
			removeRows(modelTable);
			indicatorFilters = 0;

			for(Date d: dateArray) {
				for(GenericMessage m: genericMessages) {
					if(df.parse(m.getDateM()).equals(d)) {
						String dateM = m.getDateM();
						String channelM = m.getCanalM();
						String fromM = m.getFromM();
						String subjectM = m.getTitleM();
						String contentM = m.getContentM();
						modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
						indicatorFilters++;
						count++;
					}
				}
			}
			System.out.println("Sort by oldest");
		} catch (Exception e) {
			System.out.print("Error in sorting by oldest emails: " + e.toString());
		}
	}

	/** 
	 * Sort messages, from the newest to the oldest
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param gM
	 */
	public void sortByNewest(DefaultTableModel modelTable, JMenuBar gM) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<Date> dateArray = new ArrayList<Date>();

		int count = 1;
		Date date = new Date();

		try {
			for(GenericMessage m: genericMessages) {
				date = df.parse(m.getDateM());
				if(!dateArray.contains(date)) {
					dateArray.add(date);
				}
			}
			Collections.sort(dateArray, Collections.reverseOrder());
			removeRows(modelTable);
			indicatorFilters = 0;

			for(Date d: dateArray) {
				for(GenericMessage m: genericMessages) {
					if(df.parse(m.getDateM()).equals(d)) {
						String dateM = m.getDateM();
						String channelM = m.getCanalM();
						String fromM = m.getFromM();
						String subjectM = m.getTitleM();
						String contentM = m.getContentM();
						modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
						count++;
						indicatorFilters++;
					}

				}
			}
			System.out.println("Sort by newest");
		} catch (Exception e) {
			System.out.print("Error in sorting by newest emails: " + e.toString());
		}
	}

	//Method to fill the messages on the table
	private void fillOnTable(JMenuBar gM) {
		int count = 1;
	
		boolean servMail = gM.getMenu(2).getItem(0).isSelected();
		boolean servTwitter = gM.getMenu(2).getItem(1).isSelected();
		boolean servFacebook = gM.getMenu(2).getItem(2).isSelected();
		
		indicatorFilters = 0;
		try {
			if(servMail) {
				System.out.print("E-Mail checked.");
				for (GenericMessage genM: messagesMail) {
					String dateM = genM.getDateM();
					String channelM = genM.getCanalM();
					String fromM = genM.getFromM();
					String subjectM = genM.getTitleM();
					String contentM = genM.getContentM();

					genericMessages.add(genM);
					modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
					count++;
					indicatorFilters++;
				}
			} 
			if(servTwitter) {
				System.out.print("Twitter checked.");
				for (GenericMessage genM: messagesTwitter) {
					String dateM = genM.getDateM();
					String channelM = genM.getCanalM();
					String fromM = genM.getFromM();
					String subjectM = genM.getTitleM();
					String contentM = genM.getContentM();
					
					genericMessages.add(genM);
					modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
					count++;
					indicatorFilters++;
				}
			}
			if(servFacebook) {
				System.out.print("Facebook checked.");
				for (GenericMessage genM: messagesFacebook) {
					String dateM = genM.getDateM();
					String channelM = genM.getCanalM();
					String fromM = genM.getFromM();
					String subjectM = genM.getTitleM();
					String contentM = genM.getContentM();
	
					genericMessages.add(genM);
					modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
					count++;
					indicatorFilters++;
				}
			}
			WriteXMLfile.writeMessage("dasra");
			ReadXMLfile.readMessagesXMLfile("dasra");
		} catch(Exception e) {
			System.out.print("Error in reading emails: " + e.toString());
		}
		
		if(servMail && servTwitter && servFacebook) {
			sortOne.setEnabled(true);
			sortTwo.setEnabled(true);
		} else {
			sortOne.setEnabled(false);
			sortTwo.setEnabled(false);
		}
	}

	/** 
	 * Auxiliary method to control date filters
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 */
	private void removeRows(DefaultTableModel modelTable) {
		int linhasNaTabela = indicatorFilters;
		for(int i = linhasNaTabela; i > 0; i--) {
			modelTable.removeRow(i);
			System.out.println("Row " + i + " deleted.");
		}
	}

	private void getNewsWorkingOffline() {

	}

	public ArrayList<GenericMessage> getGMemail() {
		return messagesMail;
	}

	public ArrayList<GenericMessage> getGMtweets() {
		return messagesTwitter;
	}

	public ArrayList<GenericMessage> getGMposts() {
		return messagesFacebook;
	}

	public static ArrayList<GenericMessage> getGM() {
		return genericMessages;
	}

	public DefaultTableModel getModelTable() {
		return modelTable;
	}
}