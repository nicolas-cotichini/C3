package it.unicam.ids.c3.amministratore;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.unicam.ids.c3.commerciante.Commerciante;
import it.unicam.ids.c3.commerciante.CommercianteService;
import it.unicam.ids.c3.luogo.Locker;
import it.unicam.ids.c3.luogo.Luogo;
import it.unicam.ids.c3.luogo.Magazzino;
import it.unicam.ids.c3.luogo.Negozio;
import it.unicam.ids.c3.magazziniere.Magazziniere;
import it.unicam.ids.c3.user.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class AmministratoreTest {

	@Autowired
	AmministratoreService amministratoreSer;
	@Autowired
	UserService userSer;
	@Autowired
	CommercianteService commercianteSer;
	
	@Test
	@Order(1)
	void testAmministratore() {
		try {
			amministratoreSer.eliminaPersonale(1L);
			fail("Eliminato unico amministratore");
		} catch (Exception e) {}
		Amministratore amministratore = new Amministratore();
		amministratore.setNome("Ammini");
		amministratore.setCognome("Stratore");
		amministratore.setEmail("amministratorex@ccc.com");
		amministratore.setPassword("password");
		try {
			amministratoreSer.creaAmministratore(amministratore);
		} catch (Exception e) {
			fail("Creazione amministratore fallita");
		}
		Long id = userSer.findByEmail("amministratorex@ccc.com").getId();
		try {
			amministratoreSer.eliminaPersonale(id);
		} catch (Exception e) {
			fail("Profilo non eliminato");
		}
		Amministratore ammi = new Amministratore();
		try {
			amministratoreSer.creaAmministratore(ammi);
			fail("Creazione Amministratore non dovrebbe essere permessa");
		}  catch (Exception e) {}
	}
	
	@Test
	@Order(2)
	void testCreaCommerciante() {
		Commerciante commerciante = new Commerciante();
		commerciante.setEmail("commerciantex@gmail.com");
		commerciante.setNome("Commerciante");
		commerciante.setCognome("Commerciante");
		commerciante.setPassword("password");
		commerciante.setIva("12012548962");
		try {
			amministratoreSer.creaCommerciante(commerciante);
		} catch (Exception e) {
			fail("Creazione fallita");
		}
	}
	
	@Test
	@Order(3)
	void testCreaNegozio() {
		Negozio negozio = new Negozio();
		Commerciante commerciante = (Commerciante) userSer.findByEmail("commerciantex@gmail.com");
		assertNotNull(commerciante, "Commerciante non trovato");
		try {
			amministratoreSer.creaNegozio(negozio);
			fail("Parametri NotNull Negozio non controllati");
		} catch (Exception e) {}
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
	}
	
	@Test
	@Order(4)
	void testEliminaNegozio() {
		Commerciante commerciante = (Commerciante) userSer.findByEmail("commerciantex@gmail.com");
		assertNotNull(commerciante, "Commerciante non trovato");
		Long idNegozio = commercianteSer.getIdNegozio(commerciante.getId());
		System.out.println(idNegozio);
		try {
			amministratoreSer.eliminaLuogo(idNegozio);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(5)
	void testEliminaCommerciante() {
		Commerciante commerciante = (Commerciante) userSer.findByEmail("commerciantex@gmail.com");
		assertNotNull(commerciante);
		Long id = commerciante.getId();
		try{
			userSer.eliminaProfilo(id);;
		} catch (Exception e) {
			fail("Eliminazione fallita");
		}
	}
	
	@Test
	@Order(6)
	void testCreaMagazzino() {
		Magazzino magazzino = new Magazzino();
		try {
			amministratoreSer.creaMagazzino(magazzino);
			fail("Parametri NotNull Magazzino non controllati");
		} catch (Exception e) {}
		magazzino.setNome("MagazzinoX");
		magazzino.setIndirizzo("via dei Magazzini 12/b");
		magazzino.setNote("");
		magazzino.setOrarioApertura("");
		try {
			amministratoreSer.creaMagazzino(magazzino);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	@Order(7)
	void testEliminaMagazzino() {
		List<Luogo> magazzini = amministratoreSer.getLuogoByTipo("MAGAZZINO");
		Long idMagazzino = magazzini.get(1).getId();
		try {
			amministratoreSer.eliminaLuogo(idMagazzino);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(8)
	void testCreaMagazziniere() {
		Magazziniere magazziniere = new Magazziniere();
		magazziniere.setEmail("magazzinierex@gmail.com");
		magazziniere.setNome("Magazziniere");
		magazziniere.setCognome("Magazziniere");
		magazziniere.setPassword("password");
		try {
			amministratoreSer.creaMagazziniere(magazziniere);
		} catch (Exception e) {
			fail("Creazione fallita");
		}
	}
	
	@Test
	@Order(9)
	void testEliminaMagazziniere() {
		Magazziniere magazziniere = (Magazziniere) userSer.findByEmail("magazzinierex@gmail.com");
		assertNotNull(magazziniere);
		Long id = magazziniere.getId();
		try{
			userSer.eliminaProfilo(id);
		} catch (Exception e) {
			fail("Eliminazione fallita");
		}
	}
	
	@Test
	@Order(10)
	void testCreaLocker() {
		Locker locker = new Locker();
		try {
			amministratoreSer.creaLocker(locker);
			fail("Parametri NotNull Locker non controllati");
		} catch (Exception e) {}
		locker.setNome("LockerX");
		locker.setIndirizzo("via dei Magazzini 12/b");
		locker.setNote("");
		try {
			amministratoreSer.creaLocker(locker);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Order(11)
	void testEliminaLocker() {
		List<Luogo> lockers = amministratoreSer.getLuogoByTipo("LOCKER");
		Long idLocker = lockers.get(1).getId();
		try {
			amministratoreSer.eliminaLuogo(idLocker);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
}
