import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
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
			// Instance of a DocumentBuilderFactory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			// Use factory to get an instance of document builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// User elements
			Document doc = docBuilder.newDocument();
			Element userElement = doc.createElement("users");
			doc.appendChild(userElement);

			// These data have to be read from the data entered in the GUI
			userElement.appendChild(getUser(doc, "1", "dasra", "dasra123", "diana.es.pl.91@gmail.com", "engenhariasoftware","BDA"));
			userElement.appendChild(getUser(doc, "2", "rjfae1", "rjfae123", "ricardo.f.es.pl.91@gmail.com", "engenhariasoftware","BDA"));
			userElement.appendChild(getUser(doc, "3", "dgprs", "dgprs123", "diana.es.pl.91@gmail.com", "engenhariasoftware","BDA"));
			userElement.appendChild(getUser(doc, "4", "iccco", "iccco123", "diana.es.pl.91@gmail.com", "engenhariasoftware","BDA"));

			// These data have to be read from the data entered in the GUI
			userElement.appendChild(getFilter(doc, "ISCTE"));
			userElement.appendChild(getFilter(doc, "exame"));
			userElement.appendChild(getFilter(doc, "aula"));
			userElement.appendChild(getFilter(doc, "universidade"));
			userElement.appendChild(getFilter(doc, "disciplina"));
			userElement.appendChild(getFilter(doc, "projecto"));

			// Write the content into XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);

			System.out.println("File saved!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Node getUser(Document doc, String id, String username, String password, String email, String passwordEmail, String service) {
		Element user = doc.createElement("User");

		// Set id attribute
		user.setAttribute("id", id);

		// Create user element
		user.appendChild(getUserElements(doc, user, "username", username));

		// Create password element
		user.appendChild(getUserElements(doc, user, "password", password));

		// Create email element
		user.appendChild(getUserElements(doc, user, "email", email));

		// Create password email element
		user.appendChild(getUserElements(doc, user, "passwordEmail", passwordEmail));

		// Create service element
		user.appendChild(getUserElements(doc, user, "service", service));

		return user;
	}

	private static Node getFilter(Document doc, String keyword) {
		Element filter = doc.createElement("Filter");

		filter.appendChild(getFilterElements(doc, filter, "keyword", keyword));

		return filter;
	}

	// Utility method to create text node
	private static Node getUserElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	// Utility method to create text node
	private static Node getFilterElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

}