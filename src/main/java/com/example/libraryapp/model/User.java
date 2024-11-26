package com.example.libraryapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userid_seq")
    @SequenceGenerator(name = "userid_seq", sequenceName = "userid_seq", allocationSize = 1)
    private Integer id;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String surname;

    @Column(length = 40, name = "email")
    private String username;
    private String password;
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loanList;

}
