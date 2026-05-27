package com.greyes.funzel.funzelbackend.entity;

import com.greyes.funzel.funzelbackend.enums.CategoriaGaleria;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "galeria")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Galeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "url_imagen", nullable = false, length = 300)
    private String urlImagen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoriaGaleria categoria;

    @CreationTimestamp
    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;
}
