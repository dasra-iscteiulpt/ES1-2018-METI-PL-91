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

import java.io.File;
import java.util.List;

public class ReadXMLfile {

	List<Attributes> usersList = new ArrayList<Attributes>();

	public List<Attributes> readXMLfile(String xml) {

		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using the builder to get the DOM mapping of the XML file
			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();

			//adds the user data to the array
			NodeList nodeList = doc.getElementsByTagName("User");
			for (int i = 0; i < nodeList.getLength(); i++) {
				usersList.add(getAttributes(nodeList.item(i)));
			}

		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		return usersList;

	}
	
	//Gets all the user attributes: email, username, password and service
	private static Attributes getAttributes(Node node) {
		Attributes user = new Attributes();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			user.setEmail(getTagValue("email", element));
			user.setUsername(getTagValue("username", element));
			user.setPassword(getTagValue("password", element));
			user.setService(getTagValue("service", element));
		}
		return user;
	}

	//Gets a specific user attribute based on a Tag
	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

	//Validates if the username and password introduced are in the XML file and if the login type (service) is "BDA"
	public boolean validateUser(String username, String password) {
		readXMLfile("config.xml");
		String user = new String();
		String pw = new String();
		String sr = new String();
		for (int i=0;i < usersList.size();i++)
		{
			user = usersList.get(i).getUsername();
			pw = usersList.get(i).getPassword();
			sr = usersList.get(i).getService();
			//System.out.println(user);
			//System.out.println(pw);
			//System.out.println(sr);
			if(user.equals(username) & pw.equals(password) & sr.equals("BDA"))
				return true;
		}
		return false;
	}

}

