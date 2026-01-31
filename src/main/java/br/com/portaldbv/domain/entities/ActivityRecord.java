package br.com.portaldbv.domain.entities;

import br.com.portaldbv.domain.enums.RecordTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRecord {

    private Long id;
    private Unit unit;
    private Activity activity;
    private RecordTypeEnum type;
    private String title;
    private String reason;
    private LocalDateTime createdDate;
    private LocalDate date;
    private Integer points = 0;
    private User registeredByUser;

}