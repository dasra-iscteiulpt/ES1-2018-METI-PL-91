package bda;
import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.Post;

public class ReadPosts {

	public static void main(String[] args) throws Exception {
		//This data has to be read from the GUI
		new ReadPosts().readPosts("dasra");
	}

	// Utility method to read posts that contain specific keywords 
	public ArrayList<Post> readPosts(String username){

		FacebookClient fbClient = new DefaultFacebookClient("EAAgq4lhYUWYBAAXNa26kJl11NBPtOLllQrHxq6b1WhmT78hOsiFnoBfccD6Q71NEM4ZATBZCiuu6MEg47HZAzxFLocvTbJSWASWnvbKbLgGFJ4eYp6SQ9EgxZAonJBBYJ8I3sdRyhCeZBad7H9Gq7zB9cWC1Vm443XDxVvJukrTWtgqZBzOMXi", Version.VERSION_2_12);
		// Connections support paging and are iterable
		Connection<Post> myFeed = fbClient.fetchConnection("me/feed", Post.class);
		
		ArrayList<Post> fbPosts = new ArrayList<Post>();
		
		// Iterate over the feed to access the particular pages
		for (List<Post> myFeedPage : myFeed) {
			// Iterate over the list of contained data to access the individual object
			for (Post post : myFeedPage) {
				// Filters only posts that contain the keyword
				if(post.getMessage() != null) {
					if (post.getMessage().toLowerCase().contains("mestrado") || post.getMessage().toLowerCase().contains("iscte")) {
						fbPosts.add(post);
						System.out.println("---- Post ----");
						System.out.println("Message: "+ post.getMessage() + System.lineSeparator() + "Id : " + post.getId() + System.lineSeparator() + "Created at : " + post.getCreatedTime());
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
