package com.example.nagoyameshi.form;

 import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
 @Data
public class ReservationInputForm {
     @NotBlank(message = "予約日を選択してください。")
     private String fromCheckinDateToCheckoutDate;    
     
     @NotNull(message = "ご利用人数を入力してください。")
     @Min(value = 1, message = "ご利用人数は1人以上に設定してください。")
     private Integer numberOfPeople; 
 
     // 予約日を取得する
     public LocalDate getCheckinDate() {
         String[] checkinDateAndCheckoutDate = getFromCheckinDateToCheckoutDate().split(" から ");
         return LocalDate.parse(checkinDateAndCheckoutDate[0]);
     }
 
     // 予約日２を取得する
     public LocalDate getCheckoutDate() {
         String[] checkinDateAndCheckoutDate = getFromCheckinDateToCheckoutDate().split(" から ");
         return LocalDate.parse(checkinDateAndCheckoutDate[1]);
     }
}