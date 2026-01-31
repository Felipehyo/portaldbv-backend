package br.com.portaldbv.infra.resource;

import br.com.portaldbv.infra.dto.activity.ActivityRequestDTO;
import br.com.portaldbv.infra.dto.activity.ActivityResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Activity", description = "Resource responsável por gerenciar atividades (Activities).")
@RequestMapping("/activities")
public interface ActivityResource {

    @Operation(
            summary = "Busca uma Activity pelo ID",
            description = "Retorna os detalhes de uma atividade com base no identificador único fornecido."
    )
    @GetMapping("/{id}")
    ResponseEntity<ActivityResponseDTO> getById(
            @Parameter(description = "Id da Activity a ser consultada.", required = true)
            @PathVariable Long id
    );

//    @Operation(
//            summary = "Busca todas as Activities",
//            description = "Retorna uma lista de todas as atividades disponíveis."
//    )
//    @GetMapping
//    ResponseEntity<List<ActivityResponseDTO>> getAll();

    @Operation(
            summary = "Busca todas as Activities de um clube",
            description = "Retorna uma lista de atividades associadas ao ID do clube fornecido."
    )
    @GetMapping("/club/{clubId}")
    ResponseEntity<List<ActivityResponseDTO>> getAllByClubId(
            @Parameter(description = "ID do clube cujas atividades devem ser listadas.", required = true)
            @PathVariable Long clubId
    );

    @Operation(
            summary = "Busca todas as atividades ativas para o dia atual de uma unidade",
            description = "Retorna uma lista de atividades associadas ao ID da unidade fornecido."
    )
    @GetMapping("/club/{clubId}/unit/{unitId}")
    ResponseEntity<List<ActivityResponseDTO>> getDiaryActivitiesByUnitId(
            @Parameter(description = "ID do clube cujas atividades devem ser listadas.", required = true)
            @PathVariable Long clubId,
            @Parameter(description = "ID da unidade.", required = true)
            @PathVariable Long unitId
    );

    @Operation(
            summary = "Registra uma nova Activity",
            description = "Cria uma nova atividade vinculada a um clube e, opcionalmente, a outros atributos."
    )
    @PostMapping
    ResponseEntity<ActivityResponseDTO> register(
            @Parameter(description = "Dados da nova Activity a ser criada.", required = true)
            @RequestBody ActivityRequestDTO request,

            @Parameter(description = "ID do clube ao qual a atividade será associada.", required = true)
            @RequestParam Long clubId
    );

    @Operation(
            summary = "Atualiza uma Activity existente",
            description = "Atualiza os detalhes de uma atividade com base no identificador único fornecido."
    )
    @PutMapping("/{id}")
    ResponseEntity<ActivityResponseDTO> update(
            @Parameter(description = "UUID da Activity a ser atualizada.", required = true)
            @PathVariable Long id,

            @Parameter(description = "Dados atualizados da Activity.", required = true)
            @RequestBody ActivityRequestDTO request
    );

    @Operation(
            summary = "Deleta uma Activity",
            description = "Remove uma Activity do sistema com base no identificador único fornecido."
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "Id da Activity a ser excluída.", required = true)
            @PathVariable Long id
    );
}