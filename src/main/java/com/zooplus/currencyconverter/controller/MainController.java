package com.zooplus.currencyconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zooplus.currencyconverter.service.ExchangeService;

@Controller
public class MainController {

	@Autowired
	private ExchangeService exchangeService;

	@GetMapping("/")
	public String root(Model model) {
		exchangeService.getLast10();

		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/index")
	public String index(Model model) {
		return "index";
	}
}