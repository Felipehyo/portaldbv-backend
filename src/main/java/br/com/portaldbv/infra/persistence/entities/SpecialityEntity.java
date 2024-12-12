package br.com.portaldbv.infra.persistence.entities;

import br.com.portaldbv.domain.enums.SpecialityCategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SPECIALITY")
public class SpecialityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;


    private String year;

    @NotNull
    private SpecialityCategoryEnum category;


    private String imageUrl;

}