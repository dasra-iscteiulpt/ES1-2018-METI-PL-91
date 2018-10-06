import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;


import org.jsoup.Jsoup;

public class CheckingEmails {

	public static void check(String host, String storeType, String user, String password) 
	{
		try {
			// Estes dados têm que ser lidos a partir dos dados introduzidos na GUI
			String from = "D@iscte-iul.pt"; //From filter
			String keyword = "exame"; //Keyword filter
			//String service = "email"; //Service filter

			 Properties properties = new Properties();

		      properties.put("mail.imaps.host", host);
		      properties.put("mail.imaps.port", "993");
		      properties.put("mail.imaps.starttls.enable", "true");
		      Session emailSession = Session.getDefaultInstance(properties);
		  
		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("imaps");

		      store.connect(host, user, password);

		      //create the folder object and open it
		      Folder emailFolder = store.getFolder("INBOX");
		      emailFolder.open(Folder.READ_ONLY);

		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		      System.out.println("messages.length---" + messages.length);
		      
			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];			  
				//if(message.getFrom().equals(from) & getTextFromMessage(message).contains(keyword)) {
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("Subject: " + keyword);
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("From: " + from);
				System.out.println("Text: " + getTextFromMessage(message));
				//}
			}

			//close the store and folder objects
			emailFolder.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getTextFromMessage(Message message) throws Exception {
		if (message.isMimeType("text/plain")){
			return message.getContent().toString();
		}else if (message.isMimeType("multipart/*")) {
			String result = "";
			MimeMultipart mimeMultipart = (MimeMultipart)message.getContent();
			int count = mimeMultipart.getCount();
			for (int i = 0; i < count; i ++){
				BodyPart bodyPart = mimeMultipart.getBodyPart(i);
				if (bodyPart.isMimeType("text/plain")){
					result = result + "\n" + bodyPart.getContent();
					break;  
				} else if (bodyPart.isMimeType("text/html")){
					String html = (String) bodyPart.getContent();
					result = result + "\n" + Jsoup.parse(html).text();

				}
			}
			return result;
		}
		return "";
	}

	public static void main(String[] args) {

		String host = "imap.gmail.com";
		String mailStoreType = "imaps";
		// Estes dados têm que ser lidos a partir dos dados introduzidos na GUI
		String username = "diana.es.pl.91@gmail.com";// change accordingly
		String password = "engenhariasoftware";// change accordingly

		check(host, mailStoreType, username, password);

	}

}
