package it.unicam.ids.c3.luogo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import it.unicam.ids.c3.corriere.Corriere;
import it.unicam.ids.c3.corriere.CorriereRepository;
import it.unicam.ids.c3.corriere.CorriereService;
import it.unicam.ids.c3.interflocker.InterfLocker;
import it.unicam.ids.c3.interflocker.InterfLockerRepository;
import it.unicam.ids.c3.interflocker.InterfLockerService;
import it.unicam.ids.c3.ordine.Ordine;
import it.unicam.ids.c3.ordine.OrdineRepository;
import it.unicam.ids.c3.ordine.OrdineService;
import it.unicam.ids.c3.ordine.StatoOrdine;
import it.unicam.ids.c3.user.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class LockerTest {

	@Autowired
	LockerService lockerSer;
	@Autowired
	LockerRepository lockerRep;
	@Autowired
	CellaRepository cellaRep;
	@Autowired
	LuogoService luogoSer;
	@Autowired
	UserService userSer;
	@Autowired
	AmministratoreService amministratoreSer;
	@Autowired
	AcquistoService acquistoSer;
	@Autowired
	ClienteService clienteSer;
	@Autowired
	CorriereService corriereSer;
	@Autowired
	OrdineService ordineSer;
	@Autowired
	InterfLockerService interfLockerSer;
	@Autowired
	InterfLockerRepository interfLockerRep;
	@Autowired
	OrdineRepository ordineRep;
	@Autowired
	AcquistoRepository acquistoRep;
	@Autowired
	CorriereRepository corriereRep;

	@Test
	@Order(1)
	void testLocker() {
		// Crea Locker
		Locker locker = new Locker();
		try {
			amministratoreSer.creaLocker(locker);
			fail("Parametri NotNull Locker non controllati");
		} catch (Exception e) {
		}
		locker.setNome("LockerX");
		locker.setIndirizzo("via dei Magazzini 12/c");
		locker.setNote("");
		try {
			amministratoreSer.creaLocker(locker);
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	@Order(2)
	void testNumCelleDaOccupare() {
		// Leenda: 1 Cella = 1 Acquisto Grande = 2 Acquisti Medi = 4 Acquisti Piccoli
		// Crea singolo Acquisto Grande e lo Ordina
		Acquisto acquisto = new Acquisto();
		acquisto.setIdCliente(2L);
		acquisto.setIdCommerciante(3L);
		acquisto.setIdNegozio(4L);
		acquisto.setDimensione(Dimensione.GRANDE);
		acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");

		// 1 Acquisto Grande = 1 Cella
		List<Acquisto> lista = (List<Acquisto>) acquistoRep.findAll();
		assertSame(lockerSer.calcolaCelle(lista), 1);

		// 1 Acquisto Grande + 1 Piccolo = 2 Celle
		acquisto.setDimensione(Dimensione.PICCOLO);
		acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		lista = (List<Acquisto>) acquistoRep.findAll();
		assertSame(lockerSer.calcolaCelle(lista), 2);

		// 1 Acquisto Grande + 4 Piccoli = 2 Celle
		for (int i = 0; i < 3; i++) {
			acquisto.setDimensione(Dimensione.PICCOLO);
			acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
			lista = (List<Acquisto>) acquistoRep.findAll();
			assertSame(lockerSer.calcolaCelle(lista), 2);
		}

		// 1 Acquisto Grande + 4 Piccoli + 2 Medi = 3 Celle
		for (int i = 0; i < 2; i++) {
			acquisto.setDimensione(Dimensione.MEDIO);
			acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		}
		lista = (List<Acquisto>) acquistoRep.findAll();
		assertSame(lockerSer.calcolaCelle(lista), 3);

		acquistoRep.deleteAll();

		// 2 Acquisti Grandi = 2 Celle
		for (int i = 0; i < 2; i++) {
			acquisto.setDimensione(Dimensione.GRANDE);
			acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		}
		lista = (List<Acquisto>) acquistoRep.findAll();
		assertSame(lockerSer.calcolaCelle(lista), 2);

		acquistoRep.deleteAll();

		// 2 Acquisti Medi = 1 Celle
		for (int i = 0; i < 2; i++) {
			acquisto.setDimensione(Dimensione.MEDIO);
			acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		}
		lista = (List<Acquisto>) acquistoRep.findAll();
		assertSame(lockerSer.calcolaCelle(lista), 1);
		// +1 Acquisto Medio = 2Celle
		acquisto.setDimensione(Dimensione.MEDIO);
		acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		lista = (List<Acquisto>) acquistoRep.findAll();
		assertSame(lockerSer.calcolaCelle(lista), 2);

		acquistoRep.deleteAll();

		// 4 Acquisti Piccoli = 1 Cella
		for (int i = 0; i < 4; i++) {
			acquisto.setDimensione(Dimensione.PICCOLO);
			acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		}
		lista = (List<Acquisto>) acquistoRep.findAll();
		assertSame(lockerSer.calcolaCelle(lista), 1);
		// +1 Acquisto Piccolo = 2 Celle
		acquisto.setDimensione(Dimensione.PICCOLO);
		acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		lista = (List<Acquisto>) acquistoRep.findAll();
		assertSame(lockerSer.calcolaCelle(lista), 2);

		acquistoRep.deleteAll();
	}

	@Test
	@Order(3)
	void testOrdineLocker() {
		Acquisto acquisto = new Acquisto();
		acquisto.setIdCliente(2L);
		acquisto.setIdCommerciante(3L);
		acquisto.setIdNegozio(4L);
		acquisto.setDimensione(Dimensione.GRANDE);
		for (int i = 0; i <= 29; i++) {// num Celle libere = 30
			acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		}

		// idLocker precedentemente creato
		List<Luogo> lockers = amministratoreSer.getLuogoByTipo("LOCKER");
		Long idLocker = lockers.get(1).getId();

		try {
			clienteSer.generaOrdine(2L);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		List<Ordine> ordini = clienteSer.getAllOrdiniCliente(2L);
		Ordine ordine = ordini.get(0);
		Corriere corriere = (Corriere) userSer.getById(5L);
		assertNotNull(corriere);
		// setta Corriere come Operativo
		if (!corriere.getOperativo()) {
			try {
				corriereSer.setOperativo(corriere.getId());
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
		try {
			clienteSer.creaOrdine(ordine.getId(), idLocker);
		} catch (Exception e) {
			fail(e.getMessage());
		} // Locker pieno ora

		// Prepara nuovo Ordine
		acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		try {
			clienteSer.generaOrdine(2L);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// Richiesta consegna Ordine in Locker già pieno
		ordini = clienteSer.getAllOrdiniCliente(2L);
		ordine = ordini.get(1);
		try {
			clienteSer.creaOrdine(ordine.getId(), idLocker);
			fail("Locker dovrebbe essere pieno");
		} catch (Exception e) {
		}

		// Libera Locker
		ordine = ordini.get(0);
		lockerSer.liberaCelleOrdine(ordine.getId(), idLocker);

		// Nuovo ordine con celle libere
		ordine = ordini.get(1);
		try {
			clienteSer.creaOrdine(ordine.getId(), idLocker);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// Svuota celle
		lockerSer.liberaCelleOrdine(ordine.getId(), idLocker);
	}

	@Test
	@Order(4)
	void testInterfLocker() {
		List<Luogo> lockers = amministratoreSer.getLuogoByTipo("LOCKER");
		Locker locker = (Locker) lockers.get(1);
		assertSame(locker.getInterfaccia(), false);

		InterfLocker interfaccia = new InterfLocker();
		interfaccia.setNome("Locker");
		interfaccia.setCognome("X");
		interfaccia.setEmail("interfacciaLockerx@ccc.com");
		interfaccia.setPassword("password");
		interfaccia.setRuolo("INTLOCKER");

		// idLocker null
		try {
			amministratoreSer.creaInterfLocker(interfaccia);
			fail("Manca idLocker");
		} catch (Exception e) {
		}

		//registra nuovo InterfLocker
		interfaccia.setIdLocker(locker.getId());
		try {
			amministratoreSer.creaInterfLocker(interfaccia);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// profilo già legato a questo Locker
		try {
			amministratoreSer.creaInterfLocker(interfaccia);
			fail("Profilo già esistente");
		} catch (Exception e) {
		}

		assertNotNull(interfLockerRep.findByIdLocker(locker.getId()));
		acquistoRep.deleteAll();
		ordineRep.deleteAll();
	}

	@Test
	@Order(5)
	void testApriCelle() {
		List<Luogo> lockers = amministratoreSer.getLuogoByTipo("LOCKER");
		Locker locker = (Locker) lockers.get(1);
		InterfLocker interfaccia = interfLockerRep.findByIdLocker(locker.getId());

		// Prepara Ordine occupando 2 Celle
		Acquisto acquisto = new Acquisto();
		acquisto.setIdCliente(2L);
		acquisto.setIdCommerciante(3L);
		acquisto.setIdNegozio(4L);
		acquisto.setDimensione(Dimensione.GRANDE);
		for (int i = 0; i < 2; i++) {
			acquistoSer.creaAcquisto(acquisto, 3L, 4L, "");
		}
		try {
			clienteSer.generaOrdine(2L);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		List<Ordine> ordini = clienteSer.getAllOrdiniCliente(2L);
		Ordine ordine = ordini.get(0);
		try {
			clienteSer.creaOrdine(ordine.getId(), locker.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Setta l'Ordine come Consegnato
		try {
			corriereSer.setStatoOrdine(ordine.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		ordine = ordineSer.getOrdineById(ordine.getId());
		assertSame(ordine.getStato(), StatoOrdine.CONSEGNATO);

		// Recupera Password
		List<String> passwords = lockerSer.getPasswordOrdine(ordine.getId(), locker.getId());
		assertSame(passwords.size(), 2);

		// Apro prima Cella di 2
		try {
			interfLockerSer.apriCella(interfaccia.getId(), passwords.get(0));
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertSame(ordine.getStato(), StatoOrdine.CONSEGNATO);

		// Apro seconda Cella di 2
		try {
			interfLockerSer.apriCella(interfaccia.getId(), passwords.get(1));
		} catch (Exception e) {
			fail(e.getMessage());
		}
		//Controlla che l'Ordine venga aggiornato una volta ritirato l'Ordine da ogni Cella
		ordine = ordineSer.getOrdineById(ordine.getId());
		assertSame(ordine.getStato(), StatoOrdine.RITIRATO);
	}

	@Test
	@After
	void testDelete() {
		// setta Corriero come non operativo
		Corriere corriere = (Corriere) userSer.getById(5L);
		corriere.setListaConsegne(null);
		if (corriere.getOperativo())
			corriere.setOperativo();
		corriereRep.save(corriere);

		acquistoRep.deleteAll();
		ordineRep.deleteAll();

		// Elimina Locker
		List<Luogo> lockers = amministratoreSer.getLuogoByTipo("LOCKER");
		Long idLocker = lockers.get(1).getId();
		assertNotNull(luogoSer.getById(idLocker));
		assertNotNull(cellaRep.findAllByIdLocker(idLocker));
		assertSame(cellaRep.findAllByIdLocker(idLocker).size(), 30);
		try {
			amministratoreSer.eliminaLuogo(idLocker);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNull(luogoSer.getById(idLocker));
		assertSame(cellaRep.findAllByIdLocker(idLocker).size(), 0);
		assertNull(interfLockerRep.findByIdLocker(idLocker));
	}

}
