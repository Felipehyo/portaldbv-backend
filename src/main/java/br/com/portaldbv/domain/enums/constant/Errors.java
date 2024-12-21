package br.com.portaldbv.domain.enums.constant;

public class Errors {

    /**
     * Club Errors
     */
    public static final String CLUB_ALREADY_REGISTERED_MESSAGE = "Clube com o nome informado já se encontra cadastrado";
    public static final String CLUB_ID_NOT_FOUND = "Clube com id informado não encontrado";
    public static final String CLUB_NAME_NOT_FOUND = "Clube com o nome informado não encontrado";

    /**
     * Specialities Errors
     */
    public static final String SPECIALITY_ALREADY_REGISTERED_MESSAGE = "Especialidade com o nome informado já se encontra cadastrado";
    public static final String SPECIALITY_ID_NOT_FOUND = "Especialidade com id informado não encontrado";
    public static final String INVALID_CATEGORY = "Categoria inválida";

    /**
     * Aws Errors
     */
    public static final String AWS_S3_ERROR_SAVING = "Erro ao salvar arquivo";
    public static final String AWS_S3_ERROR_DELETING = "Erro ao deletar arquivo";
    public static final String FILE_CONVERT_ERROR = "Erro ao tentar converter arquivo";

    /**
     * Unit Errors
     */
    public static final String UNIT_ALREADY_REGISTERED_MESSAGE = "Unidade com o nome informado já se encontra cadastrado";
    public static final String UNIT_ID_NOT_FOUND = "Unidade com id informado não encontrado";
    public static final String UNIT_NAME_NOT_FOUND = "Unidade com o nome informado não encontrado";

}
