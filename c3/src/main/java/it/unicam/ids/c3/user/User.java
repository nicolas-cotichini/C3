package it.unicam.ids.c3.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@NotEmpty
	@Size(max = 50)
	private String nome, cognome;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	@Size(max = 50)
	@Column(unique = true)
	@Email
	private String email;
	
	@NotNull
	private boolean attivo = true;

	@NotNull
	private String ruolo;
	
	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public boolean getAttivo() {
		return attivo;
	}
	
	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ! (o instanceof User)) return false;
        User usr = (User) o;
        return Objects.equals(id, usr.getId()) && 
        		Objects.equals(email, usr.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
    
	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", cognome=").append(cognome);
        sb.append(", email=").append(email);
        sb.append('}');
        return sb.toString();
    }
	
	public List<String> getListaRuolo() {
		if (this.ruolo.length() > 0) {
			return Arrays.asList(this.ruolo.split(","));
		}
		else return new ArrayList<>();
	}

}
