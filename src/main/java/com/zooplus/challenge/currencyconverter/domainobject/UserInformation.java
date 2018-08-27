package com.zooplus.challenge.currencyconverter.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class UserInformation {

	@Column(nullable = false)
	@NotNull(message = "Birth Day can not be null!")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime birthDay;

	private String street;

	private String zip;

	private String city;

	private String country;

	public ZonedDateTime getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(ZonedDateTime birthDay) {
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
