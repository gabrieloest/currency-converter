package com.zooplus.currencyconverter.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zooplus.currencyconverter.domainobject.Exchange;
import com.zooplus.currencyconverter.domainobject.User;

public interface ExchangeRepository extends CrudRepository<Exchange, Long> {

	@Override
	@Query("select e from Exchange e left join fetch e.rates r where e.id = :id")
	Optional<Exchange> findById(@Param("id") Long id);

	List<Exchange> findAllByUser(User user);

	Collection<Exchange> findTop10ByUserOrderByDateCreated(User user);

}
