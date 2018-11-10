package bda;
import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/** 
* Response to a specific tweet message
* @author GROUP 91
* @version 1.0
* @since September 2018
*/

public class Retweet {

	private static ReadXMLfile r = new ReadXMLfile();

	public static void main(String[] args) throws TwitterException, IOException {
		//This data has to be read from the GUI
		new Retweet().retweet("iscteiul", "dasra", Long.valueOf("978945191842340864"));
	}

	/**
	 * Utility method to retweet ISCTE tweets
	 * @param toUser 
	 * @param fromUser
	 * @param tweetId
	 * @return int, 0 = successful, 1 = failed
	 */
	public int retweet(String toUser, String fromUser, Long tweetId) {{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		r.validateUserTwitter(fromUser);
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(ReadXMLfile.twitterData[0])
		.setOAuthConsumerSecret(ReadXMLfile.twitterData[1])
		.setOAuthAccessToken(ReadXMLfile.twitterData[2])
		.setOAuthAccessTokenSecret(ReadXMLfile.twitterData[3]);

		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
		twitter.setOAuthConsumer(ReadXMLfile.twitterData[0], ReadXMLfile.twitterData[1]);
		AccessToken accessToken = new AccessToken(ReadXMLfile.twitterData[2], ReadXMLfile.twitterData[3]);
		twitter.setOAuthAccessToken(accessToken);

		try {
			twitter.retweetStatus(tweetId);
			System.out.println("Retweet successful");
			return 0;
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to retweet: " + te.getMessage());
			return 1;
		}
	}
	}
}


