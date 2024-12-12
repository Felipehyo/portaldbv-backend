package br.com.portaldbv.domain.enums.error;

import br.com.portaldbv.domain.enums.constant.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AwsErrorEnum implements ErrorDomain {
    S3_ERROR_SAVING(400, Errors.AWS_S3_ERROR_SAVING),
    S3_FILE_CONVERT_ERROR(400, Errors.FILE_CONVERT_ERROR);

    private final Integer httpStatusCode;
    private final String details;

}