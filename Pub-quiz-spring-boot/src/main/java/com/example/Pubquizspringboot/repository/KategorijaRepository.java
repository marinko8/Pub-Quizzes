package com.example.Pubquizspringboot.repository;

import com.example.Pubquizspringboot.model.Kategorija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KategorijaRepository extends JpaRepository<Kategorija,Integer> {
    Optional<Kategorija> findById(Integer id);
    Optional<Kategorija> findByNaziv(String naziv);
}
