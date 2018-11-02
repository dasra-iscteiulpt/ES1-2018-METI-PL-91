import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.Message;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class ReadTweets {

	private static ReadXMLfile r = new ReadXMLfile();

	public static void main(String[] args) throws TwitterException, IOException {
		//This data has to be read from the GUI
		new ReadTweets().readTweets("dasra");
	}

	public List<Status> readTweets(String username){
		r.validateUserTwitter(username);
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(ReadXMLfile.twitterData[0])
		.setOAuthConsumerSecret(ReadXMLfile.twitterData[1])
		.setOAuthAccessToken(ReadXMLfile.twitterData[2])
		.setOAuthAccessTokenSecret(ReadXMLfile.twitterData[3]);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		try {
			List<Status> statusList = twitter.getUserTimeline("iscteiul");
			List<Attributes> filtersList = new ArrayList<Attributes>();
			filtersList = r.readFiltersXMLfile("config.xml");
			for (Status status : statusList) {
				if(keywordValidation(status.getText(), filtersList)) {
					System.out.println(status.getUser().getName() + " : " + status.getText());
				}
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean keywordValidation(String keyword, List<Attributes> list) {
		String s = "";
		for (int i = 0; i < list.size(); i++) {
			s =list.get(i).getKeyword();
			if (keyword.toLowerCase().contains(s.toLowerCase())) {
				return true; 
			}
		}
		return false; 
	}

}
