package bda;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;

/** 
 * Comment
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

//This class is not yet working
public class WriteComment { 

	private static ReadXMLfile r = new ReadXMLfile();

	public int writeComment(String postId, String fromUser, String comment) {
		if(r.validateUserFacebook(fromUser)){
			FacebookClient fbClient = new DefaultFacebookClient(ReadXMLfile.facebookData, Version.VERSION_2_12);
			fbClient.publish(postId +"/comments", String.class, Parameter.with("message", comment));
			System.out.println("Post successful");
			return 1;
		}
		return 0;
	}
}