package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.domain.enums.error.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.request.club.BankRequestDTO;
import br.com.portaldbv.infra.dto.request.club.ClubRequestDTO;
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

@RestController
@RequestMapping("/club")
@Tag(name = "Clube", description = "Recursos relacionados ao clube")
public interface ClubResource {

    @Operation(summary = "Buscar todos", method = "GET", description = "Recurso para consultar todos os clubes")
    @ApiResponses(value = {
            @ApiResponse(description = "Clubes encontrados", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAll();

    @Operation(summary = "Buscar clube por id", method = "GET", description = "Recurso para consultar cliente por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Cliente encontrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getById(@PathVariable(value = "id") Long id);

    @Operation(summary = "Cadastrar clube", method = "POST", description = "Recurso para cadastrar clube")
    @ApiResponses(value = {
            @ApiResponse(description = "Clube cadastrado com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "400", description = Errors.CLUB_ALREADY_REGISTERED_MESSAGE, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> register(@RequestBody ClubRequestDTO club);

    @Operation(summary = "Alterar clube", method = "PATCH", description = "Recurso para alterar clube")
    @ApiResponses(value = {
            @ApiResponse(description = "Clube alterado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody ClubRequestDTO club);

    @Operation(summary = "Deletar clube", method = "DELETE", description = "Recurso para deletar clube")
    @ApiResponses(value = {
            @ApiResponse(description = "Clube deletado com sucesso", responseCode = "204", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") Long id);

    @Operation(summary = "Depositar valor", method = "POST", description = "Recurso para depositar um valor no caixa do clube")
    @ApiResponses(value = {
            @ApiResponse(description = "Valor depositado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(value = "/{id}/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> deposit(@PathVariable(value = "id") Long id, @RequestBody BankRequestDTO bankRequest);

    @Operation(summary = "Sacar valor", method = "POST", description = "Recurso para sacar/retirar um valor no caixa do clube")
    @ApiResponses(value = {
            @ApiResponse(description = "Valor sacado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(value = "/{id}/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> withdraw(@PathVariable(value = "id") Long id, @RequestBody BankRequestDTO bankRequest);


}