package com.barry.spring.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.barry.spring.model.FileInfo;
import com.barry.spring.model.Person;
import com.barry.spring.model.Role;
import com.barry.spring.model.RoleName;
import com.barry.spring.repository.PersonRepositoryInterface;
import com.barry.spring.repository.RoleRepository;
import com.barry.spring.request.SignUpForm;
import com.barry.spring.response.ResponseMessage;
import com.barry.spring.service.PersonServiceInterface;

import com.barry.spring.service.FileService;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")

public class ApiCrudController {
	
	@Autowired
	private PersonServiceInterface personService;
	@Autowired 
	private PersonRepositoryInterface userRepository;
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private FileService fileService;
	@Value("${upload.path}")
    private String uploadPath;

	//@PreAuthorize("hasRole('ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
	@GetMapping
	public List<Person> personList(){
		//fileService.getPhoto(fileId)
//		Base64.getEncoder().withoutPadding() 
//		 .encodeToString(Files.readAllBytes(Paths.get(String.join("",uploadPath,new StringBuffer().append(File.separatorChar).append(""))))); 
		return personService.findAll()
				.stream()
				.map(user->
					{
						Person person = new Person();
						try {
							if(user.getImageName()!=null) {
								person.setImageName(Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(Paths.get(String.join("",uploadPath,new StringBuffer().append(File.separatorChar).append(user.getImageName()))))));

							}
							else {
								person.setImageName(user.getImageName());
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
 						}
						person.setAge(user.getAge());
						person.setId(user.getId());
						person.setFonction(user.getFonction());
						person.setNom(user.getNom());
						person.setRoles(user.getRoles());
						person.setSexe(user.getSexe());
						person.setTel(user.getTel());
						person.setUsername(user.getUsername());
						
						return person;
					})
				
				.collect(Collectors.toList());
	}
	//
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
	@PostMapping("/create/test")
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
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestPart("person") @Valid SignUpForm signUpRequest, 
			@RequestPart(required =false, value="file") MultipartFile file){
		
		System.out.println(signUpRequest);
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}
		FileInfo fileInfo =  fileService.save(file);
		// Creating user's account
		Person user = new Person(signUpRequest.getNom(), signUpRequest.getUsername(),signUpRequest.getFonction() , signUpRequest.getEmail(),
				signUpRequest.getTel(), signUpRequest.getSexe(), signUpRequest.getAge(),encoder.encode(signUpRequest.getPassword()));
		user.setImageName(fileInfo.getFileName());
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		Role roleUser = roleRepository.findByName(RoleName.ROLE_USER).get();
		roles.add(roleUser);

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
