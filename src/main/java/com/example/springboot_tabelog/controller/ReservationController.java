package com.example.springboot_tabelog.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot_tabelog.entity.Reservation;
import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.from.ReservationCancelForm;
import com.example.springboot_tabelog.from.ReservationInputForm;
import com.example.springboot_tabelog.from.ReservationRegisterForm;
import com.example.springboot_tabelog.repository.ReservationRepository;
import com.example.springboot_tabelog.repository.ShopRepository;
import com.example.springboot_tabelog.repository.UserRepository;
import com.example.springboot_tabelog.security.UserDetailsImpl;
import com.example.springboot_tabelog.service.ReservationService;

@Controller
public class ReservationController {
	  private final ReservationRepository reservationRepository; 
	  private final UserRepository userRepository;
	  private final ShopRepository shopRepository;
	  private final ReservationService reservationService;
	 /* 
		
		
		private final ReviewRepository reviewRepository; */
	    
	    public ReservationController(ReservationRepository reservationRepository, UserRepository userRepository,ShopRepository shopRepository,ReservationService reservationService) {        
	        this.reservationRepository = reservationRepository;
	       this.userRepository = userRepository;
	       this.shopRepository = shopRepository;
	       this.reservationService = reservationService;

	       
	    }    

	    @GetMapping("/reservations")
	    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
	        User user = userDetailsImpl.getUser();
	        Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
	        
	        model.addAttribute("reservationPage", reservationPage);         
	        
	        return "reservations/index";
	    }
	    
	    @GetMapping("/shops/{id}/reservations/input")
		public String input(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id") Integer id,
				@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable,
				@ModelAttribute @Validated ReservationInputForm reservationInputForm,
				BindingResult bindingResult,
				RedirectAttributes redirectAttributes,
				Model model) {
			

			 /* User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
			  * if ("ROLE_FREE_MEMBER".equals(user.getRole().getName())) {
				redirectAttributes.addFlashAttribute("subscriptionMessage", "この機能は有料プランに加入しないと使用できません");
				return "redirect:/subscription/register";
			} */

			Shop shop = shopRepository.getReferenceById(id);

			
			

			/*if (count != null) {
				if (!reservationService.isWithinCapacity(count, capacity)) {
					FieldError fieldError = new FieldError(bindingResult.getObjectName(), "count",
							"予約人数が定員を超えています。");
					bindingResult.addError(fieldError);
				}
			}*/

			String reservationDate = reservationInputForm.getCheckinDate();
			String reservationTime = reservationInputForm.getTimeDate();
			
			if (!reservationService.isWithinOperatingHours(reservationDate, reservationTime, shop)) {
                FieldError fieldError = new FieldError(bindingResult.getObjectName(), "timeDate",
                        "予約は営業中のみ受け付けています。");
                bindingResult.addError(fieldError);
            }

			if (!reservationDate.isEmpty() && !reservationTime.isEmpty()) {
				if (!reservationService.isReservationDateWhenCurrentTimeAfter(reservationDate, reservationTime)) {
					FieldError fieldError = new FieldError(bindingResult.getObjectName(), "timeDate",
							"予約時間を過ぎています。");
					bindingResult.addError(fieldError);
					
					
				}
			}
			
			if (bindingResult.hasErrors()) {  
				model.addAttribute("shop", shop);
				model.addAttribute("errorMessage", "予約内容に不備があります。");
				
				return "shops/show";
			}


			redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);

			return "redirect:/shops/{id}/reservations/confirm";
		}
	    
	    @GetMapping("/shops/{id}/reservations/confirm")
		public String confirm(@PathVariable(name = "id") Integer id,
				@ModelAttribute ReservationInputForm reservationInputForm,
				@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes,
				Model model) {
	    	Shop shop = shopRepository.getReferenceById(id);
	    	User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
	    	/*
			if ("ROLE_FREE_MEMBER".equals(user.getRole().getName())) {
				redirectAttributes.addFlashAttribute("subscriptionMessage", "この機能は有料プランに加入しないと使用できません");
				return "redirect:/subscription/register";
			}*/

			//予約日時を取得する
			LocalDateTime reservationDateTime = LocalDateTime
					.parse(reservationInputForm.getCheckinDate() + "T" + reservationInputForm.getTimeDate());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 H時mm分");

			ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(user.getId(), shop.getId(),
					reservationDateTime.format(dtf), reservationInputForm.getNumberOfPeople());

			model.addAttribute("shop", shop);
			model.addAttribute("reservationRegisterForm", reservationRegisterForm);

			return "reservations/confirm";
		}
	    
	    @PostMapping("/shops/{id}/reservations/create")
		public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {
			reservationService.create(reservationRegisterForm);
			return "redirect:/reservations?reserved";
		}
	    
	    @PostMapping("/shops/cancel")
		public String cancel(@ModelAttribute ReservationCancelForm reservatioCancelForm,
				RedirectAttributes redirectAttributes) {
			reservationService.cancel(reservatioCancelForm);
			redirectAttributes.addFlashAttribute("successMessage", "予約のキャンセルが完了しました。");
			return "redirect:/reservations";
		}

}
