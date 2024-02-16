package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Store;

 public interface StoreRepository extends JpaRepository<Store, Integer> {
		public Page<Store> findByNameLike(String keyword, Pageable pageable);

	     public Page<Store> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword, Pageable pageable);  
	     public Page<Store> findByNameLikeOrAddressLikeOrderByPriceAsc(String nameKeyword, String addressKeyword, Pageable pageable);  
	     public Page<Store> findByAddressLikeOrderByCreatedAtDesc(String area, Pageable pageable);
	     public Page<Store> findByAddressLikeOrderByPriceAsc(String area, Pageable pageable);
	     public Page<Store> findByPriceLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable pageable);
	     public Page<Store> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable); 
	     public Page<Store> findAllByOrderByCreatedAtDesc(Pageable pageable);
	     public Page<Store> findAllByOrderByPriceAsc(Pageable pageable);  
	     
	     public List<Store> findTop10ByOrderByCreatedAtDesc();
}