package com.example.Pubquizspringboot.dto;

import com.example.Pubquizspringboot.dto.OdgovorDto;
import com.example.Pubquizspringboot.dto.PitanjeDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PitanjeOdgovorResponse {
    private PitanjeDto pitanje;
    private List<OdgovorDto> odgovori;
}
