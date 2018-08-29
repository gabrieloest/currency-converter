package com.zooplus.currencyconverter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zooplus.currencyconverter.domainobject.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("select u from User u join fetch u.authorities a where u.email = :email")
	Optional<User> findOneByEmail(@Param("email") String email);

	@Modifying
	Optional<User> deleteByEmail(String email);

}
