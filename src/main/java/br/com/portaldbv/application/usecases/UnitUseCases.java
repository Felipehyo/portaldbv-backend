package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.UnitRepositoryGateway;
import br.com.portaldbv.domain.entities.Unit;
import br.com.portaldbv.domain.enums.constant.AwsConstants;
import br.com.portaldbv.domain.enums.error.UnitErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public class UnitUseCases {

    private final UnitRepositoryGateway repository;
    private final AwsS3UseCases awsS3UseCases;
    private final String s3BucketName;

    public UnitUseCases(UnitRepositoryGateway repository, AwsS3UseCases awsS3UseCases, String s3BucketName) {
        this.repository = repository;
        this.awsS3UseCases = awsS3UseCases;
        this.s3BucketName = s3BucketName;
    }

    public List<Unit> getAllByClub(Long clubId) {
        return Optional.ofNullable(repository.getAllByClubId(clubId))
                .orElseThrow(() -> new DomainException(UnitErrorEnum.NAME_NOT_FOUND));
    }

    public Unit getById(Long id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(UnitErrorEnum.ID_NOT_FOUND));
    }

    public Unit register(Unit unit, MultipartFile multipartFile) {

        if (repository.getByClubIdAndName(unit.getClubId(), unit.getName()) != null) {
            throw new DomainException(UnitErrorEnum.ALREADY_REGISTERED);
        }

        unit.setActive(Boolean.TRUE);
        unit.setImageLink(awsS3UseCases.saveFile(multipartFile, AwsConstants.S3_PATH_UNIT, s3BucketName));
        unit.setQtdPoints(0);
        unit.setDeliveryPendingPoints(0);

        return repository.register(unit);
    }

    public Unit update(Long id, Unit unit, MultipartFile multipartFile) {
        var oldUnit = getById(id);

        oldUnit.setName(unit.getName());
        oldUnit.setInitialAge(unit.getInitialAge());
        oldUnit.setFinalAge(unit.getFinalAge());
        oldUnit.setActive(unit.getActive());
        oldUnit.setGender(unit.getGender());
        if (multipartFile != null && !multipartFile.isEmpty()) {
            final String oldImage = unit.getImageLink();
            oldUnit.setImageLink(awsS3UseCases.saveFile(multipartFile, AwsConstants.S3_PATH_UNIT, s3BucketName));
            awsS3UseCases.deleteFile(oldImage, s3BucketName);
        }

        return repository.update(oldUnit);
    }

    public void addPoints(Long id, Integer qtdPoints) {
        Unit unit = this.getById(id);
        unit.setQtdPoints(unit.getQtdPoints() + qtdPoints);
        repository.update(unit);
    }

    public void removePoints(Long id, Integer qtdPoints) {
        Unit unit = this.getById(id);
        unit.setQtdPoints(unit.getQtdPoints() - qtdPoints);
        repository.update(unit);
    }

    public void addPendencyPoints(Long id, Integer qtdPoints) {
        Unit unit = this.getById(id);
        unit.setDeliveryPendingPoints(unit.getDeliveryPendingPoints() + qtdPoints);
        repository.update(unit);
    }

    public void removePendencyPoints(Long id, Integer qtdPoints) {
        Unit unit = this.getById(id);
        unit.setDeliveryPendingPoints(unit.getDeliveryPendingPoints() - qtdPoints);
        repository.update(unit);
    }

    public void delete(Long id) {
        var unit = getById(id);
        awsS3UseCases.deleteFile(unit.getImageLink(), s3BucketName);
        repository.delete(getById(id));
    }

}
