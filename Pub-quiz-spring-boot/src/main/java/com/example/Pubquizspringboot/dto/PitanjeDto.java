package com.example.Pubquizspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PitanjeDto {
    private Integer id;
    private Integer kvizId;
    private String pitanje;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PitanjeDto that = (PitanjeDto) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getKvizId(), that.getKvizId()) &&
                Objects.equals(getPitanje(), that.getPitanje());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getKvizId(), getPitanje());
    }
}
