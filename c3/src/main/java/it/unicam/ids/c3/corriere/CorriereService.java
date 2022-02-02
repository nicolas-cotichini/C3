package it.unicam.ids.c3.corriere;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.luogo.LuogoService;
import it.unicam.ids.c3.ordine.DettaglioOrdine;
import it.unicam.ids.c3.ordine.Ordine;
import it.unicam.ids.c3.ordine.OrdineService;

@Service
public class CorriereService {

	@Autowired
	CorriereRepository corriereRep;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	OrdineService ordineSer;
	@Autowired
	LuogoService luogoSer;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void creaCorriere(Corriere corriere) {
		corriere.setPassword(passwordEncoder.encode(corriere.getPassword()));
		corriereRep.save(new Corriere(corriere));
	}

	public List<Corriere> findByOperativo() {
		return corriereRep.findByOperativo(true);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void rimuoviOrdineConsegna(Long idOrdine, Long idCorriere) {
		Corriere corriere = corriereRep.findById(idCorriere).orElse(null);
		corriere.getListaConsegne().remove(ordineSer.getOrdineById(idOrdine));
		corriereRep.save(corriere);
	}
	
	public Corriere getById(Long idCorriere) {
		return corriereRep.findById(idCorriere).orElseThrow();
	}
	
	public boolean getOperativo(Long idCorriere) {
		Corriere corriere = corriereRep.findById(idCorriere).orElse(null);
		return corriere.getOperativo();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void setOperativo(Long idCorriere) throws Exception {
		try {
		Corriere corriere = corriereRep.findById(idCorriere).orElse(null);
		corriere.setOperativo();
		corriereRep.save(corriere);
		} catch (Exception e) {
			throw new Exception("Cambio stato fallito, ritentare");
		}
	}

	public List<Ordine> getListaConsegna(Long idCorriere) {
		Corriere corriere = corriereRep.findById(idCorriere).orElse(null);
		return corriere.getListaConsegne();
	}
	
	public List<Acquisto> getListaRitiro(Long idOrdine) {
		return ordineSer.getAllAcquisti(idOrdine);
	}
	
	public DettaglioOrdine getDettagliOrdine(Long idOrdine) {
		return ordineSer.getDettagliOrdine(idOrdine);
	}

	public String getIndirizzoLuogoConsegna(Long idNegozio) {
		return ordineSer.getIndirizzoLuogoConsegna(idNegozio);
	}
	
	public void setStatoOrdine(Long idOrdine) throws Exception {
		ordineSer.setStatoOrdine(idOrdine);
	}
	
	public void cambiaLuogoConsegna(Long idOrdine, Long idLuogo) throws Exception {
		ordineSer.cambiaLuogoConsegna(idOrdine, idLuogo);
	}
}
