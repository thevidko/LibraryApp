package com.example.libraryapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typeid_seq")
    @SequenceGenerator(name = "typeid_seq", sequenceName = "typeid_seq", allocationSize = 1)
    private Integer id;
    @Column(length = 50)
    private String name;
}
