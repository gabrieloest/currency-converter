package com.zooplus.challenge.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zooplus.challenge.currencyconverter.exception.EntityNotFoundException;

@Service
public class LoginService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return userService.findUserByEmail(username);
		} catch (EntityNotFoundException e) {
			throw new UsernameNotFoundException("Email " + username + " not found!");
		}
	}

}
