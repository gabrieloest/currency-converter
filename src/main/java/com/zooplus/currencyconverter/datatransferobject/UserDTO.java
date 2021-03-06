package com.zooplus.currencyconverter.datatransferobject;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

public class UserDTO {

	private Long id;

	@NotNull(message = "Email can not be null!")
	@NotBlank(message = "Email can not be blank!")
	private String email;

	@NotNull(message = "Password can not be null!")
	@NotBlank(message = "Password can not be blank!")
	private String password;

	@NotNull(message = "Birth Day can not be null!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message = "Only dates in the past are valid!")
	private LocalDate birthDay;

	private String street;

	private String zip;

	private String city;

	private String country;

	public UserDTO() {
	}

	public UserDTO(Long id, @NotNull(message = "Email can not be null!") String email,
			@NotNull(message = "Password can not be null!") String password,
			@NotNull(message = "Birth Day can not be null!") LocalDate birthDay, String street, String zip, String city,
			String country) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.birthDay = birthDay;
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.country = country;
	}

	public static UserDTOBuilder newBuilder() {
		return new UserDTOBuilder();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public static class UserDTOBuilder {
		private Long id;
		private String email;
		private String password;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate birthDay;
		private String street;
		private String zip;
		private String city;
		private String country;

		public UserDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public UserDTOBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserDTOBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public UserDTOBuilder setBirthDay(LocalDate birthDay) {
			this.birthDay = birthDay;
			return this;
		}

		public UserDTOBuilder setStreet(String street) {
			this.street = street;
			return this;
		}

		public UserDTOBuilder setZip(String zip) {
			this.zip = zip;
			return this;
		}

		public UserDTOBuilder setCity(String city) {
			this.city = city;
			return this;
		}

		public UserDTOBuilder setCountry(String country) {
			this.country = country;
			return this;
		}

		public UserDTO createUserDTO() {
			return new UserDTO(id, email, password, birthDay, street, zip, city, country);
		}

	}

}
