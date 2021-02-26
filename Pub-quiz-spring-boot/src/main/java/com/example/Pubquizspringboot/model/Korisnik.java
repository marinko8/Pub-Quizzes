package com.example.Pubquizspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "korisnik")
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotBlank(message = "E-mail je obavezan")
    private String email;

    @NotBlank(message = "Korisnicko ime je obavezan")
    private String username;

    @Column
    @NotBlank(message = "Lozinka je obavezan")
    private String password;

    @Column
    private Double bodovi;

    @Column
    private boolean enabled;
}
