package com.example.Pubquizspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OdgovorDto {
    private Integer id;
    private Integer pitanjeId;
    private String odgovor;
    private Boolean tocno;
}
