package bda;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/** 
* Read Facebook Posts
* @author GROUP 91
* @version 1.0
* @since September 2018
*/

public class ReadPosts {

	private static ReadXMLfile r = new ReadXMLfile();

	public static void main(String[] args) throws Exception {
		//This data has to be read from the GUI
		new ReadPosts().readPosts("dasra");
	}

	/**
	 * Utility method to read posts that contain specific keywords 
	 * @param username
	 * @return fbPosts, ArrayList of Post
	 */
	public ArrayList<Post> readPosts(String username){
		r.validateUserFacebook(username);
		String s= ReadXMLfile.facebookData;
		FacebookClient fbClient = new DefaultFacebookClient(ReadXMLfile.facebookData, Version.VERSION_2_12);
		// Connections support paging and are iterable
		Connection<Post> myFeed = fbClient.fetchConnection("me/feed", Post.class, Parameter.with("limit",10));
		ArrayList<Post> fbPosts = new ArrayList<Post>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<Attributes> filtersList = new ArrayList<Attributes>();
		filtersList = r.readFiltersXMLfile("config.xml");	
		
		// Iterate over the feed to access the particular pages
		for (List<Post> myFeedPage : myFeed) {
			// Iterate over the list of contained data to access the individual object
			for (Post post : myFeedPage) {
				// Filters only posts that contain the keyword
				if(post.getMessage() != null) {
					if (keywordValidation(post.getMessage(), filtersList)) {
						fbPosts.add(post);
						System.out.println("---- Post ----");
						System.out.println("Message: "+ post.getMessage() + System.lineSeparator() + "Id : " + post.getId() + System.lineSeparator() + "Created at : " + sdf.format(post.getCreatedTime()));
					}
				}
			}
		}
		return fbPosts;	
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
