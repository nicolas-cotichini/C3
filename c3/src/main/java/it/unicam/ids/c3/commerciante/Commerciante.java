package it.unicam.ids.c3.commerciante;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.sun.istack.NotNull;

import it.unicam.ids.c3.commerciante.Commerciante;
import it.unicam.ids.c3.user.User;

@Entity
public class Commerciante extends User {

	@Column(unique = true)
	@NotNull
	private String iva;
	
	private Long idNegozio;
	
	public Commerciante() {
	}

	public Commerciante(Commerciante commerciante) {
		setNome(commerciante.getNome());
		setCognome(commerciante.getCognome());
		setEmail(commerciante.getEmail());
		setPassword(commerciante.getPassword());
		setIva(commerciante.getIva());
		setRuolo("COMMERCIANTE");
		this.idNegozio = null;
	}

	public String getIva() {
		return iva;
	}
	
	public void setIva(String iva) {
		this.iva = iva;
	}
	
	public Long getIdNegozio() {
		return idNegozio;
	}
	
	public void setIdNegozio(Long idNegozio) {
		this.idNegozio = idNegozio;
	}
	
}
