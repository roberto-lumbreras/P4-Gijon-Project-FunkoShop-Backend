
/* Se borra la base de datos anterior si existiese */

DROP DATABASE IF EXISTS funkoshop;
CREATE DATABASE funkoshop;
USE funkoshop;


-- Modificar la tabla 'users' para agregar la columna 'id'
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL,
    UNIQUE(username)  -- Esto garantiza que 'username' sea único
);


-- Crear la tabla 'authorities' con la clave foránea que hace referencia a 'users(id)'
CREATE TABLE authorities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

-- Crear un índice único para 'authorities' (aunque esta columna 'id' es la clave primaria ahora)
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