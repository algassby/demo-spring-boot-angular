package com.barry.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.barry.spring.model.Person;


@Repository
public interface PersonRepositoryInterface extends JpaRepository<Person, Integer> {
//	@Query("select p from Person p where p.nom = :nom")
//	Person findByUsername(@Param("nom") String username);
	@Query("SELECT p from Person p where p.username=:username ")
	Optional<Person> findByUsername(@Param("username") String username);
	Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("select p from Person p where p.nom =:nom")
    List<Person> findByName(@Param("nom")String nom);

}
