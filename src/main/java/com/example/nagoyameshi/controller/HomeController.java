package com.example.nagoyameshi.controller;

 import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.repository.StoreRepository;

@Controller
public class HomeController {
     private final StoreRepository StoreRepository;        
     
     public HomeController(StoreRepository StoreRepository) {
         this.StoreRepository = StoreRepository;            
     }    
    
    @GetMapping("/")
     public String index(Model model) {
         List<Store> newStore = StoreRepository.findTop10ByOrderByCreatedAtDesc();
         model.addAttribute("newStore", newStore);        
        
        return "index";
    }   
}