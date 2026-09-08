package com.dashboard.repository;

import com.dashboard.entity.Conta;
import com.dashboard.entity.enums.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByTipo(TipoConta tipo);

}
