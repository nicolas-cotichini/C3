package it.unicam.ids.c3;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.unicam.ids.c3.amministratore.Amministratore;
import it.unicam.ids.c3.amministratore.AmministratoreService;
import it.unicam.ids.c3.cliente.Cliente;
import it.unicam.ids.c3.cliente.ClienteService;
import it.unicam.ids.c3.commerciante.Commerciante;
import it.unicam.ids.c3.corriere.Corriere;
import it.unicam.ids.c3.interflocker.InterfLocker;
import it.unicam.ids.c3.luogo.Locker;
import it.unicam.ids.c3.luogo.Luogo;
import it.unicam.ids.c3.luogo.Magazzino;
import it.unicam.ids.c3.luogo.Negozio;
import it.unicam.ids.c3.magazziniere.Magazziniere;
import it.unicam.ids.c3.user.UserService;

@SpringBootTest
class C3ApplicationTests {

	@Autowired
	UserService userSer;
	@Autowired
	AmministratoreService amministratoreSer;
	@Autowired
	ClienteService clienteSer;
		
	@Test
	void populateDB() {
		//Amministratore
		Amministratore amministratore = new Amministratore();
		amministratore.setNome("Amministra");
		amministratore.setCognome("Tore");
		amministratore.setEmail("amministratore@ccc.com");
		amministratore.setPassword("password");
		try {
			amministratoreSer.creaAmministratore(amministratore);
		} catch (Exception e) {
			fail("Creazione amministratore fallita");
		}
		//Cliente
		Cliente cliente = new Cliente();
		cliente.setAttivo(true);
		cliente.setEmail("cliente@gmail.com");
		cliente.setNome("Cliente");
		cliente.setCognome("Cognome");
		cliente.setPassword("password");
		try {
			clienteSer.creaCliente(cliente);
		} catch (Exception e) {
			fail("Creazione fallita");
		}
		//Commerciante
		Commerciante commerciante = new Commerciante();
		commerciante.setAttivo(true);
		commerciante.setEmail("commerciante@ccc.com");
		commerciante.setNome("Commerciante");
		commerciante.setCognome("Commerciante");
		commerciante.setPassword("password");
		try {
			amministratoreSer.creaCommerciante(commerciante);;
		} catch (Exception e) {
			fail("Creazione fallita");
		}
		//NegozioCommerciante
		Negozio negozio = new Negozio();
		commerciante = (Commerciante) userSer.findByEmail("commerciante@ccc.com");
		negozio.setNome("Negozio2");
		negozio.setIdCommerciante(commerciante.getId());
		negozio.setIndirizzo("via dei Negozi 12/b");
		negozio.setNote("");
		negozio.setOrarioApertura("");
		try {
			amministratoreSer.creaNegozio(negozio);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		//Corriere
		Corriere corriere = new Corriere();
		corriere.setEmail("corriere@ccc.com");
		corriere.setNome("Corriere");
		corriere.setCognome("Corriere");
		corriere.setPassword("password");
		try {
			amministratoreSer.creaCorriere(corriere);
		} catch (Exception e) {
			fail("Creazione fallita");
		}
		//Magazziniere
		Magazziniere magazziniere = new Magazziniere();
		magazziniere.setEmail("magazziniere@ccc.com");
		magazziniere.setNome("Magazziniere");
		magazziniere.setCognome("Magazziniere");
		magazziniere.setPassword("password");
		try {
			amministratoreSer.creaMagazziniere(magazziniere);
		} catch (Exception e) {
			fail("Creazione fallita");
		}
		//Magazzino
		Magazzino magazzino = new Magazzino();
		magazzino.setNome("Magazzino Fenice");
		magazzino.setIndirizzo("via dei Magazzini 12/b");
		magazzino.setNote("");
		magazzino.setOrarioApertura("");
		try {
			amministratoreSer.creaMagazzino(magazzino);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		//Locker
		Locker locker = new Locker();
		locker.setNome("Locker Andromeda");
		locker.setIndirizzo("via dei Locker 12/b");
		locker.setNote("");
		try {
			amministratoreSer.creaLocker(locker);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
		//InterfLocker
		List<Luogo> lockers = amministratoreSer.getLuogoByTipo("LOCKER");
		Locker lck = (Locker) lockers.get(0);
		InterfLocker interfaccia = new InterfLocker();
		interfaccia.setNome("Locker");
		interfaccia.setCognome("Andromeda");
		interfaccia.setEmail("interflockadromeda@ccc.com");
		interfaccia.setPassword("password");
		interfaccia.setRuolo("INTLOCKER");
		interfaccia.setIdLocker(lck.getId());
		try {
			amministratoreSer.creaInterfLocker(interfaccia);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
