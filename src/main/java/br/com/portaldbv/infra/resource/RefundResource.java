package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.entities.Refund;
import br.com.portaldbv.domain.enums.constant.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.request.refund.RefundRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/refund")
@Tag(name = "Reembolsos", description = "Recursos relacionados aos reembolsos")
public interface RefundResource {

    @Operation(summary = "Buscar todos por clube", method = "GET", description = "Recurso para consultar todos por clube")
    @ApiResponses(value = {
            @ApiResponse(description = "Reembolsos encontrados", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAll(@RequestParam("clubId") Long clubId,
                                  @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                  @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                  @RequestParam(value = "userId", required = false) UUID userId,
                                  @RequestParam(value = "eventId", required = false) Long eventId,
                                  @PageableDefault(size = 24) Pageable pageable);

    @Operation(summary = "Buscar reembolso por id", method = "GET", description = "Recurso para consultar cliente por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Reembolso encontrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Refund.class))),
            @ApiResponse(responseCode = "404", description = Errors.PAYMENT_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getById(@PathVariable(value = "id") Long id);

    @Operation(summary = "Cadastrar reembolso", method = "POST", description = "Recurso para cadastrar reembolso")
    @ApiResponses(value = {
            @ApiResponse(description = "Clube cadastrado com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Refund.class))),
            @ApiResponse(responseCode = "400", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> register(@RequestBody RefundRequestDTO paymentRequest);

    @Operation(summary = "Deletar reembolso", method = "DELETE", description = "Recurso para deletar reembolso")
    @ApiResponses(value = {
            @ApiResponse(description = "Reembolso deletado com sucesso", responseCode = "204", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = Errors.PAYMENT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") Long id);

}