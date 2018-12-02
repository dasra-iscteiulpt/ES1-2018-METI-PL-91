package GUI;
import javax.swing.JFrame;
import javax.swing.JMenu;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import Email.ReadEmails;
import Facebook.ReadPosts;
import Others.Filters;
import Others.GenericMessage;
import Twitter.ReadTweets;
import XML.ReadXMLfile;
import XML.WriteXMLfile;
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

public class WindowDBA extends Thread {

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
	private ReadXMLfile rXML;
	private Filters filtersForData;
	private WindowLoading loading;

	/** 
	 * Constructor
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param workOffline, true if user is workingOffline and false otherwise
	 * @param userDBA, the identification of the user
	 * @param loading, a WindowLoading
	 */
	public WindowDBA(boolean workOffline, String userDBA, WindowLoading loading) {
		this.loading = loading;
		windowFrame = new JFrame("Good Morning Academy!");
		genericMessages = new ArrayList<GenericMessage>();
		filtersForData = new Filters();
		this.workOffline = workOffline;
		this.userDBA = userDBA;
		rXML = new ReadXMLfile();
		textFont = new Font("Calibri", Font.BOLD, 12);
	}

	@Override
	public void run() {
		startConfigWindow();
		endConfigWindow();
	}

	/** 
	 * Utility method to get the window frame
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @return windowFrame, a JFrame
	 */
	public JFrame getFrame() {
		return windowFrame;
	}

	/** 
	 * Utility method to get the JPanel
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @return panels, an arrayList of JPanel
	 */
	public ArrayList<JPanel> getPanels() {
		return panels;
	}

