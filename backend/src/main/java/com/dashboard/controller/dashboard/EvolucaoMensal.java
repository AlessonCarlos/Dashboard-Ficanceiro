package com.dashboard.controller.dashboard;

import java.math.BigDecimal;

/**
 * Um ponto do grafico de linha: total de ganhos e custos de um mes
 * (formato "yyyy-MM"), para comparar a evolucao ao longo do tempo.
 */
public record EvolucaoMensal(
        String mes,
        BigDecimal ganhos,
        BigDecimal custos
) {
}
