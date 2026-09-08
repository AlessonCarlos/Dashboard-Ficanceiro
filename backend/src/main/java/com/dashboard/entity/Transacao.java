package com.dashboard.entity;

import com.dashboard.entity.enums.TipoMovimento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Registro de uma movimentacao financeira (ganho ou custo).
 * Sempre vinculada a uma Conta e a uma Categoria; o Veiculo e opcional
 * e usado apenas para custos relacionados ao carro (combustivel,
 * recarga, manutencao) para permitir o calculo de custo por km rodado.
 */
@Entity
@Table(name = "transacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A conta e obrigatoria")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    /**
     * Opcional: so preenchido quando a transacao esta ligada a um custo
     * do veiculo (ex: recarga, manutencao, pneu).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @NotNull(message = "A categoria e obrigatoria")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "O valor e obrigatorio")
    @Positive(message = "O valor deve ser maior que zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "A data e obrigatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "O tipo da transacao e obrigatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMovimento tipo;

}
