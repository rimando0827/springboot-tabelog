package com.example.springboot_tabelog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "companys")
@Data
public class Company {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Integer id;
	 
	    @Column(name = "name")
	    private String name;

	    @Column(name = "address")
	    private String address;

	    @Column(name = "representative")
	    private String representative;

	    @Column(name = "established")
	    private String established;
	    
	    @Column(name = "capital")
	    private String capital;
	    
	    @Column(name = "description")
	    private String description;

	    @Column(name = "number_of_employees")
	    private String numberOfEmployees;
	    
	    
}
