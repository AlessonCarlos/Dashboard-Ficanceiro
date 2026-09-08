package com.dashboard.controller.dashboard;

import java.math.BigDecimal;

/**
 * Resposta agregada com o saldo total: ganhos - custos.
 * Nao mapeia uma entidade JPA, entao nao entra na regra de "sem DTOs"
 * do CRUD (aqui e um calculo, nao existe tabela "resumo").
 */
public record SaldoResumo(
        BigDecimal totalGanhos,
        BigDecimal totalCustos,
        BigDecimal saldo
) {
    public static SaldoResumo of(BigDecimal totalGanhos, BigDecimal totalCustos) {
        return new SaldoResumo(totalGanhos, totalCustos, totalGanhos.subtract(totalCustos));
    }
}
