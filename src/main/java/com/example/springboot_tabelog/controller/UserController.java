package com.example.springboot_tabelog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.from.UserEditForm;
import com.example.springboot_tabelog.repository.UserRepository;
import com.example.springboot_tabelog.repository.VerificationTokenRepository;
import com.example.springboot_tabelog.security.UserDetailsImpl;
import com.example.springboot_tabelog.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	  private final UserRepository userRepository;    
	  private final UserService userService; 
	  private final VerificationTokenRepository verificationTokenRepository;
	    
	    public UserController(UserRepository userRepository,UserService userService ,VerificationTokenRepository verificationTokenRepository) {
	        this.userRepository = userRepository;   
	        this.userService = userService; 
	        this.verificationTokenRepository = verificationTokenRepository;
	    }    
	    
	    @GetMapping
	    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {         
	        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
	        
	        model.addAttribute("user", user);
	        
	        return "user/index";
	    }
	    
	    @GetMapping("/edit")
	    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {        
	        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
	        UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(), user.getPostalCode(), user.getAddress(), user.getPhoneNumber(), user.getEmail());
	        
	        model.addAttribute("userEditForm", userEditForm);
	        
	        return "user/edit";
	    }    
	    
	    @PutMapping("/update")
	    public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
	        // メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
	        if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
	            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
	            bindingResult.addError(fieldError);                       
	        }
	        
	        if (bindingResult.hasErrors()) {
	            return "user/edit";
	        }
	        
	        userService.update(userEditForm);
	        redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");
	        
	        return "redirect:/user";
	    }    
	    
	   @DeleteMapping("/delete")
	  
	    public String delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes,HttpSession session) {        
		   // ユーザーの取得
		   Integer userId = userDetailsImpl.getUser().getId();

		   userService.delete(userId); 
		   
		   session.invalidate();
		   
		   
	        redirectAttributes.addFlashAttribute("successMessage", "退会いたしました。");
	        
	        return "redirect:/";
	    } 
}
