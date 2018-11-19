package bda;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.mail.Message;
import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.Test;

class JUnitTests {

	ReadEmails rEmails = new ReadEmails();
	ReadXMLfile rXML = new ReadXMLfile();
	WriteXMLfile wXML = new WriteXMLfile();
	SendEmail sEmail = new SendEmail();
	WindowMessage wMessage;

	@Test
	public void testReadEmails() throws Exception {
		ArrayList<Message> messageArrayOne = new ArrayList<Message>();
		ArrayList<Message> messageArrayTwo = new ArrayList<Message>();
		messageArrayOne = rEmails.readMessages("imap.gmail.com", "imaps3", "diana.es.pl.91@gmail.com", "engenhariasoftware");

		@SuppressWarnings("static-access")
		String subjectMail1 = rEmails.getSubject(messageArrayOne.get(0));
		String subjectMail2 = rEmails.getSubject(messageArrayOne.get(5));
		messageArrayTwo.add(messageArrayOne.get(0));

		assertNotEquals(messageArrayOne, messageArrayTwo);
		assertNotEquals(messageArrayOne.size(), messageArrayTwo.size());
		assertEquals(messageArrayOne.size(),6);
		assertNotEquals(subjectMail1, "Exame");
		assertEquals(subjectMail1,"Inscrição universidade");
		assertEquals(subjectMail2,"Q&A Highlights for Diana Salvador");
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
	
	@Test
	public void testWindowLogin() {
		WindowLogin wind = new WindowLogin();
		Dimension windowSize = new Dimension(350,170);
		assertEquals(windowSize, wind.getFrame().getSize());
	}
	
	@Test
	public void testWindowUnsubscribe() {
		WindowLogin windL = new WindowLogin();
		assertNotNull(windL);
		WindowUnsubscribe windU = new WindowUnsubscribe(windL.getFrame());
		assertEquals(windU.getPanels().size(), 4);
	}
	
	@Test
	public void testWindowRegister() {
		WindowLogin windL = null;
		assertNull(windL);
		windL = new WindowLogin();
		assertNotNull(windL);
		WindowRegister windReg = new WindowRegister(windL.getFrame());
		assertTrue(windReg.getFrame().isVisible());
	}
		
	@Test
	public void testWindowFilter() {
		WindowDBA windDBA = new WindowDBA(false, "dasra");
		WindowFilter windF = new WindowFilter(windDBA.getFrame());
		windF.fillFilters();
		windF.getComboBox().addItem("ISCTE");
		assertNotEquals(windF.getComboBox().getItemAt(0), "ISCTE");
	}
	
	@Test
	public void testWriteComment() {
		WriteComment comm = new WriteComment();
		assertNotNull(comm);
		int result = comm.writeComment("2210486695854771_2210487832521324", "dasra", "Comentário p/ JUNIT!");
		assertEquals(result, 1);
	}
	
	@Test
	public void testRetweet() {
		Retweet twitter = new Retweet();
		assertNotNull(twitter);
		long tId = Long.parseLong("1019834231432843265");
		int result = twitter.retweet("iscteiul", "diana.es.pl.91@gmail.com", tId, "Tweet p/ JUNIT!");
		assertNotEquals(result, 0);
	}
	
	@Test
	public void testWindowMessage() {
		WindowMessage windMess = new WindowMessage("04/07/2018", "ISCTE-IUL", "Subject - 101454389886004837", "Adicionei um vídeo a uma playlist @YouTube https://t.co/8iK7Nei6DM do Mês junho 2018 Biblioteca ISCTE-IUL", "Twitter", "dasra");
		assertFalse(windMess.getFrame().isResizable());
	} 
	
	@Test
	public void testWindowDBA() {
		WindowDBA windDBA = new WindowDBA(false, "dasra");

		assertTrue(windDBA.getGMemail().get(0).getCanalM().equals("E-Mail"));
		assertTrue(windDBA.getGMtweets().get(0).getCanalM().equals("Twitter"));
		assertTrue(windDBA.getGMposts().get(0).getCanalM().equals("Facebook"));
		
		DefaultTableModel modelTable = windDBA.getModelTable();
		assertNotNull(modelTable);
				
		windDBA.filterEmailsAll(modelTable);
		boolean sizeTableAll = (modelTable.getRowCount() > 0);
		assertTrue(sizeTableAll);
		
		windDBA.filterEmailsLast24Hours(modelTable);
		boolean sizeTable24H = (modelTable.getRowCount() > 0);
		assertTrue(sizeTable24H);
		
		windDBA.filterEmailsLast48Hours(modelTable);
		boolean sizeTable48H = (modelTable.getRowCount() > 0);
		assertTrue(sizeTable48H);
		
		windDBA.filterEmailsLastMonth(modelTable);
		boolean sizeTableMonth = (modelTable.getRowCount() > 0);
		assertTrue(sizeTableMonth);

		windDBA.filterEmailsLastWeek(modelTable);
		boolean sizeTableWeek = (modelTable.getRowCount() > 0);
		assertTrue(sizeTableWeek);
		
		int sizeTableBeforeSortNewest = modelTable.getRowCount();
		windDBA.sortByNewest(modelTable, null);
		boolean sizeTableAfterSortNewest = (sizeTableBeforeSortNewest == modelTable.getRowCount());
		assertFalse(sizeTableAfterSortNewest);	
	}
	
	@Test
	public void testXMLFile() {
		boolean existFilter = rXML.validateFilter("universidade");
		boolean existUser = rXML.validateUserBDA("dasra", "dasra123");
		boolean existsFilters = (rXML.readFiltersXMLfile().size() > 0);
		@SuppressWarnings("static-access")
		boolean existsUsers = (rXML.readUsersXMLfile().size() > 0);
		
		assertTrue(existFilter);
		assertTrue(existUser);
		assertTrue(existsFilters);
		assertTrue(existsUsers);
	}
	
	@Test
	public void writeXMLFile() {
		@SuppressWarnings("static-access")
		boolean addUser = wXML.addUser("teste", "teste123", "teste@gmail.com", "teste123", "dpthvBBPVVsWxWituNu1CBx9h", "Nsuy5xBRdruPTaBms8GfQVtFsywI16zGyExf27THMHmmEm6d2W", "1056561597659914240-pHb2DY7rIzqtPTVyohg7zVZGEoqX6B", "B2WrSZcGM9LqmyrUbvmHKesS5S7hDYuvlHKKhmVlJU38N", "EAADpFZBDLw5QBADta3Qw8JqjHPFlShpQHhsodQhywzIZBTfdQp5oJJMBSZBP8qQrVkaiA7fmmBFZAE5xKazkRJRzyekZBL8gnThcJ0f7RoLtaNwseHJgZCUJtYZC6yyoLS1q0ZBvgumVQMG56c1rovf5iWwZAX5jjIi4nVcwM39ZABCRZCd3HZA9TN7daJZC79HSw4kcZD");
		@SuppressWarnings("static-access")
		boolean addFilter = wXML.addFilter("professor");
		@SuppressWarnings("static-access")
		boolean removeUser = wXML.removeUser("teste");
		@SuppressWarnings("static-access")
		boolean removeFilter = (wXML.removeFilter("professor"));
		@SuppressWarnings("static-access")
		boolean setUserAtt = (wXML.setUserAttribute("iccco", "password", "teste"));
				
		assertTrue(addUser);
		assertTrue(addFilter);
		assertTrue(removeUser);
		assertTrue(removeFilter);
		assertTrue(setUserAtt);
	}
}