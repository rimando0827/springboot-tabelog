package com.example.springboot_tabelog.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springboot_tabelog.entity.Term;
import com.example.springboot_tabelog.service.TermService;
@Controller
public class AminTermsController {
	
	
	 private final TermService termService;
	
	
	
	public AminTermsController(TermService termService) {
		
		 this.termService = termService;
	}
		
		@GetMapping("/admin/terms")
		public String index(Model model) {
			 Term latestTerm = termService.getLatestTerm();
			 if (latestTerm != null) {
		            String htmlContent = termService.markdownToHtml(latestTerm.getDescription());
		            model.addAttribute("htmlContent", htmlContent);
		        } else {
		            model.addAttribute("htmlContent", "<p>会員規約が見つかりません。</p>");
		        }
			 
			return "admin/terms/index";
			
		}
		
	

}
