package it.unicam.ids.c3.cliente;

import javax.persistence.Entity;

import it.unicam.ids.c3.user.User;

@Entity
public class Cliente extends User {
	
	public Cliente() {
	}
	
	public Cliente(Cliente cliente) {
		setNome(cliente.getNome());
		setCognome(cliente.getCognome());
		setEmail(cliente.getEmail());
		setPassword(cliente.getPassword());
		setRuolo("CLIENTE");
	}
	
}
