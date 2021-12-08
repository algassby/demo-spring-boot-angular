package com.barry.spring.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.barry.spring.model.Person;
import com.barry.spring.model.Role;
import com.barry.spring.model.RoleName;
import com.barry.spring.repository.PersonRepositoryInterface;
import com.barry.spring.repository.RoleRepository;
import com.barry.spring.request.SignUpForm;
import com.barry.spring.response.ResponseMessage;
import com.barry.spring.service.PersonServiceInterface;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ApiCrudController {
	
	@Autowired
	private PersonServiceInterface personService;
	@Autowired 
	private PersonRepositoryInterface userRepository;
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;


	@PreAuthorize("hasRole('ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
	@GetMapping
	public List<Person> personList(){
		return personService.findAll();
	}
	
	@GetMapping("/byName")
	public List<Person> findByName(@RequestParam(required = false) String nom){
		return personService.findByName(nom);
	}
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/{id}")
	public Person getById(@PathVariable Integer id) {
		return personService.findById(id);
	}
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody @Valid SignUpForm signUpRequest ){
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}
		System.out.println(signUpRequest);
		// Creating user's account
		Person user = new Person(signUpRequest.getNom(), signUpRequest.getUsername(),signUpRequest.getFonction() , signUpRequest.getEmail(),
				signUpRequest.getTel(), signUpRequest.getSexe(), signUpRequest.getAge(),encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		Role roleUser = roleRepository.findByName(RoleName.ROLE_USER).get();
		roles.add(roleUser);
//
//		strRoles.forEach(role -> {
//			switch (role) {
//			case "ADMIN":
//				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
//						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//				roles.add(adminRole);
//
//				break;
//			case "SUPER":
//				Role pmRole = roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN)
//						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//				roles.add(pmRole);
//
//				break;
//			default:
//				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//				roles.add(userRole);
//			}
//		});

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody @Valid SignUpForm personPerson,@PathVariable Integer id){
		Person newPerson = personService.findById(id);
		
		newPerson.setNom(personPerson.getNom());
		newPerson.setPassword(personPerson.getPassword());
		newPerson.setFonction(personPerson.getFonction());
		newPerson.setEmail(personPerson.getEmail());
		newPerson.setUsername(personPerson.getUsername());
		
		newPerson.setTel(personPerson.getTel());
		newPerson.setSexe(personPerson.getSexe());
		newPerson.setAge(personPerson.getAge());
		
		Set<String> strRoles = personPerson.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "ADMIN":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "SUPER":
				Role pmRole = roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});
		newPerson.setRoles(roles);
		personService.update(newPerson);
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
