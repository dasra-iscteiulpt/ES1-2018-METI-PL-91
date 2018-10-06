import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {


	public static void main(String[] args) {

		ReadXMLfile r = new  ReadXMLfile();

		// Estes dados t�m que ser lidos a partir dos dados introduzidos na GUI
		String to = "dasra@iscte-iul.pt"; // "To" email ID needs to be mentioned.
		final String email = "diana.es.pl.91@gmail.com";//"From" email ID needs to be mentioned.
		final String password = "engenhariasoftware";//email password

		//Validates user according to XML file
		if(r.validateUserEmail(email, password)==true){
			System.out.println("Login efetuado c/ sucesso.");

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			// Get the Session object.
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email, password);
				}
			});

			try {
				// Create a default MimeMessage object.
				Message message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(email));

				// Set To: header field of the header.
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to));

				// Set Subject: header field
				message.setSubject("Subject: JavaMailAPI testing for ES1 project");

				// Now set the actual message
				message.setText("Hello, this is an email sent by JavaMailAPI.");

				/*// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();

				// Now set the actual message
				messageBodyPart.setText("This is message body");

				// Create a multipar message
				Multipart multipart = new MimeMultipart();

				// Set text message part
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
				String filename = "C:\\Users\\Diana Salvador\\git\\ES1-2018-METI-PL-91\\ES1\\attachement.txt";
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename);
				multipart.addBodyPart(messageBodyPart);

				// Send the complete message parts
				message.setContent(multipart);*/

				// Send message
				Transport.send(message);

				System.out.println("Sent message successfully....");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			System.out.println("Login incorrecto. Por favor reveja os dados de acesso.");
		}
	}
}
