package it.unicam.ids.c3.luogo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.acquisti.Dimensione;
import it.unicam.ids.c3.ordine.OrdineService;
import it.unicam.ids.c3.ordine.StatoOrdine;

@Service
public class LockerService {

	@Autowired
	LockerRepository lockerRep;
	@Autowired
	CellaRepository cellaRep;
	@Autowired
	OrdineService ordineSer;

	/**
	 * Crea un nuovo Locker
	 *
	 * @param Locker
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void creaLocker(Locker locker) {
		lockerRep.save(new Locker(locker));
		Locker lck = lockerRep.findByNome(locker.getNome());
		creaCelle(lck.getId());
	}

	/**
	 * Crea delle nuove Celle associate al Locker
	 * 
	 * @param Long idLocker
	 * @return List<Cella> lista delle celle create
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	private void creaCelle(Long idLocker) {
		Cella cella;
		for (int i = 0; i < 30; i++) {
			cella = cellaRep.save(new Cella((idLocker)));
			cella.setPassword(generaPassword());
			cellaRep.save(cella);
		}
	}

	/**
	 * Genera una password numerica di lunghezza 8 caratteri
	 * 
	 * @return String password generata
	 */
	private String generaPassword() {
		return RandomStringUtils.randomNumeric(8);
	}

	/**
	 * Determina se l'Id passato come argomento appartiene ad un Locker
	 * 
	 * @param Long idLuogo
	 * @return boolean
	 */
	public boolean isLocker(Long idLuogo) {
		if (lockerRep.findById(idLuogo).isPresent())
			return true;
		else
			return false;
	}

	/**
	 * Controlla se tutte le Celle del Locker sono libere
	 * 
	 * @param Long idLocker
	 * @return boolean
	 */
	public boolean isEmpty(Long idLocker) {
		List<Cella> celle = cellaRep.findAllByIdLocker(idLocker);
		for (Cella cl : celle) {
			if (!cl.getLibero())
				return false;
		}
		return true;
	}

	/**
	 * Riserva le Celle destinate all'Ordine nel Locker selezionato
	 * 
	 * @param List<Acquisto> daOrdinare
	 * @param Long           idOrdine
	 * @param Long           idLocker
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void cercaCelle(List<Acquisto> daOrdinare, Long idLocker, Long idOrdine) throws Exception {
		int numCelle = calcolaCelle(daOrdinare);
		try {
			allocaCelle(numCelle, idLocker, idOrdine);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * Determina numero Celle necessarie
	 * 
	 * @param List<Acquisto> per i quali sarà necessario riservare le celle
	 * @return int numero Celle necessarie
	 */
	public int calcolaCelle(List<Acquisto> daOrdinare) {
		Iterator<Acquisto> g = daOrdinare.iterator();
		int numCelle = 0;
		float pCelle = 0f;
		while (g.hasNext()) {
			Acquisto x = g.next();
			if (x.getDimensione().equals(Dimensione.GRANDE))
				numCelle++;
			else if (x.getDimensione().equals(Dimensione.MEDIO))
				pCelle += 0.5f;
			else
				pCelle += 0.25f;
		}
		numCelle = numCelle + (int) Math.ceil(pCelle);
		return numCelle;
	}

