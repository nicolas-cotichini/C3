package it.unicam.ids.c3.luogo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.acquisti.MerceService;
import it.unicam.ids.c3.commerciante.CommercianteService;
import it.unicam.ids.c3.interflocker.InterfLockerService;

@Service
public class LuogoService {

	@Autowired
	LockerService lockerSer;
	@Autowired
	MerceService merceSer;
	@Autowired
	CommercianteService commercianteSer;
	@Autowired
	InterfLockerService interfSer;
	@Autowired
	LuogoRepository<Luogo> luogoRep;
	@Autowired
	MagazzinoRepository magazzinoRep;
	@Autowired
	NegozioRepository negozioRep;

	public void existByNome(String nome) throws Exception {
		if (luogoRep.existsByNome(nome))
			throw new Exception("Nome già in uso, sceglierne uno nuovo");
	}

	public void creaLocker(Locker locker) throws Exception {
		existByNome(locker.getNome());
		lockerSer.creaLocker(locker);
	}

	/**
	 * Crea un nuovo Magazzino
	 *
	 * @param Magazzino
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void creaMagazzino(Magazzino magazzino) throws Exception {
		existByNome(magazzino.getNome());
		magazzinoRep.save(new Magazzino(magazzino));
	}

	/**
	 * Crea un nuovo Negozio
	 *
	 * @param Negozio
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void creaNegozio(Negozio negozio) throws Exception {
		existByNome(negozio.getNome());
		negozioRep.save(new Negozio(negozio));
	}

	/**
	 * Ritorna una lista con tutti i luoghi adibiti alla consegna
	 * di tipo Locker e Magazzino
	 * 
	 * @return List<Luogo>
	 */
	public List<Luogo> getListaLuoghiConsegna() {
		Iterable<Locker> lockers = lockerSer.getAllLocker();
		List<Luogo> luoghi = new ArrayList<Luogo>();
		luoghi.addAll((Collection<? extends Luogo>) lockers);
		Iterable<Magazzino> magazzini = magazzinoRep.findAll();
		luoghi.addAll((Collection<? extends Luogo>) magazzini);
		if (luoghi.isEmpty())
			return null;
		return luoghi;
	}

	/**
	 * Cerca il Luogo con id indicato
	 * 
	 * @param Long idLuogo
	 * @returnLuogo
	 */
	public Luogo getById(Long idLuogo) {
		return (Luogo) luogoRep.findById(idLuogo).orElse(null);
	}

	/**
	 * Cerca i Luoghi di tipo indicato
	 * 
	 * @param String tipo
	 * @return List<Luogo>
	 */
	public List<Luogo> getAllByTipo(String tipo) {
		return luogoRep.findAllByTipo(tipo);
	}

	public List<String> getPasswordOrdine(Long idOrdine, Long idLuogo) {
		return lockerSer.getPasswordOrdine(idOrdine, idLuogo);
	}

	/**
	 * Cerca tutti i Negozi
	 * 
	 * @return List<Negozio>
	 */
	public List<Negozio> getAllNegozi() {
		List<Negozio> negozi = new ArrayList<Negozio>();
		Iterable<Negozio> itr = negozioRep.findAll();
		negozi.addAll((Collection<? extends Negozio>) itr);
		return negozi;
	}

	/**
	 * Cerca tutti i Magazzini
	 * 
	 * @return List<Magazzino>
	 */
	public List<Magazzino> getAllMagazzini() {
		List<Magazzino> magazzini = new ArrayList<Magazzino>();
		Iterable<Magazzino> itr = magazzinoRep.findAll();
		magazzini.addAll((Collection<? extends Magazzino>) itr);
		return magazzini;
	}

	/**
	 * Elimina il Luogo con id indicato, controllando in caso di 
	 * Locker: le Celle devono essere tuttet vuote
	 * Negozio: rimuove l'id collegato al Commerciante
	 * 
	 * @param id
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void eliminaLuogo(Long idLuogo) throws Exception {
		if (lockerSer.isLocker(idLuogo)) {
			if (lockerSer.isEmpty(idLuogo)) {
				lockerSer.eliminaCelle(idLuogo);
				luogoRep.deleteById(idLuogo);
				interfSer.eliminaInterfaccia(idLuogo);
			} else
				throw new Exception("Prima di eliminare il Locker liberare tutte le celle");

		} else if (luogoRep.findById(idLuogo).orElse(null).getTipo().equals("NEGOZIO")) {
			merceSer.eliminaMerceByIdNegozio(idLuogo);
			commercianteSer.eliminaIdNegozio(idLuogo);
			luogoRep.deleteById(idLuogo);
		} else if (luogoRep.findById(idLuogo).orElse(null).getTipo().equals("MAGAZZINO"))
			luogoRep.deleteById(idLuogo);
		else throw new Exception("Id non legato ad un Luogo");
	}

	/**
	 * Ritorna la lista dei Negozi che hanno la Merce richiesta
	 * 
	 * @param String merce
	 * @return List<Luogo>
	 * @throws Exception
	 */
	public List<Luogo> getNegoziMerce(String merce) throws Exception {
		List<Long> listaNegozi = merceSer.cercaNegoziMerce(merce);
		if (listaNegozi == null)
			throw new Exception();
		else {
			List<Luogo> negozi = new ArrayList<Luogo>();
			luogoRep.findAllById(listaNegozi).forEach(negozi::add);
			return negozi;
		}
	}
	
	/**
	 * Determina se il Luogo con id indicato è di tipo Magazzino
	 * 
	 * @param Long idLuogo
	 * @return boolean
	 */
	public boolean isMagazzino(Long idLuogo) {
		if (magazzinoRep.findById(idLuogo).isPresent())
			return true;
		else
			return false;
	}

	/**
	 * Cerca il Negozio collegato al Commerciante indicato
	 * 
	 * @param Long idCommerciante
	 * @return Negozio
	 */
	public Negozio getNegozioByIdCommerciante(Long idCommerciante) {
		return negozioRep.findByIdCommerciante(idCommerciante);
	}

	/**
	 * Cerca il Negozio con il nome indicato
	 * 
	 * @param String nome
	 * @return Negozio
	 */
	public Negozio getNegozioByNome(String nome) {
		return negozioRep.findByNome(nome);
	}
}
