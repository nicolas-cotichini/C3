package it.unicam.ids.c3.luogo;

import javax.persistence.Entity;


@Entity
public class Magazzino extends Luogo {

	private String orarioApertura;

	public Magazzino() {
	}
	
	public Magazzino(Magazzino magazzino) {
		setNome(magazzino.getNome());
		setIndirizzo(magazzino.getIndirizzo());
		setNote(magazzino.getNote());
		setOrarioApertura(magazzino.getOrarioApertura());
		setTipo("MAGAZZINO");
	}

	public String getOrarioApertura() {
		return orarioApertura;
	}

	public void setOrarioApertura(String orarioApertura) {
		this.orarioApertura = orarioApertura;
	}

}
