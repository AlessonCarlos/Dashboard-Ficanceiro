package com.dashboard.repository;

import com.dashboard.entity.Transacao;
import com.dashboard.entity.enums.TipoMovimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * JpaSpecificationExecutor permite montar queries de filtro combinadas
 * dinamicamente (data, categoria, conta, tipo) na Fase 6, sem precisar
 * de um metodo derivado para cada combinacao possivel.
 */
public interface TransacaoRepository extends JpaRepository<Transacao, Long>, JpaSpecificationExecutor<Transacao> {

    List<Transacao> findByDataBetween(LocalDate inicio, LocalDate fim);

    @Query("select coalesce(sum(t.valor), 0) from Transacao t where t.tipo = :tipo")
    BigDecimal somarPorTipo(TipoMovimento tipo);

    @Query("""
            select t.categoria.nome, coalesce(sum(t.valor), 0)
            from Transacao t
            where t.tipo = com.dashboard.entity.enums.TipoMovimento.CUSTO
            group by t.categoria.nome
            order by sum(t.valor) desc
            """)
    List<Object[]> somarCustosPorCategoria();

    @Query("""
            select function('FORMATDATETIME', t.data, 'yyyy-MM'), t.tipo, coalesce(sum(t.valor), 0)
            from Transacao t
            group by function('FORMATDATETIME', t.data, 'yyyy-MM'), t.tipo
            order by function('FORMATDATETIME', t.data, 'yyyy-MM')
            """)
    List<Object[]> somarPorMesETipo();

}
