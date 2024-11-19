package com.example.libraryapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
