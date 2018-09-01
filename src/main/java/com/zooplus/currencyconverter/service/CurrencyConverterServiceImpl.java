package com.zooplus.currencyconverter.service;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.zooplus.currencyconverter.configuration.CurrencyConverterProperties;
import com.zooplus.currencyconverter.datatransferobject.CurrencyDTO;
import com.zooplus.currencyconverter.datatransferobject.RateDTO;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CurrencyConverterProperties properties;

	private String getUrl(String endpoint) {
		return properties.getHost().concat(endpoint);
	}

	private String getUrlWithKey(String endpoint) {
		return properties.getHost().concat(endpoint).concat("?app_id=").concat(properties.getKey());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<CurrencyDTO> getCurrencies() {
		RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(HttpMethod.GET,
				URI.create(getUrl("/currencies.json")));

		ResponseEntity<Map> result = restTemplate.exchange(
				requestEntity, Map.class);

		HashMap<String, String> body = (HashMap<String, String>) result.getBody();

		List<CurrencyDTO> collect = body.entrySet().stream().map(it -> new CurrencyDTO(it.getKey(), it.getValue()))
				.collect(Collectors.toList());

		return collect;
	}

	@Override
	public RateDTO getLatestRate() {
		return restTemplate.getForObject(this.getUrlWithKey("/latest.json"), RateDTO.class);
	}

	@Override
	public RateDTO getRateByDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String endpoint = "/historical/".concat(formatter.format(date)).concat(".json");
		String urlWithKey = this.getUrlWithKey(endpoint);
		return restTemplate.getForObject(urlWithKey, RateDTO.class);
	}

}
