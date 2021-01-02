package com.barry.spring.request;

import java.math.BigInteger;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;





/**
 * The persistent class for the person database table.
 * 
 */

public class RegisterForm  {


	@NotBlank
	@Size(max = 30, min = 3)
	private String fonction;

	@NotBlank
	@Size(max = 20, min = 3)
	private String nom;

	
	//@Positive(message = "le champ doit être positif")
	//@Size(max = 10, min = 10, message = "veuiller respecter le format souhaité, 10 min ou max svp!")
	private BigInteger tel;
	@NotBlank
	private String sexe;
	@Positive(message = "le champ doit être positif")
	//@Size(min = 1, max = 2, message = "veuillez entrer un 1 ou 2 chiffres maximum")
	private int age;
	
	private Set<String> roles;
	
	private String password;
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

	/**
	 * @return the roles
	 */
	public Set<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	

	


}