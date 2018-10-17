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

	private static ReadXMLfile r = new ReadXMLfile();

	public ArrayList<Message> readMessages(String imapHost, String storeType, String user, String password) {
		ArrayList<Message> m = new ArrayList<Message>();
		try {

			// create properties field
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "imaps");
			properties.put("mail.imaps.host", imapHost);
			properties.put("mail.imaps.port", "993");
			properties.put("mail.imaps.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			// emailSession.setDebug(true);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("imaps");

			store.connect(imapHost, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			System.out.println(messages.length);
			List<Attributes> filtersList = new ArrayList<Attributes>();
			filtersList = r.readFiltersXMLfile("config.xml");
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];				
				if(keywordValidation(getBodyTESTE(message), filtersList)==true) {
					m.add(messages[i]);
					System.out.println("Email number " + i);
					System.out.println(message.getFrom().toString());
					System.out.println(message.getReplyTo().toString());
					System.out.println(message.getReceivedDate().toString());
					System.out.println(message.getSubject());
					System.out.println(message.getContent().toString());
				}
			}
			System.out.print(m.size());
			emailFolder.close(false);
			store.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}

		return m;
	}

	public static boolean keywordValidation(String body, List<Attributes> list) throws Exception {
		String s = "";
		for (int i = 0; i < list.size(); i++) {
			s =list.get(i).getKeyword();
			if (body.contains(s)) {
				return true; 
			}
		}
		return false; 
	}
	
	private  String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException{
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

	public String getBodyTESTE(Message m) throws Exception {
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