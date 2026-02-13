package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.VirtualMinutesRepositoryGateway;
import br.com.portaldbv.domain.entities.VirtualMinutes;
import br.com.portaldbv.domain.enums.MinutesTypeEnum;
import br.com.portaldbv.domain.enums.constant.AwsConstants;
import br.com.portaldbv.domain.enums.error.VirtualMinutesErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class VirtualMinutesUseCases {

    private final VirtualMinutesRepositoryGateway repository;
    private final UnitUseCases unitUseCases;
    private final UserUseCases userUseCases;
    private final AwsS3UseCases awsS3UseCases;
    private final String s3BucketName;

    public List<VirtualMinutes> getAllByUnit(Long unitId, Boolean onlyActives) {
        return Optional.ofNullable(repository.getAllByUnitIdAndFilters(unitId, onlyActives))
                .orElseThrow(() -> new DomainException(VirtualMinutesErrorEnum.NOT_FOUND));
    }

    public List<VirtualMinutes> getAllByUnitAndDateBetween(Long unitId, LocalDate initialDate, LocalDate finalDate) {
        return Optional.ofNullable(repository.getByUnitIdAndDateBetween(unitId, initialDate, finalDate))
                .orElseThrow(() -> new DomainException(VirtualMinutesErrorEnum.NOT_FOUND));
    }

    public List<VirtualMinutes> getByUnitAndDate(Long unitId, LocalDate date) {
        return Optional.ofNullable(repository.getByUnitIdAndDate(unitId, date))
                .orElseThrow(() -> new DomainException(VirtualMinutesErrorEnum.NOT_FOUND));
    }

    public VirtualMinutes getById(Long id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(VirtualMinutesErrorEnum.ID_NOT_FOUND));
    }

    public VirtualMinutes registerSecretaria(VirtualMinutes virtualMinutes, Long unitId, UUID userId, List<MultipartFile> images) {
        // Validar unidade e usuário
        virtualMinutes.setUnit(unitUseCases.getById(unitId));
        virtualMinutes.setCreatedBy(userUseCases.getById(userId));
        virtualMinutes.setType(MinutesTypeEnum.SECRETARIA);

        // Verificar se já existe ata de secretaria para esta unidade nesta data
        VirtualMinutes existing = repository.findByUnitIdAndDateAndType(unitId, virtualMinutes.getDate(), MinutesTypeEnum.SECRETARIA);
        if (existing != null) {
            throw new DomainException(VirtualMinutesErrorEnum.ALREADY_REGISTERED);
        }

        // Validar e fazer upload das imagens (máximo 3)
        if (images != null && !images.isEmpty()) {
            if (images.size() > 3) {
                throw new DomainException(VirtualMinutesErrorEnum.MAX_IMAGES_EXCEEDED);
            }

            List<String> imageLinks = new ArrayList<>();
            for (MultipartFile image : images) {
                if (image != null && !image.isEmpty()) {
                    String imageUrl = awsS3UseCases.saveFile(image, AwsConstants.S3_PATH_VIRTUAL_MINUTES, s3BucketName);
                    imageLinks.add(imageUrl);
                }
            }
            virtualMinutes.setImageLinks(imageLinks);
        }

        virtualMinutes.setActive(Boolean.TRUE);
        virtualMinutes.setCreatedAt(LocalDateTime.now());
        virtualMinutes.setUpdatedAt(LocalDateTime.now());

        return repository.register(virtualMinutes);
    }

    public VirtualMinutes registerCapelania(VirtualMinutes virtualMinutes, Long unitId, UUID userId) {
        // Validar unidade e usuário
        virtualMinutes.setUnit(unitUseCases.getById(unitId));
        virtualMinutes.setCreatedBy(userUseCases.getById(userId));
        virtualMinutes.setType(MinutesTypeEnum.CAPELANIA);

        // Verificar se já existe ata de capelania para esta unidade nesta data
        VirtualMinutes existing = repository.findByUnitIdAndDateAndType(unitId, virtualMinutes.getDate(), MinutesTypeEnum.CAPELANIA);
        if (existing != null) {
            throw new DomainException(VirtualMinutesErrorEnum.ALREADY_REGISTERED);
        }

        // Capelania não possui imagens
        virtualMinutes.setImageLinks(null);
        virtualMinutes.setActive(Boolean.TRUE);
        virtualMinutes.setCreatedAt(LocalDateTime.now());
        virtualMinutes.setUpdatedAt(LocalDateTime.now());

        return repository.register(virtualMinutes);
    }

    public VirtualMinutes update(Long id, VirtualMinutes virtualMinutes, List<MultipartFile> images) {
        var oldVirtualMinutes = getById(id);

        oldVirtualMinutes.setDescription(virtualMinutes.getDescription());
        oldVirtualMinutes.setDate(virtualMinutes.getDate());

        // Atualizar imagens apenas se for secretaria
        if (oldVirtualMinutes.getType() == MinutesTypeEnum.SECRETARIA && images != null && !images.isEmpty()) {
            if (images.size() > 3) {
                throw new DomainException(VirtualMinutesErrorEnum.MAX_IMAGES_EXCEEDED);
            }

            // Deletar imagens antigas
            if (oldVirtualMinutes.getImageLinks() != null) {
                for (String oldImage : oldVirtualMinutes.getImageLinks()) {
                    awsS3UseCases.deleteFile(oldImage, s3BucketName);
                }
            }

            // Upload novas imagens
            List<String> imageLinks = new ArrayList<>();
            for (MultipartFile image : images) {
                if (image != null && !image.isEmpty()) {
                    String imageUrl = awsS3UseCases.saveFile(image, AwsConstants.S3_PATH_VIRTUAL_MINUTES, s3BucketName);
                    imageLinks.add(imageUrl);
                }
            }
            oldVirtualMinutes.setImageLinks(imageLinks);
        }

        oldVirtualMinutes.setUpdatedAt(LocalDateTime.now());

        return repository.update(oldVirtualMinutes);
    }

    public void activeOrInactive(Long id, Boolean active) {
        var oldVirtualMinutes = getById(id);
        oldVirtualMinutes.setActive(active);
        oldVirtualMinutes.setUpdatedAt(LocalDateTime.now());
        repository.update(oldVirtualMinutes);
    }

    public void delete(Long id) {
        var virtualMinutes = getById(id);

        // Deletar imagens do S3 se existirem
        if (virtualMinutes.getImageLinks() != null && !virtualMinutes.getImageLinks().isEmpty()) {
            for (String imageLink : virtualMinutes.getImageLinks()) {
                awsS3UseCases.deleteFile(imageLink, s3BucketName);
            }
        }

        repository.delete(virtualMinutes);
    }

}

