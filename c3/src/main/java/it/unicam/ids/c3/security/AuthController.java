package it.unicam.ids.c3.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import it.unicam.ids.c3.user.Profilo;
import it.unicam.ids.c3.user.User;
import it.unicam.ids.c3.user.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationMan;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserService userSer;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authReq) {

		// Effettua autenticazione
		Authentication authentication = authenticationMan
				.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Genero Token
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new AuthResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));

	}

	@GetMapping("/profilo")
	public ResponseEntity<?> getProfilo() {
		UserDetailsImpl userDetails = userSer.getUserDetails();
		try {
			User user = userSer.findByEmail(userDetails.getEmail());
			return ResponseEntity
					.ok(new Profilo(user.getId(), user.getNome(), user.getCognome(), user.getEmail(), user.getRuolo()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Impossibile caricare Profilo");
		}
	}
	
}