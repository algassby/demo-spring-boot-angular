package com.barry.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barry.spring.model.Person;
import com.barry.spring.repository.PersonRepositoryInterface;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	PersonRepositoryInterface userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Person user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

		return UserPrinciple.build(user);
	}
}