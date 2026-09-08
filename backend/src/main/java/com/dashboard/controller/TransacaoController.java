package com.dashboard.controller;

import com.dashboard.entity.Categoria;
import com.dashboard.entity.Conta;
import com.dashboard.entity.Transacao;
import com.dashboard.entity.Veiculo;
import com.dashboard.entity.enums.TipoConta;
import com.dashboard.entity.enums.TipoMovimento;
import com.dashboard.exception.ResourceNotFoundException;
import com.dashboard.repository.CategoriaRepository;
import com.dashboard.repository.ContaRepository;
import com.dashboard.repository.TransacaoRepository;
import com.dashboard.repository.VeiculoRepository;
import com.dashboard.specification.TransacaoSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final CategoriaRepository categoriaRepository;
    private final VeiculoRepository veiculoRepository;

    /**
     * Lista transacoes aplicando apenas os filtros informados.
     * Todos os parametros sao opcionais e combinaveis entre si.
     */
    @GetMapping
    public List<Transacao> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Long contaId,
            @RequestParam(required = false) TipoConta tipoConta,
            @RequestParam(required = false) TipoMovimento tipo
    ) {
        return transacaoRepository.findAll(
                TransacaoSpecification.comFiltros(dataInicio, dataFim, categoriaId, contaId, tipoConta, tipo)
        );
    }

    @GetMapping("/{id}")
    public Transacao buscarPorId(@PathVariable Long id) {
        return buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transacao criar(@Valid @RequestBody Transacao transacao) {
        transacao.setId(null);
        vincularRelacionamentos(transacao);
        return transacaoRepository.save(transacao);
    }

    @PutMapping("/{id}")
    public Transacao atualizar(@PathVariable Long id, @Valid @RequestBody Transacao dadosAtualizados) {
        Transacao existente = buscarOuFalhar(id);
        dadosAtualizados.setId(existente.getId());
        vincularRelacionamentos(dadosAtualizados);
        return transacaoRepository.save(dadosAtualizados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        Transacao existente = buscarOuFalhar(id);
        transacaoRepository.delete(existente);
    }

    /**
     * Resolve conta, categoria e veiculo (se informado) para as entidades
     * persistidas, garantindo que o id enviado no corpo da requisicao
     * corresponde a um registro existente antes de salvar.
     */
    private void vincularRelacionamentos(Transacao transacao) {
        Long contaId = transacao.getConta() != null ? transacao.getConta().getId() : null;
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta nao encontrada com id " + contaId));
        transacao.setConta(conta);

        Long categoriaId = transacao.getCategoria() != null ? transacao.getCategoria().getId() : null;
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria nao encontrada com id " + categoriaId));
        transacao.setCategoria(categoria);

        if (transacao.getVeiculo() != null && transacao.getVeiculo().getId() != null) {
            Long veiculoId = transacao.getVeiculo().getId();
            Veiculo veiculo = veiculoRepository.findById(veiculoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Veiculo nao encontrado com id " + veiculoId));
            transacao.setVeiculo(veiculo);
        } else {
            transacao.setVeiculo(null);
        }
    }

    private Transacao buscarOuFalhar(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacao nao encontrada com id " + id));
    }

}
