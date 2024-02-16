package com.example.nagoyameshi.controller;

 import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.StoreRepository;
 
 @Controller
 @RequestMapping("/Store")
public class StoreController {
     private final StoreRepository StoreRepository;        
     
     public StoreController(StoreRepository StoreRepository) {
         this.StoreRepository = StoreRepository;            
     }     
   
     @GetMapping
     public String index(@RequestParam(name = "keyword", required = false) String keyword,
                         @RequestParam(name = "area", required = false) String area,
                         @RequestParam(name = "price", required = false) Integer price, 
                         @RequestParam(name = "order", required = false) String order,
                         @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                         Model model) 
     {
         Page<Store> StorePage;
                 
         if (keyword != null && !keyword.isEmpty()) {
        	  if (order != null && order.equals("priceAsc")) {
                  StorePage = StoreRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
              } else {
                  StorePage = StoreRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
              }        
         } else if (area != null && !area.isEmpty()) {
        	  if (order != null && order.equals("priceAsc")) {
                  StorePage = StoreRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);
              } else {
                  StorePage = StoreRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
              }    
         } else if (price != null) {
        	   if (order != null && order.equals("priceAsc")) {
                   StorePage = StoreRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
               } else {
                   StorePage = StoreRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
               }    
         } else {
        	   if (order != null && order.equals("priceAsc")) {
                   StorePage = StoreRepository.findAllByOrderByPriceAsc(pageable);
               } else {
                   StorePage = StoreRepository.findAllByOrderByCreatedAtDesc(pageable);   
               }   
         }                
         
         model.addAttribute("StorePage", StorePage);
         model.addAttribute("keyword", keyword);
         model.addAttribute("area", area);
         model.addAttribute("price", price);
         model.addAttribute("order", order);
         
         return "Store/index";
     }
     @GetMapping("/{id}")
     public String show(@PathVariable(name = "id") Integer id, Model model) {
         Store Store = StoreRepository.getReferenceById(id);
         
         model.addAttribute("Store", Store); 
         model.addAttribute("reservationInputForm", new ReservationInputForm());
         
         return "Store/show";
     }    
}
