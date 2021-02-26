package com.example.Pubquizspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifikacijskiEmail {
    private String predmet;
    private String primatelj;
    private String sadrzaj;
}
