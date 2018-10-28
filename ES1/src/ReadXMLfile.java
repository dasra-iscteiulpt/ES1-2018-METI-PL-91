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

	// VARIABLES
	static List<Attributes> usersList = new ArrayList<Attributes>();
	List<Attributes> filtersList = new ArrayList<Attributes>();
	static String[] userData = new String[2];
	static ReadEmails r = new ReadEmails();

	// CONSTRUCTOR
	public ReadXMLfile() {

	}

	/** 
	 * Read all users included in config.xml file
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @return A list of all users
	 */
	public static List<Attributes> readUsersXMLfile() {
		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// Use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// Parse using the builder to get the DOM mapping of the XML file
			Document doc = db.parse("config.xml");
			doc.getDocumentElement().normalize();

			// Adds the user attributes to the array
			NodeList userList = doc.getElementsByTagName("User");
			for (int i = 0; i < userList.getLength(); i++) {
				usersList.add(getUserAttributes(userList.item(i)));
			}
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		return usersList;
	}

	/** 
	 * Read all filters included in config.xml file
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param XML file path
	 * @return A list of all filters
	 */
	public List<Attributes> readFiltersXMLfile(String xml) {

		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			// Use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// Parse using the builder to get the DOM mapping of the XML file
			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();

			// Adds the filters to the array
			NodeList filterList = doc.getElementsByTagName("Filter");
			for (int i = 0; i < filterList.getLength(); i++) {
				filtersList.add(getFilterAttributes(filterList.item(i)));
			}

		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		return filtersList;
	}


	// Gets all the user attributes: email, user, password and service
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

	// Gets the filters: keyword
	private static Attributes getFilterAttributes(Node node) {
		Attributes filter = new Attributes();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			filter.setKeyword(getTagValue("keyword", element));
		}
		return filter;
	}

	// Gets a specific user attribute based on a Tag
	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

	// Validates if the user and password introduced are in the XML file and if the login type (service) is "BDA"
	public boolean validateUserBDA(String username, String password) {
		readUsersXMLfile();
		String user = new String();
		String pw = new String();
		String sr = new String();
		String em = new String();
		String pwe = new String();
		for (int i=0;i < usersList.size();i++)
		{
			user = usersList.get(i).getUsername();
			pw = usersList.get(i).getPassword();
			sr = usersList.get(i).getService();
			em = usersList.get(i).getEmail();
			pwe = usersList.get(i).getPasswordEmail();
			if(user.equals(username) & pw.equals(password) & sr.equals("BDA")){
				userData[0]=em;
				userData[1]=pwe;
				return true;
			}
		}
		return false;
	}
}