import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import javax.mail.Message;
import org.junit.jupiter.api.Test;

class JUnitTests {
	
	ReadEmails rEmails = new ReadEmails();
	ReadXMLfile rXML = new ReadXMLfile();
	SendEmail sEmail = new SendEmail();
	WindowMessage wMessage;
	
	@Test
	public void testReadEmails() throws Exception {
		ArrayList<Message> messageArrayOne = new ArrayList<Message>();
		ArrayList<Message> messageArrayTwo = new ArrayList<Message>();
		messageArrayOne = rEmails.readMessages("imap.gmail.com", "imaps3", "diana.es.pl.91@gmail.com", "engenhariasoftware");

		@SuppressWarnings("static-access")
		String subjectMail = rEmails.getSubject(messageArrayOne.get(0));
		messageArrayTwo.add(messageArrayOne.get(0));
		
		assertNotEquals(messageArrayOne, messageArrayTwo);
		assertNotEquals(messageArrayOne.size(), messageArrayTwo.size());
		assertEquals(messageArrayOne.size(),6);
		assertNotEquals(subjectMail, "Exame");
		assertEquals(subjectMail,"Exame ES1");
	}
	
	@Test
	public void testReadXMLfile() {
		boolean xmlValidateOne = rXML.validateUserBDA("dasra", "dasra123");
		boolean xmlValidateTwo = rXML.validateUserBDA("vmbfs", "vmbfs123");
		
		assertNotEquals(xmlValidateOne, xmlValidateTwo);
		assertTrue(xmlValidateOne);
		assertFalse(xmlValidateTwo);
	}
	
	@Test
	public void testSendEmail() {
		int sucessSendMail = sEmail.senderMail("rjfae1@iscte-iul.pt", "diana.es.pl.91@gmail.com", "engenhariasoftware", "Teste JUnit", "Teste JUnit");
		
		assertNotEquals(sucessSendMail, 0);
		assertEquals(sucessSendMail, 1);
	}
	
	@Test
	public void testNullAndNotNull() {
		assertNotNull(rEmails);
		assertNotNull(rXML);
		assertNotNull(sEmail);
		assertNull(wMessage);
	}
	
}