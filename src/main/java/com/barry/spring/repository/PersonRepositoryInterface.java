package com.barry.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.barry.spring.model.Person;


@Repository
public interface PersonRepositoryInterface extends JpaRepository<Person, Integer> {
	@Query("select p from Person p where p.nom = :nom")
	Person findByUsername(@Param("nom") String username);

}
