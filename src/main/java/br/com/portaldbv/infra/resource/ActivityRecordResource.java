package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.dto.ActivityPointsMetricsDTO;
import br.com.portaldbv.infra.dto.activity.record.ActivityRecordRequestDTO;
import br.com.portaldbv.infra.dto.activity.record.ActivityRecordResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "ActivityRecord", description = "Resource responsável por gerenciar os registros de atividades (ActivityRecords).")
@RequestMapping("/activity-records")
public interface ActivityRecordResource {

    @Operation(
            summary = "Busca um ActivityRecord pelo ID",
            description = "Retorna os detalhes de um registro de atividade com base no identificador único fornecido."
    )
    @GetMapping("/{id}")
    ResponseEntity<ActivityRecordResponseDTO> getById(
            @Parameter(description = "UUID do ActivityRecord a ser consultado.", required = true)
            @PathVariable Long id
    );

    @Operation(
            summary = "Busca todos os ActivityRecords associadas a uma unidade",
            description = "Retorna uma lista de registros associados ao ID de uma unidade."
    )
    @GetMapping("/unit/{unitId}")
    ResponseEntity<List<ActivityRecordResponseDTO>> getRecordsByUnitId(
            @Parameter(description = "ID da unidade cujos registros devem ser listados.", required = true)
            @PathVariable Long unitId
    );

    @Operation(
            summary = "Busca o total de pontos separado por unidades",
            description = "Retorna uma lista de registros associados ao ID de uma unidade."
    )
    @GetMapping("/points")
    ResponseEntity<List<ActivityPointsMetricsDTO>> getTotalPoints(
            @Parameter(description = "ID da unidade cujos registros devem ser listados.", required = true)
            @RequestParam("clubId") Long clubId
    );

    @Operation(
            summary = "Busca o total de pontos separado por unidades",
            description = "Retorna uma lista de registros associados ao ID de uma unidade."
    )
    @GetMapping("/unit-points/{unitId}")
    ResponseEntity<ActivityPointsMetricsDTO> getTotalPointsByUnit(
            @Parameter(description = "ID da unidade cujos registros devem ser listados.", required = true)
            @PathVariable Long unitId
    );

    @Operation(
            summary = "Registra um novo ActivityRecord",
            description = "Cria um novo registro de atividade, vinculado a uma atividade e aos participantes."
    )
    @PostMapping
    ResponseEntity<ActivityRecordResponseDTO> register(
            @Parameter(description = "Dados do novo ActivityRecord a ser criado.", required = true)
            @RequestBody ActivityRecordRequestDTO request,

            @Parameter(description = "ID da unidade à qual o registro será associado.", required = true)
            @RequestParam Long unitId,

            @Parameter(description = "ID da atividade à qual o registro será associado.", required = true)
            @RequestParam Long activityId,

            @Parameter(description = "ID do usuário responsável pelo registro da atividade.")
            @RequestParam UUID registeredByUser
    );

    @Operation(
            summary = "Atualiza um ActivityRecord existente",
            description = "Atualiza os detalhes de um registro de atividade com base no identificador único fornecido."
    )
    @PutMapping("/{id}")
    ResponseEntity<ActivityRecordResponseDTO> update(
            @Parameter(description = "UUID do ActivityRecord a ser atualizado.", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Dados atualizados do ActivityRecord.", required = true)
            @RequestBody ActivityRecordRequestDTO request
    );

    @Operation(
            summary = "Deleta um ActivityRecord",
            description = "Remove um registro de atividade do sistema com base no identificador único fornecido."
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "UUID do ActivityRecord a ser excluído.", required = true)
            @PathVariable Long id
    );
}