package com.example.libraryapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "loan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loanid_seq")
    @SequenceGenerator(name = "loanid_seq", sequenceName = "loanid_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "loandate")
    private Date loanDate;

    @Column(name = "loanstatus", nullable = false)
    private boolean loanStatus;

    @Column(name = "returndate")
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "printoutid", nullable = false)
    private Printout printout;

    @ManyToOne
    @JoinColumn(name = "usersid", nullable = false)
    private User user;
}
