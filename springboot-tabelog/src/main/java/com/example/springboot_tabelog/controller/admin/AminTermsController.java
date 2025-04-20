package com.example.springboot_tabelog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.springboot_tabelog.entity.Term;
import com.example.springboot_tabelog.service.TermService;

@Controller
public class AminTermsController {
	 @Autowired
	private final TermService termService;

	public AminTermsController(TermService termService) {

		this.termService = termService;
	}

	@GetMapping("/admin/terms")
	public String index() {

		return "admin/terms/index";

	}

	@PostMapping("/admin/terms/view")
	public ModelAndView view(@RequestParam String markdown) {
		ModelAndView modelAndView = new ModelAndView("/admin/terms/view");
		String htmlContent = termService.markdownToHtml(markdown);
		modelAndView.addObject("markdown", markdown);
		modelAndView.addObject("htmlContent", htmlContent);
		return modelAndView;
	}
	
	@GetMapping("/admin/terms/test")
	public String test(Model model) {
		 Term latestTerm = termService.getLatestTerm();
		 if (latestTerm != null) {
	            String htmlContent = termService.markdownToHtml(latestTerm.getDescription());
	            model.addAttribute("htmlContent", htmlContent);
	        } else {
	            model.addAttribute("htmlContent", "<p>会員規約が見つかりません。</p>");
	        }
		 
		return "admin/terms/test";
		
	}

}
