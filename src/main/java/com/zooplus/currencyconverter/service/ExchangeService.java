package com.zooplus.currencyconverter.service;

import java.util.Collection;
import java.util.List;

import com.zooplus.currencyconverter.domainobject.Exchange;
import com.zooplus.currencyconverter.domainobject.User;
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;

public interface ExchangeService {

	Exchange find(Long id) throws EntityNotFoundException;

	Collection<Exchange> findAll();

	Exchange create(Exchange exchange) throws ConstraintsViolationException;

	void delete(Long id) throws EntityNotFoundException;

	Exchange update(Long id, Exchange exchange) throws EntityNotFoundException, ConstraintsViolationException;

	Collection<Exchange> getLast10(User user);

	List<Exchange> findAllByUser(User user);

}
