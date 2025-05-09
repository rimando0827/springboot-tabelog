package com.example.springboot_tabelog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.Term;
import com.example.springboot_tabelog.from.TermsEditForm;
import com.example.springboot_tabelog.repository.TermRepository;

@Service
public class TermUpdateService {
	private final TermRepository termRepository;   
	
	public TermUpdateService(TermRepository termRepository) {
        this.termRepository = termRepository;        
    }  
	
	 @Transactional
	    public void update(TermsEditForm termsEditForm) {
		 Term term = termRepository.getReferenceById(termsEditForm.getId());
	      
	        
	                      
	        term.setDescription(termsEditForm.getDescription());
	        
	                    
	        termRepository.save(term);
	    }    

}
