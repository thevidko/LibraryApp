package com.example.libraryapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "publisher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisherid_seq")
    @SequenceGenerator(name = "publisherid_seq", sequenceName = "publisherid_seq", allocationSize = 1)
    private Integer id;

    @Column(length = 50)
    private String name;
}
