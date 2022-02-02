package it.unicam.ids.c3.ordine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.acquisti.AcquistoRepository;
import it.unicam.ids.c3.acquisti.AcquistoService;
import it.unicam.ids.c3.luogo.LockerService;
import it.unicam.ids.c3.luogo.LuogoService;

@Service
public class OrdineService {

	@Autowired
	OrdineRepository ordineRep;
	@Autowired
	AcquistoService acquistoSer;
	@Autowired
	GestoreLogistica gestoreSer;
	@Autowired
	LockerService lockerSer;
	@Autowired
	LuogoService luogoSer;
	@Autowired
	AcquistoRepository acquistoRep;

	/**
	 * Ricerca tutti gli ordini effettuati dal Cliente
	 * 
	 * @param Long idCliente
	 */
	public List<Ordine> getAllOrdineByIdCliente(Long idCliente) {
		List<Ordine> ordini = ordineRep.findByIdCliente(idCliente);
		Collections.reverse(ordini);
		return ordini;
	}

	/**
	 * Cerca l'Ordine con id indicato
	 * 
	 * @param Long idOrdine
	 * @return Ordine
	 */
	public Ordine getOrdineById(Long idOrdine) {
		return ordineRep.findById(idOrdine).orElse(null);
	}

	/**
	 * Crea un nuovo Ordine, controllando che il Luogo 
	 * consegna indicato sia adatto 
	 * 
	 * @param Long idOrdine 
	 * @param Long idLuogo
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void creaOrdine(Long idOrdine, Long idLuogo) throws Exception {
		Ordine ordine = ordineRep.findById(idOrdine).orElseThrow();
		if (lockerSer.isLocker(idLuogo)) {
			try {
				lockerSer.cercaCelle(ordine.getListaAcquisto(), idLuogo, ordine.getId());
			} catch (Exception e) {
				throw new Exception("Il Locker scelto non ha abbastanza spazio");
			}
		}
		else if (!luogoSer.isMagazzino(idLuogo))
			throw new Exception("Id selezionato non è un luogo abilitato alla consegna");
		ordine.setIdLuogoConsegna(idLuogo);
		try {
			gestoreSer.cercaCorriere(ordine);
		} catch (Exception e) {
			if (lockerSer.isLocker(idLuogo))
			lockerSer.liberaCelleOrdine(idOrdine, idLuogo);
			throw new Exception("Non ci sono Corrieri disponibili al momento");
		}
		ordine.setStato(StatoOrdine.IN_GESTIONE);
		ordineRep.save(ordine);
	}

	/**
	 * Aggiorna lo stato degli Acquisti in "Ordinato"
	 * 
	 * @param Ordine
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	private void setOrdinatoListaAcquisti(Ordine ordine) {
		Iterator<Acquisto> itr = ordine.getListaAcquisto().iterator();
		while (itr.hasNext()) {
			acquistoSer.setOrdinato(itr.next());
		} 
	}
	
	/**
	 * Genera un Ordine prima che venga salvato ed affidato al Corriere
	 * 
	 * @param Long idCliente
	 * @return Ordine 
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Ordine generaOrdine(Long idCliente) throws Exception {
		List<Acquisto> acquisti = acquistoSer.getAllAcquistiByIdCliente(idCliente);
		Ordine ordine = null;
		if (!acquisti.isEmpty()) {
			// controllo sugli acquisti, verifica se già presenti in un altro Ordine
			List<Acquisto> daOrdinare = new ArrayList<Acquisto>();
			Iterator<Acquisto> itr = acquisti.iterator();
			while (itr.hasNext()) {
				Acquisto acq = itr.next();
				if (acq.getOrdinato() == false) {
					daOrdinare.add(acq);
				}
			}
			if (!daOrdinare.isEmpty()) {
				ordine = new Ordine(daOrdinare, idCliente, null);
				ordineRep.save(ordine);
				setOrdinatoListaAcquisti(ordine);
			} else {
				throw new Exception("Non ci sono acquisti da ordinare");
			}
		} else {
			throw new Exception("Non ci sono acquisti legati al tuo profilo");
		}
		return ordine;
	}

	/**
	 * Cambia il Luogo consegna dell'Ordine
	 * 
	 * @param Long idOrdine
	 * @param Long idLuogo
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void cambiaLuogoConsegna(Long idOrdine, Long idLuogo) throws Exception {
		try {
			Ordine ord = ordineRep.findById(idOrdine).get();
			Long idVecchioLuogo = ord.getIdLuogoConsegna();
			if(lockerSer.isLocker(idVecchioLuogo)) {
				lockerSer.liberaCelleOrdine(idOrdine, idVecchioLuogo);
			}
			ord.setIdLuogoConsegna(idLuogo);
			ordineRep.save(ord);
		} catch (Exception e) {
			throw new Exception("Errore modifica Luogo consegna");
		}
	}

	/**
	 * Aggiorna lo stato dell'Ordine indicato
	 * 
	 * @param Long idOrdine
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void setStatoOrdine(Long idOrdine) throws Exception {
		Ordine ordine = ordineRep.findById(idOrdine).orElse(null);
		if(ordine.getStato().equals(StatoOrdine.CONSEGNATO)) {
			ordine.setStato(StatoOrdine.RITIRATO);
			ordineRep.save(ordine);
		} else if (ordine.getStato().equals(StatoOrdine.IN_GESTIONE)) {
			ordine.setStato(StatoOrdine.CONSEGNATO);
			ordineRep.save(ordine);
		}
		else throw new Exception("Ordine già consegnato");
	}

	public List<Acquisto> getAllAcquisti(Long idOrdine) {
		List<Acquisto> acquisti = ordineRep.findById(idOrdine).orElse(null).getListaAcquisto();
		return acquisti;
	}

	/**
	 * Cerca i dettagli dell'Ordine di interesse, 
	 * quali: il nome e l'indirizzo del Luogo consegna ed eventuali password
	 * 
	 * @param Long idOrdine
	 * @return DettagliOrdine
	 */
	public DettaglioOrdine getDettagliOrdine(Long idOrdine) {
		Ordine ordine = getOrdineById(idOrdine);
		Long idLuogoConsegna = ordine.getIdLuogoConsegna();
		return new DettaglioOrdine(getNomeLuogoConsegna(idLuogoConsegna),
				getIndirizzoLuogoConsegna(idLuogoConsegna),
				getPasswordOrdine(idOrdine, idLuogoConsegna));
	}

	public String getNomeLuogoConsegna(Long idLuogo) {
		return luogoSer.getById(idLuogo).getNome();
	}

	public String getIndirizzoLuogoConsegna(Long idLuogo) {
		return luogoSer.getById(idLuogo).getIndirizzo();
	}

	public List<String> getPasswordOrdine(Long idOrdine, Long idLuogo) {
		if (lockerSer.isLocker(idLuogo))
		return luogoSer.getPasswordOrdine(idOrdine, idLuogo);
		else return null;
	}
}
