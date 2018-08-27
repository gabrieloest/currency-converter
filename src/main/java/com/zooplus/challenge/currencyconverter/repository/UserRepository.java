package com.zooplus.challenge.currencyconverter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.zooplus.challenge.currencyconverter.domainobject.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findOneByEmail(String email);

	@Modifying
	Optional<User> deleteByEmail(String email);

}
