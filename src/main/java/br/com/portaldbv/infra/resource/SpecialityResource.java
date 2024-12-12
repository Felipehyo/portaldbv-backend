package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.domain.enums.constant.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.request.speciality.SpecialityRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/speciality")
@Tag(name = "Especialidade", description = "Recursos relacionados a especialidades")
public interface SpecialityResource {

    @Operation(summary = "Buscar todas", method = "GET", description = "Recurso para consultar todos as especialidades")
    @ApiResponses(value = {
            @ApiResponse(description = "Especialidades encontradas", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAll(@RequestParam(value = "category", required = false) String category);

    @Operation(summary = "Buscar por id", method = "GET", description = "Recurso para consultar especialidades por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Especialidade encontrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getById(@PathVariable(value = "id") Long id);

    @Operation(summary = "Cadastrar especialidades", method = "POST", description = "Recurso para cadastrar especialidades")
    @ApiResponses(value = {
            @ApiResponse(description = "Especialidade cadastrado com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "400", description = Errors.CLUB_ALREADY_REGISTERED_MESSAGE, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> register(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("specialityRequest") String specialityRequest) throws JsonProcessingException;

    @Operation(summary = "Alterar especialidades", method = "PATCH", description = "Recurso para alterar especialidades")
    @ApiResponses(value = {
            @ApiResponse(description = "Especialidade alterado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Club.class))),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute("specialityRequest") SpecialityRequestDTO specialityRequest);

    @Operation(summary = "Deletar especialidades", method = "DELETE", description = "Recurso para deletar especialidades")
    @ApiResponses(value = {
            @ApiResponse(description = "Especialidade deletada com sucesso", responseCode = "204", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") Long id);


}