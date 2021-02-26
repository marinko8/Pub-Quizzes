package com.example.Pubquizspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PitanjeRequest {
    private String naziv;
    private List<OdgovorRequest> odgovori;
}
