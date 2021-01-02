package com.barry.spring.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barry.spring.model.Person;
import com.barry.spring.model.Role;
import com.barry.spring.repository.RoleRepository;
import com.barry.spring.request.RegisterForm;
import com.barry.spring.response.ResponseMessage;
import com.barry.spring.service.PersonServiceInterface;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ApiCrudController {
	
	@Autowired
	private PersonServiceInterface personService;
	
	@Autowired
	private RoleRepository roleRepository;
	
//	@Autowired
//	PasswordEncoder encoder;
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<Person> personList(){
		
		return personService.findAll();
	}
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/{id}")
	public Person getById(@PathVariable Integer id) {
		return personService.findById(id);
	}
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody @Valid RegisterForm personForm ){
		Person person = new Person(personForm.getNom(), personForm.getFonction(), personForm.getTel(), personForm.getSexe(),
				personForm.getAge(), personForm.getPassword());
		

		Set<String> strRoles = personForm.getRoles();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "ADMIN":
				Role adminRole = roleRepository.findByName("ADMIN")
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "SUPER_ADMIN":
				Role superAdminRole = roleRepository.findByName("SUPER_ADMIN")
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(superAdminRole);

				break;
			default:
				Role userRole = roleRepository.findByName("USER")
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});
		person.setRoles(roles);
		personService.create(person);
		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"+person.getNom()), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody @Valid RegisterForm personPerson,@PathVariable Integer id){
		Person newPerson = personService.findById(id);
		
		newPerson.setNom(personPerson.getNom());
		newPerson.setPassword(personPerson.getPassword());
		newPerson.setFonction(personPerson.getFonction());
		
		newPerson.setTel(personPerson.getTel());
		newPerson.setSexe(personPerson.getSexe());
		newPerson.setAge(personPerson.getAge());
		
		Set<String> strRoles = personPerson.getRoles();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "ADMIN":
				Role adminRole = roleRepository.findByName("ADMIN")
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "SUPER_ADMIN":
				Role superAdminRole = roleRepository.findByName("SUPER_ADMIN")
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(superAdminRole);

				break;
			default:
				Role userRole = roleRepository.findByName("USER")
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});
		
		personService.udpate(newPerson);
		return new ResponseEntity<> (new ResponseMessage("Update successfully"), HttpStatus.OK);
	}
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Integer id) {
		 personService.delete(id);
	}
	/**
	 * @return the personService
	 */
	public PersonServiceInterface getPersonService() {
		return personService;
	}
	/**
	 * @param personService the personService to set
	 */
	public void setPersonService(PersonServiceInterface personService) {
		this.personService = personService;
	}
	/**
	 * @return the roleRepository
	 */
	public RoleRepository getRoleRepository() {
		return roleRepository;
	}
	/**
	 * @param roleRepository the roleRepository to set
	 */
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	
}
