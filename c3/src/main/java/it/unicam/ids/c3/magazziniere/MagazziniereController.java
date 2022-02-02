package it.unicam.ids.c3.magazziniere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.ids.c3.ordine.DettaglioOrdine;
import it.unicam.ids.c3.ordine.Ordine;

@RestController
@RequestMapping("/magazziniere")
public class MagazziniereController {

	@Autowired 
	MagazziniereService magazziniereSer;

	@GetMapping("/ordine")
	public ResponseEntity<?> getOrdine(@RequestParam Long idOrdine) {
		try {
			Ordine ordine = magazziniereSer.getOrdineById(idOrdine);
			return new ResponseEntity<>(ordine, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/ordine-dettagli")
	public ResponseEntity<?> getDettagliOrdine(@RequestParam Long idOrdine) {
		try {
			DettaglioOrdine dettagli = magazziniereSer.getDettagliOrdine(idOrdine);
			return ResponseEntity.ok(dettagli);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/conferma-ritiro")
	public ResponseEntity<?> confermaRitiro(@RequestParam Long idOrdine) {
		try {
			magazziniereSer.setStatoOrdine(idOrdine);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().body(null);
	}
	
}
