package com.zooplus.currencyconverter.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zooplus.currencyconverter.domainobject.Authority;
import com.zooplus.currencyconverter.domainobject.User;
import com.zooplus.currencyconverter.domainobject.UserInformation;
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;
import com.zooplus.currencyconverter.repository.AuthorityRepository;
import com.zooplus.currencyconverter.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private AuthorityRepository authorityRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	private UserServiceImpl userService;

	private User user;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		UserInformation userInformation = new UserInformation();
		userInformation.setBirthDay(LocalDate.now());
		userInformation.setCity("Natal");
		userInformation.setCountry("Brazil");
		userInformation.setStreet("Professor Sarturnino");
		userInformation.setZip("59015320");

		user = new User("admin@email", "admin", userInformation);
		user.setId(123l);
		user.setDeleted(false);
		user.setAuthorities(new HashSet<>());
	}

	@Test
	public void findUserWhenGettingItById() throws EntityNotFoundException {
		Mockito.when(userRepository.findById(123L)).thenReturn(Optional.ofNullable(user));

		Assert.assertEquals(user, userService.find(123L));
	}

	@Test(expected = EntityNotFoundException.class)
	public void getExceptionWhenTryToFindUserByIdThatDoesNotExist() throws EntityNotFoundException {
		Mockito.when(userRepository.findById(456L)).thenReturn(Optional.empty());

		userService.find(456L);
	}

	@Test
	public void createNewUserShouldReturnIt() throws ConstraintsViolationException {
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Mockito.when(authorityRepository.findOneByName("ROLE_USER"))
				.thenReturn(Optional.ofNullable(new Authority("ROLE_USER")));

		Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn(Mockito.anyString());

		Assert.assertEquals(user, userService.create(user));
	}

	@Test(expected = ConstraintsViolationException.class)
	public void failToCreateNewUserShouldReturnDataIntegrityViolationException() throws ConstraintsViolationException {
		Mockito.when(authorityRepository.findOneByName("ROLE_USER"))
				.thenReturn(Optional.ofNullable(new Authority("ROLE_USER")));

		user.setEmail(null);
		user.setPassword(null);
		Mockito.when(userRepository.save(user))
				.thenThrow(new DataIntegrityViolationException("Username and password can not be null"));

		userService.create(user);
	}

	@Test
	public void delete() throws EntityNotFoundException {
		Mockito.when(userRepository.findById(123L)).thenReturn(Optional.ofNullable(user));

		userService.delete(123L);

		Mockito.verify(userRepository, Mockito.atLeastOnce()).findById(123L);
		Assert.assertEquals(true, user.getDeleted());
	}

	@Test
	public void findAll() {
		Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
		Assert.assertEquals(Collections.singletonList(user), userService.findAll());
	}
}
