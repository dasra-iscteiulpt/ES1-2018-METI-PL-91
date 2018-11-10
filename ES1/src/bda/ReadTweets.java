package bda;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/** 
* Reads Tweets
* @author GROUP 91
* @version 1.0
* @since September 2018
*/

public class ReadTweets {

	private static ReadXMLfile r = new ReadXMLfile();

	/**
	 * Utility method to read ISCTE tweets that contain specific keywords
	 * @param username 
	 * @return ArrayList<Status>
	 */
	public ArrayList<Status> readTweets(String username){
		ArrayList<Status> twitterStatus = new ArrayList<Status>();
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
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Paging paging = new Paging(1, 200);
			List<Status> statusList = twitter.getUserTimeline("iscteiul", paging);
			List<Attributes> filtersList = new ArrayList<Attributes>();
			filtersList = r.readFiltersXMLfile("config.xml");
			for (Status status : statusList) {
				if(keywordValidation(status.getText(), filtersList)) {
					twitterStatus.add(status);
					System.out.println("Tweet from: " + status.getUser().getName() + System.lineSeparator() + "Text : " + status.getText() + System.lineSeparator() + "Id : " + status.getId() + System.lineSeparator() + "Created at : " + sdf.format(status.getCreatedAt()) + System.lineSeparator());
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to read tweets: " + te.getMessage());
		}
		return twitterStatus;
	}
	
	/** 
	 * Check for academic key words in the tweet
	 * @author GROUP 91
	 * @version 1.0
	 * @since September 
	 * @param keyword, is the tweet text
	 * @param list, is the list with keywords
	 * @return True if the post contains academic keywords
	 */
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
