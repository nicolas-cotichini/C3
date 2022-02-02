package it.unicam.ids.c3.security;

import javax.validation.constraints.NotBlank;

public class AuthRequest {

	@NotBlank
	private String username;
	@NotBlank
	private String password;

	public AuthRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
