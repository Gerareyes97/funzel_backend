package com.greyes.funzel.funzelbackend.repository;

import com.greyes.funzel.funzelbackend.entity.ImpactoContador;
import com.greyes.funzel.funzelbackend.enums.TipoImpacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImpactoContadorRepository extends JpaRepository<ImpactoContador, Long> {

    Optional<ImpactoContador> findByTipo(TipoImpacto tipo);
}
