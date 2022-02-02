package it.unicam.ids.c3.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// Questo metodo e' invocato quando un utente tenta di accedere ad un endpoint
		// non pubblico senza credenziali corrette 
		//401 Status code
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Errore: Non sei Autorizzato");
	}

}