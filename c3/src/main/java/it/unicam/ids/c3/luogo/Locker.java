package it.unicam.ids.c3.luogo;

import javax.persistence.Entity;

@Entity
public class Locker extends Luogo {

	private boolean interfaccia;
	
	public Locker() {
	}

	public Locker(Locker locker) {
		setNome(locker.getNome());
		setIndirizzo(locker.getIndirizzo());
		setNote(locker.getNote());
		this.interfaccia = false;
		setTipo("LOCKER");
	}
	
	public boolean getInterfaccia() {
		return interfaccia;
	}
	
	public void setInterfaccia() {
		this.interfaccia = !this.interfaccia;
	}

}
