package com.example.Pubquizspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateKvizRequest {
    private String username;
    private String naziv;
    private String kategorija;
    private List<PitanjeRequest> pitanja;
}
