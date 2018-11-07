import java.io.IOException;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.GraphResponse;
import com.restfb.types.Post;

import twitter4j.TwitterException;

public class SharePostFacebook { 

	public static void main(String[] args) throws TwitterException, IOException {
		//This data has to be read from the GUI
		new SharePostFacebook().sharePost(Long.valueOf("122067392127483"), "Teste");
	}

	public int sharePost(Long postId, String comment) {
		FacebookClient fbClient = new DefaultFacebookClient("EAAgq4lhYUWYBAAXNa26kJl11NBPtOLllQrHxq6b1WhmT78hOsiFnoBfccD6Q71NEM4ZATBZCiuu6MEg47HZAzxFLocvTbJSWASWnvbKbLgGFJ4eYp6SQ9EgxZAonJBBYJ8I3sdRyhCeZBad7H9Gq7zB9cWC1Vm443XDxVvJukrTWtgqZBzOMXi", Version.VERSION_2_12);
		// Connections support paging and are iterable
		
		fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", "teste"));
		System.out.println("Post successful");

		return 1;
	}
}
