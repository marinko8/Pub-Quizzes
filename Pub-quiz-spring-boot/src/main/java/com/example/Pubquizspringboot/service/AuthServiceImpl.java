package com.example.Pubquizspringboot.service;

import com.example.Pubquizspringboot.dto.AuthenticationResponse;
import com.example.Pubquizspringboot.dto.LoginRequest;
import com.example.Pubquizspringboot.dto.RefreshTokenRequest;
import com.example.Pubquizspringboot.dto.RegisterRequest;
import com.example.Pubquizspringboot.exeptions.PubQuizExeption;
import com.example.Pubquizspringboot.model.Korisnik;
import com.example.Pubquizspringboot.model.NotifikacijskiEmail;
import com.example.Pubquizspringboot.model.VerifikacijskiToken;
import com.example.Pubquizspringboot.repository.KorisnikRepository;
import com.example.Pubquizspringboot.repository.VerifikacijskiTokenRepository;
import com.example.Pubquizspringboot.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{

    @Autowired
    KorisnikRepository korisnikRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;
    private VerifikacijskiTokenRepository verifikacijskiTokenRepository;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private RefreshTokenService refreshTokenService;

    @Override
    public void signUp(RegisterRequest requst) {
        Korisnik korisnik = new Korisnik();
        korisnik.setUsername(requst.getUsername());
        korisnik.setEmail(requst.getEmail());
        korisnik.setPassword(passwordEncoder.encode(requst.getPassword()));
        korisnik.setBodovi((double) 0);
        korisnik.setEnabled(false);


        korisnikRepository.save(korisnik);

        String token = generirajVerifikacijskiToken(korisnik);
        mailService.sendMail(new NotifikacijskiEmail("Molimo aktivirajte svoj račun",
                korisnik.getEmail(), "Hvala što ste se registrirali na Pub quizzes, " +
                "uđite na link ispod kako biste završili registraciju : " +
                "http://localhost:8080/" + token));
    }

    @Transactional(readOnly = true)
    public Korisnik getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return korisnikRepository.findByUsername(principal.getUsername())
                .orElseThrow();
    }

    private void fetchUserAndEnable(VerifikacijskiToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        Korisnik user = korisnikRepository
                .findByUsername(username)
                .orElseThrow(() -> new PubQuizExeption("Korisnik nije pronaden - " + username));
        user.setEnabled(true);
        korisnikRepository.save(user);
    }

    private String generirajVerifikacijskiToken(Korisnik korisnik) {
        String token = UUID.randomUUID().toString();
        VerifikacijskiToken verificationToken = new VerifikacijskiToken();
        verificationToken.setToken(token);
        verificationToken.setUser(korisnik);

        verifikacijskiTokenRepository.save(verificationToken);
        return token;
    }

    public void verificirajAccount(String token) {
        Optional<VerifikacijskiToken> verificationToken = verifikacijskiTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new PubQuizExeption("Token vije validan")));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
