package it.unicam.ids.c3.interflocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.ids.c3.user.UserService;

@RestController
@RequestMapping("/locker")
public class InterfLockerController {

	@Autowired
	InterfLockerService interflockerSer;
	@Autowired
	UserService userSer;
	
	@GetMapping("/apri-cella")
	public ResponseEntity<?> apriCella(@RequestParam String password) {
		try {
			Long ncella = interflockerSer.apriCella(userSer.getUserDetails().getId(), password);
			return new ResponseEntity<>("Cella #" + ncella +" aperta", HttpStatus.OK);
		} catch (Exception e) {
			return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
