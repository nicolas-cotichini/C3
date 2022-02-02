package it.unicam.ids.c3.magazziniere;

import javax.persistence.Entity;

import it.unicam.ids.c3.user.User;

@Entity
public class Magazziniere extends User{

	public Magazziniere() {
	}
	
	public Magazziniere(Magazziniere magazziniere) {
		setNome(magazziniere.getNome());
		setCognome(magazziniere.getCognome());
		setEmail(magazziniere.getEmail());
		setPassword(magazziniere.getPassword());
		setRuolo("MAGAZZINIERE");
	}
}
