package com.zooplus.currencyconverter.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zooplus.currencyconverter.domainobject.Authority;
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;
import com.zooplus.currencyconverter.repository.AuthorityRepository;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some authority specific things.
 * <p/>
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AuthorityServiceImpl.class);

	private AuthorityRepository authorityRepository;

	public AuthorityServiceImpl(final AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	/**
	 * Selects a authority by id.
	 *
	 * @param id entity primary key
	 * @return found authority
	 * @throws EntityNotFoundException if no authority with the given id was found.
	 */
	@Override
	public Authority find(Long id) throws EntityNotFoundException {
		return findAuthority(id);
	}

	/**
	 * Get All authoritys.
	 *
	 * @return found authoritys
	 */
	@Override
	@Cacheable("authoritys")
	public List<Authority> findAll() {
		return StreamSupport.stream(this.authorityRepository.findAll().spliterator(), true)
				.collect(Collectors.toList());
	}

	/**
	 * Creates a new authority.
	 *
	 * @param authority authority to be persisted
	 * @return persisted authority
	 * @throws ConstraintsViolationException if a authority already exists with the
	 *                                       given email, ... .
	 */
	@Override
	public Authority create(Authority authority) throws ConstraintsViolationException {
		return saveAuthority(authority);
	}

	/**
	 * Deletes an existing authority by id.
	 *
	 * @param id authority primary key
	 * @throws EntityNotFoundException if no authority with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long id) throws EntityNotFoundException {
		Authority authority = findAuthority(id);
		authority.setDeleted(true);
	}

	/**
	 * Update the location for a authority.
	 *
	 * @param authority authority to be updated
	 * @throws ConstraintsViolationException Missing fields
	 * @throws EntityNotFoundException       Entity not found by that Id
	 */
	@Override
	@Transactional
	public Authority update(Long authorityID, Authority authority)
			throws ConstraintsViolationException, EntityNotFoundException {
		findAuthority(authorityID);
		return saveAuthority(authority);
	}

	/**
	 * Method responsible to persist data
	 *
	 * @param authority object to be persisted
	 * @return persisted object
	 * @throws ConstraintsViolationException data violation
	 */
	private Authority saveAuthority(Authority authorityDO) throws ConstraintsViolationException {
		Authority authority;
		try {
			authority = authorityRepository.save(authorityDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("Some constraints are thrown due to authority creation", e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return authority;
	}

	/**
	 * Find a authority by it PK
	 *
	 * @param id primary key.
	 * @return authority get by the id.
	 * @throws EntityNotFoundException Entity not found by that Id.
	 */
	private Authority findAuthority(Long id) throws EntityNotFoundException {
		Authority authority = authorityRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Could not find Authority entity with id: " + id));
		return authority;
	}

}
