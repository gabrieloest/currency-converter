package com.zooplus.currencyconverter.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

		try {
			RateDTO latestRate = currencyConverterService.getLatestRate();
			latestRate.setUser(userService.getCurrentUser());
			exchangeService.create(ExchangeMapper.makeExchange(latestRate));
			model.addAttribute("rate", latestRate);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		} catch (ConstraintsViolationException e) {
			e.printStackTrace();
		}

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
	public String historical(@ModelAttribute("historicalDTO") @Valid HistoricalDTO historicalDTO, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "historical";
		}

		try {
			RateDTO rateByDate = currencyConverterService.getRateByDate(historicalDTO.getDate());
			rateByDate.setUser(userService.getCurrentUser());

			exchangeService.create(ExchangeMapper.makeExchange(rateByDate));

			historicalDTO.setBase(rateByDate.getBase());
			historicalDTO.setRates(rateByDate.getRates());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		} catch (ConstraintsViolationException e) {
			e.printStackTrace();
		}

		return "historical";
	}

	@RequestMapping(path = "/view/{id}", method = RequestMethod.GET)
	public String editProduct(Model model, @PathVariable(value = "id") Long id) {
		try {
			model.addAttribute("exchange", exchangeService.find(id));
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return "view";
	}

}