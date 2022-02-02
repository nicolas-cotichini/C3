package it.unicam.ids.c3.cliente;

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

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.luogo.Luogo;
import it.unicam.ids.c3.ordine.DettaglioOrdine;
import it.unicam.ids.c3.ordine.Ordine;
import it.unicam.ids.c3.user.UserService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	UserService userSer;
	@Autowired
	ClienteService clienteSer;


	@PostMapping("/registrazione")
	public ResponseEntity<?> registraCliente(@RequestBody Cliente cliente) {
		try {
			clienteSer.creaCliente(cliente);
		} catch (Exception e) {
			return new ResponseEntity<>("Impossibile registrare utente" + " " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione avvenuta con successo!");
	}

	@DeleteMapping("/eliminazione")
	public ResponseEntity<?> cancellaCliente() {
		try{
			clienteSer.deleteById(userSer.getUserDetails().getId());
			return ResponseEntity.ok().body(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/lista-acquisti")
	public ResponseEntity<List<Acquisto>> getAllAcquisti() {
		List<Acquisto> acquisti = clienteSer.getAllAcquistoCliente(userSer.getUserDetails().getId());
		return new ResponseEntity<>(acquisti, HttpStatus.OK);
	}

	@GetMapping("/lista-ordini")
	public ResponseEntity<List<Ordine>> getAllOrdini() {
		List<Ordine> ordini = clienteSer.getAllOrdiniCliente(userSer.getUserDetails().getId());
		return new ResponseEntity<>(ordini, HttpStatus.OK);
	}

	@GetMapping("/ordine")
	public ResponseEntity<?> getDettagliOrdine(@RequestParam Long idOrdine) {
		try {
			DettaglioOrdine dettagli = clienteSer.getDettagliOrdine(idOrdine, userSer.getUserDetails().getId());
			return ResponseEntity.ok(dettagli);
		} catch (Exception e) {
			return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/genera-ordine")
	public ResponseEntity<?> generaOrdine() {
		try {
			Ordine ordine = clienteSer.generaOrdine(userSer.getUserDetails().getId());
			return ResponseEntity.ok(ordine.getId());	
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/crea-ordine")
	public ResponseEntity<?> creaOrdine(@RequestParam Long idOrdine, @RequestParam Long idLuogo) {
		try {
			clienteSer.creaOrdine(idOrdine, idLuogo);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/cerca-negozi-merce")
	public ResponseEntity<?> getNegoziMerce(@RequestParam String merce) {
		try {
			List<Luogo> negozi = clienteSer.cercaMerce(merce);
			return new ResponseEntity<>(negozi, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Merce non trovata", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/cerca-luoghi-consegna")
	public ResponseEntity<List<Luogo>> getLuoghiConsegna() {
		try {
			List<Luogo> luoghi = clienteSer.getListaLuoghiConsegna();
			return new ResponseEntity<>(luoghi, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}
	}
}
