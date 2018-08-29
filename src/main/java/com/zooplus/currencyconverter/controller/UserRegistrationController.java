package com.zooplus.currencyconverter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zooplus.currencyconverter.controller.mapper.UserMapper;
import com.zooplus.currencyconverter.datatransferobject.UserDTO;
import com.zooplus.currencyconverter.domainobject.User;
import com.zooplus.currencyconverter.exception.ConstraintsViolationException;
import com.zooplus.currencyconverter.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public UserDTO userRegistrationDto() {
		return new UserDTO();
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDto,
			BindingResult result) {

		try {
			User existing = userService.findUserByEmail(userDto.getEmail());
			if (existing != null) {
				result.rejectValue("email", null, "There is already an account registered with that email");
			}

			if (result.hasErrors()) {
				return "registration";
			}

			userService.create(UserMapper.makeUser(userDto));
		} catch (ConstraintsViolationException e) {
			e.printStackTrace();
		}
		return "redirect:/registration?success";
	}

}