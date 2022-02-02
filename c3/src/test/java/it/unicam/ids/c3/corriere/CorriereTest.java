package it.unicam.ids.c3.corriere;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.After;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.acquisti.AcquistoRepository;
import it.unicam.ids.c3.acquisti.AcquistoService;
import it.unicam.ids.c3.acquisti.Dimensione;
import it.unicam.ids.c3.amministratore.AmministratoreService;
import it.unicam.ids.c3.cliente.ClienteService;
import it.unicam.ids.c3.ordine.GestoreLogistica;
import it.unicam.ids.c3.ordine.Ordine;
import it.unicam.ids.c3.ordine.OrdineRepository;
import it.unicam.ids.c3.user.UserRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CorriereTest {

	@Autowired
	CorriereService corriereSer;
	@Autowired
	GestoreLogistica gestoreLog;
	@Autowired
	AmministratoreService amministratoreSer;
	@Autowired
	AcquistoService acquistoSer;
	@Autowired
	ClienteService clienteSer;
	@Autowired
	UserRepository userRep;
	@Autowired
	OrdineRepository ordineRep;
	@Autowired
	AcquistoRepository acquistoRep;
	
	@Test
	@Order(1)
	void testCorriere() {
		Corriere corriere = new Corriere();
		corriere.setEmail("corrierex@ccc.com");
		corriere.setNome("Corriere");
		corriere.setCognome("Corriere");
		corriere.setPassword("password");
		try {
			amministratoreSer.creaCorriere(corriere);;
		} catch (Exception e) {
			fail("Creazione fallita");
		}
	}

	@Test
	@Order(2)
	void testAssegnaOrdine() {
		//Corriere creato precedentemente
		Corriere corriere = (Corriere) userRep.findByEmail("corrierex@ccc.com");
		
		// Crea Ordine
		// Crea Acquisto da ordinare
		Acquisto acquisto = new Acquisto();
		acquisto.setIdCliente(2L);
		acquisto.setIdCommerciante(3L);
		acquisto.setIdNegozio(4L);
		acquisto.setDimensione(Dimensione.MEDIO);
		acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		assertNotNull(clienteSer.getAllAcquistoCliente(2L));
		try {
			clienteSer.generaOrdine(2L);
		} catch (Exception e) {
			fail("Ordine non generato, " + e.getCause());
		}
		List<Ordine> ordini = clienteSer.getAllOrdiniCliente(2L);
		Ordine ordine = ordini.get(0);
		assertNotNull(ordine);
		
		//Nessun Corriere Operativo
		try {
			gestoreLog.cercaCorriere(ordine);
			fail("Nessun Corriere disponibile");
		} catch (Exception e1) {}
		assertSame(corriere.getListaConsegne().size(), 0);
		
		//Setta Corriere creato precedentemente come operativo
		try {
			corriereSer.setOperativo(corriere.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		//Assegna Ordine al Corriere
		try {
			gestoreLog.cercaCorriere(ordine);
		} catch (Exception e) {fail(e.getMessage());}
		corriere = corriereSer.getById(corriere.getId());
		assertNotNull(corriere.getListaConsegne());
		
		//Crea nuovo Corriere e lo rende Operativo
		Corriere corriere2 = new Corriere();
		corriere2.setEmail("corriere2@ccc.com");
		corriere2.setNome("Corriere");
		corriere2.setCognome("Corriere");
		corriere2.setPassword("password");
		try {
			amministratoreSer.creaCorriere(corriere2);
		} catch (Exception e) {
			fail("Creazione fallita");
		}
		
		corriere = (Corriere) userRep.findByEmail("corriere2@ccc.com");
		try {
			corriereSer.setOperativo(corriere.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertSame(corriere.getListaConsegne().size(), 0);
		
		//Crea nuovo Ordine
		acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		try {
			clienteSer.generaOrdine(2L);
		} catch (Exception e) {
			fail("Ordine non generato, " + e.getCause());
		}
		ordini = clienteSer.getAllOrdiniCliente(2L);
		ordine = ordini.get(1);
		
		//Affida l'Ordine al secondo Corriere creato in quanto
		//quello con la lista consegna minore
		try {
			gestoreLog.cercaCorriere(ordine);
		} catch (Exception e) {fail(e.getMessage());}
		corriere = corriereSer.getById(corriere.getId());
		assertNotNull(corriere.getListaConsegne());
		
	}
	
	@Test
	@After
	void liberaDB() {
		ordineRep.deleteAll();
		acquistoRep.deleteAll();
		Corriere corriere = (Corriere) userRep.findByEmail("corriere2@ccc.com");
		try {
			amministratoreSer.eliminaPersonale(corriere.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		corriere = (Corriere) userRep.findByEmail("corrierex@ccc.com");
		try {
			amministratoreSer.eliminaPersonale(corriere.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
