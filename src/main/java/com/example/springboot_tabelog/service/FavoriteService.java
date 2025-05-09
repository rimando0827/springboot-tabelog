package com.example.springboot_tabelog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.Favorite;
import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.from.FavoriteForm;
import com.example.springboot_tabelog.repository.FavoriteRepository;
import com.example.springboot_tabelog.repository.ShopRepository;
import com.example.springboot_tabelog.repository.UserRepository;

@Service
public class FavoriteService {
	
	private final FavoriteRepository favoriteRepository;
	private final ShopRepository shopRepository;
	private final UserRepository userRepository;
	
	public FavoriteService(FavoriteRepository favoriteRepository,
			ShopRepository shopRepository,
			UserRepository userRepository) {
		
		this.favoriteRepository = favoriteRepository;
		this.shopRepository = shopRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void create(FavoriteForm favoriteForm) {
		Favorite favorite = new Favorite();
		
		Shop shop = shopRepository.getReferenceById(favoriteForm.getShopId());
	    User user = userRepository.getReferenceById(favoriteForm.getUserId());
		
	    favorite.setShop(shop);
	    favorite.setUser(user);
		
		favoriteRepository.save(favorite);
		
	}

}
