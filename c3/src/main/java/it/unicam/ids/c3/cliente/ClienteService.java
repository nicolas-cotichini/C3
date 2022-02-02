package it.unicam.ids.c3.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.acquisti.AcquistoService;
import it.unicam.ids.c3.luogo.Luogo;
import it.unicam.ids.c3.luogo.LuogoService;
import it.unicam.ids.c3.ordine.DettaglioOrdine;
import it.unicam.ids.c3.ordine.Ordine;
import it.unicam.ids.c3.ordine.OrdineService;
import it.unicam.ids.c3.ordine.StatoOrdine;
import it.unicam.ids.c3.user.UserService;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRep;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserService userSer;
	@Autowired
	AcquistoService acquistoSer;
	@Autowired
	OrdineService ordineSer;
	@Autowired
	LuogoService luogoSer;


	/**
	 * Crea un nuovo Cliente
	 * 
	 * @param cliente
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void creaCliente(Cliente cliente) throws Exception {
		userSer.existByEmail(cliente.getEmail());
		cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
		clienteRep.save(new Cliente(cliente));
	}

	/**
	 * Controlla se esiste un Cliente con id specificato
	 * 
	 * @param id
	 * @return boolean 
	 */
	public boolean existsById(Long id) {
		return clienteRep.existsById(id);
	}
	
	/**
	 * Elimina il Cliente con l'id specificato controllando che 
	 * non ci siano Ordini da ritirare o Acquisti non ancora ordinati
	 * 
	 * @param id Cliente
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void deleteById(Long idCliente) throws Exception {
		List<Acquisto> acquisti = acquistoSer.getAllAcquistiByIdCliente(idCliente);
		for(Acquisto acq: acquisti) {
			if(!acq.getOrdinato()) {
				throw new Exception("Hai ancora degli acquisti da ordinare");
			}
		}
		List<Ordine> ordini = ordineSer.getAllOrdineByIdCliente(idCliente);
		for(Ordine ord: ordini) {
			if (!ord.getStato().equals(StatoOrdine.RITIRATO)) {
				throw new Exception("Hai ancora degli ordini da ritirare");
			}
		}
		try {
			clienteRep.deleteById(idCliente);
		} catch (Exception e) {
			throw new Exception("Errore eliminazione profilo");
		}
	}

	/**
	 * Ricerca il Cliente tramite id
	 * 
	 * @param id del Cliente di interesse
	 * @return il Cliente con quel id
	 */
	public Cliente getById(Long idCliente) {
		return clienteRep.findById(idCliente).orElseThrow();
	}

	public List<Acquisto> getAllAcquistoCliente(Long idCliente) {
		return acquistoSer.getAllAcquistiByIdCliente(idCliente);
	}

	public List<Ordine> getAllOrdiniCliente(Long idCliente) {
		return ordineSer.getAllOrdineByIdCliente(idCliente);
	}

	public DettaglioOrdine getDettagliOrdine(Long idOrdine, Long idCliente) throws Exception {
		Ordine ordine = getOrdineById(idOrdine);
		if (ordine.getIdCliente().equals(idCliente)) {
			return ordineSer.getDettagliOrdine(idOrdine);
		} else
			throw new Exception("Ordine non collecato al tuo account");
	}

	public Ordine generaOrdine(Long idCliente) throws Exception {
		return ordineSer.generaOrdine(idCliente);
	}
	
	public void creaOrdine(Long idOrdine, Long idLuogo) throws Exception {
		ordineSer.creaOrdine(idOrdine, idLuogo);
	}

	public Acquisto getAcquistoById(Long idAcquisto) {
		return acquistoSer.getAcquistoById(idAcquisto);
	}


	public Ordine getOrdineById(Long idOrdine) {
		return ordineSer.getOrdineById(idOrdine);
	}

	public List<Luogo> cercaMerce(String merce) throws Exception {
		return luogoSer.getNegoziMerce(merce);
	}
	
	public List<Luogo> getListaLuoghiConsegna() {
		return luogoSer.getListaLuoghiConsegna();
	}

}
