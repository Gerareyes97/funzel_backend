package com.greyes.funzel.funzelbackend.repository;

import com.greyes.funzel.funzelbackend.entity.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {

    Optional<Donacion> findByReferencia(String referencia);
}
