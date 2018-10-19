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

public class ReadEmails {

	// ATRIBUTOS
	private static ReadXMLfile r = new ReadXMLfile();

	// CONSTRUTOR
	public ReadEmails() {

	}

	/** 
	 * Connects to the Email API and saves all messages that contain academic filters in an array
	 * @author GROUP 91
	 * @version 1.0
	 * @since September 
	 * @param ImapHost are StoreType API parameters email
	 * @param User and Password are the access data to the email
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
			filtersList = r.readFiltersXMLfile("config.xml");
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];				
				if(keywordValidation(getBody(message), getSubject(message), filtersList)==true) {
					m.add(messages[i]);
					/*System.out.println("Email number " + i);
					System.out.println(message.getFrom().toString());
					System.out.println(message.getReplyTo().toString());
					System.out.println(message.getReceivedDate().toString());
					System.out.println(message.getSubject());
					System.out.println(getBody(message));*/
				}
			}
			System.out.print(m.size());
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}

		return m;
	}

	/** 
	 * Check for academic key words
	 * @author GROUP 91
	 * @version 1.0
	 * @since September 
	 * @param Body is the content of the message and list is the list of keywords
	 * @return True if the message contains academic keywords
	 */
	public static boolean keywordValidation(String body, String subject, List<Attributes> list) throws Exception {
		String s = "";
		for (int i = 0; i < list.size(); i++) {
			s =list.get(i).getKeyword();
			if (body.toLowerCase().contains(s.toLowerCase()) || subject.toLowerCase().contains(s.toLowerCase())) {
				return true; 
			}
		}
		return false; 
	}

	public static String getSubject(Message m) throws Exception {
		// SUBJECT
		String s="";
		if (m.getSubject() != null)
			s=m.getSubject();
		//System.out.println("SUBJECT: " + m.getSubject());
		return s;	
	}
	
	// MÉTODOS AINDA EM CONSTRUÇÃO - JAVADOC POR CONSTRUIR
	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException{
		String body = "";
		int count = mimeMultipart.getCount();

		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				body = body + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				body= body + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart){
				body = body + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
			}
		}
		return body;
	}

	public static String getBody(Message m) throws Exception {
		String body = "";
		if (m.isMimeType("text/plain")) {
			body = m.getContent().toString();
		}
		else if (m.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) m.getContent();
			body = getTextFromMimeMultipart(mimeMultipart);
		}
		return body;
	}

}