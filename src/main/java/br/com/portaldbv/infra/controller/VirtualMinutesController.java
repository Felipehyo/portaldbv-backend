package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.VirtualMinutesUseCases;
import br.com.portaldbv.infra.dto.virtualminutes.VirtualMinutesRequestDTO;
import br.com.portaldbv.infra.mapper.VirtualMinutesMapper;
import br.com.portaldbv.infra.resource.VirtualMinutesResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VirtualMinutesController implements VirtualMinutesResource {

    private final VirtualMinutesUseCases useCases;
    private final VirtualMinutesMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResponseEntity<Object> registerSecretaria(
            MultipartFile[] files,
            String minutesRequestJson,
            Long unitId,
            UUID userId
    ) throws JsonProcessingException {
        VirtualMinutesRequestDTO request = objectMapper.readValue(minutesRequestJson, VirtualMinutesRequestDTO.class);

        // Converter array para lista, filtrando arquivos nulos ou vazios
        List<MultipartFile> images = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    images.add(file);
                }
            }
        }

        var domain = useCases.registerSecretaria(mapper.toDomain(request), unitId, userId, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(domain));
    }

    @Override
    public ResponseEntity<Object> registerCapelania(VirtualMinutesRequestDTO request, Long unitId, UUID userId) {
        var domain = useCases.registerCapelania(mapper.toDomain(request), unitId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(domain));
    }

    @Override
    public ResponseEntity<Object> getByUnitAndDate(Long unitId, LocalDate date) {
        var virtualMinutes = useCases.getByUnitAndDate(unitId, date);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTOList(virtualMinutes));
    }

    @Override
    public ResponseEntity<Object> getAllByUnitId(Long unitId, Boolean onlyActives) {
        var virtualMinutes = useCases.getAllByUnit(unitId, onlyActives);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTOList(virtualMinutes));
    }

    @Override
    public ResponseEntity<Object> getAllByUnitIdAndDateBetween(Long unitId, LocalDate initialDate, LocalDate finalDate) {
        var virtualMinutes = useCases.getAllByUnitAndDateBetween(unitId, initialDate, finalDate);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTOList(virtualMinutes));
    }

    @Override
    public ResponseEntity<Object> getById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTO(useCases.getById(id)));
    }


    @Override
    public ResponseEntity<Object> delete(Long id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> status(Long id, Boolean activate) {
        useCases.activeOrInactive(id, activate);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

