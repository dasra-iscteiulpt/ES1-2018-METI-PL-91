package BDA;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** 
 * Write in a XML file that contains a set of attributes per user and keywords
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

public class WriteXMLfile {

	public static void main(String argv[]) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		setupFile();

		//These data have to be read from the data entered in the GUI
		addUser("dasra", "dasra123", "diana.es.pl.91@gmail.com", "engenhariasoftware", "dpthvBBPVVsWxWituNu1CBx9h", "Nsuy5xBRdruPTaBms8GfQVtFsywI16zGyExf27THMHmmEm6d2W", "1056561597659914240-pHb2DY7rIzqtPTVyohg7zVZGEoqX6B", "B2WrSZcGM9LqmyrUbvmHKesS5S7hDYuvlHKKhmVlJU38N", "EAADpFZBDLw5QBADta3Qw8JqjHPFlShpQHhsodQhywzIZBTfdQp5oJJMBSZBP8qQrVkaiA7fmmBFZAE5xKazkRJRzyekZBL8gnThcJ0f7RoLtaNwseHJgZCUJtYZC6yyoLS1q0ZBvgumVQMG56c1rovf5iWwZAX5jjIi4nVcwM39ZABCRZCd3HZA9TN7daJZC79HSw4kcZD");
		addUser("rjfae1", "rjfae123", "ricardo.f.es.pl.91@gmail.com", "engenhariasoftware", "DF5DCJ5FACNHxE1tLXZwU6Psb", "IPezOOcIibcLbdGextZlwPrV86f19BuBz4ltTVh2Ki9XSr3X4L", "1056591307521646593-dBBRKbiDGKn7CDqI9x0uu34FybAWeA", "lQfqJuHcliUr0iQdMHh11M8IJMFUW0WBwc898ePUZNaHb", "EAAgq4lhYUWYBAOcVQlFoJumaZBUUiPGDuSSW0xOFrH0JqZCZBCFx04q3YIlBrpZCga4ZBEp2S7ZBXlkAllFuTDfishHVXvze0YEAuOOjy02BwnJteWhdhkWVZBlCkd6WUcnHwGK8fT2fzKEumkte4eJfvf1paXViPEEDLhQV6YMZCOFSsBSDq2JuH5HPXrp0OAQZD");
		//These data have to be read from the data entered in the GUI	
		addFilter("tecnologia");
		addFilter("biblioteca");
		addFilter("mestrado");
		addFilter("universidade");
		//removeUser("teste");
		//removeUser("rjfae1");
		//removeFilter("universidade");
		setUserAttribute("iccco", "password", "teste");
	}

	/**
	 * Utility method to setup an initial file 
	 */
	private static void setupFile(){
		try {
			// Instance of a DocumentBuilderFactory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

			// Use factory to get an instance of document builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// User elements
			Document doc = docBuilder.newDocument();

			// Add the new users node
			Element userElement = doc.createElement("users");
			doc.appendChild(userElement);

			// Add the new user and filter nodes
			userElement.appendChild(createUserBDA(doc, "iccco", "iccco123", "ivo.es.pl.91@gmail.com", "engenhariasoftware", "dpthvBBPVVsWxWituNu1CBx9h", "Nsuy5xBRdruPTaBms8GfQVtFsywI16zGyExf27THMHmmEm6d2W", "1056561597659914240-pHb2DY7rIzqtPTVyohg7zVZGEoqX6B", "B2WrSZcGM9LqmyrUbvmHKesS5S7hDYuvlHKKhmVlJU38N", "EAAgq4lhYUWYBAAXNa26kJl11NBPtOLllQrHxq6b1WhmT78hOsiFnoBfccD6Q71NEM4ZATBZCiuu6MEg47HZAzxFLocvTbJSWASWnvbKbLgGFJ4eYp6SQ9EgxZAonJBBYJ8I3sdRyhCeZBad7H9Gq7zB9cWC1Vm443XDxVvJukrTWtgqZBzOMXi"));
			//userElement.appendChild(createFilter(doc,"iscte"));
			userElement.appendChild(createFilter(doc,"aula"));
			userElement.appendChild(createFilter(doc,"universidade"));
			userElement.appendChild(createFilter(doc,"disciplina"));
			userElement.appendChild(createFilter(doc,"projecto"));

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			System.out.println("XML file updated successfully");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * Utility method to write an XML with all the messages
	 * @param username, is one of the mandatory parameters for writing the backup file
	 */
	public static void writeMessage(String username){
		try {
			// Instance of a DocumentBuilderFactory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

			// Use factory to get an instance of document builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Message elements
			Document doc = docBuilder.newDocument();

			// Add the new message node
			Element userElement = doc.createElement("Message");
			doc.appendChild(userElement);

			// Add the new message
			ArrayList<GenericMessage> gm = WindowDBA.getGM(); 
			for (int i = 0; i < gm.size(); i++) {
				userElement.appendChild(createMessage(doc, gm.get(i).getDateM(), gm.get(i).getCanalM(), gm.get(i).getFromM(), gm.get(i).getTitleM(), gm.get(i).getContentM()));
				System.out.println(gm.get(i));
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("WorkOfflineBackup/userBackup_" + username + ".xml"));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			System.out.println("XML file updated successfully");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * Utility method to add an user to an existing XML file
	 * @param username, is one of the mandatory parameters for adding an user
	 * @param password, is one of the mandatory parameters for adding an user
	 * @param email, is one of the mandatory parameters for adding an user
	 * @param passwordEmail, is one of the mandatory parameters for adding an user
	 * @param OAuthConsumerKey, is one of the mandatory parameters for adding an user
	 * @param OAuthConsumerSecret, is one of the mandatory parameters for adding an user
	 * @param OAuthAccessToken, is one of the mandatory parameters for adding an user
	 * @param OAuthAccessTokenSecret, is one of the mandatory parameters for adding an user
	 * @param userAccessToken, is one of the mandatory parameters for adding an user
	 * @return Returns true case user is successfully added and false otherwise
	 */
	public static boolean addUser(String username, String password, String email, String passwordEmail, String OAuthConsumerKey, String OAuthConsumerSecret, String OAuthAccessToken, String OAuthAccessTokenSecret, String userAccessToken){
		try {
			// Instance of a DocumentBuilderFactory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

			// Use factory to get an instance of document builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Get an instance of existing document
			Document existingdoc = docBuilder.parse("config.xml");

			// Create an empty document
			Document doc = docBuilder.newDocument();

			// Add the new root node
			Node copy = doc.importNode(existingdoc.getDocumentElement(), true);
			doc.appendChild(copy);
			copy.appendChild(createUserBDA(doc, username, password, email, passwordEmail, OAuthConsumerKey, OAuthConsumerSecret, OAuthAccessToken, OAuthAccessTokenSecret, userAccessToken));

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			System.out.println("User added successfully");
			return true;

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			return false;
		} catch (SAXException sax) {
			sax.printStackTrace();
			return false;
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		}
	}

	/**
	 * Utility method to add a filter to an existing XML file
	 * @param keyword, is one of the mandatory parameters for adding a filter
	 * @return Returns true case filter is successfully added and false otherwise
	 */
	public static boolean addFilter(String keyword){
		try {
			// Instance of a DocumentBuilderFactory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

			// Use factory to get an instance of document builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Get an instance of existing document
			Document existingdoc = docBuilder.parse("config.xml");

			// Create an empty document
			Document doc = docBuilder.newDocument();

			// Add the new root node
			Node copy = doc.importNode(existingdoc.getDocumentElement(), true);
			doc.appendChild(copy);
			copy.appendChild(createFilter(doc, keyword));

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			System.out.println("Filter added successfully");
			return true;

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			return false;
		} catch (SAXException sax) {
			sax.printStackTrace();
			return false;
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		}
	}

	/**
	 * Utility method to remove an user of an existing XML file
	 * @param username, is one of the mandatory parameters for removing an user
	 * @return Returns true case user is successfully removed and false otherwise
	 */
	public static boolean removeUser(String username){
		try {
			// Instance of a DocumentBuilderFactory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

			// Use factory to get an instance of document builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Instance of existing document
			Document doc = docBuilder.parse("config.xml");

			// Get user nodes
			NodeList usersList = doc.getElementsByTagName("User");
			System.out.println(usersList.getLength());
			for (int i = 0; i < usersList.getLength(); i++) {
				Node node = usersList.item(i);
				Element e = (Element) node;
				String s = e.getAttributes().getNamedItem("Id").getNodeValue();
				if(username.equals(s)) {
					doc.getDocumentElement().removeChild(node);
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);
			System.out.println("User removed successfully");
			return true;

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			return false;
		} catch (SAXException sax) {
			sax.printStackTrace();
			return false;
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		}
	}

	/**
	 * Utility method to remove a filter of an existing XML file
	 * @param keyword, is one of the mandatory parameters for removing a filter
	 * @return Returns true case filter is successfully removed and false otherwise
	 */
	public static boolean removeFilter(String keyword) {
		try {
			// Instance of a DocumentBuilderFactory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

			// Use factory to get an instance of document builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Instance of existing document
			Document doc = docBuilder.parse("config.xml");

			Attributes filter = new Attributes();

			// Get filter nodes
			NodeList filtersList = doc.getElementsByTagName("Filter");
			System.out.println(filtersList.getLength());
			for (int i = 0; i < filtersList.getLength(); i++) {
				Node node = filtersList.item(i);
				Element e = (Element) node;
				filter.setKeyword(ReadXMLfile.getTagValue("keyword", e));
				String s = filter.getKeyword();
				if(keyword.equals(s)) {
					doc.getDocumentElement().removeChild(node);
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);
			System.out.println("Filter removed successfully");
			return true;

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			return false;
		} catch (SAXException sax) {
			sax.printStackTrace();
			return false;
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		}
	}

	/**
	 * Utility method to modify an user attribute in an existing XML file
	 * @param username, is one of the mandatory parameters for setting an attribute
	 * @param tag, is one of the mandatory parameters for setting an attribute
	 * @param newValue, is one of the mandatory parameters for setting an attribute
	 * @return Returns true case attribute is successfully modified and false otherwise
	 */
	public static boolean setUserAttribute(String username, String tag, String newValue){
		try {
			// Instance of a DocumentBuilderFactory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

			// Use factory to get an instance of document builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Get an instance of existing document
			Document doc = docBuilder.parse("config.xml");
			doc.getDocumentElement().normalize();

			NodeList usersList = doc.getElementsByTagName("User");
			//Get the user element by tag name directly
			for (int i = 0; i < usersList.getLength(); i++) {
				Node user = usersList.item(i);
				Element e = (Element) user;
				String s = e.getAttributes().getNamedItem("Id").getNodeValue();
				if(username.equals(s)) {
					Node node = doc.getElementsByTagName(tag).item(i);
					if (node.getNodeName().equals(tag)) {
						node.getFirstChild().setNodeValue(newValue) ;
					}
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			System.out.println("User tag value modified successfully");
			return true;

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			return false;
		} catch (SAXException sax) {
			sax.printStackTrace();
			return false;
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		}
	}

	/**
	 * Utility method to create a user
	 * @param doc, is one of the mandatory parameters for creating an user
	 * @param username, is one of the mandatory parameters for creating an user
	 * @param password, is one of the mandatory parameters for creating an user
	 * @param email, is one of the mandatory parameters for creating an user
	 * @param passwordEmail, is one of the mandatory parameters for creating an user
	 * @param OAuthConsumerKey, is one of the mandatory parameters for creating an user
	 * @param OAuthConsumerSecret, is one of the mandatory parameters for creating an user
	 * @param OAuthAccessToken, is one of the mandatory parameters for creating an user
	 * @param OAuthAccessTokenSecret, is one of the mandatory parameters for creating an user
	 * @param userAccessToken, is one of the mandatory parameters for creating an user
	 * @return Returns an user Node
	 */
	public static Node createUserBDA(Document doc, String username, String password, String email, String passwordEmail, String OAuthConsumerKey, String OAuthConsumerSecret, String OAuthAccessToken, String OAuthAccessTokenSecret, String userAccessToken) {
		Element user = doc.createElement("User");

		// Set id attribute
		user.setAttribute("Id", username);

		// Create user element
		user.appendChild(getUserElements(doc, user, "username", username));

		// Create password element
		user.appendChild(getUserElements(doc, user, "password", password));

		// Create email element
		user.appendChild(getUserElements(doc, user, "email", email));

		// Create password email element
		user.appendChild(getUserElements(doc, user, "passwordEmail", passwordEmail));

		// Create OAuthConsumerKey element
		user.appendChild(getUserElements(doc, user, "OAuthConsumerKey", OAuthConsumerKey));

		// Create OAuthConsumerSecret element
		user.appendChild(getUserElements(doc, user, "OAuthConsumerSecret", OAuthConsumerSecret));

		// Create OAuthAccessToken element
		user.appendChild(getUserElements(doc, user, "OAuthAccessToken", OAuthAccessToken));

		// Create OAuthAccessTokenSecret element
		user.appendChild(getUserElements(doc, user, "OAuthAccessTokenSecret", OAuthAccessTokenSecret));

		// Create userAccessToken element
		user.appendChild(getUserElements(doc, user, "userAccessToken", userAccessToken));

		return user;
	}

	/**
	 * Utility method to create a message
	 * @param doc, is one of the mandatory parameters for creating a message
	 * @param date, is one of the mandatory parameters for creating a message
	 * @param channel, is one of the mandatory parameters for creating a message
	 * @param from, is one of the mandatory parameters for creating a message
	 * @param subject, is one of the mandatory parameters for creating a message
	 * @param content, is one of the mandatory parameters for creating a message
	 * @return Returns an user Node
	 */
	public static Node createMessage(Document doc, String date, String channel, String from, String subject, String content) {
		Element user = doc.createElement("GM");

		// Create date element
		user.appendChild(getMessageElements(doc, user, "date", date));

		// Create channel element
		user.appendChild(getMessageElements(doc, user, "channel", channel));

		// Create from element
		user.appendChild(getMessageElements(doc, user, "from", from));

		// Create subject element
		user.appendChild(getMessageElements(doc, user, "subject", subject));

		// Create content element
		user.appendChild(getMessageElements(doc, user, "content", content));

		return user;
	}

	/**
	 * Utility method to create user element
	 * @param doc, is one of the mandatory parameters for getting an element
	 * @param element, is one of the mandatory parameters for getting an element
	 * @param name, is one of the mandatory parameters for getting an element
	 * @param value, is one of the mandatory parameters for getting an element
	 * @return Returns an element Node  
	 */
	private static Node getUserElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	/**
	 * Utility method to create message element
	 * @param doc, is one of the mandatory parameters for getting an element
	 * @param element, is one of the mandatory parameters for getting an element
	 * @param name, is one of the mandatory parameters for getting an element
	 * @param value, is one of the mandatory parameters for getting an element
	 * @return Returns an element Node  
	 */
	private static Node getMessageElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	/**
	 * Utility method to create a filter
	 * @param doc, is one of the mandatory parameters for creating a filter
	 * @param keyword, is one of the mandatory parameters for creating a filter
	 * @return Returns a filter Node 
	 */
	private static Node createFilter(Document doc, String keyword) {
		Element filter = doc.createElement("Filter");
		filter.appendChild(getFilterElements(doc, filter, "keyword", keyword));
		return filter;
	}

	/**
	 * Utility method to create filter element
	 * @param doc, is one of the mandatory parameters for getting an element
	 * @param element, is one of the mandatory parameters for getting an element
	 * @param name, is one of the mandatory parameters for getting an element
	 * @param value, is one of the mandatory parameters for getting an element
	 * @return Returns an element Node 
	 */
	private static Node getFilterElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}