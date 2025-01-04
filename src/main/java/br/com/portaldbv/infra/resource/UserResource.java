package br.com.portaldbv.infra.resource;

import br.com.portaldbv.domain.entities.User;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.domain.enums.constant.Errors;
import br.com.portaldbv.infra.dto.ErrorDTO;
import br.com.portaldbv.infra.dto.request.user.UserRequestDTO;
import br.com.portaldbv.infra.dto.request.AmountRequest;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuário", description = "Recursos relacionados a usuários")
public interface UserResource {

    @Operation(summary = "Buscar todos por clube", method = "GET", description = "Recurso para consultar todos os usuários por clube")
    @ApiResponses(value = {
            @ApiResponse(description = "Usuários encontrados", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAllByClubId(@RequestParam(value = "clubId") Long clubId, @RequestParam(value = "onlyActives", required = false) Boolean onlyActives, @RequestParam(value = "onlyUsersWithCashValue", required = false) Boolean onlyUsersWithCashValue, @RequestParam(value = "type", required = false) List<UserTypeEnum> userTypeList);

    @Operation(summary = "Buscar por id", method = "GET", description = "Recurso para consultar usuário por id")
    @ApiResponses(value = {
            @ApiResponse(description = "Usuário encontrado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = Errors.USER_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id);

    @Operation(summary = "Cadastrar usuário", method = "POST", description = "Recurso para cadastrar usuários")
    @ApiResponses(value = {
            @ApiResponse(description = "Usuário cadastrado com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = Errors.USER_ALREADY_REGISTERED, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> register(@RequestParam(value = "clubId") Long clubId, @RequestBody UserRequestDTO userRequest) throws JsonProcessingException;

    @Operation(summary = "Alterar usuário", method = "PATCH", description = "Recurso para alterar usuários")
    @ApiResponses(value = {
            @ApiResponse(description = "Usuário alterado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = Errors.UNIT_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody UserRequestDTO userRequest) throws JsonProcessingException;

    @Operation(summary = "Depositar valor em caixa", method = "PATCH", description = "Recurso para depositar algum valor no caixa do usuário")
    @ApiResponses(value = {
            @ApiResponse(description = "Valor depositado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = Errors.USER_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> depositAmount(@PathVariable(value = "id") UUID id, @RequestBody AmountRequest amountRequest);

    @Operation(summary = "Sacar valor em caixa", method = "PATCH", description = "Recurso para sacar algum valor no caixa do usuário")
    @ApiResponses(value = {
            @ApiResponse(description = "Valor sacado com sucesso", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = Errors.USER_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PatchMapping(value = "/{id}/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> withdrawAmount(@PathVariable(value = "id") UUID id, @RequestBody AmountRequest amountRequest);

    @Operation(summary = "Deletar usuário", method = "DELETE", description = "Recurso para deletar usuários")
    @ApiResponses(value = {
            @ApiResponse(description = "Usuário deletado com sucesso", responseCode = "204", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = Errors.USER_ID_NOT_FOUND, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id);


}