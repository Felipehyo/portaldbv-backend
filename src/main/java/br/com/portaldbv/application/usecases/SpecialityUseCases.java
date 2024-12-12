package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.SpecialityRepositoryGateway;
import br.com.portaldbv.domain.entities.Speciality;
import br.com.portaldbv.domain.enums.SpecialityCategoryEnum;
import br.com.portaldbv.domain.enums.constant.AwsConstants;
import br.com.portaldbv.domain.enums.error.SpecialityErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public class SpecialityUseCases {

    private final SpecialityRepositoryGateway repository;
    private final AwsS3UseCases awsS3UseCases;
    private final String s3BucketName;

    public SpecialityUseCases(SpecialityRepositoryGateway repository, AwsS3UseCases awsS3UseCases, String s3BucketName) {
        this.repository = repository;
        this.awsS3UseCases = awsS3UseCases;
        this.s3BucketName = s3BucketName;
    }

    public List<Speciality> getAll(String category) {
        if (category != null && !category.trim().isEmpty()) {
            return repository.getByCategory(SpecialityCategoryEnum.getByName(category));
        }
        return repository.getAll();
    }

    public Speciality getById(Long id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(SpecialityErrorEnum.ID_NOT_FOUND));
    }

    public Speciality register(Speciality speciality, MultipartFile multipartFile) {

        if (repository.getByName(speciality.getName()) != null) {
            throw new DomainException(SpecialityErrorEnum.ALREADY_REGISTERED);
        }

        speciality.setImageUrl(awsS3UseCases.saveFile(multipartFile, AwsConstants.S3_PATH_NAME, s3BucketName));

        return repository.register(speciality);
    }

    public Speciality update(Long id, Speciality newSpeciality, MultipartFile multipartFile) {
        var speciality = getById(id);

        if (newSpeciality.getName() != null && !newSpeciality.getName().trim().isEmpty()) {
            speciality.setName(newSpeciality.getName());
        }

        if (newSpeciality.getYear() != null && !newSpeciality.getYear().trim().isEmpty()) {
            speciality.setYear(newSpeciality.getYear());
        }

        if (newSpeciality.getCategory() != null) {
            speciality.setCategory(newSpeciality.getCategory());
        }

        if (multipartFile != null) {
            speciality.setImageUrl(awsS3UseCases.updateFile(multipartFile, AwsConstants.S3_PATH_NAME, newSpeciality.getImageUrl(), s3BucketName));
        }

        return repository.update(speciality);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

}
