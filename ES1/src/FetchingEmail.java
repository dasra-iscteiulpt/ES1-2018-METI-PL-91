import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

public class FetchingEmail {

	public static void fetch(String pop3Host, String storeType, String user, String password) {
		try {
			// Estes dados têm que ser lidos a partir dos dados introduzidos na GUI
			String from = "Diana_Salvador@iscte-iul.pt"; //From filter
			String keyword = "exame"; //Keyword filter
			String f = "";
			
			// create properties field
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "imaps");
			properties.put("mail.imaps.host", pop3Host);
			properties.put("mail.imaps.port", "993");
			properties.put("mail.imaps.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			// emailSession.setDebug(true);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("imaps");

			store.connect(pop3Host, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			//System.out.println("messages.length---" + messages.length);

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				if(message.getFrom()[0].toString().contains("<"))
					f = message.getFrom()[0].toString().substring(message.getFrom()[0].toString().indexOf("<") + 1, message.getFrom()[0].toString().indexOf(">"));
				else {
					f = message.getFrom()[0].toString();
				}
				if(f.equals(from) & keywordValidation(writePart(message),keyword)==true) {
					System.out.println("Email number " + i + writePart(message) );
				}
			}
			emailFolder.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		String host = "imap.gmail.com";
		String mailStoreType = "imaps3";
		String username = "diana.es.pl.91@gmail.com";// change accordingly
		String password = "engenhariasoftware";// change accordingly

		//Call method fetch
		fetch(host, mailStoreType, username, password);
	}

	public static String writePart(Message m) throws Exception {
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

	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException{
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

	public static boolean keywordValidation(String body, String keyword) throws Exception {
		if (body.contains(keyword)) 
			return true;
		return false; 
	}
}