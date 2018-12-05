package BDA;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import javax.swing.JMenuBar;
import javax.swing.table.DefaultTableModel;

/** 
* Filters and sorts messages
* @author GROUP 91
* @version 1.0
* @since September 2018 
*/

public class Filters {

	/** 
	 * Method to filter messages from the last 24 hours
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param indicatorFilters, is a counter
	 * @param genericMessages, is an ArrayList of messages
	 * @return indicatorFilters, the number of filtered messages
	 */
	public int filterMessagesLast24Hours(DefaultTableModel modelTable, int indicatorFilters, ArrayList<GenericMessage> genericMessages) {
		removeRows(modelTable, indicatorFilters);
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
		return indicatorFilters;

	}

	/** 
	 * Method to filter messages from the last 48 hours
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param indicatorFilters, is a counter 
	 * @param genericMessages, is an ArrayList of messages
	 * @return indicatorFilters, the number of filtered messages
	 */
	public int filterMessagesLast48Hours(DefaultTableModel modelTable, int indicatorFilters, ArrayList<GenericMessage> genericMessages) {
		removeRows(modelTable, indicatorFilters);
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
		return indicatorFilters;
	}

	/** 
	 * Method to filter messages from the last Week
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param indicatorFilters, is a counter
	 * @param genericMessages, is an ArrayList of messages
	 * @return indicatorFilters, the number of filtered messages
	 */
	public int filterMessagesLastWeek(DefaultTableModel modelTable, int indicatorFilters, ArrayList<GenericMessage> genericMessages) {
		removeRows(modelTable, indicatorFilters);
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
		return indicatorFilters;

	}

	/** 
	 * Method to filter messages from the last Month
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param indicatorFilters, is a counter
	 * @param genericMessages, is an ArrayList of messages
	 * @return indicatorFilters, the number of filtered messages
	 */
	public int filterMessagesLastMonth(DefaultTableModel modelTable, int indicatorFilters, ArrayList<GenericMessage> genericMessages) {
		removeRows(modelTable, indicatorFilters);
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
		return indicatorFilters;

	}

	/** 
	 * Method to show all messages
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param indicatorFilters, is a counter
	 * @param genericMessages, is an ArrayList of messages
	 * @return indicatorFilters, the number messages
	 */
	public int filterMessagesAll(DefaultTableModel modelTable, int indicatorFilters, ArrayList<GenericMessage> genericMessages) {
		removeRows(modelTable, indicatorFilters);
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
		return indicatorFilters;

	}

	/** 
	 * Sort messages, from the oldest to the newest
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param gM, is the JMenuBar
	 * @param indicatorFilters, is a counter
	 * @param genericMessages, is an ArrayList of messages
	 * @return indicatorFilters, the number of filtered messages
	 */
	public int sortByOldest(DefaultTableModel modelTable, JMenuBar gM, int indicatorFilters, ArrayList<GenericMessage> genericMessages) {
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
			removeRows(modelTable, indicatorFilters);
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
		return indicatorFilters;
	}

	/** 
	 * Sort messages, from the newest to the oldest
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param gM, is the JMenuBar
	 * @param indicatorFilters, is a counter
	 * @param genericMessages, is an ArrayList of messages
	 * @return indicatorFilters, the number of filtered messages
	 */
	public int sortByNewest(DefaultTableModel modelTable, JMenuBar gM, int indicatorFilters, ArrayList<GenericMessage> genericMessages) {
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
			removeRows(modelTable, indicatorFilters);
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
		return indicatorFilters;
	}

	/** 
	 * Utility method to remove rows from the table everytime the table model shows different data caused by filtering
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param modelTable is the JTABLE that contains the messages
	 * @param indicatorFilters, is a counter
	 */
	private void removeRows(DefaultTableModel modelTable, int indicatorFilters) {
		int linhasNaTabela = indicatorFilters;
		for(int i = linhasNaTabela; i > 0; i--) {
			modelTable.removeRow(i);
			System.out.println("Row " + i + " deleted.");
		}
	}

}