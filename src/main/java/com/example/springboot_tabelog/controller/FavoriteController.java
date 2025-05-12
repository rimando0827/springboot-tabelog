package com.example.springboot_tabelog.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot_tabelog.entity.Favorite;
import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.from.FavoriteForm;
import com.example.springboot_tabelog.repository.FavoriteRepository;
import com.example.springboot_tabelog.security.UserDetailsImpl;
import com.example.springboot_tabelog.service.FavoriteService;

@Controller
public class FavoriteController {
	private FavoriteService favoriteService;
	private FavoriteRepository favoriteRepository;
	
	
	public FavoriteController(FavoriteRepository favoriteRepository,FavoriteService favoriteService) {
		 this.favoriteService = favoriteService;
		this.favoriteRepository = favoriteRepository;
		

	}
	
	@GetMapping("/favorites")
	public String index(@PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,

			Model model) {

		User user = userDetailsImpl.getUser();

		Page<Favorite> favoriteList = favoriteRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), pageable);

		model.addAttribute("favoriteList", favoriteList);

		return "/favorites/index";

	}
	
	@PostMapping("/shops/{shopId}/favorites/create")
	public String create(@PathVariable(name = "shopId") Integer shopId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@ModelAttribute @Validated FavoriteForm favoriteForm, Model model,RedirectAttributes redirectAttributes) {

		Integer userId = userDetailsImpl.getUser().getId();
		favoriteForm.setShopId(shopId);
		favoriteForm.setUserId(userId);

		favoriteService.create(favoriteForm); // サービスを呼び出して登録

		redirectAttributes.addFlashAttribute("message", "お気に入りに追加しました！");
		return "redirect:/shops/" + shopId; // 戻る際にリダイレクトすることで状態を更新
	}
	
	
	@DeleteMapping("/shops/{shopId}/favorites/{favoriteId}/delete")
	public String deleteFavorite(@PathVariable(name = "shopId") Integer shopId,
	                             @PathVariable(name = "favoriteId") Integer favoriteId, 
	                             RedirectAttributes redirectAttributes,Model model) {

	    favoriteRepository.deleteById(favoriteId);

	    redirectAttributes.addFlashAttribute("message", "お気に入りを削除しました！");
	    return "redirect:/shops/" + shopId;
	}

}
