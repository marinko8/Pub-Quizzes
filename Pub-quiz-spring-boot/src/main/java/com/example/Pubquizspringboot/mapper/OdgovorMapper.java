package com.example.Pubquizspringboot.mapper;

import com.example.Pubquizspringboot.dto.OdgovorDto;
import com.example.Pubquizspringboot.model.Odgovor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OdgovorMapper {

    @Mapping(target = "pitanjeId", expression = "java(odgovor.getPitanje().getId())")
    OdgovorDto mapToDto(Odgovor odgovor);
}
