package it.unicam.ids.c3.acquisti;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class Merce {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ElementCollection(targetClass = Long.class)
	private List<Long> listaNegozi;
	
	@NotNull
	@Column(unique = true)
	private String nome;

	public Merce() {
	}

	public Merce(Merce merce, Long idNegozio) {
		setNome(merce.getNome());
		this.listaNegozi = new ArrayList<Long>();
		listaNegozi.add(idNegozio);
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

	public List<Long> getListaNegozi() {
		return listaNegozi;
	}

	public void setListaNegozi(Long long1) {
		this.listaNegozi.add(long1);
	}
}
