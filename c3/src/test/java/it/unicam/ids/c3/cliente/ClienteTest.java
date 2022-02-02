package it.unicam.ids.c3.cliente;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.acquisti.AcquistoRepository;
import it.unicam.ids.c3.acquisti.AcquistoService;
import it.unicam.ids.c3.acquisti.Dimensione;
import it.unicam.ids.c3.corriere.Corriere;
import it.unicam.ids.c3.corriere.CorriereService;
import it.unicam.ids.c3.ordine.Ordine;
import it.unicam.ids.c3.ordine.OrdineRepository;
import it.unicam.ids.c3.ordine.OrdineService;
import it.unicam.ids.c3.user.UserRepository;
import it.unicam.ids.c3.user.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ClienteTest {

	@Autowired
	ClienteService clienteSer;
	@Autowired
	UserService userSer;
	@Autowired
	UserRepository userRep;
	@Autowired
	AcquistoService acquistoSer;
	@Autowired
	CorriereService corriereSer;
	@Autowired
	OrdineService ordineSer;
	@Autowired
	OrdineRepository ordineRep;
	@Autowired
	AcquistoRepository acquistoRep;
	
	@Test
	@Order(1)
	void testCreaCliente() {
		Cliente cliente = new Cliente();
		cliente.setAttivo(true);
		cliente.setEmail("clientex@gmail.com");
		cliente.setNome("cliente");
		cliente.setCognome("cognome");
		cliente.setPassword("password");
		try {
			clienteSer.creaCliente(cliente);
		} catch (Exception e) {
			fail("Creazione fallita");
		}
	}
	
	@Test
	@Order(2)
	void testCreaOrdine() {
		boolean exist = userRep.existsByEmail("clientex@gmail.com");
		assertTrue(exist);
		Cliente cliente = (Cliente) userSer.findByEmail("clientex@gmail.com");
		assertNotNull(cliente);
		try {
			clienteSer.generaOrdine(cliente.getId());
			fail("Ordine privo di acquisti");
		} catch (Exception e) {}
		
		//Crea Acquisto da ordinare
		Acquisto acquisto = new Acquisto();
		acquisto.setIdCliente(cliente.getId());
		acquisto.setIdCommerciante(3L);
		acquisto.setIdNegozio(4L);
		acquisto.setDimensione(Dimensione.MEDIO);
		acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		assertNotNull(clienteSer.getAllAcquistoCliente(cliente.getId()));
		try {
			clienteSer.generaOrdine(cliente.getId());
		} catch (Exception e) {fail("Ordine non generato, " + e.getCause() );}
		
		
		List<Ordine> ordini = clienteSer.getAllOrdiniCliente(cliente.getId());
		Ordine ordine = ordini.get(0);
		assertNotNull(ordine);
		try {
			clienteSer.creaOrdine(ordine.getId(), 7L);
			fail("Non ci sono Corrierei disponibili");
		} catch (Exception e) {}
		
		//Attiva profilo Corriere
		Corriere corriere = (Corriere) userSer.getById(5L);
		assertNotNull(corriere);
		try {
			corriereSer.setOperativo(corriere.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		try {
			clienteSer.creaOrdine(ordine.getId(), 7L);
		} catch (Exception e) {fail(e.getMessage());}
		
		//Disattiva profilo Corriere
		try {
			corriereSer.setOperativo(corriere.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(3)
	void testEliminaCLiente() {
		Cliente cliente = (Cliente) userSer.findByEmail("clientex@gmail.com");
		assertNotNull(cliente);
		Long idCliente = cliente.getId();
		try{
			clienteSer.deleteById(idCliente);
			fail("Hai ancora Ordini da ritirare");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//Ritira Ordine settandolo in "RITIRATO"
		List<Ordine> ordini = clienteSer.getAllOrdiniCliente(idCliente );
		Ordine ordine = ordini.get(0);
		try {
			ordineSer.setStatoOrdine(ordine.getId());
			ordineSer.setStatoOrdine(ordine.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@After
	void testliberaDB() {
		Cliente cliente = (Cliente) userSer.findByEmail("clientex@gmail.com");
		assertNotNull(cliente);
		List<Ordine> ordini = clienteSer.getAllOrdiniCliente(cliente.getId());
		Ordine ordine = ordini.get(0);
		Corriere corriere = (Corriere) userSer.getById(5L);
		assertNotNull(corriere);
		try {
			corriereSer.rimuoviOrdineConsegna(ordine.getId(), corriere.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		try {
			clienteSer.deleteById(cliente.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		ordineRep.deleteAll();
		acquistoRep.deleteAll();	
	}

}
