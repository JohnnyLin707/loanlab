package com.example.loanlab.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "client_loan")
public class ClientLoan {

    @Id
    @NotNull(message = "Client Number is required")
    private Integer clientNo;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Loan Amount is required")
    @DecimalMin(value = "0.01", message = "Loan Amount must be greater than 0")
    private Double loanAmount;

    @NotNull(message = "Years is required")
    @Min(value = 1, message = "Years must be at least 1")
    private Integer years;

    @NotBlank(message = "Loan Type is required")
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
