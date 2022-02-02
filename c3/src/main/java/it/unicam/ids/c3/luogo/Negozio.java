package it.unicam.ids.c3.luogo;

import javax.persistence.Entity;

import javax.validation.constraints.NotNull;


@Entity
public class Negozio extends Luogo {

	private String orarioApertura;

	@NotNull
	private Long idCommerciante;

	public Negozio() {
	}

	public Negozio(Negozio negozio) {
		setNome(negozio.getNome());
		setIndirizzo(negozio.getIndirizzo());
		setNote(negozio.getNote());
		setOrarioApertura(negozio.getOrarioApertura());
		setIdCommerciante(negozio.getIdCommerciante());
		setTipo("NEGOZIO");
	}

	public String getOrarioApertura() {
		return orarioApertura;
	}
	
	public void setOrarioApertura(String orarioApertura) {
		this.orarioApertura = orarioApertura;
	}
	
	public Long getIdCommerciante() {
		return idCommerciante;
	}
	
	public void setIdCommerciante(Long idCommerciante) {
		this.idCommerciante = idCommerciante;
	}
}
