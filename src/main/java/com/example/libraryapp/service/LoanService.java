package com.example.libraryapp.service;

import com.example.libraryapp.model.Loan;
import com.example.libraryapp.model.Printout;
import com.example.libraryapp.model.User;

import java.sql.Date;
import java.util.List;

public interface LoanService {
    List<Loan> getLoans();
    List<Loan> searchLoans(String id, String bookTitle, String copy, String email, Boolean returned);
    List<Loan> filterLoansByReturnedStatus(Boolean returned);
    List<Loan> getLoansByUser(User user);
    List<Loan> getActiveLoansByUser(User user);
    Loan createLoan(User user, Printout printout, Date loanDate);
    Loan updateLoanByPrintoutId(Integer printoutId, Date returnDate, boolean loanStatus);
    User getUserByPrintoutId(Integer printoutId);
}
