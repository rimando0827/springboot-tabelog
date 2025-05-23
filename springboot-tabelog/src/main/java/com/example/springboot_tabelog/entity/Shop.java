package com.example.springboot_tabelog.entity;

import java.sql.Timestamp;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "shops")
@Data
public class Shop {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Integer id;
	 
	    @ManyToOne
	    @JoinColumn(name = "category_id")
	    private Category category;

	    @Column(name = "name")
	    private String name;

	    @Column(name = "image_name")
	    private String imageName;

	    @Column(name = "description")
	    private String description;

	    @Column(name = "price_lower_limit")
	    private String priceLowerLimit;
	    
	    @Column(name = "price_upper_limit")
	    private String priceUpperLimit;

	    @Column(name = "opening_times")
	    private LocalTime openingTimes;
	    
	    @Column(name = "closed_times")
	    private LocalTime closedTimes;

	    @Column(name = "postal_code")
	    private String postalCode;

	    @Column(name = "address")
	    private String address;

	    @Column(name = "phone_number")
	    private String phoneNumber;
	    
	    @Column(name = "closed_day")
	    private String closedDay;

	    @Column(name = "created_at", insertable = false, updatable = false)
	    private Timestamp createdAt;

	    @Column(name = "updated_at", insertable = false, updatable = false)
	    private Timestamp updatedAt;
}
