package com.example.libraryapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_id_seq")
    @SequenceGenerator(name = "books_id_seq", sequenceName = "books_id_seq", allocationSize = 1)
    private Integer id;
    @Column(length = 50)
    private String title;

    @ManyToOne
    @JoinColumn(name = "Author", nullable = false)
    private Author authorId;
    private Integer genre;
    private Integer publisher;
    @Column(name = "releasedate")
    private Date releaseDate;
    private Integer type;
    @Column(length = 150)
    private String cover;
    private Character section;
}
