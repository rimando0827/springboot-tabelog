package com.example.springboot_tabelog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.repository.UserRepository;
import com.example.springboot_tabelog.security.UserDetailsImpl;
import com.example.springboot_tabelog.service.StripeService;
import com.example.springboot_tabelog.service.UserService;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Subscription;

@Controller
@RequestMapping("/subscriptions")
public class PaidMemberController {
	@Value("${stripe.price-id}")
	private String priceId;

	private final UserRepository userRepository;
	private final UserService userService;
	private final StripeService stripeService;

	public PaidMemberController(UserRepository userRepository, UserService userService, StripeService stripeService) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.stripeService = stripeService;
	}
	
	@GetMapping("/register")
    public String register() {      
        return "subscriptions/register";
    } 
    
    @PostMapping("/create")
    public String create(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestParam String paymentMethodId, RedirectAttributes redirectAttributes) {      
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
        
        if (user.getStripeCustomerId() == null || user.getStripeCustomerId().isEmpty()) { 
            Customer customer = stripeService.createCustomer(user.getName(), user.getEmail(), paymentMethodId);
            userService.createStripeCustomer(user, customer.getId());
        } 
        
        
        
        stripeService.createSubscription(user.getStripeCustomerId(), priceId); //planId = プライスID
        
        userService.updateRole(user, "ROLE_PREMIUM");
        userService.refreshAuthenticationByRole("ROLE_PREMIUM");
        redirectAttributes.addFlashAttribute("successMessage", "有料プランへの登録が完了しました。");    
        
        return "redirect:/";
    }
    
    @GetMapping("/edit")
    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {      
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId()); 
        PaymentMethod paymentMethod = stripeService.getDefaultPaymentMethod(user.getStripeCustomerId());        
        
        model.addAttribute("card", paymentMethod.getCard());
        model.addAttribute("cardHolderName", paymentMethod.getBillingDetails().getName());
        
        return "subscriptions/edit";
    }
    
    @PostMapping("/update")
    public String update(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestParam String paymentMethodId, RedirectAttributes redirectAttributes) {
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
        
        try {
            String oldDefaultPaymentMethodId = stripeService.getDefaultPaymentMethod(user.getStripeCustomerId()).getId();        
            stripeService.updateSubscription(user.getStripeCustomerId(), paymentMethodId);
            stripeService.detachPaymentMethod(oldDefaultPaymentMethodId);
            redirectAttributes.addFlashAttribute("successMessage", "お支払い方法を変更しました。");                           
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "お支払い方法の変更に失敗しました。");
        }                         
        
        return "redirect:/";
    }
    
    @PostMapping("/delete")
    public String delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes) {
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());          
        
        Subscription subscription = stripeService.getSubscription(user.getStripeCustomerId());
        if (subscription != null) {
            stripeService.cancelSubscription(subscription);
            userService.updateRole(user, "ROLE_GENERAL");
            userService.refreshAuthenticationByRole("ROLE_GENERAL");
            redirectAttributes.addFlashAttribute("successMessage", "有料プランを解約しました。");    
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "有料プランの解約に失敗しました。");    
        }                        
        
        return "redirect:/";
    }

}


