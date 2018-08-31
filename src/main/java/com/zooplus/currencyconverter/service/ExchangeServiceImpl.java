package com.zooplus.currencyconverter.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zooplus.currencyconverter.domainobject.Exchange;
import com.zooplus.currencyconverter.domainobject.User;
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;
import com.zooplus.currencyconverter.repository.ExchangeRepository;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some exchange specific things.
 * <p/>
 */
@Service
public class ExchangeServiceImpl implements ExchangeService {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ExchangeServiceImpl.class);

	private ExchangeRepository exchangeRepository;

	public ExchangeServiceImpl(final ExchangeRepository exchangeRepository) {
		this.exchangeRepository = exchangeRepository;
	}

	/**
	 * Selects a exchange by id.
	 *
	 * @param id entity primary key
	 * @return found exchange
	 * @throws EntityNotFoundException if no exchange with the given id was found.
	 */
	@Override
	public Exchange find(Long id) throws EntityNotFoundException {
		return findExchange(id);
	}

	/**
	 * Get All exchanges.
	 *
	 * @return found exchanges
	 */
	@Override
	@Cacheable("exchanges")
	public List<Exchange> findAll() {
		return StreamSupport.stream(this.exchangeRepository.findAll().spliterator(), true)
				.collect(Collectors.toList());
	}

	/**
	 * Get All exchanges.
	 *
	 * @return found exchanges
	 */
	@Override
	@Cacheable("exchanges")
	public List<Exchange> findAllByUser(User user) {
		return StreamSupport.stream(this.exchangeRepository.findAllByUser(user).spliterator(), true)
				.collect(Collectors.toList());
	}

	/**
	 * Creates a new exchange.
	 *
	 * @param exchange exchange to be persisted
	 * @return persisted exchange
	 * @throws ConstraintsViolationException if a exchange already exists with the
	 *                                       given email, ... .
	 */
	@Override
	public Exchange create(Exchange exchange) throws ConstraintsViolationException {
		return saveExchange(exchange);
	}

	/**
	 * Deletes an existing exchange by id.
	 *
	 * @param id exchange primary key
	 * @throws EntityNotFoundException if no exchange with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long id) throws EntityNotFoundException {
		Exchange exchange = findExchange(id);
		exchange.setDeleted(true);
	}

	/**
	 * Update the location for a exchange.
	 *
	 * @param exchange exchange to be updated
	 * @throws ConstraintsViolationException Missing fields
	 * @throws EntityNotFoundException       Entity not found by that Id
	 */
	@Override
	@Transactional
	public Exchange update(Long exchangeID, Exchange exchange)
			throws ConstraintsViolationException, EntityNotFoundException {
		findExchange(exchangeID);
		return saveExchange(exchange);
	}

	/**
	 * Method responsible to persist data
	 *
	 * @param exchange object to be persisted
	 * @return persisted object
	 * @throws ConstraintsViolationException data violation
	 */
	private Exchange saveExchange(Exchange exchangeDO) throws ConstraintsViolationException {
		Exchange exchange;
		try {
			exchange = exchangeRepository.save(exchangeDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("Some constraints are thrown due to exchange creation", e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return exchange;
	}

	/**
	 * Find a exchange by it PK
	 *
	 * @param id primary key.
	 * @return exchange get by the id.
	 * @throws EntityNotFoundException Entity not found by that Id.
	 */
	private Exchange findExchange(Long id) throws EntityNotFoundException {
		Exchange exchange = exchangeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Could not find Exchange entity with id: " + id));
		return exchange;
	}

	@Override
	public Collection<Exchange> getLast10() {

		return null;
	}

}
