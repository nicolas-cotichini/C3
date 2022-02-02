package it.unicam.ids.c3.amministratore;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.ids.c3.commerciante.Commerciante;
import it.unicam.ids.c3.corriere.Corriere;
import it.unicam.ids.c3.interflocker.InterfLocker;
import it.unicam.ids.c3.luogo.Locker;
import it.unicam.ids.c3.luogo.Luogo;
import it.unicam.ids.c3.luogo.Magazzino;
import it.unicam.ids.c3.luogo.Negozio;
import it.unicam.ids.c3.magazziniere.Magazziniere;
import it.unicam.ids.c3.user.User;

@RestController
@RequestMapping("/amministratore")
public class AmministratoreController {

	@Autowired
	AmministratoreService amministratoreSer;

	@PostMapping("/registrazione-amministratore")
	public ResponseEntity<?> registraAmministratore(@RequestBody Amministratore personale) {
		try {
			amministratoreSer.creaAmministratore(personale);
		} catch (Exception e) {
			return new ResponseEntity<String>("Impossibile registrare personale. " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione avvenuta con successo!");
	}

	@PostMapping("/registrazione-commerciante")
	public ResponseEntity<?> registraCommerciante(@RequestBody Commerciante personale) {
		try {
			amministratoreSer.creaCommerciante(personale);
		} catch (Exception e) {
			return new ResponseEntity<String>("Impossibile registrare personale. " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione avvenuta con successo!");
	}

	@PostMapping("/registrazione-corriere")
	public ResponseEntity<?> registraCorriere(@RequestBody Corriere personale) {
		try {
			amministratoreSer.creaCorriere(personale);
		} catch (Exception e) {
			return new ResponseEntity<String>("Impossibile registrare personale. " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione avvenuta con successo!");
	}

	@PostMapping("/registrazione-magazziniere")
	public ResponseEntity<?> registraMagazziniere(@RequestBody Magazziniere personale) {
		try {
			amministratoreSer.creaMagazziniere(personale);
		} catch (Exception e) {
			return new ResponseEntity<String>("Impossibile registrare personale. " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione avvenuta con successo!");
	}
	
	@PostMapping("/registrazione-interfaccia-locker")
	public ResponseEntity<?> registraInterfLocker(@RequestBody InterfLocker interf) {
		try {
			amministratoreSer.creaInterfLocker(interf);
		} catch (Exception e) {
			return new ResponseEntity<String>("Impossibile registrare interfaccia" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione avvenuta con successo!");
	}

	@GetMapping("/personale")
	public ResponseEntity<?> getPersonaleById(@RequestParam Long idPersonale) {
		List<User> personale = amministratoreSer.getPersonaleById(idPersonale);
		return new ResponseEntity<>(personale, HttpStatus.OK);
	}

	@GetMapping("/lista-personale")
	public ResponseEntity<?> getPersonaleByRuolo(@RequestParam String ruolo) {
		List<User> personale = amministratoreSer.getPersonaleByTipo(ruolo);
		return new ResponseEntity<>(personale, HttpStatus.OK);
	}

	@DeleteMapping("/elimina-personale")
	public ResponseEntity<?> eliminaPersonale(@RequestParam Long idPersonale) {
		try{
			amministratoreSer.eliminaPersonale(idPersonale);
			return ResponseEntity.ok().body(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/registrazione-locker")
	public ResponseEntity<?> registraLocker(@RequestBody Locker luogo) {
		try {
			amministratoreSer.creaLocker(luogo);
		} catch (Exception e) {
			return new ResponseEntity<String>("Impossibile registrare Locker. " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione avvenuta con successo!");
	}

	@PostMapping("/registrazione-magazzino")
	public ResponseEntity<?> registraMagazzino(@RequestBody Magazzino luogo) {
		try {
			amministratoreSer.creaMagazzino(luogo);
		} catch (Exception e) {
			return new ResponseEntity<String>("Impossibile registrare Magazzino. " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione avvenuta con successo!");
	}

	@PostMapping("/registrazione-negozio")
	public ResponseEntity<?> registraNegozio(@RequestBody Negozio luogo) {
		try {
			amministratoreSer.creaNegozio(luogo);
		} catch (Exception e) {
			return new ResponseEntity<String>("Impossibile registrare Negozio. " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione avvenuta con successo!");
	}

	@GetMapping("/luogo")
	public ResponseEntity<?> getluogoById(@RequestParam Long idLuogo) {
		List<Luogo> luogo = amministratoreSer.getLuogoById(idLuogo);
		return new ResponseEntity<>(luogo, HttpStatus.OK);
	}

	@GetMapping("/lista-luogo")
	public ResponseEntity<?> getLuogoByTipo(@RequestParam String tipo) {
		List<Luogo> luogo = amministratoreSer.getLuogoByTipo(tipo);
		return new ResponseEntity<>(luogo, HttpStatus.OK);
	}

	@DeleteMapping("elimina-luogo")
	public ResponseEntity<?> eliminaLuogo(@RequestParam Long idLuogo) throws Exception {
		try {
			amministratoreSer.eliminaLuogo(idLuogo);
			return ResponseEntity.ok().body(HttpStatus.OK);
		} catch (Exception e) {
		 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
