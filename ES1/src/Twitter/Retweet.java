package Twitter;
import XML.ReadXMLfile;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/** 
 * Retweet to a specific tweet
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

public class Retweet {

	private static ReadXMLfile r = new ReadXMLfile();

	/**
	 * Utility method to retweet to ISCTE tweets
	 * @param toUser, is ISCTE
	 * @param fromUser, is the BDA user
	 * @param tweetId, is the tweet identification
	 * @param comment, is the text of the retweet
	 * @return int, 0 = successful, 1 = failed
	 */
	public int retweet(String toUser, String fromUser, Long tweetId, String comment) {
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
			twitter.updateStatus(comment + " https://twitter.com/edent/status/" + tweetId);
			System.out.println("Retweet successful");
			return 0;
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to retweet: " + te.getMessage());
			return 1;
		}
	}
}


