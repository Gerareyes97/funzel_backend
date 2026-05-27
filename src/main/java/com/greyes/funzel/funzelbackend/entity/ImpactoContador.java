package com.greyes.funzel.funzelbackend.entity;

import com.greyes.funzel.funzelbackend.enums.TipoImpacto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "impacto_contadores")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpactoContador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoImpacto tipo;

    @Column(nullable = false)
    private Long valor = 0L;

    @Column(name = "ultima_actualizacion", nullable = false)
    @UpdateTimestamp
    private LocalDateTime ultimaActualizacion;
}