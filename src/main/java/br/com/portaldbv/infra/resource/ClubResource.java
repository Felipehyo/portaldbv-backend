package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.domain.enums.error.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.request.ClubRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/club")
@Tag(name = "Clube", description = "Recursos relacionados ao clube")
public interface ClubResource {

    @Operation(summary = "Cadastrar clube", method = "POST", description = "Recurso para cadastrar clube")
    @ApiResponses(value = {
            @ApiResponse(description = "Clube cadastrado com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "400", description = Errors.CLUB_ALREADY_REGISTERED_MESSAGE, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> register(@RequestBody ClubRequestDTO club);

    @Operation(summary = "Buscar clube por id", method = "GET", description = "Recurso para consultar cliente por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Cliente encontrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getById(@PathVariable(value = "id") Long id);

    @Operation(summary = "Buscar clube por nome", method = "GET", description = "Recurso para consultar clube pelo nome")
    @ApiResponses(value = {
            @ApiResponse(description = "Cliente encontrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_NAME_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getByName(@RequestParam(value = "name") String name);

}