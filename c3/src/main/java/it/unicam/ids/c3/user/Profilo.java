package it.unicam.ids.c3.user;

public class Profilo {

	private Long id;
	private String nome, cognome, email, ruolo;
	
	public Profilo(Long id, String nome, String cognome, String email, String ruolo) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo = ruolo;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getRuolo() {
		return ruolo;
	}
}
