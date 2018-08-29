package com.zooplus.challenge.currencyconverter.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zooplus.challenge.currencyconverter.domainobject.Authority;
import com.zooplus.challenge.currencyconverter.domainobject.User;
import com.zooplus.challenge.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.challenge.currencyconverter.exception.EntityNotFoundException;
import com.zooplus.challenge.currencyconverter.repository.UserRepository;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some user specific things.
 * <p/>
 */
@Service
public class UserServiceImpl implements UserService {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Selects a user by id.
	 *
	 * @param id entity primary key
	 * @return found user
	 * @throws EntityNotFoundException if no user with the given id was found.
	 */
	@Override
	public User find(Long id) throws EntityNotFoundException {
		return findUser(id);
	}

	/**
	 * Get All users.
	 *
	 * @return found users
	 */
	@Override
	@Cacheable("users")
	public List<User> findAll() {
		return StreamSupport.stream(this.userRepository.findAll().spliterator(), true)
				.collect(Collectors.toList());
	}

	/**
	 * Creates a new user.
	 *
	 * @param user user to be persisted
	 * @return persisted user
	 * @throws ConstraintsViolationException if a user already exists with the given
	 *                                       email, ... .
	 */
	@Override
	public User create(User user) throws ConstraintsViolationException {
		return saveUser(user);
	}

	/**
	 * Deletes an existing user by id.
	 *
	 * @param id user primary key
	 * @throws EntityNotFoundException if no user with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long id) throws EntityNotFoundException {
		User user = findUser(id);
		user.setDeleted(true);
	}

	/**
	 * Update the location for a user.
	 *
	 * @param user user to be updated
	 * @throws ConstraintsViolationException Missing fields
	 * @throws EntityNotFoundException       Entity not found by that Id
	 */
	@Override
	@Transactional
	public User update(Long userID, User user) throws ConstraintsViolationException, EntityNotFoundException {
		findUser(userID);
		return saveUser(user);
	}

	/**
	 * Method responsible to persist data
	 *
	 * @param user object to be persisted
	 * @return persisted object
	 * @throws ConstraintsViolationException data violation
	 */
	private User saveUser(User userDO) throws ConstraintsViolationException {
		User user;
		try {
			userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
			userDO.setAuthorities(Arrays.asList(new Authority("ROLE_USER")).stream().collect(Collectors.toSet()));
			user = userRepository.save(userDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("Some constraints are thrown due to user creation", e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return user;
	}

	/**
	 * Find a user by it PK
	 *
	 * @param id primary key.
	 * @return user get by the id.
	 * @throws EntityNotFoundException Entity not found by that Id.
	 */
	private User findUser(Long id) throws EntityNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Could not find User entity with id: " + id));
		return user;
	}

	/**
	 * Find a user by it Email
	 *
	 * @param email.
	 * @return user get by the email.
	 * @throws EntityNotFoundException Entity not found by that Email.
	 */
	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findOneByEmail(email).orElse(null);
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOneByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),
				mapRolesToAuthorities(user.getAuthorities()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Authority> authorities) {
		return authorities.stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName()))
				.collect(Collectors.toList());
	}

}
