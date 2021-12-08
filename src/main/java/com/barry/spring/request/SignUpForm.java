package com.barry.spring.request;

import java.math.BigInteger;
import java.util.Set;

import javax.validation.constraints.*;

public class SignUpForm {

    private String nom;

  
    private String username;

  
    private String email;
    
    
    private String fonction;
    
    private String sexe;
    
    private BigInteger tel;
    private int age;
    
    private Set<String> role;
    
    
    
    private String password;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
    	return this.role;
    }
    
    public void setRole(Set<String> role) {
    	this.role = role;
    }

	/**
	 * @return the fonction
	 */
	public String getFonction() {
		return fonction;
	}

	/**
	 * @param fonction the fonction to set
	 */
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	/**
	 * @return the sexe
	 */
	public String getSexe() {
		return sexe;
	}

	/**
	 * @param sexe the sexe to set
	 */
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	/**
	 * @return the tel
	 */
	public BigInteger getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(BigInteger tel) {
		this.tel = tel;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "SignUpForm [name=" + nom + ", username=" + username + ", email=" + email + ", fonction=" + fonction
				+ ", sexe=" + sexe + ", tel=" + tel + ", age=" + age + ", role=" + role + ", password=" + password
				+ "]";
	}
	
    
    
}