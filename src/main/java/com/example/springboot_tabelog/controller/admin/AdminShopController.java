package com.example.springboot_tabelog.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.from.ShopEditForm;
import com.example.springboot_tabelog.from.ShopRegisterForm;
import com.example.springboot_tabelog.repository.CategoryRepository;
import com.example.springboot_tabelog.repository.ShopRepository;
import com.example.springboot_tabelog.service.ShopService;

@Controller
@RequestMapping("/admin/shops")
public class AdminShopController {

	private final ShopRepository shopRepository;
	private final CategoryRepository categoryRepository;
	private final ShopService shopService;  

	public AdminShopController(ShopRepository shopRepository,CategoryRepository categoryRepository,ShopService shopService) {
		this.shopRepository = shopRepository;
		this.categoryRepository = categoryRepository;
		this.shopService = shopService;
		
	}



	@GetMapping
	public String index(Model model,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			@RequestParam(name = "keyword", required = false) String keyword) {
		Page<Shop> shopPage;

		if (keyword != null && !keyword.isEmpty()) {
			shopPage = shopRepository.findByNameLike("%" + keyword + "%", pageable);
		} else {
			shopPage = shopRepository.findAll(pageable);
		}

		model.addAttribute("shopPage", shopPage);
		model.addAttribute("keyword", keyword);

		return "admin/shops/index";

	}

	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		Shop shop = shopRepository.getReferenceById(id);

		model.addAttribute("shop", shop);

		return "admin/shops/show";

	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("shopRegisterForm", new ShopRegisterForm());
		model.addAttribute("categoryList" , categoryRepository.findAll());
		return "admin/shops/register";
	}
	
	@PostMapping("/create")
	 public String create(@ModelAttribute @Validated ShopRegisterForm shopRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {        
        if (bindingResult.hasErrors()) {
            return "admin/shops/register";
        }
        
        shopService.create(shopRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");    
        
        return "redirect:/admin/shops";
    }    
	
	  @GetMapping("/{id}/edit")
	    public String edit(@PathVariable(name = "id") Integer id, Model model) {
		    Shop shop = shopRepository.getReferenceById(id);
	        String imageName = shop.getImageName();
	        ShopEditForm shopEditForm = new ShopEditForm(shop.getId(), shop.getCategory() ,shop.getName(), null, shop.getDescription(), shop.getPriceLowerLimit(), shop.getPriceUpperLimit(),shop.getOpeningTimes(),shop.getClosedTimes(), shop.getPostalCode(), shop.getAddress(), shop.getPhoneNumber() , shop.getClosedDay());
	        
	        model.addAttribute("imageName", imageName);
	        model.addAttribute("categoryList" , categoryRepository.findAll());
	        model.addAttribute("shopEditForm", shopEditForm);
	        
	        return "admin/shops/edit";
	    }    
	  
	  @PutMapping("/{id}/update")
	    public String update(@ModelAttribute @Validated ShopEditForm shopEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {        
	        if (bindingResult.hasErrors()) {
	            return "admin/shops/edit";
	        }
	        
	        shopService.update(shopEditForm);
	        redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");
	        
	        return "redirect:/admin/shops";
	    }    
	  
	  @DeleteMapping("/{id}/delete")
	    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {  
		  
		       shopRepository.deleteById(id);
	                
	        redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");
	        
	        return "redirect:/admin/shops";
	    }   
	  
	  
}
