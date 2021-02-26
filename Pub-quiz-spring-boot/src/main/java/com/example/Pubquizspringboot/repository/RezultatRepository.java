package com.example.Pubquizspringboot.repository;

import com.example.Pubquizspringboot.model.Korisnik;
import com.example.Pubquizspringboot.model.Kviz;
import com.example.Pubquizspringboot.model.Rezultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezultatRepository extends JpaRepository<Rezultat,Integer> {
    List<Rezultat> findAllByUsername(String username);
    Long countAllByUsername(String username);
    void deleteAllByKviz(Kviz kviz);
}
