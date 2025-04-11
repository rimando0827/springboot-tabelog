package com.example.springboot_tabelog.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springboot_tabelog.entity.Company;
import com.example.springboot_tabelog.repository.CompanyRepository;

@Controller
public class CompanyController {
	private final CompanyRepository companyRepository;
	
	
	 public CompanyController(CompanyRepository companyRepository) {
		 this.companyRepository = companyRepository;
	 }
	
	@GetMapping("/companys")
	public String index( Model model) {
		List<Company> companies = companyRepository.findAll();
		
		
		model.addAttribute("companies", companies);

		return "companys/index";
	}
	
	
}
