package com.example.libraryapp.repository;

import com.example.libraryapp.model.Loan;
import com.example.libraryapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("SELECT l FROM Loan l WHERE (:returned IS NULL OR l.loanStatus = :returned)")
    List<Loan> findByReturned(@Param("returned") Boolean returned);

    @Query("SELECT l FROM Loan l WHERE (:returned IS NULL OR l.loanStatus = :returned) AND l.id = :id")
    List<Loan> findByIdContaining(@Param("id") Integer id, @Param("returned") Boolean returned);

    @Query("SELECT l FROM Loan l WHERE (:returned IS NULL OR l.loanStatus = :returned) AND LOWER(l.printout.book.title) LIKE %:title%")
    List<Loan> findByBookTitleContainingIgnoreCase(@Param("title") String title, @Param("returned") Boolean returned);

    @Query("SELECT l FROM Loan l WHERE (:returned IS NULL OR l.loanStatus = :returned) AND l.printout.id = :printoutId")
    List<Loan> findByPrintoutId(@Param("printoutId") Integer printoutId, @Param("returned") Boolean returned);

    @Query("SELECT l FROM Loan l WHERE (:returned IS NULL OR l.loanStatus = :returned) AND LOWER(l.user.username) LIKE %:email%")
    List<Loan> findByUserEmailContainingIgnoreCase(@Param("email") String email, @Param("returned") Boolean returned);

    List<Loan> findByUser(User user);

    List<Loan> findByUserAndLoanStatus(User user, boolean loanStatus);
}
