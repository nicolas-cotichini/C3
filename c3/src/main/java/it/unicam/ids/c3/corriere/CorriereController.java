package it.unicam.ids.c3.corriere;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.ids.c3.acquisti.Acquisto;
import it.unicam.ids.c3.luogo.LuogoService;
import it.unicam.ids.c3.luogo.Magazzino;
import it.unicam.ids.c3.ordine.DettaglioOrdine;
import it.unicam.ids.c3.ordine.Ordine;
import it.unicam.ids.c3.user.UserService;

@RestController
@RequestMapping("/corriere")
public class CorriereController {

	@Autowired
	UserService userSer;
	@Autowired
	CorriereService corriereSer;
	@Autowired
	LuogoService luogoSer;

	@GetMapping("/lista-consegne")
	public ResponseEntity<List<Ordine>> getAllConsegne() {
		List<Ordine> ordini = corriereSer.getListaConsegna(userSer.getUserDetails().getId());
		return new ResponseEntity<>(ordini, HttpStatus.OK);
	}
	
	@GetMapping("/ordine")
	public ResponseEntity<?> getDettagliOrdine(@RequestParam Long idOrdine) {
		try {
			DettaglioOrdine dettagli = corriereSer.getDettagliOrdine(idOrdine);
			return ResponseEntity.ok(dettagli);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/lista-ritiri")
	public ResponseEntity<?> getAllRitiri(@RequestParam Long idOrdine) {
		List<Acquisto> listaRitiri = corriereSer.getListaRitiro(idOrdine);
		return new ResponseEntity<>(listaRitiri, HttpStatus.OK);	
		}
	
	@GetMapping("/indirizzo-ritiro")
	public ResponseEntity<?> getIndirizzoRitiro(@RequestParam Long idNegozio) {
		return new ResponseEntity<>(corriereSer.getIndirizzoLuogoConsegna(idNegozio), HttpStatus.OK);	
		}
		
	@GetMapping("/conferma-consegna")
	public ResponseEntity<?> confermaConsegna(@RequestParam Long idOrdine) {
		try {
			corriereSer.setStatoOrdine(idOrdine);
			corriereSer.rimuoviOrdineConsegna(idOrdine, userSer.getUserDetails().getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().body(null);
	}
	
	@GetMapping("/modifica-luogo-consegna")
	public ResponseEntity<?> aggiornaLuogoConsegna(@RequestParam Long idOrdine, @RequestParam Long idLuogo) {
		try {
			corriereSer.cambiaLuogoConsegna(idOrdine, idLuogo);
		} catch (Exception e) {
			return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().body(null);
	}
	
	@GetMapping("/lista-magazzini")
	public ResponseEntity<?> getAllMagazzini() {
		List<Magazzino> magazzini = luogoSer.getAllMagazzini();
		return new ResponseEntity<>(magazzini, HttpStatus.OK);	
	}
	
	@GetMapping("/stato")
	public ResponseEntity<Boolean> getOperativo() {
		return ResponseEntity.ok(corriereSer.getOperativo(userSer.getUserDetails().getId()));
	}
	
	@GetMapping("/cambia-stato")
	public ResponseEntity<?> setOperativo() {
		try {
			corriereSer.setOperativo(userSer.getUserDetails().getId());
		} catch (Exception e) {
			return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().body(null);
	} 
	


}
