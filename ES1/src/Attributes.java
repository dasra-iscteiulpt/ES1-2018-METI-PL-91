
public class Attributes {
	private String email;
	private String username;
	private String password;
	private String service;
	private String from;
	private String keyword;
	private String timeFilterFrom;
	private String timeFilterTo;
	
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
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getTimeFilterFrom() {
		return timeFilterFrom;
	}
	public void setTimeFilterFrom(String timeFilterFrom) {
		this.timeFilterFrom = timeFilterFrom;
	}
	public String getTimeFilterTo() {
		return timeFilterTo;
	}
	public void setTimeFilterTo(String timeFilterTo) {
		this.timeFilterTo = timeFilterTo;
	}
	
	/*public String toString() {
		return "User: Email=" + this.email + " Username=" + this.username + " Password=" + this.password +
				" Service=" + this.service;
	}*/

}

