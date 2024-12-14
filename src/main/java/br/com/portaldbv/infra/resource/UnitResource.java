package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.entities.Unit;
import br.com.portaldbv.domain.enums.constant.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.request.unit.UnitRequestDTO;
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
@RequestMapping("/unit")
@Tag(name = "Unidade", description = "Recursos relacionados a unidades")
public interface UnitResource {

    @Operation(summary = "Buscar todas por clube", method = "GET", description = "Recurso para consultar todos as unidades por clube")
    @ApiResponses(value = {
            @ApiResponse(description = "Unidades encontradas", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllByClubId(@RequestParam(value = "clubId") Long clubId);

    @Operation(summary = "Buscar por id", method = "GET", description = "Recurso para consultar unidades por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Unidade encontrada com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
            @ApiResponse(responseCode = "404", description = Errors.UNIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getById(@PathVariable(value = "id") Long id);

    @Operation(summary = "Cadastrar unidades", method = "POST", description = "Recurso para cadastrar unidades")
    @ApiResponses(value = {
            @ApiResponse(description = "Unidade cadastrada com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
            @ApiResponse(responseCode = "400", description = Errors.CLUB_ALREADY_REGISTERED_MESSAGE, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> register(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("unitRequest") String unitRequest) throws JsonProcessingException;

    @Operation(summary = "Alterar unidades", method = "PATCH", description = "Recurso para alterar unidades")
    @ApiResponses(value = {
            @ApiResponse(description = "Unidade alterada com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
            @ApiResponse(responseCode = "404", description = Errors.UNIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("unitRequest") String unitRequest) throws JsonProcessingException;

    @Operation(summary = "Adicionar pontos", method = "PATCH", description = "Recurso para adicionar pontos para a unidade")
    @ApiResponses(value = {
            @ApiResponse(description = "Pontos adicionados com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
            @ApiResponse(responseCode = "404", description = Errors.UNIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}/add/{qtdPoints}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> addPoints(@PathVariable(value = "id") Long id, @PathVariable(value = "qtdPoints") Integer qtdPoints);

    @Operation(summary = "Remover pontos", method = "PATCH", description = "Recurso para remover pontos para a unidade")
    @ApiResponses(value = {
            @ApiResponse(description = "Pontos removidos com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
            @ApiResponse(responseCode = "404", description = Errors.UNIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}/remove/{qtdPoints}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> removePoints(@PathVariable(value = "id") Long id, @PathVariable(value = "qtdPoints") Integer qtdPoints);

    @Operation(summary = "Adicionar pontos pendentes de entrega", method = "PATCH", description = "Recurso para adicionar pontos pendentes de entrega para a unidade")
    @ApiResponses(value = {
            @ApiResponse(description = "Pontos adicionados com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
            @ApiResponse(responseCode = "404", description = Errors.UNIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}/add-pendency/{qtdPoints}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> addPendencyPoints(@PathVariable(value = "id") Long id, @PathVariable(value = "qtdPoints") Integer qtdPoints);

    @Operation(summary = "Remover pontos pendentes de entrega", method = "PATCH", description = "Recurso para remover pontos pendentes de entrega para a unidade")
    @ApiResponses(value = {
            @ApiResponse(description = "Pontos removidos com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))),
            @ApiResponse(responseCode = "404", description = Errors.UNIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}/remove-pendency/{qtdPoints}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> removePendencyPoints(@PathVariable(value = "id") Long id, @PathVariable(value = "qtdPoints") Integer qtdPoints);

    @Operation(summary = "Deletar unidades", method = "DELETE", description = "Recurso para deletar unidades")
    @ApiResponses(value = {
            @ApiResponse(description = "Unidade deletada com sucesso", responseCode = "204", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = Errors.UNIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") Long id);


}