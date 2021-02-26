package com.example.Pubquizspringboot.controller;

import com.example.Pubquizspringboot.dto.AuthenticationResponse;
import com.example.Pubquizspringboot.dto.LoginRequest;
import com.example.Pubquizspringboot.dto.RefreshTokenRequest;
import com.example.Pubquizspringboot.dto.RegisterRequest;
import com.example.Pubquizspringboot.service.AuthService;
import com.example.Pubquizspringboot.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signUp(registerRequest);
        return new ResponseEntity<>("Korisnik uspješno registriran",
                OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verificirajAccount(@PathVariable String token) {
        authService.verificirajAccount(token);
        return new ResponseEntity<>("Uspjesno ste verfificirali korisnicki racun", OK);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh token izbrisan");
    }
}
