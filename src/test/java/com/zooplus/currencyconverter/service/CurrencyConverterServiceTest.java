package com.zooplus.currencyconverter.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zooplus.currencyconverter.configuration.CurrencyConverterProperties;
import com.zooplus.currencyconverter.datatransferobject.ConvertRateDTO;
import com.zooplus.currencyconverter.datatransferobject.CurrencyDTO;
import com.zooplus.currencyconverter.datatransferobject.RateDTO;
import com.zooplus.currencyconverter.domainobject.User;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterServiceTest {

	@InjectMocks
	private CurrencyConverterServiceImpl currencyConverterService;
	
	@Mock
	private CurrencyConverterProperties properties;
	
	@Mock
    private RestTemplate restTemplate;

	private RateDTO rateDTO;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(properties.getHost()).thenReturn("localhost");
		Mockito.when(properties.getKey()).thenReturn("123456789");
		
		rateDTO = new RateDTO();
		rateDTO.setBase("USD");
		rateDTO.setUser(new User("admin@email.com", "admin", null));
		
		HashMap<String, BigDecimal> rates = new HashMap<>();
		
		rates.put("BRL", new BigDecimal(4.15595));
		rates.put("EUR", new BigDecimal(0.862461));
		
		rateDTO.setRates(rates);
	}

	@Test
	public void convertValuesTest() throws JsonProcessingException {
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(rateDTO);
		
		ConvertRateDTO convertValues = currencyConverterService.convertValues("EUR", new BigDecimal(1), "BRL");
		
		Assert.assertEquals(4.819499925, convertValues.getToValue().doubleValue(), 1d);
	}
	
	@Test
	public void getLatestRateTest() {
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(rateDTO);
		
		RateDTO latestRate = currencyConverterService.getLatestRate();
		
		Assert.assertEquals(rateDTO, latestRate);
		Assert.assertTrue(latestRate.getRates().size() == 2);
	}
	
	@Test
	public void getCurrenciesTest() {
		Map<String, String> body = new HashMap<>();
		
		body.put("BRL", "4.15595");
		body.put("EUR", "0.862461");
		
		ResponseEntity<Map> result = new ResponseEntity(body, HttpStatus.OK);
		
		Mockito.when(restTemplate.exchange(Mockito.any(), Mockito.any(Class.class))).thenReturn(result);
		
		List<CurrencyDTO> currencies = currencyConverterService.getCurrencies();
		
		List<CurrencyDTO> expected = new ArrayList<>();
		expected.add(new CurrencyDTO("BRL", "4.15595"));
		expected.add(new CurrencyDTO("EUR", "0.862461"));
		
		Assert.assertTrue(currencies.containsAll(expected));
	}
}
