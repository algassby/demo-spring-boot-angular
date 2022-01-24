	package com.barry.spring.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barry.spring.config.JwtProvider;
import com.barry.spring.model.Person;
import com.barry.spring.model.Role;
import com.barry.spring.model.RoleName;
import com.barry.spring.repository.RoleRepository;
import com.barry.spring.repository.PersonRepositoryInterface;
import com.barry.spring.request.LoginForm;
import com.barry.spring.request.SignUpForm;
import com.barry.spring.response.JwtResponse;
import com.barry.spring.response.ResponseMessage;

@CrossOrigin(origins = "*", maxAge = 3600,allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired 
	private PersonRepositoryInterface userRepository;

//	@Autowired
//	private PersonRepositoryInterface userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		Person user = new Person(signUpRequest.getNom(), signUpRequest.getUsername(),signUpRequest.getFonction() , signUpRequest.getEmail(),
				signUpRequest.getTel(), signUpRequest.getSexe(), signUpRequest.getAge(),encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER).get();
		Set<Role> roles = new HashSet<>();

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
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
}