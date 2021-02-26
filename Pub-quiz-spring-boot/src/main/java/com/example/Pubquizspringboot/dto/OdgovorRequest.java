package com.example.Pubquizspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OdgovorRequest {
    private String naziv;
    private Boolean tocan;
}
