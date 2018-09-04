package com.zooplus.currencyconverter.datatransferobject;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ConvertRateDTO {

	@NotNull(message = "Origin currency can not be null!")
	private String fromCurrency;

	@NotNull(message = "Origin value can not be null!")
	@Min(value = 1, message = "Value can not be less than 1")
	private BigDecimal fromValue;

	@NotNull(message = "Destiny currency can not be null!")
	private String toCurrency;

	private BigDecimal toValue;

	public ConvertRateDTO() {
	}

	public ConvertRateDTO(String fromCurrency, BigDecimal fromValue, String toCurrency, BigDecimal toValue) {
		this.fromCurrency = fromCurrency;
		this.fromValue = fromValue;
		this.toCurrency = toCurrency;
		this.toValue = toValue;
	}

	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public BigDecimal getFromValue() {
		return fromValue;
	}

	public void setFromValue(BigDecimal fromValue) {
		this.fromValue = fromValue;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	public BigDecimal getToValue() {
		return toValue;
	}

	public void setToValue(BigDecimal toValue) {
		this.toValue = toValue;
	}

}
