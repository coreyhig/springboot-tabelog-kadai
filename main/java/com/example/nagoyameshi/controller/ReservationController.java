package com.example.nagoyameshi.controller;

import java.time.LocalDate;
import java.time.LocalTime;

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

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.StoreRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReservationService;
 
 @Controller
public class ReservationController {
     private final ReservationRepository reservationRepository;
     private final StoreRepository StoreRepository;
     private final ReservationService reservationService; 
     
     public ReservationController(ReservationRepository reservationRepository, StoreRepository StoreRepository, ReservationService reservationService) {      
         this.reservationRepository = reservationRepository;
         this.StoreRepository = StoreRepository;
         this.reservationService = reservationService;
     }    
 
     @GetMapping("/reservations")
     public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
         User user = userDetailsImpl.getUser();
         Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
         
         model.addAttribute("reservationPage", reservationPage);         
         
         return "reservations/index";
     }
     @GetMapping("/Store/{id}/reservations/input")
     public String input(@PathVariable(name = "id") Integer id,
                         @ModelAttribute @Validated ReservationInputForm reservationInputForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model)
     {   
         Store Store = StoreRepository.getReferenceById(id);
         Integer numberOfPeople = reservationInputForm.getNumberOfPeople();   
         Integer capacity = Store.getCapacity();
         
         if (numberOfPeople != null) {
             if (!reservationService.isWithinCapacity(numberOfPeople, capacity)) {
                 FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "人数が定員を超えています。");
                 bindingResult.addError(fieldError);                
             }            
         }         
         
         if (bindingResult.hasErrors()) {            
             model.addAttribute("Store", Store);            
             model.addAttribute("errorMessage", "入力内容に不備があります。"); 
             return "Store/show";
         }
         
         redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);           
         
         return "redirect:/Store/{id}/reservations/confirm";
     } 
     @GetMapping("/Store/{id}/reservations/confirm")
     public String confirm(@PathVariable(name = "id") Integer id,
                           @ModelAttribute ReservationInputForm reservationInputForm,
                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,  
                           Model model) 
     {        
         Store Store = StoreRepository.getReferenceById(id);
         User user = userDetailsImpl.getUser(); 
                 
         //予約日時を取得する
         LocalDate checkinDate = reservationInputForm.getCheckinDate();
         LocalTime checkinTime = reservationInputForm.getCheckinTime();
         
         ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(Store.getId(), user.getId(), checkinDate.toString(), checkinTime.toString(), reservationInputForm.getNumberOfPeople() );
         
         model.addAttribute("Store", Store);  
         model.addAttribute("reservationRegisterForm", reservationRegisterForm); 
         
         return "reservations/confirm";
     }
     
     @PostMapping("/Store/{id}/reservations/create")
     public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {                
         reservationService.create(reservationRegisterForm);        
         
         return "redirect:/reservations?reserved";
     }
    
}