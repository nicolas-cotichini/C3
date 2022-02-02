package it.unicam.ids.c3.amministratore;

import javax.persistence.Entity;
import javax.persistence.Table;

import it.unicam.ids.c3.user.User;

@Entity
@Table(name = "Amministratore")
public class Amministratore extends User {

	public Amministratore() {}
	
	public Amministratore(Amministratore amministratore) {
		setNome(amministratore.getNome());
		setCognome(amministratore.getCognome());
		setEmail(amministratore.getEmail());
		setPassword(amministratore.getPassword());
		setRuolo("AMMINISTRATORE");
		setAttivo(true);
	}
	
}
