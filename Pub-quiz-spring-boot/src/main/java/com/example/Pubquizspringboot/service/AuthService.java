package com.example.Pubquizspringboot.service;

import com.example.Pubquizspringboot.dto.AuthenticationResponse;
import com.example.Pubquizspringboot.dto.LoginRequest;
import com.example.Pubquizspringboot.dto.RefreshTokenRequest;
import com.example.Pubquizspringboot.dto.RegisterRequest;

public interface AuthService {
    void signUp(RegisterRequest requst);
    AuthenticationResponse login(LoginRequest loginRequest);
    void verificirajAccount(String token);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
