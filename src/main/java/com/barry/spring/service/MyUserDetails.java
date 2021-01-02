//package com.barry.spring.service;
//import java.util.*;
// 
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.barry.spring.model.Person;
//import com.barry.spring.model.Role;
// 
//public class MyUserDetails implements UserDetails {
// 
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 3566551808788843045L;
//	private Person user;
//     
//    public MyUserDetails(Person user) {
//        this.user = user;
//    }
// 
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Role> roles = user.getRoles();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//         
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//         
//        return authorities;
//    }
// 
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
// 
//    @Override
//    public String getUsername() {
//        return user.getNom();
//    }
// 
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
// 
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
// 
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
// 
//    @Override
//    public boolean isEnabled() {
//        return user.isEnabled();
//    }
// 
//}