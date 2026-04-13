package com.example.loanlab.controller;

import com.example.loanlab.entity.ClientLoan;
import com.example.loanlab.repository.ClientLoanRepository;
import com.example.loanlab.service.AmortizationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientLoanController.class)
class ClientLoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientLoanRepository repository;

    @MockBean
    private AmortizationService amortizationService;

    @Test
    @DisplayName("Display all client records")
    void testDisplayRecords() throws Exception {
        ClientLoan c1 = new ClientLoan(101, "John Doe", 3000.0, 3, "Personal");
        ClientLoan c2 = new ClientLoan(102, "Jane Doe", 5000.0, 2, "Business");

        when(repository.findAll()).thenReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("clients"));

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Add a new client successfully")
    void testAddRecord() throws Exception {
        when(repository.existsById(101)).thenReturn(false);
        when(repository.save(any(ClientLoan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/save")
                        .param("clientNo", "101")
                        .param("name", "John Doe")
                        .param("loanAmount", "3000")
                        .param("years", "3")
                        .param("loanType", "Personal"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("successMessage", "Client added successfully!"));

        verify(repository).existsById(101);
        verify(repository).save(argThat(client ->
                client.getClientNo().equals(101) &&
                        client.getName().equals("John Doe") &&
                        client.getLoanAmount().equals(3000.0) &&
                        client.getYears().equals(3) &&
                        client.getLoanType().equals("Personal")
        ));
    }

    @Test
    @DisplayName("Do not add duplicate client number")
    void testAddDuplicateRecord() throws Exception {
        when(repository.existsById(101)).thenReturn(true);

        mockMvc.perform(post("/save")
                        .param("clientNo", "101")
                        .param("name", "John Doe")
                        .param("loanAmount", "3000")
                        .param("years", "3")
                        .param("loanType", "Personal"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-client"))
                .andExpect(model().attributeExists("duplicateError"));

        verify(repository).existsById(101);
        verify(repository, never()).save(any(ClientLoan.class));
    }

    @Test
    @DisplayName("Delete a client successfully")
    void testDeleteRecord() throws Exception {
        mockMvc.perform(get("/delete/101"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("successMessage", "Client deleted successfully!"));

        verify(repository, times(1)).deleteById(101);
    }

    @Test
    @DisplayName("Open edit page with existing client data")
    void testShowEditForm() throws Exception {
        ClientLoan client = new ClientLoan(101, "John Doe", 3000.0, 3, "Personal");
        when(repository.findById(101)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/edit/101"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-client"))
                .andExpect(model().attributeExists("clientLoan"));

        verify(repository).findById(101);
    }

    @Test
    @DisplayName("Update a client successfully")
    void testUpdateRecord() throws Exception {
        when(repository.save(any(ClientLoan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/update")
                        .param("originalClientNo", "101")
                        .param("clientNo", "101")
                        .param("name", "John Smith")
                        .param("loanAmount", "4500")
                        .param("years", "4")
                        .param("loanType", "Business"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("successMessage", "Client updated successfully!"));

        verify(repository).save(argThat(client ->
                client.getClientNo().equals(101) &&
                        client.getName().equals("John Smith") &&
                        client.getLoanAmount().equals(4500.0) &&
                        client.getYears().equals(4) &&
                        client.getLoanType().equals("Business")
        ));
    }
}
