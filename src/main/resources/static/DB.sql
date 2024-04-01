DROP DATABASE IF EXISTS audiovisual;
CREATE DATABASE audiovisual;
USE audiovisual;

CREATE TABLE usuarios (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL
);

CREATE TABLE tipocontenidos (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE generos (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE plataformas (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE tipocontenido_plataforma (
    tipo_id INT NOT NULL,
    plataforma_id INT NOT NULL,
    PRIMARY KEY (tipo_id, plataforma_id),
    FOREIGN KEY (tipo_id) REFERENCES tipocontenidos(id),
    FOREIGN KEY (plataforma_id) REFERENCES plataformas(id)
);

CREATE TABLE contenidos (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    tipo_id INT NOT NULL,
    genero1_id INT NOT NULL,
    genero2_id INT,
    estado ENUM("pendiente", "viendo", "terminado", "abandonado", "en pausa") NOT NULL,
    plataforma_id INT NOT NULL,
    calificacion DECIMAL(2,1) NOT NULL,
    comentario TEXT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (tipo_id) REFERENCES tipocontenidos(id),
    FOREIGN KEY (genero1_id) REFERENCES generos(id),
    FOREIGN KEY (genero2_id) REFERENCES generos(id),
    FOREIGN KEY (plataforma_id) REFERENCES plataformas(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
