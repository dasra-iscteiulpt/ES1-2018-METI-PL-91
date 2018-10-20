import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import javax.mail.Message;
import org.junit.jupiter.api.Test;

public class JUnitTests {
	ReadEmails rEmails = new ReadEmails();;
	ReadXMLfile rXML = new ReadXMLfile();
	SendEmail sEmail = new SendEmail();
	WindowMessage wMessage;

	@Test
	public void testReadEmails() throws Exception {
		ArrayList<Message> m1 = new ArrayList<Message>();
		ArrayList<Message> m2 = new ArrayList<Message>();
		m1=rEmails.readMessages("imap.gmail.com", "imaps3", "diana.es.pl.91@gmail.com", "engenhariasoftware");
		@SuppressWarnings("static-access")
		String r = rEmails.getSubject(m1.get(0));
		m2.add(m1.get(0));
		assertNotEquals(m1,m2);
		assertNotEquals(m1.size(),m2.size());
		assertEquals(m1.size(),5);
		assertNotEquals(r,"Exame");
		assertEquals(r,"Exame ES1");
	}
	
	@Test
	public void testReadXMLfile() {
		boolean r1 = rXML.validateUserBDA("dasra", "dasra123");
		boolean r2 = rXML.validateUserBDA("vmbfs", "vmbfs123");
		assertNotEquals(r1,r2);
		assertTrue(r1);
		assertFalse(r2);
	}
	
	@Test
	public void testSendEmail() {
		int s = sEmail.senderMail("rjfae1@iscte-iul.pt", "diana.es.pl.91@gmail.com", "engenhariasoftware", "Teste JUnit", "Teste JUnit");
		assertNotEquals(s,0);
		assertEquals(s,1);
	}
	
	@Test
	public void testNullAndNotNull() {
		assertNotNull(rEmails);
		assertNotNull(rXML);
		assertNotNull(sEmail);
		assertNull(wMessage);
	}
}
