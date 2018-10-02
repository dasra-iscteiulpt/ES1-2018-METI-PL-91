
public class Attributes {
	private String email;
	private String username;
	private String password;
	private String service;

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
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}

	@Override
	public String toString() {
		return "User: Email=" + this.email + " Username=" + this.username + " Password=" + this.password +
				" Service=" + this.service;
	}

}

