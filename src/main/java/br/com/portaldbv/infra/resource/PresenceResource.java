package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.entities.Presence;
import br.com.portaldbv.domain.enums.constant.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.presence.PresenceRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/presence")
@Tag(name = "presence", description = "Recursos relacionados a presença")
public interface PresenceResource {

    @Operation(summary = "Buscar todos", method = "GET", description = "Recurso para consultar todos")
    @ApiResponses(value = {
            @ApiResponse(description = "Recursos encontrados", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllByClubIdAndUserId(@RequestParam(value = "clubId", required = false) Long clubId, @RequestParam(value = "userId", required = false) UUID userId);

    @Operation(summary = "Buscar por id", method = "GET", description = "Recurso para consultar por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Encontrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Presence.class))),
            @ApiResponse(responseCode = "404", description = Errors.KIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}/percentage", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllByClubIdWithPercentage(@PathVariable(value = "id") Long id);

    @Operation(summary = "Buscar todos", method = "GET", description = "Recurso para consultar todos")
    @ApiResponses(value = {
            @ApiResponse(description = "Recursos encontrados", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(value = "/day", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllByDay(@RequestParam(value = "clubId") Long clubId, @RequestParam(value = "day", required = false) LocalDate day);

    @Operation(summary = "Cadastrar presença", method = "POST", description = "Recurso para cadastrar presença")
    @ApiResponses(value = {
            @ApiResponse(description = "Presença cadastrada com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Presence.class))),
            @ApiResponse(responseCode = "400", description = Errors.KIT_ALREADY_REGISTERED_MESSAGE, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> register(@RequestParam(value = "userId") UUID userId, @RequestBody PresenceRequestDTO presence);

}