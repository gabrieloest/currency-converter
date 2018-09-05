package com.zooplus.currencyconverter.service;

import java.util.Collections;
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
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;
import com.zooplus.currencyconverter.repository.AuthorityRepository;

@RunWith(MockitoJUnitRunner.class)
public class AuthorityServiceTest {

	@Mock
	private AuthorityRepository authorityRepository;

	@InjectMocks
	private AuthorityServiceImpl authorityService;

	private Authority authority;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		authority = new Authority("ROLE_USER");
		authority.setId(123l);
		authority.setDeleted(false);
	}

	@Test
	public void findAuthorityWhenGettingItById() throws EntityNotFoundException {
		Mockito.when(authorityRepository.findById(123L)).thenReturn(Optional.ofNullable(authority));

		Assert.assertEquals(authority, authorityService.find(123L));
	}

	@Test(expected = EntityNotFoundException.class)
	public void getExceptionWhenTryToFindAuthorityByIdThatDoesNotExist() throws EntityNotFoundException {
		Mockito.when(authorityRepository.findById(456L)).thenReturn(Optional.empty());

		authorityService.find(456L);
	}

	@Test
	public void createNewAuthorityShouldReturnIt() throws ConstraintsViolationException {
		Mockito.when(authorityRepository.save(authority)).thenReturn(authority);

		Assert.assertEquals(authority, authorityService.create(authority));
	}

	@Test(expected = ConstraintsViolationException.class)
	public void failToCreateNewAuthorityShouldReturnDataIntegrityViolationException() throws ConstraintsViolationException {
		authority.setName(null);
		
		Mockito.when(authorityRepository.save(authority)).thenThrow(new DataIntegrityViolationException(""));
		
		authorityService.create(authority);
	}

	@Test
	public void delete() throws EntityNotFoundException {
		Mockito.when(authorityRepository.findById(123L)).thenReturn(Optional.ofNullable(authority));

		authorityService.delete(123L);

		Mockito.verify(authorityRepository, Mockito.atLeastOnce()).findById(123L);
		Assert.assertEquals(true, authority.getDeleted());
	}

	@Test
	public void findAll() {
		Mockito.when(authorityRepository.findAll()).thenReturn(Collections.singletonList(authority));
		Assert.assertEquals(Collections.singletonList(authority), authorityService.findAll());
	}
}
