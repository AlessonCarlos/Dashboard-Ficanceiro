package com.dashboard.repository;

import com.dashboard.entity.Categoria;
import com.dashboard.entity.enums.TipoMovimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByTipo(TipoMovimento tipo);

}
