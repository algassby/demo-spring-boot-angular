/**
 * 
 */
package com.barry.spring.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.barry.spring.model.Person;
import com.barry.spring.model.PersonDto;

/**
 * @author algas
 *
 */
public interface PersonServiceInterface {
	
	public List<Person> findAll();
	public Person findById(int id);
	@Query("SELECT p from Person p where p.username=: username ")
	public Person findByUsername(@Param("username") String name);
	public Person create(PersonDto person);
	public Person create(Person person) ;
	public Person udpate(Person person);
	public  void delete(int id);
	
	
	

}
