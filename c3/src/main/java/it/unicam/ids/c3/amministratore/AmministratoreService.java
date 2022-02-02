package it.unicam.ids.c3.amministratore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.unicam.ids.c3.commerciante.Commerciante;
import it.unicam.ids.c3.commerciante.CommercianteService;
import it.unicam.ids.c3.corriere.Corriere;
import it.unicam.ids.c3.interflocker.InterfLocker;
import it.unicam.ids.c3.luogo.Locker;
import it.unicam.ids.c3.luogo.Luogo;
import it.unicam.ids.c3.luogo.LuogoService;
import it.unicam.ids.c3.luogo.Magazzino;
import it.unicam.ids.c3.luogo.Negozio;
import it.unicam.ids.c3.magazziniere.Magazziniere;
import it.unicam.ids.c3.user.User;
import it.unicam.ids.c3.user.UserService;

@Service
public class AmministratoreService {

	@Autowired
	AmministratoreRepository amministratoreRep;
	@Autowired
	UserService userSer;
	@Autowired
	LuogoService luogoSer;
	@Autowired
	CommercianteService commercianteSer;
	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * @return numero di profili di tipo Amministratore
	 */
	public Long count() {
		return amministratoreRep.count();
	}

	public void creaAmministratore(Amministratore amministratore) throws Exception {
		userSer.existByEmail(amministratore.getEmail());
		amministratore.setPassword(passwordEncoder.encode(amministratore.getPassword()));
		amministratoreRep.save(new Amministratore(amministratore));
	}

	public void creaCommerciante(Commerciante commerciante) throws Exception {
		userSer.creaCommerciante(commerciante);
	}

	public void creaCorriere(Corriere corriere) throws Exception {
		userSer.creaCorriere(corriere);
	}

	public void creaMagazziniere(Magazziniere magazziniere) throws Exception {
		userSer.creaMagazziniere(magazziniere);
	}
	
	public void creaInterfLocker(InterfLocker interf) throws Exception {
		userSer.creaInterfLocker(interf);
	}

	public List<User> getPersonaleById(Long idPersonale) {
		List<User> personale = new ArrayList<User>();
		personale.add(userSer.getById(idPersonale));
		return personale;
	}

	public List<User> getPersonaleByTipo(String tipo) {
		List<User> personale = userSer.getByTipo(tipo);
		return personale;
	}

	/**
	 * Elimina il personale indicato dall'id controllando nel caso di profilo
	 * di tipo Amministratore che questo non sia l'unico presente
	 * @param id
	 * @throws Exception
	 */
	public void eliminaPersonale(Long id) throws Exception {
		User user = userSer.getById(id);
		if (user.getRuolo().equals("AMMINISTRATORE")) {
			if (count() >= 2) {
				userSer.eliminaProfilo(id);
			}
			else
				throw new Exception("Unico profilo Amministratore, impossibile eliminare");
		} else 
			userSer.eliminaProfilo(id);
	}

	/**
	 * Controlla che i dati fondamentali del Luogo siano presenti 
	 * @param luogo
	 * @throws Exception
	 */
	public void controlloDatiLuogo(Luogo luogo) throws Exception {
		if (luogo.getIndirizzo() == null || luogo.getNome() == null)
			throw new Exception("Mancano dei dati");
	}
	
	public void creaLocker(Locker locker) throws Exception {
		controlloDatiLuogo(locker);
		luogoSer.creaLocker(locker);
	}

	public void creaMagazzino(Magazzino magazzino) throws Exception {
		controlloDatiLuogo(magazzino);
		luogoSer.creaMagazzino(magazzino);
	}

	/**
	 * Crea un Negozio controllando che l'idCommerciante appartenga ad un profilo di quel tipo
	 * e che non sia già collegato ad un altro Negozio
	 * 
	 * @param negozio
	 * @throws Exception
	 */
	public void creaNegozio(Negozio negozio) throws Exception {
		controlloDatiLuogo(negozio);
		Commerciante cm;
		try {
			cm = commercianteSer.getById(negozio.getIdCommerciante());
		} catch (Exception e) {
			throw new Exception("Id non legato ad un Commerciante");
		}
		if (cm.getIdNegozio() == null) {
			luogoSer.creaNegozio(negozio);
			commercianteSer.aggiornaIdNegozio(cm.getId(), luogoSer.getNegozioByNome(negozio.getNome()).getId());
		} else
			throw new Exception("Il Commerciante è già registrato ad un Negozio");
	}

	public List<Luogo> getLuogoById(Long idLuogo) {
		List<Luogo> luogo = new ArrayList<Luogo>();
		luogo.add(luogoSer.getById(idLuogo));
		return luogo;
	}

	public List<Luogo> getLuogoByTipo(String tipo) {
		List<Luogo> luogo = luogoSer.getAllByTipo(tipo);
		return luogo;
	}

	public void eliminaLuogo(Long id) throws Exception {
		luogoSer.eliminaLuogo(id);
	}
	

}
