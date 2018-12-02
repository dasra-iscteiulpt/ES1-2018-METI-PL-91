package BDA;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;

/** 
 * Write a comment in a specific Facebook post
 * @author GROUP 91
 * @version 1.0
 * @since September 2018
 */

public class WriteComment { 

	private static ReadXMLfile r = new ReadXMLfile();

	/**
	 * Utility method to write a comment in a Facebook Post
	 * @param postId, is the post identification
	 * @param fromUser, is the BDA user
	 * @param comment, is the text of the comment
	 * @return int, 1 = successful, 0 = failed
	 */
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