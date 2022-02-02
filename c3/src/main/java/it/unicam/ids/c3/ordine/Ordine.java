package it.unicam.ids.c3.ordine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

import it.unicam.ids.c3.acquisti.Acquisto;

@Entity
public class Ordine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany
	private List<Acquisto> listaAcquisto;
	
	@NotNull
	private StatoOrdine stato;
	@NotNull
	private Long idCliente;
	@NotNull
	private Long idLuogoConsegna;
	@NotNull
	private String data;
	
	public Ordine() {
	}

	public Ordine(List<Acquisto> acquisti, Long idCliente, Long idLuogoConsegna) {
		setListaAcquisto(acquisti);
		setStato(StatoOrdine.DA_ORDINARE);
		setIdLuogoConsegna(idLuogoConsegna);
		setIdCliente(idCliente);
		setData();
	}

	public Long getId() {
		return id;
	}

	public Long getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	
	public Long getIdLuogoConsegna() {
		return idLuogoConsegna;
	}

	public void setIdLuogoConsegna(Long idLuogoConsegna) {
		this.idLuogoConsegna = idLuogoConsegna;
	}
	
	public StatoOrdine getStato() {
		return stato;
	}

	public void setStato(StatoOrdine stato) {
		this.stato = stato;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData() {
		LocalDate oggi = LocalDate.now();
		this.data = oggi.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
	}
	
	public List<Acquisto> getListaAcquisto() {
		return listaAcquisto;
	}

	public void setListaAcquisto(List<Acquisto> listaAcquisto) {
		this.listaAcquisto = listaAcquisto;
	}

}
