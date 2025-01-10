package com.example.libraryapp.service;

import com.example.libraryapp.model.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> getLoans();
    List<Loan> searchLoans(String id, String bookTitle, String copy, String email, Boolean returned);
    List<Loan> filterLoansByReturnedStatus(Boolean returned);
}
