package com.example.libraryapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "printout")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Printout {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookcopy_bookcopyid_seq")
    @SequenceGenerator(name = "bookcopy_bookcopyid_seq", sequenceName = "bookcopy_bookcopyid_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "book", nullable = false)
    private Book book;
    private boolean available;

    @OneToMany(mappedBy = "printout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans;
}
