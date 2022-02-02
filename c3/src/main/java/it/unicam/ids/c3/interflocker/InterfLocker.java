package it.unicam.ids.c3.interflocker;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import it.unicam.ids.c3.user.User;

@Entity
public class InterfLocker extends User{

	@NotNull
	private Long idLocker;
	
	public InterfLocker() {}
	
	public InterfLocker(InterfLocker interfLocker) {
		setNome(interfLocker.getNome());
		setCognome(interfLocker.getCognome());
		setEmail(interfLocker.getEmail());
		setPassword(interfLocker.getPassword());
		setIdLocker(interfLocker.getIdLocker());
		setRuolo("INTLOCKER");
	}
	
	public Long getIdLocker() {
		return idLocker;
	}
	
	public void setIdLocker(Long idLocker) {
		this.idLocker = idLocker;
	}

}
