package com.dashboard.entity;

import com.dashboard.entity.enums.TipoMovimento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Categoria de uma transacao (ex: Combustivel, Manutencao, Alimentacao,
 * Corrida Uber). Define se a categoria representa um ganho ou um custo.
 */
@Entity
@Table(name = "categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da categoria e obrigatorio")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O tipo da categoria e obrigatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMovimento tipo;

}
