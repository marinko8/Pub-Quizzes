package com.example.Pubquizspringboot.repository;

import com.example.Pubquizspringboot.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik,Integer> {
    Optional<Korisnik> findByUsername(String username);

    Optional<Korisnik> findById(Integer id);
}
