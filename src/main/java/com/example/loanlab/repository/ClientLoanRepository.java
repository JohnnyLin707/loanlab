package com.example.loanlab.repository;

import com.example.loanlab.entity.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository<ClientLoan, Integer> {
}
