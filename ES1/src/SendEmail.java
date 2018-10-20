import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	
	// CONSTRUCTOR
	public SendEmail() {
		
	}
	
	/** 
 	* Send E-mails
	* @author GROUP 91
	* @version 1.0
	* @since September 
	* @param toEmail, is one of the mandatory parameters for sending the email
	* @param fromEmail, is one of the mandatory parameters for sending the email
	* @param fromPWEmail, is one of the mandatory parameters for sending the email
	* @param contentEmail, is one of the mandatory parameters for sending the email
	* @param subjectEmail, is one of the mandatory parameters for sending the email
	* @return Returns 1 case email is successfully sent and 0 otherwise
	*/
	public int senderMail(String toEmail, String fromEmail, String fromPWEmail, String contentEmail, String subjectEmail) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, fromPWEmail);
			}
		});
			
		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(fromEmail));
			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(toEmail));

			// Set Subject: header field
			message.setSubject(subjectEmail);

			// Now set the actual message
			message.setText(contentEmail);

			// Send message
			Transport.send(message);
			
			System.out.println("Sent message successfully.");
			return 1;
		} catch (MessagingException e) {
			System.out.println("An unexpected error has occurred. Try again.");
			return 0;
		}
	}

}