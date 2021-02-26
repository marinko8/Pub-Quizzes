package com.example.Pubquizspringboot.service;

import com.example.Pubquizspringboot.exeptions.PubQuizExeption;
import com.example.Pubquizspringboot.model.Korisnik;
import com.example.Pubquizspringboot.repository.KorisnikRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private KorisnikRepository korisnikRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<Korisnik> userOptional = korisnikRepository.findByUsername(username);
        Korisnik korisnik = userOptional
                .orElseThrow(() -> new PubQuizExeption("nema korisnika s tim username-om"));

        return new org.springframework.security
                .core.userdetails.User(korisnik.getUsername(), korisnik.getPassword(),
                korisnik.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
