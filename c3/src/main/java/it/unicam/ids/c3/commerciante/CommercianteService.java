package it.unicam.ids.c3.commerciante;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.acquisti.AcquistoService;
import it.unicam.ids.c3.acquisti.Merce;
import it.unicam.ids.c3.acquisti.MerceService;
import it.unicam.ids.c3.cliente.ClienteService;
import it.unicam.ids.c3.luogo.LuogoService;
import it.unicam.ids.c3.luogo.Negozio;

@Service
public class CommercianteService {

	@Autowired
	CommercianteRepository commercianteRep;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AcquistoService acquistoSer;
	@Autowired
	MerceService merceSer;
	@Autowired
	LuogoService luogoSer;
	@Autowired
	ClienteService clienteSer;
	
	/**
	 * Crea un nuovo Commerciante
	 * 
	 * @param commerciante
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void creaCommerciante(Commerciante commerciante) {
		commerciante.setPassword(passwordEncoder.encode(commerciante.getPassword()));
		commercianteRep.save(new Commerciante(commerciante));
	}

	/**
	 * Aggiorna l'id Negozio legato al Commerciante
	 * 
	 * @param idCommerciante
	 * @param idNegozio
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void aggiornaIdNegozio(Long idCommerciante, Long idNegozio) {
		Commerciante commerciante = commercianteRep.findById(idCommerciante).orElse(null);
		commerciante.setIdNegozio(idNegozio);
		commercianteRep.save(commerciante);
	}

	/**
	 * Rimuove l'id Negozio legato al Commerciante
	 * 
	 * @param idCommerciante
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void eliminaIdNegozio(Long idCommerciante) {
		Commerciante commerciante = commercianteRep.findByIdNegozio(idCommerciante);
		if(commerciante != null) {
		commerciante.setIdNegozio(null);
		commercianteRep.save(commerciante);
		}
	}
	
	public Long creaAcquisto(Acquisto acquisto, Long idCommerciante) throws Exception {
		Long idNegozio = getIdNegozio(idCommerciante);
		String nomeNegozio = luogoSer.getById(idNegozio).getNome();
		if (clienteSer.existsById(acquisto.getIdCliente())) {
			return acquistoSer.creaAcquisto(acquisto, idCommerciante, idNegozio, nomeNegozio);
		}
		else throw new Exception();
	}

	/**
	 * Cerca il Commerciante con id specificato
	 * 
	 * @param idCommerciante
	 */
	public Commerciante getById(Long idCommerciante) {
		return commercianteRep.findById(idCommerciante).orElseThrow();
	}
	
	public Long getIdNegozio(Long idCommerciante) {
		return commercianteRep.findById(idCommerciante).orElseThrow().getIdNegozio();
	}
	
	/**
	 * Cerca tutti gli acquisti effettuati dal Commerciante
	 * 
	 * @param id del Commerciante
	 * @return lista acquisti creati dal Commerciante
	 */
	public List<Acquisto> getAllAcquistoCommerciante(Long idCommerciante) {
		return acquistoSer.getAllAcquistiByIdCommerciante(idCommerciante);
	}

	public List<Merce> getAllMerceById(Long id) {
		return merceSer.getAllMerceByIdNegozio(getIdNegozio(id));
	}

	public void creaMerce(Merce merce, Long idNegozio) throws Exception {
		merceSer.creaMerce(merce, idNegozio);
	}

	public boolean eliminaMerce(String nome, Long idNegozio ) {
		return merceSer.eliminaMerce(nome, idNegozio);	
	}

	public Negozio getDettagliNegozio(Long idCommerciante) {
		return luogoSer.getNegozioByIdCommerciante(idCommerciante);
	}
	
}
