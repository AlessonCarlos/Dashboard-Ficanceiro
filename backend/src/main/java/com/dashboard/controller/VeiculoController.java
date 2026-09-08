package com.dashboard.controller;

import com.dashboard.entity.Veiculo;
import com.dashboard.exception.ResourceNotFoundException;
import com.dashboard.repository.VeiculoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;

    @GetMapping
    public List<Veiculo> listar() {
        return veiculoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Veiculo buscarPorId(@PathVariable Long id) {
        return buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Veiculo criar(@Valid @RequestBody Veiculo veiculo) {
        veiculo.setId(null);
        return veiculoRepository.save(veiculo);
    }

    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable Long id, @Valid @RequestBody Veiculo dadosAtualizados) {
        Veiculo existente = buscarOuFalhar(id);
        dadosAtualizados.setId(existente.getId());
        return veiculoRepository.save(dadosAtualizados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        Veiculo existente = buscarOuFalhar(id);
        veiculoRepository.delete(existente);
    }

    private Veiculo buscarOuFalhar(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo nao encontrado com id " + id));
    }

}
