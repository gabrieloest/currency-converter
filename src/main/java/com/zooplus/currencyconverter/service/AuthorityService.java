package com.zooplus.currencyconverter.service;

import java.util.Collection;

import com.zooplus.currencyconverter.domainobject.Authority;
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;

public interface AuthorityService {

	Authority find(Long id) throws EntityNotFoundException;

	Collection<Authority> findAll();

	Authority create(Authority authority) throws ConstraintsViolationException;

	void delete(Long id) throws EntityNotFoundException;

	Authority update(Long id, Authority authority) throws EntityNotFoundException, ConstraintsViolationException;

}
