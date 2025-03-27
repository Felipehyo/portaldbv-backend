package br.com.portaldbv.domain.enums.constant;

public class Errors {

    /**
     * General Error
     */

    public static final String NOT_FOUND = "Não foi encontrado nenhum registro";
    public static final String ID_NOT_FOUND = "Registro com id informado não encontrado";
    public static final String ALREADY_REGISTERED = "Registro já se encontra cadastrado";

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
    public static final String UNIT_INVALID_CLUB = "Unidade não pertence ao clube informado";
    public static final String UNIT_NAME_NOT_FOUND = "Unidade com o nome informado não encontrado";
    public static final String UNIT_NOT_EXIST = "Não existem unidades cadastradas para esse clube";

    /**
     * User Errors
     */
    public static final String USER_ID_NOT_FOUND = "Usuário com id informado não encontrado";
    public static final String USER_ALREADY_REGISTERED = "Já existe um usuário com este cpf cadastrado";
    public static final String INVALID_PASSWORD = "Senha inválida. A senha deve conter entre 6 e 16 dígitos.";
    public static final String INVALID_USER = "Usuário inválido. O usuário deve conter entre 6 e 100 dígitos.";
    public static final String INVALID_CREDENTIALS = "Email ou senha inválidos.";

    /**
     * Event Errors
     */
    public static final String EVENT_ALREADY_REGISTERED_MESSAGE = "Evento com o nome e data informado já se encontra cadastrado";
    public static final String EVENT_NOT_FOUND = "Evento não encontrado";
    public static final String EVENT_ID_NOT_FOUND = "Evento com id informado não encontrado";
    public static final String EVENT_INVALID_CLUB = "Evento não pertence ao clube informado";
    public static final String EVENT_NAME_NOT_FOUND = "Evento com o nome informado não encontrado";

    /**
     * Payment Errors
     */
    public static final String PAYMENT_ALREADY_REGISTERED_MESSAGE = "Evento com o nome informado já se encontra cadastrado";
    public static final String PAYMENT_NOT_FOUND = "Pagamento não encontrado";
    public static final String PAYMENT_ID_NOT_FOUND = "Id informado não encontrado";
    public static final String PAYMENT_INVALID_CLUB = "Evento não pertence ao clube informado";
    public static final String PAYMENT_NAME_NOT_FOUND = "Evento com o nome informado não encontrado";
    public static final String PAYMENT_INSUFFICIENT_BALANCE = "Saldo insuficiente na conta do usuário para remover pagamento.";

    /**
     * Event Register Errors
     */

    public static final String EVENT_REGISTER_ALREADY_REGISTERED = "Usuário já esta incrito no evento";
    public static final String EVENT_REGISTER_NOT_REGISTERED = "Usuário não está incrito no evento";

    /**
     * Kit Errors
     */
    public static final String KIT_ALREADY_REGISTERED_MESSAGE = "Kit com o nome informado já se encontra cadastrado";
    public static final String KIT_ID_NOT_FOUND = "Kit com id informado não encontrado";
    public static final String KIT_NAME_NOT_FOUND = "Kit com o nome informado não encontrado";

}
