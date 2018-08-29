package com.zooplus.currencyconverter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zooplus.currencyconverter.domainobject.Exchange;
import com.zooplus.currencyconverter.domainobject.User;

public interface ExchangeRepository extends CrudRepository<Exchange, Long> {

	List<Exchange> findAllByUser(User user);

}
