package com.example.Pubquizspringboot.repository;

import com.example.Pubquizspringboot.model.Kviz;
import com.example.Pubquizspringboot.model.Pitanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PitanjeRepository extends JpaRepository<Pitanje,Integer> {

    List<Pitanje> findAllByKviz(Kviz kviz);
}
