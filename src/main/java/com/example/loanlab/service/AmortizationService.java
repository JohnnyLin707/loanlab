package com.example.loanlab.service;

import com.example.loanlab.entity.ClientLoan;
import com.example.loanlab.model.AmortizationRow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AmortizationService {

    public List<AmortizationRow> buildSchedule(ClientLoan clientLoan) {
        List<AmortizationRow> schedule = new ArrayList<>();

        double loanAmount = clientLoan.getLoanAmount();
        int termInMonths = clientLoan.getYears() * 12;

        double annualRate = getAnnualRate(clientLoan.getLoanType());
        double monthlyRate = annualRate / 12.0;

        double monthlyPaymentExact =
                (loanAmount * monthlyRate) /
                        (1 - Math.pow(1 + monthlyRate, -termInMonths));

        double balance = loanAmount;

        for (int month = 1; month <= termInMonths; month++) {
            double interest = balance * monthlyRate;
            double principal = monthlyPaymentExact - interest;
            double newBalance = balance - principal;

            if (month == termInMonths) {
                principal = balance;
                interest = monthlyPaymentExact - principal;
                newBalance = 0.00;
            }

            schedule.add(new AmortizationRow(
                    month,
                    round(monthlyPaymentExact),
                    round(interest),
                    round(principal),
                    round(newBalance)
            ));

            balance = newBalance;
        }

        return schedule;
    }

    public double getAnnualRate(String loanType) {
        if ("Business".equalsIgnoreCase(loanType)) {
            return 0.09;
        }
        return 0.06;
    }

    public double getMonthlyPayment(ClientLoan clientLoan) {
        double loanAmount = clientLoan.getLoanAmount();
        int termInMonths = clientLoan.getYears() * 12;
        double annualRate = getAnnualRate(clientLoan.getLoanType());
        double monthlyRate = annualRate / 12.0;

        double monthlyPayment =
                (loanAmount * monthlyRate) /
                        (1 - Math.pow(1 + monthlyRate, -termInMonths));

        return round(monthlyPayment);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
