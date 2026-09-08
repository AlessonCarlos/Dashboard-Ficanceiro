package com.dashboard.controller;

import com.dashboard.entity.Categoria;
import com.dashboard.entity.enums.TipoMovimento;
import com.dashboard.exception.ResourceNotFoundException;
import com.dashboard.repository.CategoriaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listar(@RequestParam(required = false) TipoMovimento tipo) {
        if (tipo != null) {
            return categoriaRepository.findByTipo(tipo);
        }
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id) {
        return buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria criar(@Valid @RequestBody Categoria categoria) {
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    @PutMapping("/{id}")
    public Categoria atualizar(@PathVariable Long id, @Valid @RequestBody Categoria dadosAtualizados) {
        Categoria existente = buscarOuFalhar(id);
        dadosAtualizados.setId(existente.getId());
        return categoriaRepository.save(dadosAtualizados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        Categoria existente = buscarOuFalhar(id);
        categoriaRepository.delete(existente);
    }

    private Categoria buscarOuFalhar(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria nao encontrada com id " + id));
    }

}
