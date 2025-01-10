package com.example.libraryapp.service;

import com.example.libraryapp.model.Loan;
import com.example.libraryapp.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;

    @Override
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> searchLoans(String id, String bookTitle, String copy, String email, Boolean returned) {
        if (id != null && !id.isEmpty()) {
            return loanRepository.findByIdContaining(Integer.valueOf(id), returned);
        } else if (bookTitle != null && !bookTitle.isEmpty()) {
            return loanRepository.findByBookTitleContainingIgnoreCase(bookTitle, returned);
        } else if (copy != null && !copy.isEmpty()) {
            return loanRepository.findByPrintoutId(Integer.valueOf(copy), returned);
        } else if (email != null && !email.isEmpty()) {
            return loanRepository.findByUserEmailContainingIgnoreCase(email, returned);
        } else {
            return loanRepository.findByReturned(returned);
        }
    }

    @Override
    public List<Loan> filterLoansByReturnedStatus(Boolean returned) {
        return loanRepository.findByReturned(returned);
    }
}
