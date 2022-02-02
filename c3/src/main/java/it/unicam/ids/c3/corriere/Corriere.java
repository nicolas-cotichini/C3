package it.unicam.ids.c3.corriere;

import java.util.List;

import com.sun.istack.NotNull;

import javax.persistence.*;

import it.unicam.ids.c3.ordine.Ordine;
import it.unicam.ids.c3.user.User;

@Entity
public class Corriere extends User {

	@OneToMany(fetch = FetchType.EAGER)
	private List<Ordine> listaConsegne;

	@NotNull
	private boolean operativo;

	public Corriere() {
	}

	public Corriere(User corriere) {
		setNome(corriere.getNome());
		setCognome(corriere.getCognome());
		setEmail(corriere.getEmail());
		setPassword(corriere.getPassword());
		setRuolo("CORRIERE");
		this.listaConsegne = null;
		this.operativo = false;
	}

	public boolean getOperativo() {
		return operativo;
	}

	public void setOperativo() {
		this.operativo = !this.operativo;
	}

	public List<Ordine> getListaConsegne() {
		if (listaConsegne != null)
			return listaConsegne;
		else
			return null;
	}

	public void setListaConsegne(List<Ordine> listaConsegne) {
		this.listaConsegne = listaConsegne;
	}

}
