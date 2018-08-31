package com.zooplus.currencyconverter.controller.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.zooplus.currencyconverter.datatransferobject.RateDTO;
import com.zooplus.currencyconverter.domainobject.Exchange;
import com.zooplus.currencyconverter.domainobject.Rate;

public class ExchangeMapper {
	public static Exchange makeExchange(RateDTO rateDTO) {
		Exchange exchange = new Exchange(rateDTO.getUser(), rateDTO.getBase(), new BigDecimal(1),
				rateDTO.getTimestampAsLocalDate());
		List<Rate> rates = rateDTO.getRates().entrySet().stream()
				.map(it -> new Rate(it.getKey(), it.getValue(), exchange))
				.collect(Collectors.toList());

		exchange.setRates(rates);

		return exchange;
	}

}
