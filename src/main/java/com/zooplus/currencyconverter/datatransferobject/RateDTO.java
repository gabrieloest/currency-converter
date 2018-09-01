package com.zooplus.currencyconverter.datatransferobject;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.zooplus.currencyconverter.domainobject.User;

public class RateDTO {

	private Long timestamp;

	private String base;

	private Map<String, BigDecimal> rates;

	private User user;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestampToString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		return formatter.format(getTimestampAsLocalDate());
	}

	public LocalDate getTimestampAsLocalDate() {
		
		return Instant.ofEpochMilli((long) this.timestamp * 1000).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Map<String, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(Map<String, BigDecimal> rates) {
		this.rates = rates;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
