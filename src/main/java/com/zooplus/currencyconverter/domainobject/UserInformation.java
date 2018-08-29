package com.zooplus.currencyconverter.domainobject;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class UserInformation {

	@Column(nullable = false)
	@NotNull(message = "Birth Day can not be null!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDay;

	private String street;

	private String zip;

	private String city;

	private String country;

	public UserInformation() {
	}

	public UserInformation(@NotNull(message = "Birth Day can not be null!") LocalDate birthDay, String street,
			String zip, String city, String country) {
		super();
		this.birthDay = birthDay;
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.country = country;
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
}
