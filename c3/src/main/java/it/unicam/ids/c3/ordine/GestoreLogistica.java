package it.unicam.ids.c3.ordine;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.ids.c3.corriere.Corriere;
import it.unicam.ids.c3.corriere.CorriereRepository;
import it.unicam.ids.c3.corriere.CorriereService;

@Service
public class GestoreLogistica {

	@Autowired
	CorriereService corriereSer;
	@Autowired
	CorriereRepository corriereRep;
	
	/**
	 * Ricerca i corrieri disponibili per la consegna ed affida l'ordine al meno oberato 
	 * 
	 * @param Ordine da affidare
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void cercaCorriere(Ordine ordine) throws Exception {
		List<Corriere> corrieri = corriereSer.findByOperativo();
		if (corrieri.isEmpty())
			throw new Exception("Non ci sono corrieri disponibili");
		affidaOrdine(corrieri, ordine);
	}

	/**
	 * Ricerca il Corriere con il minor numero di consegne da effettuare
	 * 
	 * @param Lista<Corriere> disponibili
	 * @param Ordine
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void affidaOrdine(List<Corriere> corriere, Ordine ordine) {
		Iterator<Corriere> cor = corriere.iterator();
		Corriere scelto = cor.next(), x;
		while (cor.hasNext()) {
			x = cor.next();
			if (scelto.getListaConsegne().size() > x.getListaConsegne().size())
				scelto = x;
		}
		scelto.getListaConsegne().add(ordine);
		corriereRep.save(scelto);
	}

}
