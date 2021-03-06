package BDA;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

/** 
* Reads e-emails
* @author GROUP 91
* @version 1.0
* @since September 2018 
*/

public class ReadEmails {

	// VARIABLES
	private static ReadXMLfile r = new ReadXMLfile();

	/** 
	 * Constructor
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	public ReadEmails() {

	}

	/** 
	 * Connects to the Email API and saves all messages that contain academic filters in an array
	 * @author GROUP 91
	 * @version 1.0
	 * @since September 
	 * @param imapHost, is the API parameter email
	 * @param storeType, is the API parameter email
	 * @param user is the access data to the email
	 * @param password is the access data to the email
	 * @return An array with all academic messages
	 */

	public ArrayList<Message> readMessages(String imapHost, String storeType, String user, String password) {
		ArrayList<Message> m = new ArrayList<Message>();
		try {

			// Create properties field
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "imaps");
			properties.put("mail.imaps.host", imapHost);
			properties.put("mail.imaps.port", "993");
			properties.put("mail.imaps.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// Create the IMAP store object and connect with the server
			Store store = emailSession.getStore("imaps");

			store.connect(imapHost, user, password);

			// Create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// Retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			System.out.println(messages.length);
			List<Attributes> filtersList = new ArrayList<Attributes>();
			filtersList = r.readFiltersXMLfile();
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];				
				if(keywordValidation(getBody(message), getSubject(message), filtersList)==true) {
					m.add(messages[i]);
				}
			}
			System.out.print(m.size());
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}

		return m;
	}

	/** 
	 * Check for academic key words in the email
	 * @author GROUP 91
	 * @version 1.0
	 * @since September 
	 * @param body, is the content of the message
	 * @param subject, is the title of the message
	 * @param list, is the list with keywords
	 * @return True if the message contains academic keywords
	 */

	public static boolean keywordValidation(String body, String subject, List<Attributes> list) {
		String s = "";
		for (int i = 0; i < list.size(); i++) {
			s =list.get(i).getKeyword();
			if (body.toLowerCase().contains(s.toLowerCase()) || subject.toLowerCase().contains(s.toLowerCase())) {
				return true; 
			}
		}
		return false; 
	}

	/** 
	 * Get the subject from the e-mail message
	 * @author GROUP 91
	 * @version 1.0
	 * @since September 
	 * @param m, is the subject of the message
	 * @return s, if the message contains academic keywords
	 */
	public String getSubject(Message m) {
		String s="";
		try {
			if (m.getSubject() != null)
				s=m.getSubject();
			return s;
		}catch (Exception e){
			System.out.println("Error -> " + e);
		}
		return null;
	}

	/** 
	 * Get the text of the email if the type is MIME multipart, used in the getBody(Message) method
	 * @author GROUP 91
	 * @version 1.0
	 * @since September 
	 * @param mimeMultipart, MimeMultipart object
	 * @return body, a string from the body text
	 */
	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException{
		String body = "";
		int count = mimeMultipart.getCount();

		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				body = body + "\n" + bodyPart.getContent();
				break; 
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				body= body + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart){
				body = body + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
			}
		}
		return body;
	}

	/** 
	 * Get the text from the body of email whether it's plain text or multipart message
	 * @author GROUP 91
	 * @version 1.0
	 * @since September 
	 * @param m, Message object
	 * @return body, text from the body of email 
	 */
	public static String getBody(Message m){
		String body = "";
		try {
			if (m.isMimeType("text/plain")) {
				body = m.getContent().toString();
			}
			else if (m.isMimeType("multipart/*")) {
				MimeMultipart mimeMultipart = (MimeMultipart) m.getContent();
				body = getTextFromMimeMultipart(mimeMultipart);
			}
			return body;
		}
		catch (Exception e) {
			System.out.println("Error ->" + e);
		}
		return null;
	}

}