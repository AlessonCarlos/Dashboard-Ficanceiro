package com.dashboard.controller.dashboard;

import com.dashboard.entity.enums.TipoMovimento;
import com.dashboard.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final TransacaoRepository transacaoRepository;

    @GetMapping("/resumo")
    public SaldoResumo resumo() {
        BigDecimal totalGanhos = transacaoRepository.somarPorTipo(TipoMovimento.GANHO);
        BigDecimal totalCustos = transacaoRepository.somarPorTipo(TipoMovimento.CUSTO);
        return SaldoResumo.of(totalGanhos, totalCustos);
    }

    @GetMapping("/gastos-por-categoria")
    public List<GastoPorCategoria> gastosPorCategoria() {
        return transacaoRepository.somarCustosPorCategoria().stream()
                .map(linha -> new GastoPorCategoria((String) linha[0], (BigDecimal) linha[1]))
                .toList();
    }

    @GetMapping("/evolucao-mensal")
    public List<EvolucaoMensal> evolucaoMensal() {
        // Agrupa as linhas (mes, tipo, total) vindas da query em um mapa
        // mes -> [ganhos, custos], preservando a ordem cronologica.
        Map<String, BigDecimal[]> porMes = new LinkedHashMap<>();

        for (Object[] linha : transacaoRepository.somarPorMesETipo()) {
            String mes = (String) linha[0];
            TipoMovimento tipo = (TipoMovimento) linha[1];
            BigDecimal total = (BigDecimal) linha[2];

            BigDecimal[] valores = porMes.computeIfAbsent(mes, m -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            if (tipo == TipoMovimento.GANHO) {
                valores[0] = total;
            } else {
                valores[1] = total;
            }
        }

        return porMes.entrySet().stream()
                .map(entrada -> new EvolucaoMensal(entrada.getKey(), entrada.getValue()[0], entrada.getValue()[1]))
                .toList();
    }

}
