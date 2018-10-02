import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

//write a XML file that contains a set of attributes per user 

public class WriteXMLfile {

	public static void main(String argv[]) {

		try {
			// instance of a DocumentBuilderFactory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			// use factory to get an instance of document builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// user elements
			Document doc = docBuilder.newDocument();
			Element userElement = doc.createElement("users");
			doc.appendChild(userElement);

			//set attributes to users
			userElement.appendChild(getUser(doc, "1", "dasra@iscte-iul.pt", "dasra", "dasra123", "BDA"));
			userElement.appendChild(getUser(doc, "2", "dasra@facebook.com", "dasra-fb", "dasra123", "Facebook"));

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);
			
			//check current directory
			//String workingDir = System.getProperty("user.dir");
			//System.out.println("Current working directory : " + workingDir);
			   
			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	private static Node getUser(Document doc, String id, String email, String username, String password, String service) {
		Element user = doc.createElement("User");

		//set id attribute
		user.setAttribute("id", id);

		//create email element
		user.appendChild(getUserElements(doc, user, "email", email));

		//create username element
		user.appendChild(getUserElements(doc, user, "username", username));

		//create password element
		user.appendChild(getUserElements(doc, user, "password", password));

		//create service element
		user.appendChild(getUserElements(doc, user, "service", service));

		return user;
	}

	//utility method to create text node
	private static Node getUserElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}

