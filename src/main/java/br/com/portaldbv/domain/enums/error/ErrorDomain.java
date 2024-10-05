package br.com.portaldbv.domain.enums.error;

public interface ErrorDomain {

    Integer getHttpStatusCode();

    String getDetails();

}