package BDA;

/** 
* Getters and setters for users' attributes
* @author GROUP 91
* @version 1.0
* @since September 2018 
*/

public class Attributes {
	
	private String email;
	private String username;
	private String password;
	private String keywordFilter;
	private String passwordEmail;
	
	/**
	 * Twitter tokens
	 * "The OAuth 2.0 authorization framework enables third-party applications to obtain limited access to a web service.
	 * An open protocol to allow secure authorization in a simple and standard method from web, mobile and desktop applications."
	 * (Text source: https://oauth.net/ ).
	 * 
	 * Both oAuthConsumerKey and oAuthConsumerSecret are Client Credentials.
	 * Both oAuthAccessToken and oAuthAccessTokenSecret are Token Credentials.
	 * 
	 * */
	private String oAuthConsumerKey;
	private String oAuthConsumerSecret;
	private String oAuthAccessToken;
	private String oAuthAccessTokenSecret;
	
	/**
	 * Facebook token
	 * "The user token is the most commonly used type of token. 
	 * This kind of access token is needed any time the app calls an API to read, modify or write a specific person's Facebook data on their behalf. 
	 * User access tokens are generally obtained via a login dialog and require a person to permit your app to obtain one."
	 * (Text source: https://developers.facebook.com/docs/facebook-login/access-tokens/)
	 * 
	 * */
	private String userAccessToken;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getKeyword() {
		return keywordFilter;
	}
	public void setKeyword(String keyword) {
		this.keywordFilter = keyword;
	}
	public String getPasswordEmail() {
		return passwordEmail;
	}
	public void setPasswordEmail(String passwordEmail) {
		this.passwordEmail = passwordEmail;
	}
	public String getOAuthConsumerKey() {
		return oAuthConsumerKey;
	}
	public void setOAuthConsumerKey(String OAuthConsumerKey) {
		this.oAuthConsumerKey = OAuthConsumerKey;
	}
	public String getOAuthConsumerSecret() {
		return oAuthConsumerSecret;
	}
	public void setOAuthConsumerSecret(String OAuthConsumerSecret) {
		this.oAuthConsumerSecret = OAuthConsumerSecret;
	}
	public String getOAuthAccessToken() {
		return oAuthAccessToken;
	}
	public void setOAuthAccessToken(String OAuthAccessToken) {
		this.oAuthAccessToken = OAuthAccessToken;
	}
	public String getOAuthAccessTokenSecret() {
		return oAuthAccessTokenSecret;
	}
	public void setOAuthAccessTokenSecret(String OAuthAccessTokenSecret) {
		this.oAuthAccessTokenSecret = OAuthAccessTokenSecret;
	}
	public String getUserAccessToken() {
		return userAccessToken;
	}
	public void setUserAccessToken(String userAccessToken) {
		this.userAccessToken = userAccessToken;
	}
}

