package br.com.portaldbv.infra.resource;

import br.com.portaldbv.infra.dto.cashbook.CashBookRequestDTO;
import br.com.portaldbv.infra.dto.cashbook.CashBookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "CashBook", description = "Resource responsável por gerenciar registros financeiros (CashBooks).")
@RequestMapping("/cashbooks")
public interface CashBookResource {

    @Operation(
            summary = "Busca um CashBook pelo ID",
            description = "Retorna os detalhes de um CashBook com base no identificador único fornecido."
    )
    @GetMapping("/{id}")
    ResponseEntity<CashBookResponseDTO> getById(
            @Parameter(description = "UUID do CashBook a ser consultado.", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Busca todos os CashBooks de um clube",
            description = "Retorna uma lista de todos os CashBooks associados ao ID do clube fornecido."
    )
    @GetMapping("/club/{clubId}")
    ResponseEntity<List<CashBookResponseDTO>> getAllByClubId(
            @Parameter(description = "ID do clube cujos registros financeiros devem ser listados.", required = true)
            @PathVariable Long clubId,
            @RequestParam(value = "eventId", required = false) Long eventId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(size = 24) Pageable pageable
    );

    @Operation(
            summary = "Registra um novo CashBook",
            description = "Cria um novo registro financeiro, vinculado a um clube e, opcionalmente, a um evento."
    )
    @PostMapping
    ResponseEntity<CashBookResponseDTO> register(
            @Parameter(description = "Dados do novo CashBook a ser registrado.", required = true)
            @RequestBody CashBookRequestDTO request,

            @Parameter(description = "ID do clube ao qual o CashBook será associado.", required = true)
            @RequestParam Long clubId,

            @Parameter(description = "ID do evento ao qual o CashBook será opcionalmente associado.")
            @RequestParam(required = false) Long eventId
    );

    @Operation(
            summary = "Atualiza um CashBook existente",
            description = "Atualiza os detalhes de um CashBook com base no identificador único fornecido."
    )
    @PutMapping("/{id}")
    ResponseEntity<CashBookResponseDTO> update(
            @Parameter(description = "UUID do CashBook a ser atualizado.", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Dados atualizados do CashBook.", required = true)
            @RequestBody CashBookRequestDTO request,

            @Parameter(description = "ID do evento ao qual o CashBook será atualizado.")
            @RequestParam(required = false) Long eventId
    );

    @Operation(
            summary = "Deleta um CashBook",
            description = "Remove um CashBook do sistema com base no identificador único fornecido."
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "UUID do CashBook a ser excluído.", required = true)
            @PathVariable UUID id
    );
}