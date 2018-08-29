package com.zooplus.currencyconverter.service;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.zooplus.currencyconverter.domainobject.User;
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;

public interface UserService extends UserDetailsService {

	User find(Long id) throws EntityNotFoundException;

	Collection<User> findAll();

	User create(User user) throws ConstraintsViolationException;

	void delete(Long id) throws EntityNotFoundException;

	User update(Long id, User user) throws EntityNotFoundException, ConstraintsViolationException;

	User findUserByEmail(String email);

}