	/**
	 * Alloca le celle necessare, tenendo conto delle celle disponibili
	 * 
	 * @param int  numCelle
	 * @param Long idLocker
	 * @param Long idOrdine
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	private void allocaCelle(int numCelle, Long idLocker, Long idOrdine) throws Exception {
		// tiene traccia delle celle riservate in fase di scelta,
		// utile nel caso di impossibilità posizionamento Ordine nel Locker7
		List<Long> celleOccupate = new ArrayList<Long>();
		List<Long> celleTrovate = getAllCelleDisponibiliByIdLocker(idLocker);
		Iterator<Long> itr = celleTrovate.iterator();
		for (int i = numCelle; i > 0; i--) {
			try {
				celleOccupate.add(trovaCella(itr, idOrdine));
			} catch (Exception e) {
				// nel caso non si riesca ad occupare tutte le celle necessarie per l'Ordine
				// in questo Locker libera le Celle finora occupate
				liberaCelleOccupate(celleOccupate);
				throw new Exception("Non c'è abbastanza spazio in questo Locker");
			}
		}
		aggiornaPasswordCelle(celleOccupate);
	}

	/**
	 * Aggiorna la password delle Celle occupate dal nuovo Ordine
	 * 
	 * @param List<Long> celleOccupate
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	private void aggiornaPasswordCelle(List<Long> celleOccupate) {
		for (Long idCella : celleOccupate) {
			aggiornaPassword(idCella);
		}
	}

	/**
	 * Aggiorna la password della Cella indicata
	 * 
	 * @param Long idCella
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	private void aggiornaPassword(Long idCella) {
		String password = generaPassword();
		Cella cella = cellaRep.findById(idCella).orElseThrow();
		cella.setPassword(password);
		cellaRep.save(cella);
	}

	/**
	 * Libera le celle precedentemente occupate in fase di ricerca delle celle
	 * libere
	 * 
	 * @see allocaCelle()
	 * @param celleOccupate
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	private void liberaCelleOccupate(List<Long> celleOccupate) {
		Iterator<Long> itr = celleOccupate.iterator();
		while (itr.hasNext()) {
			Long x = itr.next();
			liberaCella(x);
		}
	}

	/**
	 * Libera le celle assegnato all'Ordine
	 * 
	 * @param Long idOrdine
	 * @param Long idLocker
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void liberaCelleOrdine(Long idOrdine, Long idLocker) {
		List<Cella> celle = cellaRep.findAllByIdLocker(idLocker);
		for (Cella cll : celle) {
			if (cll.getIdOrdine() != null && cll.getIdOrdine().equals(idOrdine)) {
				this.liberaCella(cll.getId());
			}
		}
	}

	/**
	 * Libera la cella indicata
	 * 
	 * @param idCella da liberare
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void liberaCella(Long idCella) {
		Cella cll = cellaRep.findById(idCella).orElseThrow();
		cll.setLibero();
		cll.setIdOrdine(null);
		cellaRep.save(cll);
	}

	/**
	 * Cerca tutte le Celle disponibili nel Locker selezionato
	 * 
	 * @param Long idLocker
	 * @return List<Long> degli id delle celle libere trovate
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	private List<Long> getAllCelleDisponibiliByIdLocker(Long idLocker) throws Exception {
		List<Cella> x = cellaRep.findAllByIdLocker(idLocker);
		List<Long> celleTrovate = new ArrayList<Long>();
		for (Cella cella : x) {
			if (cella.getLibero())
				celleTrovate.add(cella.getId());
		}
		if (celleTrovate.isEmpty()) {
			throw new Exception();
		} else
			return celleTrovate;
	}

	/**
	 * Trova e setta una cella come occupata per consegnare l'Ordine
	 * 
	 * @param Iterator<Long> con gli id delle Celle libere
	 * @param Long           idOrdine
	 * @return Long id Cella da occupare
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	private Long trovaCella(Iterator<Long> itr, Long idOrdine) throws Exception {
		while (itr.hasNext()) {
			Long cellaDaOccupare = itr.next();
			Cella cll = cellaRep.findById(cellaDaOccupare).orElseThrow();
			if (cll.getLibero()) {
				cll.setLibero();
				cll.setIdOrdine(idOrdine);
				cellaRep.save(cll);
				return cellaDaOccupare;
			}
		}
		throw new Exception();
	}

	/**
	 * Cerca tutti i Locker
	 * 
	 * @return Iterable<Locker>
	 */
	public Iterable<Locker> getAllLocker() {
		return lockerRep.findAll();
	}

	/**
	 * Ritorna le password delle celle contententi gli acquisti dell'Ordine di
	 * interesse
	 * 
	 * @param Long idOrdine
	 * @param Long idLocker
	 * @return List<String> di password
	 */
	public List<String> getPasswordOrdine(Long idOrdine, Long idLocker) {
		List<String> listaPassword = new ArrayList<String>();
		if (isLocker(idLocker)) {
			Iterable<Cella> x = cellaRep.findAllByIdLocker(idLocker);
			for (Cella cella : x) {
				if (idOrdine.equals(cella.getIdOrdine())) {
					listaPassword.add(cella.getPassword());
				}
			}
			return listaPassword;
		} else {
			listaPassword.add("non richiesta");
			return listaPassword;
		}
	}

	/**
	 * Controlla se la password è corretta ed indica quale Cella è stata aperta
	 * Controlla anche se tutto l'Ordine è stato ritirato
	 * 
	 * @param Locker   idLocker
	 * @param Password password
	 * @return Long Numero della cella aperta
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Long apriCella(Long idLocker, String password) throws Exception {
		List<Cella> celleLocker = cellaRep.findAllByIdLocker(idLocker);
		// id prima Cella
		Long mod = celleLocker.get(0).getId();
		Long idOrdine;
		Long idCella;
		for (Cella cella : celleLocker) {
			if (cella.getPassword().equals(password)) {
				idOrdine = cella.getIdOrdine();
				idCella = cella.getId();
				if (ordineSer.getOrdineById(idOrdine).getStato().equals(StatoOrdine.CONSEGNATO)) {
					liberaCella(idCella);
					List<Cella> controlloRitiro = cellaRep.findAllByIdOrdine(idOrdine);
					if (controlloRitiro.isEmpty())
						ordineSer.setStatoOrdine(idOrdine);
					aggiornaPassword(idCella);
				}
				return idCella + 1 - mod;
			}
		}
		throw new Exception("Password errata!");
	}

	/**
	 * Aggiorna lo stato del Locker indicando che ora ha un profilo interfaccia
	 * 
	 * @param Long idLocker
	 * @throws Exception 
	 */
	public void setInterfaccia(Long idLocker) throws Exception {
		Locker locker = lockerRep.findById(idLocker).orElseThrow();
		if (!locker.getInterfaccia()) {
			locker.setInterfaccia();
			lockerRep.save(locker);
		} else throw new Exception("Questo locker ha già un profilo");
	}

	/**
	 * Elimina le Celle del Locker indicato
	 * 
	 * @param Long idLocker
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void eliminaCelle(Long idLocker) {
		cellaRep.deleteAll(cellaRep.findAllByIdLocker(idLocker));
	}
}
