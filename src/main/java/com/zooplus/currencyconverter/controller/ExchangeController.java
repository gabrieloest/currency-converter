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

import com.zooplus.currencyconverter.controller.mapper.ExchangeMapper;
import com.zooplus.currencyconverter.datatransferobject.CurrencyDTO;
import com.zooplus.currencyconverter.datatransferobject.HistoricalDTO;
import com.zooplus.currencyconverter.datatransferobject.RateDTO;
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;
import com.zooplus.currencyconverter.service.CurrencyConverterService;
import com.zooplus.currencyconverter.service.ExchangeService;
import com.zooplus.currencyconverter.service.UserService;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {

	@Autowired
	private CurrencyConverterService currencyConverterService;

	@Autowired
	private ExchangeService exchangeService;

	@Autowired
	private UserService userService;

	@ModelAttribute("historicalDTO")
	public HistoricalDTO historicalDTO() {
		return new HistoricalDTO();
	}

	@GetMapping(path = "/currencies")
	public String showCurrenciesForm(Model model) {
		List<CurrencyDTO> currencies = currencyConverterService.getCurrencies();
		model.addAttribute("currencies", currencies);
		return "currencies";
	}

	@GetMapping(path = "/current")
	public String showCurrentExchangeForm(Model model) {
		RateDTO latestRate = currencyConverterService.getLatestRate();

		try {
			latestRate.setUser(userService.getCurrentUser());
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			exchangeService.create(ExchangeMapper.makeExchange(latestRate));
		} catch (ConstraintsViolationException e) {
			e.printStackTrace();
		}

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
		RateDTO rateByDate = currencyConverterService.getRateByDate(historicalDTO.getDate());

		try {
			rateByDate.setUser(userService.getCurrentUser());
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			exchangeService.create(ExchangeMapper.makeExchange(rateByDate));
		} catch (ConstraintsViolationException e) {
			e.printStackTrace();
		}

		historicalDTO.setBase(rateByDate.getBase());
		historicalDTO.setRates(rateByDate.getRates());

		return "historical";
	}

}