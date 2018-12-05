package BDA;
import java.io.File;
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
	private List<Attributes> usersList = new ArrayList<Attributes>();
	private List<Attributes> filtersList = new ArrayList<Attributes>();
	private static ArrayList<GenericMessage> messagesList = new ArrayList<GenericMessage>();
	public static String[] userData = new String[3];
	public static String[] twitterData = new String[4];
	public static String facebookData = new String();
	
	/** 
	 * Constructor
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 */
	public ReadXMLfile() {

	}

	public int getUsersListSize() {
		return this.usersList.size();
	} 
	
	/** 
	 * Read all users included in config.xml file
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @return A list with all users attributes
	 */
	public void readUsersXMLfile() {
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
	}

	/** 
	 * Read all filters included in config.xml file
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @return A list with all the filters
	 */
	public List<Attributes> readFiltersXMLfile() {

		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			// Use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// Parse using the builder to get the DOM mapping of the XML file
			Document doc = db.parse("config.xml");
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


	/** 
	 * Read all messages included in usersBackup.xml file
	 * @author GROUP 91
	 * @version 1.0
	 * @since September
	 * @param username, the user identification
	 * @return A list with all messages
	 */
	public ArrayList<GenericMessage> readMessagesXMLfile(String username) {
		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// Use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// test to see if a file exists
			File tmpDir = new File("WorkOfflineBackup/userBackup_" + username + ".xml");
			boolean exists = tmpDir.exists();
			if (exists == true){
				{
					// Parse using the builder to get the DOM mapping of the XML file
					Document doc = db.parse("WorkOfflineBackup/userBackup_" + username + ".xml");
					doc.getDocumentElement().normalize();

					// Adds the user attributes to the array
					NodeList gmList = doc.getElementsByTagName("GM");
					for (int i = 0; i < gmList.getLength(); i++) {
						messagesList.add(getMessagesAttributes(gmList.item(i)));
						//System.out.println(messagesList.get(i).getTitleM());
					}
				}
			}
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		return messagesList;
	}

	/**
	 * Gets all the user attributes: email, user, password and service
	 * @param node, is one of the mandatory parameters for getting user attributes
	 * @return Returns user attributes
	 */
	private static Attributes getUserAttributes(Node node) {
		Attributes user = new Attributes();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			user.setUsername(getTagValue("username", element));
			user.setPassword(getTagValue("password", element));
			user.setEmail(getTagValue("email", element));
			user.setPasswordEmail(getTagValue("passwordEmail", element));
			user.setOAuthConsumerKey(getTagValue("OAuthConsumerKey", element));
			user.setOAuthConsumerSecret(getTagValue("OAuthConsumerSecret", element));
			user.setOAuthAccessToken(getTagValue("OAuthAccessToken", element));
			user.setOAuthAccessTokenSecret(getTagValue("OAuthAccessTokenSecret", element));
			user.setUserAccessToken(getTagValue("userAccessToken", element));
		}
		return user;
	}

	/**
	 * Gets all the messages attributes: email, user, password and service
	 * @param node, is one of the mandatory parameters for getting user attributes
	 * @return Returns user attributes
	 */
	private static GenericMessage getMessagesAttributes(Node node) {
		GenericMessage gm = new GenericMessage("","","","","");
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			gm.setDateM(getTagValue("date", element));
			gm.setCanalM(getTagValue("channel", element));
			gm.setTitleM(getTagValue("subject", element));
			gm.setFromM(getTagValue("from", element));
			gm.setContentM(getTagValue("content", element));
		}
		return gm;
	}

	/**
	 * Gets the filters: keyword
	 * @param node, is one of the mandatory parameters for getting filter attributes
	 * @return Returns filter attributes
	 */
	private static Attributes getFilterAttributes(Node node) {
		Attributes filter = new Attributes();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			filter.setKeyword(getTagValue("keyword", element));
		}
		return filter;
	}

	/**
	 * Gets a specific user attribute based on a Tag
	 * @param tag, is one of the mandatory parameters for getting tag value
	 * @param element, is one of the mandatory parameters for getting tag value
	 * @return Returns tag value
	 */
	static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

	/**
	 * Validates if the user and password introduced are in the XML file and setup the user data configuration
	 * @param username, is one of the mandatory parameters for validating an user
	 * @param password, is one of the mandatory parameters for validating an user
	 * @return Returns true case user is successfully validated and false otherwise
	 */
	public boolean validateUserBDA(String username, String password) {
		readUsersXMLfile();
		String user = new String();
		String pw = new String();
		String em = new String();
		String pwe = new String();
		for (int i=0;i < usersList.size();i++)
		{
			user = usersList.get(i).getUsername();
			pw = usersList.get(i).getPassword();
			em = usersList.get(i).getEmail();
			pwe = usersList.get(i).getPasswordEmail();
			if(user.equals(username) & pw.equals(password)){
				userData[0] = em;
				userData[1] = pwe;
				userData[2] = user;
				return true;
			}
		}
		return false;
	}

	/**
	 * Validates if the user is in the XML file and setup the twitter data configuration
	 * @param username, is one of the mandatory parameters for validating an user
	 * @return Returns true case user is successfully validated and false otherwise
	 */
	public boolean validateUserTwitter(String username) {
		readUsersXMLfile();
		String user = new String();
		String OAuthConsumerKey = new String();
		String OAuthConsumerSecret = new String();
		String AuthAccessToken = new String();
		String OAuthAccessTokenSecret = new String();
		for (int i=0;i < usersList.size();i++)
		{
			user = usersList.get(i).getUsername();
			OAuthConsumerKey = usersList.get(i).getOAuthConsumerKey();
			OAuthConsumerSecret = usersList.get(i).getOAuthConsumerSecret();
			AuthAccessToken = usersList.get(i).getOAuthAccessToken();
			OAuthAccessTokenSecret = usersList.get(i).getOAuthAccessTokenSecret();
			if(user.equals(username)){
				twitterData[0] = OAuthConsumerKey;
				twitterData[1] = OAuthConsumerSecret;
				twitterData[2] = AuthAccessToken;
				twitterData[3] = OAuthAccessTokenSecret;
				return true;
			}
		}
		return false;
	}

	/**
	 * Validates if the user is in the XML file and setup the facebook data configuration
	 * @param username, is one of the mandatory parameters for validating an user
	 * @return Returns true case user is successfully validated and false otherwise
	 */
	public boolean validateUserFacebook(String username) {
		readUsersXMLfile();
		String user = new String();
		String userAccessToken = new String();
		for (int i=0;i < usersList.size();i++)
		{
			user = usersList.get(i).getUsername();
			userAccessToken = usersList.get(i).getUserAccessToken();
			if(user.equals(username)){
				facebookData = userAccessToken;
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to setup the user data configuration
	 * @param username, is one of the mandatory parameters 
	 * @return Returns true case the setup is successful and false otherwise
	 */
	public boolean setupRegister(String username) {
		readUsersXMLfile();
		String user = new String();
		String em = new String();
		String pwe = new String();
		String OAuthConsumerKey = new String();
		String OAuthConsumerSecret = new String();
		String AuthAccessToken = new String();
		String OAuthAccessTokenSecret = new String();
		String userAccessToken = new String();

		for (int i=0;i < usersList.size();i++)
		{
			user = usersList.get(i).getUsername();
			em = usersList.get(i).getEmail();
			pwe = usersList.get(i).getPasswordEmail();
			OAuthConsumerKey = usersList.get(i).getOAuthConsumerKey();
			OAuthConsumerSecret = usersList.get(i).getOAuthConsumerSecret();
			AuthAccessToken = usersList.get(i).getOAuthAccessToken();
			OAuthAccessTokenSecret = usersList.get(i).getOAuthAccessTokenSecret();
			userAccessToken = usersList.get(i).getUserAccessToken();
			if(user.equals(username)){
				userData[0] = em;
				userData[1] = pwe;
				twitterData[0] = OAuthConsumerKey;
				twitterData[1] = OAuthConsumerSecret;
				twitterData[2] = AuthAccessToken;
				twitterData[3] = OAuthAccessTokenSecret;
				facebookData = userAccessToken;
				return true;
			}
		}
		return false;
	}

	/**
	 * Validates if a user already exists in the XML file
	 * @param username, is one of the mandatory parameters for validating an user
	 * @return Returns true case user already exists and false otherwise
	 */
	public boolean validateUserRegister(String username) {
		readUsersXMLfile();
		String user = new String();
		for (int i=0;i < usersList.size();i++)
		{
			user = usersList.get(i).getUsername();
			if(user.equals(username)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Validates if a filter already exists in the XML file
	 * @param keyword, is one of the mandatory parameters for validating a filter
	 * @return Returns true case filter already exists and false otherwise
	 */
	public boolean validateFilter(String keyword) {
		readFiltersXMLfile();
		String k = new String();
		for (int i=0;i < filtersList.size();i++)
		{
			k = filtersList.get(i).getKeyword();
			if(k.equals(keyword.toLowerCase())){
				return true;
			}
		}
		return false;
	}
}
