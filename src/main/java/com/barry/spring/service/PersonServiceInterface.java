/**
 * 
 */
package com.barry.spring.service;

import java.util.List;

import com.barry.spring.model.Person;
import com.barry.spring.model.PersonDto;

/**
 * @author algas
 *
 */
public interface PersonServiceInterface {
	
	public List<Person> findAll();
	public Person findById(int id);
	public Person findByUsername(String name);
	public Person create(PersonDto person);
	public Person create(Person person) ;
	public Person udpate(Person person);
	public  void delete(int id);
	
	
	

}
