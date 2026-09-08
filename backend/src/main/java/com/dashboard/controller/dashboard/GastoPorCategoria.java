package com.dashboard.controller.dashboard;

import java.math.BigDecimal;

/**
 * Um ponto do grafico de pizza: total de custos de uma categoria.
 */
public record GastoPorCategoria(
        String categoria,
        BigDecimal total
) {
}
