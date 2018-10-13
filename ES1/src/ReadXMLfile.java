import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.List;

public class ReadXMLfile {

	static List<Attributes> usersList = new ArrayList<Attributes>();
	List<Attributes> filtersList = new ArrayList<Attributes>();
	static ReadEmails r = new ReadEmails();
	
	public static List<Attributes> readUsersXMLfile(String xml) {

		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using the builder to get the DOM mapping of the XML file
			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();

			//adds the user attributes to the array
			NodeList userList = doc.getElementsByTagName("User");
			for (int i = 0; i < userList.getLength(); i++) {
				usersList.add(getUserAttributes(userList.item(i)));
			}
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		return usersList;
	}

	public List<Attributes> readFiltersXMLfile(String xml) {

		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using the builder to get the DOM mapping of the XML file
			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();

			//adds the filters to the array
			NodeList filterList = doc.getElementsByTagName("Filter");
			for (int i = 0; i < filterList.getLength(); i++) {
				filtersList.add(getFilterAttributes(filterList.item(i)));
			}

		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		return filtersList;
	}

	//Gets all the user attributes: email, username, password and service
	private static Attributes getUserAttributes(Node node) {
		Attributes user = new Attributes();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			user.setUsername(getTagValue("username", element));
			user.setPassword(getTagValue("password", element));
			user.setEmail(getTagValue("email", element));
			user.setPasswordEmail(getTagValue("passwordEmail", element));
			user.setService(getTagValue("service", element));
		}
		return user;
	}

	private static Attributes getFilterAttributes(Node node) {
		Attributes filter = new Attributes();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			filter.setKeyword(getTagValue("keyword", element));
			//System.out.println(getTagValue("keyword", element));

		}
		return filter;
	}

	//Gets a specific user attribute based on a Tag
	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}


	//Validates if the username and password introduced are in the XML file and if the login type (service) is "BDA"
	public boolean validateUserBDA(String username, String password) {
		readUsersXMLfile("config.xml");
		String user = new String();
		String pw = new String();
		String sr = new String();
		String em = new String();
		for (int i=0;i < usersList.size();i++)
		{
			user = usersList.get(i).getUsername();
			pw = usersList.get(i).getPassword();
			sr = usersList.get(i).getService();
			em = usersList.get(i).getEmail();
			if(user.equals(username) & pw.equals(password) & sr.equals("BDA"))			{
				authenticateUserEmail(em,usersList);
				return true;
			}
		}
		return false;
	}
	//Searches for the email associated to the username and read the messages in the inbox using the filters in XML file
	public static boolean authenticateUserEmail(String email, List<Attributes> usersList) {
		String s = "";
		for (int i = 0; i < usersList.size(); i++) {
			s = usersList.get(i).getEmail();
			if (email.equals(s)) {
				r.readMessages("imap.gmail.com", "imaps3", usersList.get(i).getEmail(), usersList.get(i).getPasswordEmail());
				return true; 
			}
		}
		return false;
	}

	//Validates if the username and password introduced are in the XML file and if the login type (service) is "Email"
	public boolean validateUserEmail(String email, String password) {
		readUsersXMLfile("config.xml");
		String e = new String();
		String pw = new String();
		String sr = new String();

		for (int i=0;i < usersList.size();i++){
			e = usersList.get(i).getEmail();
			pw = usersList.get(i).getPassword();
			sr = usersList.get(i).getService();
			if(email.equals(e) & pw.equals(password) & sr.equals("Email")) {
				return true;
			}
		}
		return false;
	}

}

