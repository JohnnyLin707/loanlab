package com.example.loanlab.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "client_loan")
public class ClientLoan {

    @Id
    private Integer clientNo;

    private String name;
    private Double loanAmount;
    private Integer years;
    private String loanType;

    public ClientLoan() {
    }

    public ClientLoan(Integer clientNo, String name, Double loanAmount, Integer years, String loanType) {
        this.clientNo = clientNo;
        this.name = name;
        this.loanAmount = loanAmount;
        this.years = years;
        this.loanType = loanType;
    }

    public Integer getClientNo() {
        return clientNo;
    }

    public void setClientNo(Integer clientNo) {
        this.clientNo = clientNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
}
