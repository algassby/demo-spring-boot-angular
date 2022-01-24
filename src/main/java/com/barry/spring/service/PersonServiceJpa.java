package com.barry.spring.service;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barry.spring.model.Person;
import com.barry.spring.model.PersonDto;
import com.barry.spring.repository.PersonRepositoryInterface;

@Service
@Transactional
public class PersonServiceJpa implements PersonServiceInterface {

	@Autowired
	private PersonRepositoryInterface personRepository;
	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	@Override
	public Person findById(int id) {
		
		return personRepository.findById(id).orElseThrow();
	}

	@Override
	public Person create(Person person) {
		return personRepository.save(person);
		
	}

	@Override
	public Person update(Person person) {
		return personRepository.save(person);
	}

	@Override
	public void delete(int id) {
		personRepository.deleteById(id);
		
	}

	@Override
	public Person findByUsername(String name) {
		
		return personRepository.findByUsername(name).orElse(null);
	}

	@Override
	public Person create(PersonDto person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> findByName(String name) {
		
		return personRepository.findByName(name);
	}

	

}
