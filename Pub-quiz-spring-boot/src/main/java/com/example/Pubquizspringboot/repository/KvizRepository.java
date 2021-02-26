package com.example.Pubquizspringboot.repository;

import com.example.Pubquizspringboot.model.Kategorija;
import com.example.Pubquizspringboot.model.Kviz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface KvizRepository extends JpaRepository<Kviz,Integer> {
    Page<Kviz> findAllByOrderByIdDesc(Pageable page);

    List<Kviz> findAllByIdNotInAndKategorijaOrderByIdDesc(List<Integer> id,Kategorija kategorija, Pageable page);
    Long countAllByIdNotInAndKategorija(List<Integer> id,Kategorija kategorija);

    List<Kviz> findAllByKategorijaOrderByIdDesc(Kategorija kategorija, Pageable page);
    Long countAllByKategorija(Kategorija kategorija);

    List<Kviz> findAllByIdNotInOrderByIdDesc(List<Integer> id,Pageable page);
    Long countAllByIdNotIn(List<Integer> id);

    List<Kviz> findAllByAutorOrderByIdDesc(String username, Pageable page);

    Long countAllByAutor(String username);
}
