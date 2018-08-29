package com.zooplus.currencyconverter.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.zooplus.currencyconverter.datatransferobject.UserDTO;
import com.zooplus.currencyconverter.domainobject.User;
import com.zooplus.currencyconverter.domainobject.UserInformation;

public class UserMapper {
	public static User makeUser(UserDTO userDTO) {
		UserInformation userInformation = new UserInformation(userDTO.getBirthDay(), userDTO.getStreet(),
				userDTO.getZip(), userDTO.getCity(), userDTO.getCountry());
		return new User(userDTO.getEmail(), userDTO.getPassword(), userInformation);
	}

	public static UserDTO makeUserDTO(User user) {
		UserDTO.UserDTOBuilder userDTOBuilder = UserDTO.newBuilder()
				.setEmail(user.getEmail())
				.setPassword(user.getPassword())
				.setBirthDay(user.getUserInformation().getBirthDay())
				.setCity(user.getUserInformation().getCity())
				.setCountry(user.getUserInformation().getCountry())
				.setStreet(user.getUserInformation().getStreet())
				.setZip(user.getUserInformation().getZip());

		return userDTOBuilder.createUserDTO();
	}

	public static List<UserDTO> makeUserDTOList(Collection<User> users) {
		return users.stream()
				.map(UserMapper::makeUserDTO)
				.collect(Collectors.toList());
	}
}
