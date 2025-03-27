package br.com.portaldbv.domain.entities;

import br.com.portaldbv.domain.enums.PresenceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Presence {

    private Long id;
    private User user;
    private Kit kit;
    private LocalDate date;
    private LocalDateTime createdAt;
    private PresenceTypeEnum presenceType;
    private Club club;

}