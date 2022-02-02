package it.unicam.ids.c3.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.cliente.ClienteService;
import it.unicam.ids.c3.commerciante.Commerciante;
import it.unicam.ids.c3.commerciante.CommercianteService;
import it.unicam.ids.c3.corriere.Corriere;
import it.unicam.ids.c3.corriere.CorriereService;
import it.unicam.ids.c3.interflocker.InterfLocker;
import it.unicam.ids.c3.interflocker.InterfLockerService;
import it.unicam.ids.c3.magazziniere.Magazziniere;
import it.unicam.ids.c3.magazziniere.MagazziniereService;
import it.unicam.ids.c3.security.UserDetailsImpl;

@Service
public class UserService{
	
	@Autowired
	UserRepository<User> userRep;
	@Autowired
	ClienteService clienteSer;
	@Autowired
	CommercianteService commercianteSer;
	@Autowired
	CorriereService corriereSer;
	@Autowired
	InterfLockerService interfSer;
	@Autowired
	MagazziniereService magazziniereSer;
	
	/**
	 * Controlla se esiste un utente registrato con tale email
	 * 
	 * @param String email
	 * @throws Exception 
	 */
	public void existByEmail (String email) throws Exception {
		if (userRep.existsByEmail(email))
			throw new Exception("Email: " + email+  " già in uso");
	}
	
	/**
	 * Ritorna l'User registrato con tale email
	 * 
	 * @param String email 
	 * @return User 
	 */
	public User findByEmail (String email) {
		return userRep.findByEmail(email);
	}
	
	public User getById(Long id) {
		return userRep.findById(id).orElseThrow();
	}
	
	public List<User> getByTipo(String tipo) {
		return userRep.findAllByRuolo(tipo);
	}
	
	public UserDetailsImpl getUserDetails() {
		return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}
		
	public void creaCommerciante(Commerciante commerciante) throws Exception {
		existByEmail(commerciante.getEmail());
		try {
			commercianteSer.creaCommerciante(commerciante);
		} catch (Exception e) {
			throw new Exception("Iva: " + commerciante.getIva() +  " già in uso");
		}
	}

	public void creaCorriere(Corriere corriere) throws Exception {
		existByEmail(corriere.getEmail());
		corriereSer.creaCorriere(corriere);
	}
	
	public void creaInterfLocker(InterfLocker interf) throws Exception {
		existByEmail(interf.getEmail());
		interfSer.creaInterfLocker(interf);
	}
	
	public void creaMagazziniere(Magazziniere magazziniere) throws Exception {
		existByEmail(magazziniere.getEmail());
		magazziniereSer.creaMagazziniere(magazziniere);
	}

	/**
	 * Elimina il profilo indicato, controllando se nel caso di
	 * Corriere la lista delle consegne sia vuoto
	 * 
	 * @param Long id profilo
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void eliminaProfilo(Long id) throws Exception {
		User user = getById(id);
		if (user.getRuolo().equals("CORRIERE")) {
			if(corriereSer.getListaConsegna(id).isEmpty()) {
				userRep.deleteById(id);
			}
			else throw new Exception("Profilo Corriere con ancora delle consegne in sospeso, impossibile eliminare");
		} else userRep.deleteById(id);	
	}

}
