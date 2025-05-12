package com.example.springboot_tabelog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.Category;
import com.example.springboot_tabelog.from.CategoryEditForm;
import com.example.springboot_tabelog.from.CategoryRegisterForm;
import com.example.springboot_tabelog.repository.CategoryRepository;


@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;    
	   
	   public CategoryService(CategoryRepository categoryRepository) {
	       this.categoryRepository = categoryRepository;        
	   }    
	   
	   @Transactional
	   public void create(CategoryRegisterForm categoryRegisterForm) {
		   Category category = new Category();        
	           
	       category.setName(categoryRegisterForm.getName());                
	      
	       categoryRepository.save(category);
	   }  
	   
	   @Transactional
	    public void update(CategoryEditForm categoryEditForm) {
		   Category category = categoryRepository.getReferenceById(categoryEditForm.getId());
	        
	              
		   category.setName(categoryEditForm.getName());                
		     
	                    
		   categoryRepository.save(category);
	    }    

}
