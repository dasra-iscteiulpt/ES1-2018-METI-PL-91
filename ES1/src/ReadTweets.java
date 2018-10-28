import java.util.Iterator;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class ReadTweets {

	public static void main(String[] args) {
		new ReadTweets().readTweets();
	}

	public void readTweets(){
		 ConfigurationBuilder cb = new ConfigurationBuilder();

	        cb.setDebugEnabled(true)
	        .setOAuthConsumerKey("")
	        .setOAuthConsumerSecret("")
	        .setOAuthAccessToken("")
	        .setOAuthAccessTokenSecret("");
		
		TwitterFactory tf = new TwitterFactory();
		Twitter twitter = tf.getInstance();
		
		// The factory instance is re-useable and thread safe.
		//Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query("#iscte");
		query.setCount(10);
		query.setSince("2018-10-01");

		try {
			QueryResult result = twitter.search(query);
			List<Status> tweetsList = result.getTweets();
			Iterator<Status> itTweets = tweetsList.iterator();

			while(itTweets.hasNext()) {
				Status tweet = (Status)itTweets.next();
				System.out.println("@" + tweet.getUser() + " - " +  tweet.getText());
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

}
