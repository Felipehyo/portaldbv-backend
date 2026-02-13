package br.com.portaldbv.domain.entities;

import br.com.portaldbv.domain.enums.MinutesTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VirtualMinutes {

    private Long id;
    private MinutesTypeEnum type; // SECRETARIA ou CAPELANIA
    private LocalDate date; // Data da ata (sem hora)
    private String description; // Descrição da secretaria ou capelania
    private List<String> imageLinks; // Links das fotos no S3 (máximo 3 para secretaria)
    private Unit unit; // Vinculado à unidade
    private User createdBy; // Usuário que criou
    private LocalDateTime createdAt; // Data e hora de criação do registro
    private LocalDateTime updatedAt;
    private Boolean active;

}

