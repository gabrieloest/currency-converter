package com.zooplus.currencyconverter.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.zooplus.currencyconverter.datatransferobject.ConvertRateDTO;
import com.zooplus.currencyconverter.datatransferobject.CurrencyDTO;
import com.zooplus.currencyconverter.datatransferobject.RateDTO;

public interface CurrencyConverterService {

	List<CurrencyDTO> getCurrencies();

	RateDTO getLatestRate();

	RateDTO getRateByDate(LocalDate date);

	ConvertRateDTO convertValues(String fromCurrency, BigDecimal fromValue, String toCurrency);
}
