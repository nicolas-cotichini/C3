package it.unicam.ids.c3.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getEmail()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 40000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public String getEmailFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Firma JWT non valida: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Token JWT non valido: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("Token JWT scaduto: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("Token JWT non supportato: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT vuota", e.getMessage());
		}

		return false;
	}
	
	public boolean refreshJwtToken(String token) {
		try {
			return validateJwtToken(token);
		} catch (ExpiredJwtException e) {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().setExpiration((new Date(System.currentTimeMillis() + expiration * 1000)));
			return false;
		}
		
	}
}
