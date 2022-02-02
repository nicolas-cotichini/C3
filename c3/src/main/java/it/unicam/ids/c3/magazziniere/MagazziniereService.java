package it.unicam.ids.c3.magazziniere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.ordine.DettaglioOrdine;
import it.unicam.ids.c3.ordine.Ordine;
import it.unicam.ids.c3.ordine.OrdineService;

@Service
public class MagazziniereService {

	@Autowired
	MagazziniereRepository magazziniereRep;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	OrdineService ordineSer;
	
	/**
	 * Crea un nuovo Magazziniere
	 * 
	 * @param Magaziniere
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void creaMagazziniere(Magazziniere magazziniere) {
		magazziniere.setPassword(passwordEncoder.encode(magazziniere.getPassword()));
		magazziniereRep.save(new Magazziniere(magazziniere));
	}
	
	/**
	 * Cerca il Magazziniere con id indicato
	 * 
	 * @param Long idMagazziniere
	 * @return Magazziniere
	 */
	public Magazziniere getById(Long idMagazziniere) {
		return magazziniereRep.findById(idMagazziniere).orElseThrow();
	}

	public Ordine getOrdineById(Long idOrdine) {
		return ordineSer.getOrdineById(idOrdine);
	}

	public DettaglioOrdine getDettagliOrdine(Long idOrdine) {
		return ordineSer.getDettagliOrdine(idOrdine);
	}
	
	public void setStatoOrdine(Long idOrdine) throws Exception {
		ordineSer.setStatoOrdine(idOrdine);
	}
}
