package com.zooplus.currencyconverter.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.zooplus.currencyconverter.domainobject.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

	Optional<Authority> findOneById(Long id);

	Optional<Authority> findOneByName(String name);
}
