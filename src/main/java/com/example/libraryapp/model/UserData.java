package com.example.libraryapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "userdata")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userdataid_seq")
    @SequenceGenerator(name = "userdataid_seq", sequenceName = "userdataid_seq", allocationSize = 1)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;

    @OneToOne(mappedBy = "userData") // Nevlastník vztahu, odkazuje na 'userData' v User
    private User user;
}
