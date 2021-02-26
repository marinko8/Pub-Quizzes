package com.example.Pubquizspringboot.mapper;

import com.example.Pubquizspringboot.dto.PitanjeDto;
import com.example.Pubquizspringboot.model.Pitanje;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface PitanjeMapper {
    @Mapping(target = "kvizId", expression = "java(pitanje.getKviz().getId())")
    PitanjeDto mapToDto(Pitanje pitanje);
}
