package com.zooplus.challenge.currencyconverter.service;

import java.util.Collection;

import com.zooplus.challenge.currencyconverter.domainobject.User;
import com.zooplus.challenge.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.challenge.currencyconverter.exception.EntityNotFoundException;

public interface UserService {

	User find(Long id) throws EntityNotFoundException;

	Collection<User> findAll();

	User create(User user) throws ConstraintsViolationException;

	void delete(Long id) throws EntityNotFoundException;

	User update(Long id, User user) throws EntityNotFoundException, ConstraintsViolationException;

	User findUserByEmail(String email) throws EntityNotFoundException;

}
