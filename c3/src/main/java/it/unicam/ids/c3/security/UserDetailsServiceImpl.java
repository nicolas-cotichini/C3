package it.unicam.ids.c3.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.unicam.ids.c3.user.User;
import it.unicam.ids.c3.user.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService userSer;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userSer.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("Email : " + email + "non trovata!"));
		} else return UserDetailsImpl.build(user);
	}

}