package it.unicam.ids.c3.commerciante;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.acquisti.AcquistoRepository;
import it.unicam.ids.c3.acquisti.AcquistoService;
import it.unicam.ids.c3.acquisti.Dimensione;
import it.unicam.ids.c3.acquisti.Merce;
import it.unicam.ids.c3.acquisti.MerceRepository;
import it.unicam.ids.c3.acquisti.MerceService;
import it.unicam.ids.c3.amministratore.AmministratoreService;
import it.unicam.ids.c3.luogo.LuogoService;
import it.unicam.ids.c3.luogo.Negozio;
import it.unicam.ids.c3.user.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CommercianteTest {

	@Autowired
	AmministratoreService amministratoreSer;
	@Autowired
	CommercianteService commercianteSer;
	@Autowired
	UserService userSer;
	@Autowired
	LuogoService luogoSer;
	@Autowired
	AcquistoService acquistoSer;
	@Autowired
	MerceService merceSer;
	@Autowired
	AcquistoRepository acquistoRep;
	@Autowired
	MerceRepository merceRep;
	
	@Test
	@Order(1)
	void testCreate() {
		Commerciante commerciante = new Commerciante();
		commerciante.setAttivo(true);
		commerciante.setEmail("commerciantex@ccc.com");
		commerciante.setNome("Commerciante");
		commerciante.setCognome("Commerciante");
		commerciante.setPassword("password");
		try {
			commercianteSer.creaCommerciante(commerciante);
		} catch (Exception e) {
			fail("Creazione fallita");
		}
	}
	
	@Test
	@Order(2)
	void testCreaAcquisto() {
		Acquisto acquisto = new Acquisto();
		acquisto.setIdCliente(2L);
		acquisto.setIdCommerciante(3L);
		acquisto.setIdNegozio(4L);
		acquisto.setDimensione(Dimensione.MEDIO);
		acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		assertNotNull(commercianteSer.getAllAcquistoCommerciante(3L));
	}
	
	@Test
	@Order(3)
	void testMerce() {
		Merce merce = new Merce();
		merce.setNome("Nutella");
		try {
			merceSer.creaMerce(merce, 4L);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(merceSer.cercaNegoziMerce("Nutella"));
		assertNotNull(merceSer.cercaNegoziMerce("NUTELLA"));
		assertNotNull(merceSer.cercaNegoziMerce("nutella"));
		try {
			merceSer.creaMerce(merce, 4L);
			fail("Merce già presente");
		} catch (Exception e) {}
		
		assertNotNull(merceSer.getAllMerceByIdNegozio(4L));
		merceSer.eliminaMerceByIdNegozio(4L);
		assertNull(merceSer.getAllMerceByIdNegozio(4L));
		try {
			merceSer.creaMerce(merce, 4L);
		} catch (Exception e) {}
		
		Negozio negozio = new Negozio();
		Commerciante commerciante = (Commerciante) userSer.findByEmail("commerciantex@ccc.com");
		assertNotNull(commerciante, "Commerciante non trovato");
		negozio.setNome("NegozioX");
		negozio.setIdCommerciante(commerciante.getId());
		negozio.setIndirizzo("via dei Negozi 12/b");
		negozio.setNote("");
		negozio.setOrarioApertura("");
		try {
			amministratoreSer.creaNegozio(negozio);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		Long idNegozio = luogoSer.getNegozioByIdCommerciante(commerciante.getId()).getId();
		try {
			merceSer.creaMerce(merce, idNegozio);
		} catch (Exception e) {fail(e.getMessage());}
		
		//Controlla che la Merce legata al Negozio venga rimossa
		assertNotNull(merceSer.getAllMerceByIdNegozio(idNegozio));
		merceSer.eliminaMerceByIdNegozio(4L);
		assertNotNull(merceSer.getAllMerceByIdNegozio(idNegozio));
	}
	
	@Test
	@After
	void testDelete() {
		Commerciante commerciante = (Commerciante) userSer.findByEmail("commerciantex@ccc.com");
		assertNotNull(commerciante);
		Long id = commerciante.getId();
		try{
			userSer.eliminaProfilo(id);
		} catch (Exception e) {
			fail("Eliminazione fallita");
		}
		Long idNegozio = luogoSer.getNegozioByIdCommerciante(commerciante.getId()).getId();
		try {
			luogoSer.eliminaLuogo(idNegozio);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		acquistoRep.deleteAll();
		merceRep.deleteAll();
	}

}
