/* Se borra la base de datos anterior si existiese */
DROP DATABASE IF EXISTS funkoshop;
CREATE DATABASE funkoshop;
USE funkoshop;

/* Tabla de usuarios */
CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);

/* Tabla de roles de usuario */
CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

/* Índice único para la tabla authorities */
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

/* Tabla de categorías */
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_hash VARCHAR(255),
    highlights BOOLEAN NOT NULL
);

/* Tabla de productos */
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    discount INT NOT NULL CHECK (discount >= 0),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    stock INT NOT NULL CHECK (stock >= 0),
    image_hash VARCHAR(255),
    image_hash2 VARCHAR(255),
    category_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id)
);