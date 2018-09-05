package com.zooplus.currencyconverter.service;

import java.math.BigDecimal;
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

import com.zooplus.currencyconverter.domainobject.Exchange;
import com.zooplus.currencyconverter.domainobject.User;
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;
import com.zooplus.currencyconverter.repository.ExchangeRepository;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeServiceTest {

	@Mock
	private ExchangeRepository exchangeRepository;

	@InjectMocks
	private ExchangeServiceImpl exchangeService;

	private Exchange exchange;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		
		User user = new User("admin@email", "admin", null);
		user.setId(123l);
		user.setDeleted(false);
		user.setAuthorities(new HashSet<>());
		
		exchange = new Exchange(user, "USD", new BigDecimal(1), LocalDate.now());
		exchange.setId(123l);
		exchange.setDeleted(false);
	}

	@Test
	public void findExchangeWhenGettingItById() throws EntityNotFoundException {
		Mockito.when(exchangeRepository.findById(123L)).thenReturn(Optional.ofNullable(exchange));

		Assert.assertEquals(exchange, exchangeService.find(123L));
	}

	@Test(expected = EntityNotFoundException.class)
	public void getExceptionWhenTryToFindExchangeByIdThatDoesNotExist() throws EntityNotFoundException {
		Mockito.when(exchangeRepository.findById(456L)).thenReturn(Optional.empty());

		exchangeService.find(456L);
	}

	@Test
	public void createNewExchangeShouldReturnIt() throws ConstraintsViolationException {
		Mockito.when(exchangeRepository.save(exchange)).thenReturn(exchange);

		Assert.assertEquals(exchange, exchangeService.create(exchange));
	}

	@Test(expected = ConstraintsViolationException.class)
	public void failToCreateNewExchangeShouldReturnConstraintsViolationException() throws ConstraintsViolationException {
		exchange.setCurrency(null);
		
		Mockito.when(exchangeRepository.save(exchange)).thenThrow(new DataIntegrityViolationException(""));
		
		exchangeService.create(exchange);
	}

	@Test
	public void delete() throws EntityNotFoundException {
		Mockito.when(exchangeRepository.findById(123L)).thenReturn(Optional.ofNullable(exchange));

		exchangeService.delete(123L);

		Mockito.verify(exchangeRepository, Mockito.atLeastOnce()).findById(123L);
		Assert.assertEquals(true, exchange.getDeleted());
	}

	@Test
	public void findAll() {
		Mockito.when(exchangeRepository.findAll()).thenReturn(Collections.singletonList(exchange));
		Assert.assertEquals(Collections.singletonList(exchange), exchangeService.findAll());
	}
}
