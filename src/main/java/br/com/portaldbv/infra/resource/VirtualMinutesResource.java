package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.enums.constant.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.virtualminutes.VirtualMinutesRequestDTO;
import br.com.portaldbv.infra.dto.virtualminutes.VirtualMinutesResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/virtual-minutes")
@Tag(name = "Ata Virtual", description = "Recursos relacionados às atas virtuais de secretaria e capelania")
public interface VirtualMinutesResource {

    @Operation(summary = "Cadastrar ata de secretaria com fotos", method = "POST",
               description = "Recurso para cadastrar ata de secretaria com até 3 fotos. Só pode haver 1 ata de secretaria por unidade por dia.")
    @ApiResponses(value = {
            @ApiResponse(description = "Ata de secretaria cadastrada com sucesso", responseCode = "201",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = VirtualMinutesResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Já existe uma ata de secretaria para esta unidade nesta data ou máximo de imagens excedido",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(value = "/secretaria", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> registerSecretaria(
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam("minutesRequest") String minutesRequestJson,
            @RequestParam(value = "unitId") Long unitId,
            @RequestParam(value = "userId") UUID userId
    ) throws JsonProcessingException;

    @Operation(summary = "Cadastrar ata de capelania", method = "POST",
               description = "Recurso para cadastrar ata de capelania (sem fotos). Só pode haver 1 ata de capelania por unidade por dia.")
    @ApiResponses(value = {
            @ApiResponse(description = "Ata de capelania cadastrada com sucesso", responseCode = "201",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = VirtualMinutesResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Já existe uma ata de capelania para esta unidade nesta data",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(value = "/capelania", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> registerCapelania(
            @RequestBody VirtualMinutesRequestDTO request,
            @RequestParam(value = "unitId") Long unitId,
            @RequestParam(value = "userId") UUID userId
    );

    @Operation(summary = "Buscar atas por data específica", method = "GET",
               description = "Recupera as atas (secretaria e capelania) e links das imagens no S3 para uma unidade em uma data específica")
    @ApiResponses(value = {
            @ApiResponse(description = "Atas encontradas", responseCode = "200",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(value = "/by-date", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getByUnitAndDate(
            @RequestParam(value = "unitId") Long unitId,
            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    );

    @Operation(summary = "Buscar todas as atas de uma unidade", method = "GET",
               description = "Recurso para consultar todas as atas de uma unidade específica")
    @ApiResponses(value = {
            @ApiResponse(description = "Recursos encontrados", responseCode = "200",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllByUnitId(
            @RequestParam(value = "unitId") Long unitId,
            @RequestParam(value = "onlyActives", required = false) Boolean onlyActives
    );

    @Operation(summary = "Buscar atas por período", method = "GET",
               description = "Recurso para consultar atas por período de datas")
    @ApiResponses(value = {
            @ApiResponse(description = "Recursos encontrados", responseCode = "200",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(value = "/by-period", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllByUnitIdAndDateBetween(
            @RequestParam(value = "unitId") Long unitId,
            @RequestParam(value = "initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initialDate,
            @RequestParam(value = "finalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finalDate
    );

    @Operation(summary = "Buscar ata por id", method = "GET",
               description = "Recurso para consultar ata por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Ata encontrada com sucesso", responseCode = "200",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = VirtualMinutesResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = Errors.VIRTUAL_MINUTES_ID_NOT_FOUND,
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getById(@PathVariable(value = "id") Long id);

    @Operation(summary = "Deletar ata virtual", method = "DELETE",
               description = "Recurso para deletar uma ata (remove também as imagens do S3)")
    @ApiResponses(value = {
            @ApiResponse(description = "Ata deletada com sucesso", responseCode = "204", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Ata não encontrada",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") Long id);

    @Operation(summary = "Alterar status da ata", method = "PATCH",
               description = "Recurso para ativar ou inativar uma ata")
    @ApiResponses(value = {
            @ApiResponse(description = "Status alterado com sucesso", responseCode = "204", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Ata não encontrada",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> status(@PathVariable(value = "id") Long id, @RequestParam(value = "active") Boolean activate);

}

