package com.example.loanlab.model;

public class AmortizationRow {

    private int month;
    private double payment;
    private double interest;
    private double principal;
    private double balance;

    public AmortizationRow(int month, double payment, double interest, double principal, double balance) {
        this.month = month;
        this.payment = payment;
        this.interest = interest;
        this.principal = principal;
        this.balance = balance;
    }

    public int getMonth() {
        return month;
    }

    public double getPayment() {
        return payment;
    }

    public double getInterest() {
        return interest;
    }

    public double getPrincipal() {
        return principal;
    }

    public double getBalance() {
        return balance;
    }
}
