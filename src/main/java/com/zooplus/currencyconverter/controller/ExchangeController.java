package com.zooplus.currencyconverter.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zooplus.currencyconverter.service.CurrencyConverterService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeController {

	@Autowired
	private CurrencyConverterService service;

	@GetMapping(path = "/currencies")
	public ResponseEntity<?> currencies() {
		return ResponseEntity.ok(service.getCurrencies());
	}

	@GetMapping(path = "/latest")
	public ResponseEntity<?> latest() {
		return ResponseEntity.ok(service.getLatestRate());
	}

	@GetMapping(path = "/historical")
	public ResponseEntity<?> historical() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate formatDateTime = LocalDate.parse("2001-02-16", formatter);

		return ResponseEntity.ok(service.getRateByDate(formatDateTime));
	}

}