	/** 
	 * Utility method to add JPanels
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
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
		servicesMenu.add(chkboxTwitter);
		servicesMenu.add(chkboxFacebook);

		if(workOffline) {
			chkboxMail.setEnabled(false);
			chkboxTwitter.setEnabled(false);
			chkboxFacebook.setEnabled(false);
			editFilters.setEnabled(false);
		} else {
			chkboxMail.setSelected(true);
			chkboxTwitter.setSelected(true);
			chkboxFacebook.setSelected(true);
		}

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
		tableContent.setTableHeader(null); // Hides default A B C... table header

		//tableContent.setAutoscrolls(true);
		tableContent.setFont(textFont);
		panels.get(3).add(tableContent);
		JScrollPane scroll = new JScrollPane(tableContent);
		panels.get(3).add(scroll);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		modelTable = (DefaultTableModel) tableContent.getModel();
		modelTable.addRow(new String[]{"ID", "DATE", "CHANNEL", "FROM", "SUBJECT", "CONTENT"});

		if(workOffline == false) { // CHECKBOX NOT SELECTED
			try {
				getAndFillNewsOnTable(generalMenu, modelTable);
				WriteXMLfile.writeMessage(userDBA);
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

		/**
		 * Interrupt sent to WindowLoading (gif image loading)
		 * */
		loading.interrupt();
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
				String infoGroup = "68092 - Diana Salvador" + lineSep + "65799 - Ricardo Ferreira" + lineSep + "73422 - Ivo Carvalho";
				String toolsProj = "Tools: GitHub, Trello, Maven & EclEmma.";
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
						WindowMessage windMess = new WindowMessage(dateM, fromM, titleM, contentM, canalM, userDBA, workOffline);
					}
				}
			}
		});

		// RADIO BUTTON OLDEST
		MR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MR.setSelected(true);
				indicatorFilters = filtersForData.sortByNewest(modelTable, gM, indicatorFilters, genericMessages);
			}
		});

		// RADIO BUTTON NEWEST
		MO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MO.setSelected(true);
				indicatorFilters = filtersForData.sortByOldest(modelTable, gM, indicatorFilters, genericMessages);
			}
		});

		// COMBO BOX FILTER DATE
		CB.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				switch (CB.getSelectedIndex()) {
				case 0:
					System.out.println("All");
					indicatorFilters = filtersForData.filterMessagesAll(MT, indicatorFilters, genericMessages);
					sortOne.setEnabled(true);
					sortTwo.setEnabled(true);
					break;
				case 1:
					System.out.println("Last 24H");
					indicatorFilters = filtersForData.filterMessagesLast24Hours(MT, indicatorFilters, genericMessages);
					sortOne.setEnabled(false);
					sortTwo.setEnabled(false);
					break;
				case 2:
					System.out.println("Last 48H");
					indicatorFilters = filtersForData.filterMessagesLast48Hours(MT, indicatorFilters, genericMessages);
					sortOne.setEnabled(false);
					sortTwo.setEnabled(false);
					break;
				case 3:
					System.out.println("Last week");
					indicatorFilters = filtersForData.filterMessagesLastWeek(MT, indicatorFilters, genericMessages);
					sortOne.setEnabled(false);
					sortTwo.setEnabled(false);
					break;
				default:
					System.out.println("Last month");
					indicatorFilters = filtersForData.filterMessagesLastMonth(MT, indicatorFilters, genericMessages);
					sortOne.setEnabled(false);
					sortTwo.setEnabled(false);
					break;             
				} 
			}
		});
	}

	/** 
	 * Method for collecting messages
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param generalMenu, is the JMenuBar 
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
	 * Method to fill the messages on the table
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param gM, is the JMenuBar 
	 */
	private void fillOnTable(JMenuBar gM) {
		int count = 1;

		boolean servMail = gM.getMenu(2).getItem(0).isSelected();
		boolean servTwitter = gM.getMenu(2).getItem(1).isSelected();
		boolean servFacebook = gM.getMenu(2).getItem(2).isSelected();

		indicatorFilters = 0;
		removeRows(modelTable);
		genericMessages.clear();
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
		} catch(Exception e) {
			System.out.print("Error in reading emails: " + e.toString());
		} finally {
			WriteXMLfile.writeMessage("dasra");
			rXML.readMessagesXMLfile("dasra");
		}

		/*if(servMail && servTwitter && servFacebook) {
			sortOne.setEnabled(true);
			sortTwo.setEnabled(true);
		} else {
			sortOne.setEnabled(false);
			sortTwo.setEnabled(false);
		}*/
	}

	/** 
	 * Auxiliary method to remove rows from the JTable
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable, is the JTABLE that contains the messages
	 */
	private void removeRows(DefaultTableModel modelTable) {
		int linhasNaTabela = indicatorFilters;
		for(int i = linhasNaTabela; i > 0; i--) {
			modelTable.removeRow(i);
			System.out.println("Row " + i + " deleted.");
		}
	}

	/** 
	 * Auxiliary method to fill the table with the messages from XML file (when working offline)
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	private void getNewsWorkingOffline() {
		int count = 1;
		indicatorFilters = 0;
		for (GenericMessage genM: rXML.readMessagesXMLfile(userDBA)) {
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

	/** 
	 * Utility method to get the emails
	 * @return messagesMail, an ArrayList of GenericMessage
	 */
	public ArrayList<GenericMessage> getGMemail() {
		return messagesMail;
	}

	/** 
	 * Utility method to get the tweets
	 * @return messagesTwitter, an ArrayList of GenericMessage
	 */
	public ArrayList<GenericMessage> getGMtweets() {
		return messagesTwitter;
	}

	/** 
	 * Utility method to get the posts from Facebook
	 * @return messagesFacebook, an ArrayList of GenericMessage
	 */
	public ArrayList<GenericMessage> getGMposts() {
		return messagesFacebook;
	}

	/** 
	 * Utility method to get messages
	 * @return genericMessages, an ArrayList of GenericMessage
	 */
	public static ArrayList<GenericMessage> getGM() {
		return genericMessages;
	}

	/** 
	 * Utility method to get a DefaultTableModel
	 * @return modelTable, a DefaultTableModel
	 */
	public DefaultTableModel getModelTable() {
		return modelTable;
	}

	/** 
	 * Utility method to get the indicator filters
	 * @return indicatorFilters, an integer
	 */
	public int getIndicatorFilters() {
		return indicatorFilters;
	}

	/** 
	 * Utility method to get filters
	 * @return filtersForData, an Filters
	 */
	public Filters getFiltersForData() {
		return filtersForData;
	}
}