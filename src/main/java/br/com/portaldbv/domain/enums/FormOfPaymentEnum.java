package br.com.portaldbv.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FormOfPaymentEnum {

    PIX("Pix"),
    CHURCH("Igreja"),
    CASH("Dinheiro");

    public final String value;

}