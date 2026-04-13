package com.example.loanlab.controller;

import com.example.loanlab.entity.ClientLoan;
import com.example.loanlab.repository.ClientLoanRepository;
import com.example.loanlab.service.AmortizationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientLoanController {

    private final ClientLoanRepository repository;
    private final AmortizationService amortizationService;

    public ClientLoanController(ClientLoanRepository repository,
                                AmortizationService amortizationService) {
        this.repository = repository;
        this.amortizationService = amortizationService;
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
    public String saveClient(@Valid @ModelAttribute("clientLoan") ClientLoan clientLoan,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "add-client";
        }

        if (repository.existsById(clientLoan.getClientNo())) {
            model.addAttribute("duplicateError", "Client number already exists!");
            return "add-client";
        }

        repository.save(clientLoan);
        redirectAttributes.addFlashAttribute("successMessage", "Client added successfully!");
        return "redirect:/";
    }

    @GetMapping("/edit/{clientNo}")
    public String showEditForm(@PathVariable Integer clientNo, Model model) {
        ClientLoan client = repository.findById(clientNo).orElseThrow();
        model.addAttribute("clientLoan", client);
        return "edit-client";
    }

    @PostMapping("/update")
    public String updateClient(@RequestParam("originalClientNo") Integer originalClientNo,
                               @Valid @ModelAttribute("clientLoan") ClientLoan clientLoan,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "edit-client";
        }

        if (!originalClientNo.equals(clientLoan.getClientNo())
                && repository.existsById(clientLoan.getClientNo())) {
            model.addAttribute("duplicateError", "Client number already exists!");
            return "edit-client";
        }

        if (originalClientNo.equals(clientLoan.getClientNo())) {
            repository.save(clientLoan);
        } else {
            repository.save(clientLoan);
            repository.deleteById(originalClientNo);
        }

        redirectAttributes.addFlashAttribute("successMessage", "Client updated successfully!");
        return "redirect:/";
    }

    @GetMapping("/delete/{clientNo}")
    public String deleteClient(@PathVariable Integer clientNo,
                               RedirectAttributes redirectAttributes) {
        repository.deleteById(clientNo);
        redirectAttributes.addFlashAttribute("successMessage", "Client deleted successfully!");
        return "redirect:/";
    }

    @GetMapping("/amortization/{clientNo}")
    public String showAmortization(@PathVariable Integer clientNo, Model model) {
        ClientLoan client = repository.findById(clientNo).orElseThrow();

        model.addAttribute("client", client);
        model.addAttribute("annualRate", amortizationService.getAnnualRate(client.getLoanType()) * 100);
        model.addAttribute("monthlyPayment", amortizationService.getMonthlyPayment(client));
        model.addAttribute("schedule", amortizationService.buildSchedule(client));

        return "amortization";
    }
}
