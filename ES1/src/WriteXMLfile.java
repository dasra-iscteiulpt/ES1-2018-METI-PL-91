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

			//Estes dados têm que ser lidos a partir dos dados introduzidos na GUI
			userElement.appendChild(getUser(doc, "1", "dasra", "dasra123", "diana.es.pl.91@gmail.com", "engenhariasoftware","BDA"));
			userElement.appendChild(getUser(doc, "2", "rjfae1", "rjfae123", "diana.es.pl.91@gmail.com", "engenhariasoftware","BDA"));
			userElement.appendChild(getUser(doc, "3", "dgprs", "dgprs123", "diana.es.pl.91@gmail.com", "engenhariasoftware","BDA"));
			userElement.appendChild(getUser(doc, "4", "iccco", "iccco123", "diana.es.pl.91@gmail.com", "engenhariasoftware","BDA"));

			//Estes dados têm que ser lidos a partir dos dados introduzidos na GUI

			userElement.appendChild(getFilter(doc, "ISCTE"));
			userElement.appendChild(getFilter(doc, "exame"));
			userElement.appendChild(getFilter(doc, "aula"));
			userElement.appendChild(getFilter(doc, "universidade"));
			userElement.appendChild(getFilter(doc, "disciplina"));
			userElement.appendChild(getFilter(doc, "projecto"));

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

	private static Node getUser(Document doc, String id, String username, String password, String email, String passwordEmail, String service) {
		Element user = doc.createElement("User");

		//set id attribute
		user.setAttribute("id", id);

		//create username element
		user.appendChild(getUserElements(doc, user, "username", username));

		//create password element
		user.appendChild(getUserElements(doc, user, "password", password));

		//create email element
		user.appendChild(getUserElements(doc, user, "email", email));

		//create password email element
		user.appendChild(getUserElements(doc, user, "passwordEmail", passwordEmail));

		//create service element
		user.appendChild(getUserElements(doc, user, "service", service));

		return user;
	}

	private static Node getFilter(Document doc, String keyword) {
		Element filter = doc.createElement("Filter");

		//set id attribute
		//filter.setAttribute("keyword", keyword);


		//create keyword element
		filter.appendChild(getFilterElements(doc, filter, "keyword", keyword));

		return filter;
	}

	//utility method to create text node
	private static Node getUserElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	private static Node getFilterElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

}

