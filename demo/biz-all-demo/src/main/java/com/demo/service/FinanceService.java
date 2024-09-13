package com.demo.service;

import com.biz.operation.log.OperationLog;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
public class FinanceService {

    @OperationLog(category = "FINANCE_OPERATION", subcategory = "BONUS_PAYMENT",
            content = "'Bonus payment for Employee ID: ' + #employeeId + ' in amount: $' + #amount")
    public void payBonus(String employeeId, double amount) {
        // 发放奖金的逻辑
        System.out.println("Paying bonus to employee " + employeeId + " amount: $" + amount);
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Payment {
        public String employeeId;
        public double amount;
    }
}
