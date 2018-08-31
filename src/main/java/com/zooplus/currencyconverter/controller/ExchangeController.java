package com.zooplus.currencyconverter.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zooplus.currencyconverter.datatransferobject.CurrencyDTO;
import com.zooplus.currencyconverter.datatransferobject.HistoricalDTO;
import com.zooplus.currencyconverter.datatransferobject.RateDTO;
import com.zooplus.currencyconverter.service.CurrencyConverterService;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {

	@Autowired
	private CurrencyConverterService service;

	@ModelAttribute("historicalDTO")
	public HistoricalDTO historicalDTO() {
		return new HistoricalDTO();
	}

	@GetMapping(path = "/currencies")
	public String showCurrenciesForm(Model model) {
		List<CurrencyDTO> currencies = service.getCurrencies();
		model.addAttribute("currencies", currencies);
		return "currencies";
	}

	@GetMapping(path = "/current")
	public String showCurrentExchangeForm(Model model) {
		RateDTO latestRate = service.getLatestRate();
		model.addAttribute("rate", latestRate);
		return "current";
	}

	@PostMapping(path = "/current")
	public String current(BindingResult result) {
		return "current";
	}

	@GetMapping(path = "/historical")
	public String showHistoricalForm(@ModelAttribute("historicalDTO") HistoricalDTO historicalDTO, Model model) {
		Map<String, BigDecimal> map = new HashMap<>();
		model.addAttribute("rates", map);
		return "historical";
	}

	@PostMapping(path = "/historical")
	public String historical(@ModelAttribute("historicalDTO") HistoricalDTO historicalDTO,
			BindingResult result, Model model) {
		RateDTO rateByDate = service.getRateByDate(historicalDTO.getDate());

		historicalDTO.setRates(rateByDate.getRates());

		return "historical";
	}

}