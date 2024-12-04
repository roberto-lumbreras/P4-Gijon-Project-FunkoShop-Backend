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
