import javax.swing.JFrame;
import javax.swing.JMenu;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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

	private JFrame windowFrame;
	private ArrayList<JPanel> panels;
	private static ReadEmails r = new ReadEmails();

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
		chkDate.addItem("24 horas");
		chkDate.addItem("7 dias");
		chkDate.addItem("Mês atual");
		chkDate.addItem("Ano atual");

		ButtonGroup sortOptions = new ButtonGroup();
		sortOptions.add(sortOne);
		sortOptions.add(sortTwo);

		panels.get(3).add(sortOne);
		panels.get(3).add(sortTwo);
		panels.get(3).add(chkDate);


		// CONFIGURAÇÃO DA TABELA
		JTable tableContent = new JTable(0,6);
		panels.get(3).add(tableContent);

		DefaultTableModel modelTable = (DefaultTableModel) tableContent.getModel();
		modelTable.addRow(new String[]{"Id", "Date", "Channel", "From", "Subject", "Content"});

		getAndFillNewsOnTable(modelTable);

		buttonsMenuConfig(generalMenu, sortOne, sortTwo, tableContent);
	}

	private void endConfigWindow() {
		// CONFIGURAÇÃO WINDOW FRAME
		windowFrame.setLocationRelativeTo(null);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setResizable(false);
		windowFrame.validate();
		windowFrame.setVisible(true);
	}

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

	// MÉTODO PRINCIPAL PARA RECOLHA DE NOTÍCIAS
	private void getAndFillNewsOnTable(DefaultTableModel modelTable) {
		ReadEmails rMails = new ReadEmails();
		Message[] messagesMail = rMails.readMessages("imap.gmail.com", "imaps3", "diana.es.pl.91@gmail.com", "engenhariasoftware");

		int count = 0;
		try {

			for (int i = 0; i < messagesMail.length; i++) {
				String dateM = messagesMail[i].getReceivedDate().toString();
				String channelM = "EM";
				String fromM = messagesMail[i].getFrom().toString();
				String subjectM = messagesMail[i].getSubject();
				String contentM = messagesMail[i].getContent().toString();
				modelTable.addRow(new String[] { Integer.toString(count), dateM, channelM, fromM, subjectM, contentM });
				count++;
			}
		} catch(Exception e) {
			System.out.print("Erro a ler e-mails: " + e.toString());
		}
	}
}

