package com.barry.spring.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4290857047065949123L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;


    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;

    @Column
    private String description;

    //@ManyToMany(targetEntity = Person.class, mappedBy = "roles", cascade = CascadeType.ALL)
   // private Set<Person> persons = new HashSet<>();
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
	 * @return the name
	 */
	public RoleName getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(RoleName name) {
		this.name = name;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
    	

//	/**
//	 * @return the persons
//	 */
//	public Set<Person> getPersons() {
//		return persons;
//	}
//
//	/**
//	 * @param persons the persons to set
//	 */
//	public void setPersons(Set<Person> persons) {
//		this.persons = persons;
//	}

    
    
    
}
