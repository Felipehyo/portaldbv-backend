package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.entities.Kit;
import br.com.portaldbv.domain.enums.constant.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.request.kit.KitRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/kit")
@Tag(name = "Kit", description = "Recursos relacionados ao kit")
public interface KitResource {

    @Operation(summary = "Buscar kit por id", method = "GET", description = "Recurso para consultar kit por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Kit encontrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Kit.class))),
            @ApiResponse(responseCode = "404", description = Errors.KIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getById(@PathVariable(value = "id") Long id);

    @Operation(summary = "Buscar todos", method = "GET", description = "Recurso para consultar todos")
    @ApiResponses(value = {
            @ApiResponse(description = "Recursos encontrados", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllByUserId(@RequestParam(value = "userId") UUID userId);

    @Operation(summary = "Cadastrar kit", method = "POST", description = "Recurso para cadastrar kit")
    @ApiResponses(value = {
            @ApiResponse(description = "Kit cadastrado com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Kit.class))),
            @ApiResponse(responseCode = "400", description = Errors.KIT_ALREADY_REGISTERED_MESSAGE, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> register(@RequestParam(value = "userId") UUID userId, @RequestBody KitRequestDTO kit);

    @Operation(summary = "Metricas de kit por user", method = "GET", description = "Recurso para consultar métricas de kit por usuário")
    @ApiResponses(value = {
            @ApiResponse(description = "Métricas encontradas com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Kit.class))),
            @ApiResponse(responseCode = "404", description = Errors.KIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/user-metrics", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> metricsByUser(@RequestParam(value = "userId") UUID userId);

    @Operation(summary = "Metricas de kit", method = "GET", description = "Recurso para consultar métricas de kit")
    @ApiResponses(value = {
            @ApiResponse(description = "Métricas encontradas com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = Errors.KIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/club-metrics", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> allMetricsByClub(@RequestParam(value = "clubId") Long clubId);

    @Operation(summary = "Deletar kit", method = "DELETE", description = "Recurso para deletar kit")
    @ApiResponses(value = {
            @ApiResponse(description = "Clube deletado com sucesso", responseCode = "204", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = Errors.KIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") Long id);

}