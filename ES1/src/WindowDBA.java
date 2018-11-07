import javax.swing.JFrame;
import javax.swing.JMenu;
import java.awt.BorderLayout;
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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JRadioButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class WindowDBA {

	// VARIABLES
	private JFrame windowFrame;
	private ArrayList<JPanel> panels;
	private ArrayList<GenericMessage> genericMessages;
	private DefaultTableModel modelTable;
	private int indicatorFilters = 0;
	
	// CONSTRUCTOR
	public WindowDBA(String title) {
		windowFrame = new JFrame(title);
		genericMessages = new ArrayList<GenericMessage>();
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
		windowFrame.setSize(500, 500);
		// CONFIGURA플O JPANEL NA WINDOWFRAME
		windowFrame.add(panels.get(0), BorderLayout.SOUTH);
		windowFrame.add(panels.get(1), BorderLayout.WEST);
		windowFrame.add(panels.get(2), BorderLayout.EAST);
		windowFrame.add(panels.get(3), BorderLayout.CENTER);
		
		// CONFIGURA플O DO MENU
		JMenuBar generalMenu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Sort");
		JMenu aboutMenu = new JMenu("More");
		
		JMenuItem workOnline = new JMenuItem("Work online");
		workOnline.setEnabled(false);
		JMenuItem workOffline = new JMenuItem("Work offline");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem newest = new JMenuItem("Newest");
		JMenuItem oldest = new JMenuItem("Oldest");
		JMenuItem about = new JMenuItem("About");
		JMenuItem help = new JMenuItem("Help");

		fileMenu.add(workOnline);
		fileMenu.add(workOffline);
		fileMenu.add(exit);
		editMenu.add(newest);
		editMenu.add(oldest);
		aboutMenu.add(about);
		aboutMenu.add(help);
		
		generalMenu.add(fileMenu);
		generalMenu.add(editMenu);
		generalMenu.add(aboutMenu);
		windowFrame.add(generalMenu, BorderLayout.NORTH);
		
		// CONFIGURA플O DOS RADIO BUTTON & COMBOBOX
		JRadioButton sortOne = new JRadioButton("Newest");
		JRadioButton sortTwo = new JRadioButton("Oldest");
		
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

		// CONFIGURA플O DA TABELA
		JTable tableContent = new JTable(0,6);
		panels.get(3).add(tableContent);

		modelTable = (DefaultTableModel) tableContent.getModel();
		modelTable.addRow(new String[]{"Id", "Date", "Channel", "From", "Subject", "Content"});
		
		getAndFillNewsOnTable(modelTable);
		
		buttonsMenuConfig(generalMenu, sortOne, sortTwo, chkDate, modelTable, tableContent);
	}

	/** 
 	* Construction of the main window structure
	* @author GROUP 91
	* @version 1.0
	* @since September
	*/
	private void endConfigWindow() {
		// CONFIGURA플O WINDOW FRAME
		windowFrame.setLocationRelativeTo(null);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		// NEWEST ACTION
		gM.getMenu(1).getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( !MR.isSelected() ) {
					MR.setSelected(true);
					sortByNewest(modelTable);
				}
			}
		});
		
		// OLDEST ACTION
		gM.getMenu(1).getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( !MO.isSelected() ) {
					MO.setSelected(true);
					sortByOldest(modelTable);
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
				String toolsProj = "Used tools: Git, Trello.";
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
			        	String dateM = TC.getModel().getValueAt(TC.getSelectedRow(),1).toString();
			        	String canalM = TC.getModel().getValueAt(TC.getSelectedRow(),2).toString();
			        	String fromM = TC.getModel().getValueAt(TC.getSelectedRow(),3).toString();
			        	String titleM = TC.getModel().getValueAt(TC.getSelectedRow(),4).toString();
			        	String contentM = TC.getModel().getValueAt(TC.getSelectedRow(),5).toString();
			        	@SuppressWarnings("unused")
						WindowMessage windMess = new WindowMessage(dateM, fromM, titleM, contentM, canalM);
			   
	            	}
	            }
	         }
	      });

		// RADIO BUTTON OLDEST
		MR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					MR.setSelected(true);
					sortByNewest(modelTable);
			}
		});
		
		// RADIO BUTTON NEWEST
		MO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					MO.setSelected(true);
					sortByOldest(modelTable);
			}
		});
	    
	    // COMBO BOX FILTER DATE
	    CB.addActionListener (new ActionListener () {
	        public void actionPerformed(ActionEvent e) {
	        	switch (CB.getSelectedIndex()) {
	        	case 0:
	            	System.out.println("Todos");
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
	            	System.out.println("Last WEEK");
	            	filterEmailsLastWeek(MT);
	                break;
	            default:
	            	System.out.println("Last MONTH");
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
	private void getAndFillNewsOnTable(DefaultTableModel modelTable) {
		ReadEmails rMails = new ReadEmails();
		ReadTweets rTweets = new ReadTweets();

		ArrayList<GenericMessage> messagesMail = GenericMessage.receiveMailReturnMessage(rMails.readMessages("imap.gmail.com", "imaps3", ReadXMLfile.userData[0], ReadXMLfile.userData[1]));
		ArrayList<GenericMessage> messagesTwitter = GenericMessage.receiveTweetsReturnMessage(rTweets.readTweets("dasra"));
		// ArrayList<GenericMessage> messagesFace = GenericMessage.receiveMailReturnMessage(listMail);
		
		fillOnTable(messagesMail, messagesTwitter);
		indicatorFilters = genericMessages.size();
		
	}
	
	/** 
	* Method to filter emails from the last 24 hours
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsLast24Hours(DefaultTableModel modelTable) {
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
						String channelM = "EM";
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
	}
	
	/** 
	* Method to filter emails from the last 48 hours
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsLast48Hours(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();	
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		indicatorFilters = 0;
		int count = 1;
		try {
			for (GenericMessage m: genericMessages) {
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
	}
	
	/** 
	* Method to filter emails from the last Week
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsLastWeek(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		indicatorFilters = 0;

		int count = 1;
		try {
			for (GenericMessage m: genericMessages) {
					c.setTime(new Date());
					c.add(Calendar.DATE, -7);
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
				System.out.println("Emails from last week");
		} catch (Exception e) {
			System.out.print("Error in filtering by hour: " + e.toString());
		}
	}
	
	/** 
	* Method to filter emails from the last Month
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsLastMonth(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
		
	}
	
	/** 
	* Method to show all messages
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsAll(DefaultTableModel modelTable) {
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
	}
	
	/** 
	* Sort messages, from the oldest to the newest
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void sortByOldest(DefaultTableModel modelTable) {
		removeRows(modelTable);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<Date> dateArray = new ArrayList<Date>();
		int count = 1;
		Date date = new Date();
		System.out.println("hello + " + genericMessages.size());
		try {
			for(GenericMessage m: genericMessages) {
				date = df.parse(m.getDateM());
				dateArray.add(date);
			}
			Collections.sort(dateArray);
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
	*/
	private void sortByNewest(DefaultTableModel modelTable) {
		removeRows(modelTable);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		ArrayList<Date> dateArray = new ArrayList<Date>();
		int count = 1;
		Date date = new Date();
		try {
			for(GenericMessage m: genericMessages) {
				date = df.parse(m.getDateM());
				dateArray.add(date);
				System.out.println("date = " + date);
			}
			Collections.sort(dateArray, Collections.reverseOrder());
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

	private void fillOnTable(ArrayList<GenericMessage> gM1, ArrayList<GenericMessage> gM2) {
		int count = 1;
		try {
			for (GenericMessage genM: gM1) {
				String dateM = genM.getDateM();
				String channelM = genM.getCanalM();
				String fromM = genM.getFromM();
				String subjectM = genM.getTitleM();
				String contentM = genM.getContentM();
			    modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
			    count++;
			}
			
			for (GenericMessage genM: gM2) {
				String dateM = genM.getDateM();
				String channelM = genM.getCanalM();
				String fromM = genM.getFromM();
				String subjectM = genM.getTitleM();
				String contentM = genM.getContentM();
			    modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
			    count++;
			}
			
		} catch(Exception e) {
			System.out.print("Error in reading emails: " + e.toString());
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
	
}