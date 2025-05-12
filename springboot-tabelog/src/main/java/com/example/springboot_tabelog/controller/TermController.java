package com.example.springboot_tabelog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springboot_tabelog.entity.Term;
import com.example.springboot_tabelog.repository.TermRepository;
import com.example.springboot_tabelog.service.TermService;
@Controller
public class TermController {

	 @Autowired
		private final TermService termService;
		 private final TermRepository termRepository;
		 

		public TermController(TermService termService, TermRepository termRepository) {
	        this.termRepository=termRepository; 
			this.termService = termService;
			
		}
	
	@GetMapping("/terms")
	public String index(Model model) {
		Term term = termService.getLatestTerm();
		Term termNum = termRepository.getReferenceById(1);
        model.addAttribute("term", term);
        model.addAttribute("termNum", termNum);

		return "terms/index";

	}
}
