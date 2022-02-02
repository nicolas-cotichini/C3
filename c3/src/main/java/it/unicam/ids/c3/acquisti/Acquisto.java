package it.unicam.ids.c3.acquisti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class Acquisto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private Long idCommerciante;
	@NotNull
	private Long idNegozio;
	@NotNull
	private Long idCliente;
	@NotNull
	private Dimensione dimensione;
	@NotNull
	private boolean ordinato;
	@NotNull
	private String nomeNegozio;
	@NotNull
	private String data;
	

	public Acquisto() {
	}

	public Acquisto(Acquisto acquisto, Long idCommerciante, Long idNegozio, String nomeNegozio) {
		setIdCommerciante(idCommerciante);
		setIdNegozio(idNegozio);
		setNomeNegozio(nomeNegozio);
		setIdCliente(acquisto.getIdCliente());
		setDimensione(acquisto.getDimensione());
		this.ordinato = false;
		setData();
	}

	public Long getId() {
		return id;
	}

	public Long getIdCommerciante() {
		return idCommerciante;
	}

	public void setIdCommerciante(Long idCommerciante) {
		this.idCommerciante = idCommerciante;
	}
	
	public Long getIdNegozio() {
		return idNegozio;
	}
	
	public void setIdNegozio(Long idNegozio) {
		this.idNegozio = idNegozio;
	}
	
	public Long getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Dimensione getDimensione() {
		return dimensione;
	}

	public void setDimensione(Dimensione dimensione) {
		this.dimensione = dimensione;
	}

	public boolean getOrdinato() {
		return ordinato;
	}

	public void setOrdinato() {
		ordinato = true;
	}
	
	public String getNomeNegozio() {
		return nomeNegozio;
	}
	
	public void setNomeNegozio(String nomeNegozio) {
		this.nomeNegozio = nomeNegozio;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData() {
		LocalDate oggi = LocalDate.now();
		this.data = oggi.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
	}
	
}
