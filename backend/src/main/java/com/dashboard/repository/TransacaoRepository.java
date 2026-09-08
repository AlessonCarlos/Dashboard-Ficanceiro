package com.dashboard.repository;

import com.dashboard.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

/**
 * JpaSpecificationExecutor permite montar queries de filtro combinadas
 * dinamicamente (data, categoria, conta, tipo) na Fase 6, sem precisar
 * de um metodo derivado para cada combinacao possivel.
 */
public interface TransacaoRepository extends JpaRepository<Transacao, Long>, JpaSpecificationExecutor<Transacao> {

    List<Transacao> findByDataBetween(LocalDate inicio, LocalDate fim);

}
