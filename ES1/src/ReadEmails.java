import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

public class ReadEmails {
	private int id;
	private String date;
	private String service;
	private String from;
	private String to;
	private String subject;
	private String body;
	private static ReadXMLfile r = new ReadXMLfile();
	//static String fr ="Diana_Salvador@iscte-iul.pt";

	public Message[] readMessages(String imapHost, String storeType, String user, String password) {
		Message[] content = new Message[1];
		Message[] m = new Message[100];
		try {
			// Estes dados têm que ser lidos a partir dos dados introduzidos na GUI
			String f = "";
			int j=1;
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
			List<Attributes> filtersList = new ArrayList<Attributes>();
			filtersList = r.readFiltersXMLfile("config.xml");
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];				
				/*if(message.getFrom()[0].toString().contains("<"))
					f = message.getFrom()[0].toString().substring(message.getFrom()[0].toString().indexOf("<") + 1, message.getFrom()[0].toString().indexOf(">"));
				else {
					f = message.getFrom()[0].toString();
				}
				if(f.equals(fr) & */
				if(keywordValidation(getBodyTESTE(message), filtersList)==true) {
					m = (Message[]) Arrays.copyOf(content, j);
					m[j-1]=message;
					System.out.println("Email number " + getId(i));
					System.out.println(getService());
					System.out.println(getFromTESTE(m[j-1]));
					System.out.println(getToTESTE(m[j-1]));
					System.out.println(getDateTESTE(m[j-1]));
					System.out.println(getSubjectTESTE(m[j-1]));
					System.out.println(getBodyTESTE(m[j-1]));
					j++;
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

	public static int getId(int i) throws Exception {
		return i+1;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static String getService() throws Exception {
		String s="email";
		return s;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getFromTESTE(Message m) throws Exception {
		Address[] a;
		String s="";
		// FROM
		if ((a = m.getFrom()) != null) {
			s=a[0].toString();
			//System.out.println("FROM: " + a[0].toString());
		}
		return s;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getToTESTE(Message m) throws Exception {
		Address[] a;
		String s="";
		// TO
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
			s=a[0].toString();
			//System.out.println("TO: " + a[0].toString());
		}
		return s;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getDateTESTE(Message m) throws Exception {
		// DATE
		String s="";
		if (m.getReceivedDate() != null)
			s=m.getReceivedDate().toString();
		//System.out.println("Date: " + m.getReceivedDate().toString());
		return s;	
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSubjectTESTE(Message m) throws Exception {
		// SUBJECT
		String s="";
		if (m.getSubject() != null)
			s=m.getSubject();
		//System.out.println("SUBJECT: " + m.getSubject());
		return s;	
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public void setBody(String body) {
		this.body = body;
	}

}