package it.unicam.ids.c3.acquisti;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AcquistoService {

	@Autowired
	AcquistoRepository acquistoRep;

	/**
	 * Crea un nuovo Acquisto 
	 * @param acquisto
	 * @param idCommerciante
	 * @param idNegozio
	 * @param nomeNegozio
	 * @return id Acquisto appena creato
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Long creaAcquisto(Acquisto acquisto, Long idCommerciante, Long idNegozio, String nomeNegozio) {
		Acquisto acq = acquistoRep.save(new Acquisto(acquisto, idCommerciante, idNegozio, nomeNegozio));
		return acq.getId();
	}

	/**
	 * Ricerca tutti gli acquisti effettuati dal Cliente
	 * 
	 * @param idCliente
	 */
	public List<Acquisto> getAllAcquistiByIdCliente(Long idCliente) {
		List<Acquisto> acquisti = acquistoRep.findByIdCliente(idCliente);
		Collections.reverse(acquisti);
		return acquisti;
	}

	/**
	 * Ricerca l'Acquisto di interesse
	 * 
	 * @param id dell'Acquisto
	 * @return l'Acquisto con quel id o null se non trovato
	 */
	public Acquisto getAcquistoById(Long idAcquisto) {
		return acquistoRep.findById(idAcquisto).orElse(null);
	}

	/**
	 * Aggiorna lo stato dell'acquisto in Ordinato
	 * @param acquisto
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void setOrdinato(Acquisto acquisto) {
		acquisto.setOrdinato();
		acquistoRep.save(acquisto);
	}

	/**
	 * Ritorna tutti gli acquisti registrati dal Commerciante 
	 * 
	 * @param idCommerciante
	 * @return lista acquisti registrati dal Commerciante
	 */
	public List<Acquisto> getAllAcquistiByIdCommerciante(Long idCommerciante) {
		List<Acquisto> acquisti = acquistoRep.findByIdCommerciante(idCommerciante);
		Collections.reverse(acquisti);
		return acquisti;
	}

}
