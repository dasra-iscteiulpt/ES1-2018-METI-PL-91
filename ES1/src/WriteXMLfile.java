import java.io.File;
import java.io.IOException;

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

//write a XML file that contains a set of attributes per user 

public class WriteXMLfile {

	public static void main(String argv[]) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		setupFile();

		//These data have to be read from the data entered in the GUI
		addUser("dasra", "dasra123", "diana.es.pl.91@gmail.com", "engenhariasoftware", "dpthvBBPVVsWxWituNu1CBx9h", "Nsuy5xBRdruPTaBms8GfQVtFsywI16zGyExf27THMHmmEm6d2W", "1056561597659914240-pHb2DY7rIzqtPTVyohg7zVZGEoqX6B", "B2WrSZcGM9LqmyrUbvmHKesS5S7hDYuvlHKKhmVlJU38N");
		addUser("rjfae1", "rjfae123", "ricardo.f.es.pl.91@gmail.com", "engenhariasoftware", "DF5DCJ5FACNHxE1tLXZwU6Psb", "IPezOOcIibcLbdGextZlwPrV86f19BuBz4ltTVh2Ki9XSr3X4L", "1056591307521646593-dBBRKbiDGKn7CDqI9x0uu34FybAWeA", "lQfqJuHcliUr0iQdMHh11M8IJMFUW0WBwc898ePUZNaHb");
		//These data have to be read from the data entered in the GUI	
		//addFilter("ciencia");
		//addFilter("biblioteca");

		//removeUser("rjfae1");
		//removeFilter("iscte");

		setUserAttribute("iccco", "password", "teste");
	}

	// Utility method to setup an initial file 
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
			userElement.appendChild(createUserBDA(doc, "iccco", "iccco123", "ivo.es.pl.91@gmail.com", "engenhariasoftware", "dpthvBBPVVsWxWituNu1CBx9h", "Nsuy5xBRdruPTaBms8GfQVtFsywI16zGyExf27THMHmmEm6d2W", "1056561597659914240-pHb2DY7rIzqtPTVyohg7zVZGEoqX6B", "B2WrSZcGM9LqmyrUbvmHKesS5S7hDYuvlHKKhmVlJU38N"));
			userElement.appendChild(createFilter(doc,"iscte"));
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

	// Utility method to add an user to an existing XML file
	private static void addUser(String username, String password, String email, String passwordEmail, String OAuthConsumerKey, String OAuthConsumerSecret, String OAuthAccessToken, String OAuthAccessTokenSecret){
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
			copy.appendChild(createUserBDA(doc, username, password, email, passwordEmail, OAuthConsumerKey, OAuthConsumerSecret, OAuthAccessToken, OAuthAccessTokenSecret));

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			System.out.println("User added successfully");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException sax) {
			sax.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	// Utility method to add a filter to an existing XML file
	private static void addFilter(String keyword){
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

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException sax) {
			sax.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	// Utility method to remove an user of an existing XML file
	private static void removeUser(String username){
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

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException sax) {
			sax.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	// Utility method to remove a filter of an existing XML file
	private static void removeFilter(String keyword) {
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
				System.out.println(s);
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

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException sax) {
			sax.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	// Utility method to modify an user attribute in an existing XML file
	private static void setUserAttribute(String username, String tag, String newValue) throws ParserConfigurationException, SAXException, IOException, TransformerException{
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
	}

	// Utility method to create a user
	private static Node createUserBDA(Document doc, String username, String password, String email, String passwordEmail, String OAuthConsumerKey, String OAuthConsumerSecret, String OAuthAccessToken, String OAuthAccessTokenSecret) {
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

		return user;
	}

	// Utility method to create user node
	private static Node getUserElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}


	// Utility method to create filter
	private static Node createFilter(Document doc, String keyword) {
		Element filter = doc.createElement("Filter");
		filter.appendChild(getFilterElements(doc, filter, "keyword", keyword));
		return filter;
	}

	// Utility method to create filter node
	private static Node getFilterElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}