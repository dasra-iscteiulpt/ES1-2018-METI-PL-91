package bda;
import java.io.IOException;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;

import twitter4j.TwitterException;

/** 
* Comment
* @author GROUP 91
* @version 1.0
* @since September 2018
*/

//This class is not yet working
public class Comment { 

	public static void main(String[] args) throws TwitterException, IOException {
		//This data has to be read from the GUI
		new Comment().sharePost(Long.valueOf("122067392127483"), "Teste");
		
	}

	public int sharePost(Long postId, String comment) {
		FacebookClient fbClient = new DefaultFacebookClient("EAAgq4lhYUWYBAAXNa26kJl11NBPtOLllQrHxq6b1WhmT78hOsiFnoBfccD6Q71NEM4ZATBZCiuu6MEg47HZAzxFLocvTbJSWASWnvbKbLgGFJ4eYp6SQ9EgxZAonJBBYJ8I3sdRyhCeZBad7H9Gq7zB9cWC1Vm443XDxVvJukrTWtgqZBzOMXi", Version.VERSION_2_12);
		// Connections support paging and are iterable
		
		fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", "RestFB test"));
		System.out.println("Post successful");

		return 1;
	}
}