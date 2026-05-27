-- =========================================
-- FUNZEL - Migración V1
-- =========================================

CREATE TABLE donaciones (
    id BIGSERIAL PRIMARY KEY,
    referencia VARCHAR(100) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    monto NUMERIC(10,2) NOT NULL,
    estado VARCHAR(20) NOT NULL
    CHECK (estado IN ('PENDIENTE', 'APROBADA', 'RECHAZADA')),
    pasarela VARCHAR(30) NOT NULL DEFAULT 'DAVIVIENDA',
    codigo_autorizacion VARCHAR(100),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_confirmacion TIMESTAMP
);


CREATE TABLE voluntarios (
     id BIGSERIAL PRIMARY KEY,
     nombre VARCHAR(150) NOT NULL,
     email VARCHAR(150) NOT NULL,
     telefono VARCHAR(30),
     tipo VARCHAR(30) NOT NULL
     CHECK (tipo IN ('VOLUNTARIADO', 'SERVICIO_SOCIAL')),
     mensaje TEXT,
     fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE aliados (
     id BIGSERIAL PRIMARY KEY,
     nombre_empresa VARCHAR(150) NOT NULL,
     contacto VARCHAR(150),
     email VARCHAR(150) NOT NULL,
     telefono VARCHAR(30),
     tipo VARCHAR(30) NOT NULL
     CHECK (tipo IN ('ALIADO', 'PATROCINADOR')),
     mensaje TEXT,
     fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE contactos (
      id BIGSERIAL PRIMARY KEY,
      nombre VARCHAR(150) NOT NULL,
      email VARCHAR(150) NOT NULL,
      asunto VARCHAR(150),
      mensaje TEXT NOT NULL,
      fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE impacto_contadores (
       id BIGSERIAL PRIMARY KEY,
       tipo VARCHAR(30) UNIQUE NOT NULL
       CHECK (tipo IN ('BASURA', 'ARBOLES', 'TORTUGAS')),
       valor BIGINT NOT NULL DEFAULT 0,
       ultima_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO impacto_contadores (tipo, valor) VALUES
        ('BASURA', 0),
        ('ARBOLES', 0),
        ('TORTUGAS', 0);


CREATE TABLE galeria (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(150),
    descripcion TEXT,
    url_imagen TEXT NOT NULL,
    categoria VARCHAR(50)
    CHECK (categoria IN ('TORTUGAS', 'PLAYAS', 'REFORESTACION', 'EDUCACION')),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
