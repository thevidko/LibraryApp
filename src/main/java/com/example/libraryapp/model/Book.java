package com.example.libraryapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    private Author author;
    @ManyToOne
    @JoinColumn(name = "Genre", nullable = false)
    private Genre genre;
    @ManyToOne
    @JoinColumn(name = "Publisher", nullable = false)
    private Publisher publisher;
    @Column(name = "releasedate")
    private Date releaseDate;
    @ManyToOne
    @JoinColumn(name = "type",nullable = false)
    private Type type;
    @Column(length = 150)
    private String cover;
    private Character section;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Printout> printouts = new ArrayList<>();

    public void addPrintout(Printout printout) {
        this.printouts.add(printout);
    }
}
