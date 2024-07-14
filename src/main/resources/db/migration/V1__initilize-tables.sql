
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    correo_electronico VARCHAR(255),
    clave VARCHAR(255)
);

CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    categoria VARCHAR(50)
);


CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255),
    mensaje TEXT,
    fecha_creacion DATETIME,
    status VARCHAR(20),
    autor_id BIGINT,
    curso_id BIGINT,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE TABLE respuestas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje TEXT,
    topico_id BIGINT,
    fecha_creacion DATETIME,
    autor_id BIGINT,
    solucion TEXT,
    FOREIGN KEY (topico_id) REFERENCES topicos(id),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);

