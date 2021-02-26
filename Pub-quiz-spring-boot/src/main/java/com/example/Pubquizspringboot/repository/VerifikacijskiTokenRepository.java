package com.example.Pubquizspringboot.repository;

import com.example.Pubquizspringboot.model.VerifikacijskiToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifikacijskiTokenRepository extends JpaRepository<VerifikacijskiToken,Long> {
    Optional<VerifikacijskiToken> findByToken(String token);
}
