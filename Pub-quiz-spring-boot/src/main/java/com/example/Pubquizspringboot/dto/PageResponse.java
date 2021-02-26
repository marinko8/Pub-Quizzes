package com.example.Pubquizspringboot.dto;

import com.example.Pubquizspringboot.model.Kviz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageResponse {

    private List<Kviz> kvizovi;

    private Long ukupno;
}
