package com.dashboard.entity;

import com.dashboard.entity.enums.TipoConta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Conta financeira (ex: Uber, 99, Pessoal), usada para separar
 * transacoes de trabalho das transacoes pessoais.
 */
@Entity
@Table(name = "conta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da conta e obrigatorio")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O tipo da conta e obrigatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoConta tipo;

}
