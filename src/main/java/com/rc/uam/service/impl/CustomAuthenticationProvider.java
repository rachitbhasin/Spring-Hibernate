package com.rc.uam.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rc.uam.exception.UamException;
import com.rc.uam.model.User;
import com.rc.uam.service.UserService;
import com.rc.uam.utility.Constants;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = Logger.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	UserService userService;
	
	final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException, UsernameNotFoundException {
		
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		if(email.isEmpty() || password.isEmpty()){
			throw new UsernameNotFoundException("Email ID or password cannot be left blank.");
		}
		
		User user = null;
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		try {
			user = userService.getByField(Constants.EMAIL, email);
		}catch(UamException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		if(user == null) {
			throw new UsernameNotFoundException(
					"Either the username or the password is incorrect. Please try again.");
		}else {
			// String hashedPassword = passwordEncoder.encode(password);

			String hashedPassword = password;
			// if (!passwordEncoder.matches(user.getPassword(), hashedPassword))
			// {
			if (!user.getPassword().equals(hashedPassword)) {
				throw new BadCredentialsException(
						"Either the username or the password is incorrect. Please try again.");
			} else if (user.getStatus() == 0) {
				throw new BadCredentialsException(
						"Your account is no longer active, please contact the administrator.");
			} else if (user.getDeleted()) {
				throw new BadCredentialsException(
						"Your account has been deleted, please contact the administrator.");
			} else {
				grantedAuthorities.add(new SimpleGrantedAuthority(user
						.getRole().getName()));
				
				Authentication auth = new UsernamePasswordAuthenticationToken(
						user, password, grantedAuthorities);
				return auth;
			}
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
