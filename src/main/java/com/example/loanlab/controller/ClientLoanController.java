package com.example.loanlab.controller;

import com.example.loanlab.entity.ClientLoan;
import com.example.loanlab.repository.ClientLoanRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientLoanController {

    private final ClientLoanRepository repository;

    public ClientLoanController(ClientLoanRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("clients", repository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("clientLoan", new ClientLoan());
        return "add-client";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute ClientLoan clientLoan) {
        repository.save(clientLoan);
        return "redirect:/";
    }
}
