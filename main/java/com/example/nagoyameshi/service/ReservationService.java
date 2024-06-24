package com.example.nagoyameshi.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.StoreRepository;
import com.example.nagoyameshi.repository.UserRepository;
 
 @Service
public class ReservationService {
     private final ReservationRepository reservationRepository;  
     private final StoreRepository StoreRepository;  
     private final UserRepository userRepository;  
     
     public ReservationService(ReservationRepository reservationRepository, StoreRepository StoreRepository, UserRepository userRepository) {
         this.reservationRepository = reservationRepository;  
         this.StoreRepository = StoreRepository;  
         this.userRepository = userRepository;  
     }    
     
     @Transactional
     public void create(ReservationRegisterForm reservationRegisterForm) {
         Reservation reservation = new Reservation();       
         Store store = StoreRepository.getReferenceById(reservationRegisterForm.getStoreId());
         User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
         LocalDate checkinDate = LocalDate.parse(reservationRegisterForm.getCheckinDate());
         LocalTime checkinTime = LocalTime.parse(reservationRegisterForm.getCheckinTime());    
                 
         reservation.setStore(store);
         reservation.setUser(user);
         reservation.setCheckinDate(checkinDate);
         reservation.setCheckinTime(checkinTime);         
         reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
         
         reservationRepository.save(reservation);
     }   
     
     // 人数が定員以下かどうかをチェックする
     public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
         return numberOfPeople <= capacity;
     }
         
}