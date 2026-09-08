package com.dashboard.specification;

import com.dashboard.entity.Transacao;
import com.dashboard.entity.enums.TipoConta;
import com.dashboard.entity.enums.TipoMovimento;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * Monta a query de Transacao combinando apenas os filtros que
 * realmente foram informados (todo parametro null e ignorado).
 */
public class TransacaoSpecification {

    private TransacaoSpecification() {
    }

    public static Specification<Transacao> comFiltros(
            LocalDate dataInicio,
            LocalDate dataFim,
            Long categoriaId,
            Long contaId,
            TipoConta tipoConta,
            TipoMovimento tipo
    ) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (dataInicio != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("data"), dataInicio));
            }
            if (dataFim != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("data"), dataFim));
            }
            if (categoriaId != null) {
                predicates = cb.and(predicates, cb.equal(root.get("categoria").get("id"), categoriaId));
            }
            if (contaId != null) {
                predicates = cb.and(predicates, cb.equal(root.get("conta").get("id"), contaId));
            }
            if (tipoConta != null) {
                predicates = cb.and(predicates, cb.equal(root.get("conta").get("tipo"), tipoConta));
            }
            if (tipo != null) {
                predicates = cb.and(predicates, cb.equal(root.get("tipo"), tipo));
            }

            return predicates;
        };
    }

}
