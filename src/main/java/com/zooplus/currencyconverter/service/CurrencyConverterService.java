package com.zooplus.currencyconverter.service;

import java.time.LocalDate;
import java.util.List;

import com.zooplus.currencyconverter.datatransferobject.CurrencyDTO;
import com.zooplus.currencyconverter.datatransferobject.RateDTO;

public interface CurrencyConverterService {

	List<CurrencyDTO> getCurrencies();

	RateDTO getLatestRate();

	RateDTO getRateByDate(LocalDate date);
}
