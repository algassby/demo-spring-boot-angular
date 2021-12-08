package com.barry.spring.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;


import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@Table(name = "person")
@NamedQuery(name="findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2052804925526053591L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	
	private String fonction;

	
	private String nom;

	private String username;

	
    private String password;
	//@Positive(message = "le champ doit être positif")
	//@Size(max = 10, min = 10, message = "veuiller respecter le format souhaité, 10 min ou max svp!")
	private BigInteger tel;
	@NotBlank
	private String sexe;
	@Positive(message = "le champ doit être positif")
	//@Size(min = 1, max = 2, message = "veuillez entrer un 1 ou 2 chiffres maximum")
	private int age;
	
	
	private String email;
	@Transient
	private boolean enabled;
	
	@Transient
	private String token;

	@JsonProperty("roles")
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles", 
    		joinColumns = {
            @JoinColumn(name = "user_id") }, 
    		inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
//	@ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL )
    private Set<Role> roles = new HashSet<>();
	public Person(String nom , String username,String fonction, String email, BigInteger tel, String sexe, int age, String password) {
		super();
		
		this.nom = nom;
		this.username = username;
		this.email = email;
		this.fonction = fonction;
		this.tel = tel;
		this.sexe = sexe;
		this.age = age;
		this.password = password;
	
	
	}

	public Person() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFonction() {
		return this.fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public BigInteger getTel() {
		return tel;
	}

	public void setTel(BigInteger tel) {
		this.tel = tel;
	}


	public String getPassword() {
		return password;
	}
	


	public void setPassword(String password) {
		this.password = password;
	}
//
//	public boolean isEnabled() {
//		return this.isEnabled();
//	}
//
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	


}