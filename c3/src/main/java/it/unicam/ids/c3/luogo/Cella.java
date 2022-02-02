package it.unicam.ids.c3.luogo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class Cella {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long idOrdine;
	
	@NotNull
	private Long idLocker;
	
	@NotNull
	private boolean libero;
	
	@NotNull
	private String password;
	
	public Cella() {
	}
	
	public Cella(Long idLocker) {
		setIdLocker(idLocker);
		this.libero = true;
	}
	
	public Long getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(Long idOrdine) {
		this.idOrdine = idOrdine;
	}

	public boolean getLibero() {
		return libero;
	}

	public void setLibero() {
		this.libero = !this.libero;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}
	
	public Long getIdLocker() {
		return idLocker;
	}
	
	public void setIdLocker(Long idLocker) {
		this.idLocker = idLocker;
	}
}
