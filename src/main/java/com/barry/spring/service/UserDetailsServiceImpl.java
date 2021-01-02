//package com.barry.spring.service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.barry.spring.model.Person;
//import com.barry.spring.repository.PersonRepositoryInterface;
// 
//public class UserDetailsServiceImpl implements UserDetailsService {
// 
//    @Autowired
//    private PersonRepositoryInterface userRepository;
//     
//    @Override
//    public UserDetails loadUserByUsername(String username)
//            throws UsernameNotFoundException {
//        Person user = userRepository.findByUsername(username);
//         
//        if (user == null) {
//            throw new UsernameNotFoundException("Could not find user");
//        }
//         
//        return new MyUserDetails(user);
//    }
// 
//}