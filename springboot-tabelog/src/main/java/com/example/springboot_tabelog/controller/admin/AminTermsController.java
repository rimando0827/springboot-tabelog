package com.example.springboot_tabelog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot_tabelog.entity.Term;
import com.example.springboot_tabelog.from.TermsEditForm;
import com.example.springboot_tabelog.repository.TermRepository;
import com.example.springboot_tabelog.service.TermService;
import com.example.springboot_tabelog.service.TermUpdateService;

@Controller
public class AminTermsController {
	 @Autowired
	private final TermService termService;
		private final TermUpdateService termUpdateService;
	 private final TermRepository termRepository;
	 

	public AminTermsController(TermService termService, TermRepository termRepository, TermUpdateService termUpdateService) {
        this.termRepository=termRepository; 
		this.termService = termService;
		this.termUpdateService = termUpdateService;
	}

	@GetMapping("/admin/terms")
	public String index(Model model) {
		Term term = termService.getLatestTerm();
		Term termNum = termRepository.getReferenceById(1);
        model.addAttribute("term", term);
        model.addAttribute("termNum", termNum);

		return "admin/terms/index";

	}
	
	@GetMapping("/admin/terms/{id}/edit")
	public String edit(@PathVariable(name = "id") int id, Model model) {
		Term term =termRepository.getReferenceById(1);
		TermsEditForm termsEditForm = new TermsEditForm(term.getId(),term.getDescription()); 
		model.addAttribute("termsEditForm" ,termsEditForm);
		return "admin/terms/edit";
	}
	
	   @PutMapping("admin/terms/{id}/update")
	    public String update(@ModelAttribute @Validated TermsEditForm termsEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {        
	        if (bindingResult.hasErrors()) {
	            return "admin/terms/edit";
	        }
	        
	        termUpdateService.update(termsEditForm);
	        redirectAttributes.addFlashAttribute("successMessage", "利用規約を編集しました。");
	        
	        return "redirect:/admin/terms";
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
			 
	            //String htmlContent = termService.markdownToHtml(latestTerm.getDescription());
	            model.addAttribute("htmlContent", latestTerm);
	        } else {
	            model.addAttribute("htmlContent", "<p>会員規約が見つかりません。</p>");
	        }
		 
		return "admin/terms/test";
		
	}
	
	
	  

}
