/**
 * 
 */
package com.barry.spring.model;

import java.math.BigInteger;

import javax.validation.constraints.Positive;

/**
 * @author algas
 *
 */
public class PersonDto {
	
		
		private int id;

		private String fonction;

		
		private String nom;

		
	    private String password;
		//@Positive(message = "le champ doit être positif")
		//@Size(max = 10, min = 10, message = "veuiller respecter le format souhaité, 10 min ou max svp!")
		private BigInteger tel;
		
		private String sexe;
		
		//@Size(min = 1, max = 2, message = "veuillez entrer un 1 ou 2 chiffres maximum")
		private int age;

		public PersonDto( String fonction, String nom, String password, BigInteger tel, String sexe, int age) {
			
			this.fonction = fonction;
			this.nom = nom;
			this.password = password;
			this.tel = tel;
			this.sexe = sexe;
			this.age = age;
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


}
