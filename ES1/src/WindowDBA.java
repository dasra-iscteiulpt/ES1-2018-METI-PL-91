import javax.swing.JFrame;
import javax.swing.JMenu;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.swing.JPanel;
import javax.mail.Message;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class WindowDBA {

	// ATRIBUTOS
	private JFrame windowFrame;
	private ArrayList<JPanel> panels;
	private ArrayList<Message> messagesMail;
	private DefaultTableModel modelTable;
	private int indicatorFilters = 0;

	// CONSTRUTOR
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
		JMenuItem moreOlder = new JMenuItem("More older");
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
		JRadioButton sortTwo = new JRadioButton("More Old");
		
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

		// CONFIGURAÇÃO DA TABELA
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
		
		// MORE RECENT ACTION
		gM.getMenu(1).getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( !MR.isSelected() ) {
					MR.setSelected(true);
					sortByMoreRecent(modelTable);
				}
			}
		});
		
		// MORE OLD ACTION
		gM.getMenu(1).getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( !MO.isSelected() ) {
					MO.setSelected(true);
					sortByOlder(modelTable);
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

		// RADIO BUTTON OLDER
		MR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					MR.setSelected(true);
					sortByMoreRecent(modelTable);
			}
		});
		
		// RADIO BUTTON MORE RECENT
		MO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					MO.setSelected(true);
					sortByOlder(modelTable);
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
		messagesMail = rMails.readMessages("imap.gmail.com", "imaps3", "diana.es.pl.91@gmail.com", "engenhariasoftware");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.println(messagesMail.size());
	    int count = 1;
		try {
			for (Message m: messagesMail) {
				String dateM = sdf.format(m.getReceivedDate());
				String channelM = "EM";
				String fromM = m.getFrom()[0].toString();
				String subjectM = m.getSubject().toString();
				String contentM = ReadEmails.getBody(m);
			    modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
			    count++;
			}
			indicatorFilters = messagesMail.size();
		} catch(Exception e) {
			System.out.print("Erro a ler e-mails: " + e.toString());
		}

	}
	
	/** 
	* Method to filter news from the last 24 hours
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsLast24Hours(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		indicatorFilters = 0;
		int count = 1;
		try {
			for (Message m: messagesMail) {
					c.setTime(new Date());
					c.add(Calendar.DATE, -1);
					Date d = c.getTime();
					if (m.getReceivedDate().after(d)) {
						String dateM = sdf.format(m.getReceivedDate());
						String channelM = "EM";
						String fromM = m.getFrom()[0].toString();
						String subjectM = m.getSubject();
						String contentM = ReadEmails.getBody(m);
					    modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
					    count++;
					    indicatorFilters++;
					}
				}
			System.out.println("Emails from last 24h");
		} catch (Exception e) {
			System.out.print("Erro a filtrar por hora: " + e.toString());
		}
	}
	
	/** 
	* Method to filter news from the last 48 hours
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsLast48Hours(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		indicatorFilters = 0;
		int count = 1;
		try {
			for (Message m: messagesMail) {
				c.setTime(new Date());
				c.add(Calendar.DATE, -2);
				Date d = c.getTime();
				if (m.getReceivedDate().after(d)) {
					String dateM = sdf.format(m.getReceivedDate());
					String channelM = "EM";
					String fromM = m.getFrom()[0].toString();
					String subjectM = m.getSubject();
					String contentM = ReadEmails.getBody(m);
				    modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
				    count++;
					indicatorFilters++;
				}
				}
			System.out.println("Emails from last 48h");
		} catch (Exception e) {
			System.out.print("Erro a filtrar por hora: " + e.toString());
		}
	}
	
	/** 
	* Method to filter news from the last Week
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsLastWeek(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		indicatorFilters = 0;

		int count = 1;
		try {
			for (Message m: messagesMail) {
					c.setTime(new Date());
					c.add(Calendar.DATE, -7);
					Date d = c.getTime();
					if (m.getReceivedDate().after(d)) {
						String dateM = sdf.format(m.getReceivedDate());
						String channelM = "EM";
						String fromM = m.getFrom()[0].toString();
						String subjectM = m.getSubject();
						String contentM = ReadEmails.getBody(m);
					    modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
						count++;
						indicatorFilters++;
					}
				}
				System.out.println("Emails from last week");
		} catch (Exception e) {
			System.out.print("Erro a filtrar por hora: " + e.toString());
		}
	}
	
	/** 
	* Method to filter news from the last Month
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsLastMonth(DefaultTableModel modelTable) {
		removeRows(modelTable);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		indicatorFilters = 0;
		int count = 1;
		try {
			for (Message m: messagesMail) {
				c.setTime(new Date());
				c.add(Calendar.DATE, -30);
				Date d = c.getTime();
				if (m.getReceivedDate().after(d)) {
					String dateM = sdf.format(m.getReceivedDate());
					String channelM = "EM";
					String fromM = m.getFrom()[0].toString();
					String subjectM = m.getSubject();
					String contentM = ReadEmails.getBody(m);
				    modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
				    count++;
					indicatorFilters++;
				}
			}
		    System.out.println("Emails from last month");
		} catch (Exception e) {
			System.out.print("Erro a filtrar por hora: " + e.toString());
		}
		
	}
	
	/** 
	* Method shows all messages
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void filterEmailsAll(DefaultTableModel modelTable) {
		removeRows(modelTable);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int count = 1;
		indicatorFilters = 0;
		try {
			for (Message m: messagesMail) {
				String dateM = sdf.format(m.getReceivedDate());
					String channelM = "EM";
					String fromM = m.getFrom()[0].toString();
					String subjectM = m.getSubject();
					String contentM = ReadEmails.getBody(m);
					modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
					count++;
					indicatorFilters++;
			}
		    System.out.println("All Emails");
		} catch (Exception e) {
			System.out.print("Erro a filtrar por hora: " + e.toString());
		}
	}
	
	/** 
	* Sort messages, from the most recent to the oldest
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void sortByOlder(DefaultTableModel modelTable) {
		removeRows(modelTable);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<Date> dateArray = new ArrayList<Date>();
		int count = 1;
		Date date = new Date();
		System.out.println("hello + " + messagesMail.size());
		try {
			for(Message m: messagesMail) {
				date=m.getReceivedDate();
				dateArray.add(date);
			}
			Collections.sort(dateArray);
			indicatorFilters = 0;
			for(Date d: dateArray) {
				for(Message m: messagesMail) {
					if(m.getReceivedDate().equals(d)) {
						String dateM = sdf.format(m.getReceivedDate());
						String channelM = "EM";
						String fromM = m.getFrom()[0].toString();
						String subjectM = m.getSubject();
						String contentM = ReadEmails.getBody(m);
						modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
						count++;
						indicatorFilters++;
					}
				}
			}
			System.out.println("Sort by More Recent");
		} catch (Exception e) {
			System.out.print("Erro a sortear emails mais recentes: " + e.toString());
		}
	}

	/** 
	* Sort messages, from the oldest to the most recent
	* @author GROUP 91
	* @version 1.0
	* @since September
	* @param modelTable is the JTABLE that contains the messages
	*/
	private void sortByMoreRecent(DefaultTableModel modelTable) {
		removeRows(modelTable);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<Date> dateArray = new ArrayList<Date>();
		int count = 1;
		Date date = new Date();
		try {
			for(Message m: messagesMail) {
				date=m.getReceivedDate();
				dateArray.add(date);
				System.out.println("date = " + date);
			}
			Collections.sort(dateArray, Collections.reverseOrder());
			indicatorFilters = 0;
			for(Date d: dateArray) {
				for(Message m: messagesMail) {
					if(m.getReceivedDate().equals(d)) {
						String dateM = sdf.format(m.getReceivedDate());
						String channelM = "EM";
						String fromM = m.getFrom()[0].toString();
						String subjectM = m.getSubject();
						String contentM = ReadEmails.getBody(m);
						modelTable.insertRow(count, new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
						count++;
						indicatorFilters++;
					}
				}
			}
			System.out.println("Sort by More Recent");
		} catch (Exception e) {
			System.out.print("Erro a sortear emails mais recentes: " + e.toString());
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
			System.out.println("Linha " + i + " eliminada.");
		}
	}
	
}