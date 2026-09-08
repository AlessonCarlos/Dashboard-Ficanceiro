package com.dashboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Veiculo usado pelo motorista para trabalhar (ex: BYD Dolphin Mini).
 * Usado para vincular custos do veiculo (manutencao, recarga/combustivel)
 * e calcular o custo por km rodado.
 */
@Entity
@Table(name = "veiculo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O modelo do veiculo e obrigatorio")
    @Column(nullable = false)
    private String modelo;

    @NotBlank(message = "A placa do veiculo e obrigatoria")
    @Column(nullable = false, unique = true)
    private String placa;

    @NotNull(message = "O ano do veiculo e obrigatorio")
    @Positive
    @Column(nullable = false)
    private Integer ano;

    @NotNull(message = "O valor de compra e obrigatorio")
    @PositiveOrZero
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorCompra;

    @NotNull(message = "O km atual e obrigatorio")
    @PositiveOrZero
    @Column(nullable = false)
    private Double kmAtual;

    /**
     * Consumo medio em km/l (veiculos a combustao) ou km/kWh (eletricos),
     * usado para estimar custo por km rodado.
     */
    @NotNull(message = "O consumo medio e obrigatorio")
    @Positive
    @Column(nullable = false)
    private Double consumoMedio;

}
