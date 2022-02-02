package it.unicam.ids.c3.commerciante;

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
import it.unicam.ids.c3.acquisti.Merce;
import it.unicam.ids.c3.luogo.Luogo;
import it.unicam.ids.c3.user.UserService;

@RestController
@RequestMapping("/commerciante")
public class CommercianteController {

	@Autowired
	UserService userSer;

	@Autowired
	CommercianteService commercianteSer;

	@PostMapping("/crea-merce")
	public ResponseEntity<?> creaMerce(@RequestBody Merce merce) {
		Long id = userSer.getUserDetails().getId();
		try {
			commercianteSer.creaMerce(merce, commercianteSer.getIdNegozio(id));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Registrazione merce avvenuta con successo!");
	}

	@DeleteMapping("/elimina-merce")
	public ResponseEntity<?> eliminaMerce(@RequestParam String nomeMerce) {
		Long idCommerciante  = userSer.getUserDetails().getId();
		if (commercianteSer.eliminaMerce(nomeMerce, commercianteSer.getIdNegozio(idCommerciante )))
			return ResponseEntity.ok("Eliminazione merce avvenuta con successo!");
		return new ResponseEntity<>("Impossibile eliminare la merce: " , HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/crea-acquisto")
	public ResponseEntity<?> creaAcquisto(@RequestBody Acquisto acquisto) {
		Long idCommerciante = userSer.getUserDetails().getId();
		try {
			Long idAcquisto = commercianteSer.creaAcquisto(acquisto, idCommerciante);
			return new ResponseEntity<>(idAcquisto, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Registrazione Acquisto fallita");
		}
	}

	@GetMapping("/lista-acquisti")
	public ResponseEntity<List<Acquisto>> getAllAcquisti() {
		List<Acquisto> acquisti = commercianteSer.getAllAcquistoCommerciante(userSer.getUserDetails().getId());
		return new ResponseEntity<>(acquisti, HttpStatus.OK);
	}
	
	@GetMapping("/lista-merci")
	public ResponseEntity<List<Merce>> getAllMerci() {
		List<Merce> merci = commercianteSer.getAllMerceById(userSer.getUserDetails().getId());
		return new ResponseEntity<>(merci, HttpStatus.OK);
	}
	
	@GetMapping("/negozio")
	public ResponseEntity<Luogo> getNegozio() {
		Luogo negozio = commercianteSer.getDettagliNegozio(userSer.getUserDetails().getId());
		return new ResponseEntity<>(negozio, HttpStatus.OK);
	}
	
}
