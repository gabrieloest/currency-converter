package com.zooplus.currencyconverter.datatransferobject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class HistoricalDTO {

	@NotNull(message = "Birth Day can not be null!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private String base;

	private Map<String, BigDecimal> rates;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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

}
