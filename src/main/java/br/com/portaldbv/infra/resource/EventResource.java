package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.domain.enums.constant.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.club.BankRequestDTO;
import br.com.portaldbv.infra.dto.event.EventRequestDTO;
import br.com.portaldbv.infra.dto.event.UserRegisterDTO;
import br.com.portaldbv.infra.dto.event.EventResponseDTO;
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
@RequestMapping("/event")
@Tag(name = "Evento", description = "Recursos relacionados ao evento")
public interface EventResource {

    @Operation(summary = "Buscar todos", method = "GET", description = "Recurso para consultar todos")
    @ApiResponses(value = {
            @ApiResponse(description = "Recursos encontrados", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllByClubId(@RequestParam(value = "clubId") Long clubId, @RequestParam(value = "onlyActives", required = false) Boolean onlyActives);

    @Operation(summary = "Buscar por id", method = "GET", description = "Recurso para consultar por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Cliente encontrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = Errors.CLUB_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getById(@PathVariable(value = "id") Long id);

    @Operation(summary = "Cadastrar", method = "POST", description = "Recurso para cadastrar")
    @ApiResponses(value = {
            @ApiResponse(description = "Clube cadastrado com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = Errors.CLUB_ALREADY_REGISTERED_MESSAGE, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> register(@RequestBody EventRequestDTO request, @RequestParam(value = "clubId") Long clubId);

    @Operation(summary = "Alterar", method = "PATCH", description = "Recurso para alterar")
    @ApiResponses(value = {
            @ApiResponse(description = "Clube alterado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = Errors.EVENT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody EventRequestDTO club);

    @Operation(summary = "Deleta", method = "DELETE", description = "Recurso para deletar")
    @ApiResponses(value = {
            @ApiResponse(description = "Clube deletado com sucesso", responseCode = "204", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = Errors.EVENT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") Long id);

    @Operation(summary = "Depositar valor", method = "POST", description = "Recurso para depositar um valor no caixa")
    @ApiResponses(value = {
            @ApiResponse(description = "Valor depositado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = Errors.EVENT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(value = "/{id}/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> deposit(@PathVariable(value = "id") Long id, @RequestBody BankRequestDTO bankRequest);

    @Operation(summary = "Sacar valor", method = "POST", description = "Recurso para sacar/retirar um valor no caixa")
    @ApiResponses(value = {
            @ApiResponse(description = "Valor sacado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = Errors.EVENT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(value = "/{id}/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> withdraw(@PathVariable(value = "id") Long id, @RequestBody BankRequestDTO bankRequest);

    @Operation(summary = "Alterar status", method = "POST", description = "Recurso para ativar ou inativar")
    @ApiResponses(value = {
            @ApiResponse(description = "Status alterado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = Errors.EVENT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> status(@PathVariable(value = "id") Long id, @RequestParam(value = "active") Boolean activate);

    @Operation(summary = "Registrar usuário em evento", method = "POST", description = "Recurso para registrar ou remover usuário em evento")
    @ApiResponses(value = {
            @ApiResponse(description = "Registrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = Errors.EVENT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(value = "/{id}/register", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> registerUserInEvent(@PathVariable(value = "id") Long id, @RequestBody UserRegisterDTO userRegisterDTO);

    @Operation(summary = "Consultar inscrições por evento", method = "POST", description = "Recurso para consultar inscrições pelo evento")
    @ApiResponses(value = {
            @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = Errors.EVENT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}/register", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllRegistersByEvent(@PathVariable(value = "id") Long id, @RequestParam(value = "type", required = false) List<UserTypeEnum> userTypeList);

    @Operation(summary = "Consultar usuários e retornar inscrito / não inscrito", method = "POST", description = "Recurso para consultar usuários ativos, e verificar se estão inscritos ou não inscritos, para que o usuário final consiga discernir")
    @ApiResponses(value = {
            @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = Errors.EVENT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}/subscribes", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getSubscribeList(@PathVariable(value = "id") Long id);

}