package com.example.nagoyameshi.form;

 import lombok.AllArgsConstructor;
import lombok.Data;
 
 @Data
 @AllArgsConstructor
public class ReservationRegisterForm {    
     private Integer StoreId;
         
     private Integer userId;    
         
     private String checkinDate;    
         
     private String checkoutDate;    
     
     private Integer numberOfPeople;
     
     private Integer amount;    
}
