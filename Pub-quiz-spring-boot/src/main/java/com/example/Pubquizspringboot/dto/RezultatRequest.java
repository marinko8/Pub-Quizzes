package com.example.Pubquizspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RezultatRequest {
    Integer idKviza;
    String username;
    Double bodovi;
    Integer tezina;
    Integer ocjena;
}
