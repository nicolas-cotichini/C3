package it.unicam.ids.c3.ordine;

import java.util.List;

public class DettaglioOrdine {

	private String luogo, indirizzo;
	private List<String> password;
	
	public DettaglioOrdine(String luogo, String indirizzo, List<String> password) {
		this.luogo = luogo;
		this.indirizzo = indirizzo;
		this.password = password;
	}

	public String getLuogo() {
		return luogo;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public List<String> getPassword() {
		return password;
	}

}
