package it.unicam.ids.c3.security;

import java.util.List;

public class AuthResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String name;
	private String email;
	private List<String> roles;

	public AuthResponse(String token, Long id, String name, String email, List<String> roles) {
		this.token = token;
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRoles() {
		return roles;
	}
}
