package com.example.nagoyameshi.service;

 import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.entity.User;
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
     public void create(Map<String, String> paymentIntentObject) {
         Reservation reservation = new Reservation();
         
         Integer StoreId = Integer.valueOf(paymentIntentObject.get("StoreId"));
         Integer userId = Integer.valueOf(paymentIntentObject.get("userId"));
         Store Store = StoreRepository.getReferenceById(StoreId); 
         User user = userRepository.getReferenceById(userId);
         LocalDate checkinDate = LocalDate.parse(paymentIntentObject.get("checkinDate"));
         Integer numberOfPeople = Integer.valueOf(paymentIntentObject.get("numberOfPeople"));        
         Integer amount = Integer.valueOf(paymentIntentObject.get("amount"));     
                 
         reservation.setStore(Store);
         reservation.setUser(user);
         reservation.setCheckinDate(checkinDate);
         reservation.setNumberOfPeople(numberOfPeople);
         reservation.setAmount(amount);
         
         reservationRepository.save(reservation);
     }    
     // 人数が定員以下かどうかをチェックする
     public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
         return numberOfPeople <= capacity;
     }
     
     // 料金を計算する
     public Integer calculateAmount(LocalDate checkinDate, LocalDate checkoutDate, Integer price) {
         long numberOfNights = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
         int amount = price * (int)numberOfNights;
         return amount;
     }    
}