package com.dashboard.controller;

import com.dashboard.entity.Conta;
import com.dashboard.entity.enums.TipoConta;
import com.dashboard.exception.ResourceNotFoundException;
import com.dashboard.repository.ContaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaRepository contaRepository;

    @GetMapping
    public List<Conta> listar(@RequestParam(required = false) TipoConta tipo) {
        if (tipo != null) {
            return contaRepository.findByTipo(tipo);
        }
        return contaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Conta buscarPorId(@PathVariable Long id) {
        return buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Conta criar(@Valid @RequestBody Conta conta) {
        conta.setId(null);
        return contaRepository.save(conta);
    }

    @PutMapping("/{id}")
    public Conta atualizar(@PathVariable Long id, @Valid @RequestBody Conta dadosAtualizados) {
        Conta existente = buscarOuFalhar(id);
        dadosAtualizados.setId(existente.getId());
        return contaRepository.save(dadosAtualizados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        Conta existente = buscarOuFalhar(id);
        contaRepository.delete(existente);
    }

    private Conta buscarOuFalhar(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta nao encontrada com id " + id));
    }

}
