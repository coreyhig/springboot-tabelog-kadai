package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.form.StoreEditForm;
import com.example.nagoyameshi.form.StoreRegisterForm;
import com.example.nagoyameshi.repository.StoreRepository;
import com.example.nagoyameshi.service.StoreService;
 
@Controller
@RequestMapping("/admin/Store")
public class AdminStoreController {
     private final StoreRepository StoreRepository;
     private final StoreService StoreService; 
     
     public AdminStoreController(StoreRepository StoreRepository, StoreService StoreService) {
         this.StoreRepository = StoreRepository;
         this.StoreService = StoreService; 
     }	
     
     @GetMapping
     public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {    
     
         Page<Store> StorePage;
         
         if (keyword != null && !keyword.isEmpty()) {
             StorePage = StoreRepository.findByNameLike("%" + keyword + "%", pageable);                
         } else {
             StorePage = StoreRepository.findAll(pageable);
         }  
    	 
         model.addAttribute("StorePage", StorePage);      
         
         model.addAttribute("keyword", keyword);
         
         return "admin/Store/index";
     }
     @GetMapping("/{id}")
     public String show(@PathVariable(name = "id") Integer id, Model model) {
         Store Store = StoreRepository.getReferenceById(id);
         
         model.addAttribute("Store", Store);
         
         return "admin/Store/show";
     }    
     @GetMapping("/register")
     public String register(Model model) {
         model.addAttribute("StoreRegisterForm", new StoreRegisterForm());
         return "admin/Store/register";
     }    
     @PostMapping("/create")
     public String create(@ModelAttribute @Validated StoreRegisterForm StoreRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {        
         if (bindingResult.hasErrors()) {
             return "admin/Store/register";
         }
         
         StoreService.create(StoreRegisterForm);
         redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");    
         
         return "redirect:/admin/Store";
     }    
     @GetMapping("/{id}/edit")
     public String edit(@PathVariable(name = "id") Integer id, Model model) {
         Store Store = StoreRepository.getReferenceById(id);
         String imageName = Store.getImageName();
         StoreEditForm StoreEditForm = new StoreEditForm(Store.getId(), Store.getName(), null, Store.getDescription(), Store.getPrice(), Store.getCapacity(), Store.getPostalCode(), Store.getAddress(), Store.getPhoneNumber());
         
         model.addAttribute("imageName", imageName);
         model.addAttribute("StoreEditForm", StoreEditForm);
         
         return "admin/Store/edit";
     }  
     @PostMapping("/{id}/update")
     public String update(@ModelAttribute @Validated StoreEditForm StoreEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {        
         if (bindingResult.hasErrors()) {
             return "admin/Store/edit";
         }
         
         StoreService.update(StoreEditForm);
         redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");
         
         return "redirect:/admin/Store";
     }    
     @PostMapping("/{id}/delete")
     public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {        
         StoreRepository.deleteById(id);
                 
         redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");
         
         return "redirect:/admin/Store";
     }    
}