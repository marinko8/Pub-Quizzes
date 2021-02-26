package com.example.Pubquizspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "kviz")
public class Kviz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String autor;

    private String naziv;

    private Integer broj_pitanja;

    private Integer zbroj_ocjena;

    private Integer broj_ocjena;

    private Integer zbroj_ocjena_tezine;

    private Integer broj_ocjena_tezine;

    private Integer broj_rjesenja;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kategorije", referencedColumnName = "id")
    private Kategorija kategorija;
}
