package com.barry.spring.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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

    @Column
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
