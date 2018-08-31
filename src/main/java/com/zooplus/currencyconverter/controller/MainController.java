package com.zooplus.currencyconverter.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zooplus.currencyconverter.domainobject.Exchange;
import com.zooplus.currencyconverter.exception.EntityNotFoundException;
import com.zooplus.currencyconverter.service.ExchangeService;
import com.zooplus.currencyconverter.service.UserService;

@Controller
public class MainController {

	@Autowired
	private ExchangeService exchangeService;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String root(Model model) {
		try {
			Collection<Exchange> last10 = exchangeService.getLast10(userService.getCurrentUser());
			model.addAttribute("topTen", last10);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/index")
	public String index(Model model) {
		try {
			Collection<Exchange> last10 = exchangeService.getLast10(userService.getCurrentUser());
			model.addAttribute("topTen", last10);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		return "index";
	}
}