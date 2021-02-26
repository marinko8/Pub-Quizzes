package com.example.Pubquizspringboot.repository;

import com.example.Pubquizspringboot.model.Odgovor;
import com.example.Pubquizspringboot.model.Pitanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OdgovorRepository extends JpaRepository<Odgovor,Integer> {
    List<Odgovor> findAllByPitanje(Pitanje pitanje);

    void deleteAllByPitanje(Pitanje pitanje);
}
