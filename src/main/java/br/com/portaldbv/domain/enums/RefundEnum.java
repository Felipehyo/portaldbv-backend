package br.com.portaldbv.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RefundEnum {

    PENDING_REFUND("Pendente de Reembolso"),
    REFUNDED("Reembolsado"),
    NO_REFUNDS("Sem Reembolso");

    public final String value;

}