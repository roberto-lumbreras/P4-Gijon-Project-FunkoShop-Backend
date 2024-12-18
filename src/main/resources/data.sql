/*  DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS products;

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    imgUrl VARCHAR(255),
    highlights BOOLEAN
);

CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    price DECIMAL(10,2),
    stock INT,
    discount INT,
    imgUrl VARCHAR(255),
    imgUrl2 VARCHAR(255),
    created_at TIMESTAMP,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories (id)
); */


INSERT INTO categories (name, img_url, highlights) VALUES 
('Marvel', 'aasdf', true), 
('Iron Man', 'aasdf', false), 
('Star Wars', 'aasdf', true);


INSERT INTO products (name, description, price, stock, discount, img_url, img_url2, category_id, created_at)
VALUES
    ('Funko Pop Spider-Man', 'Figura coleccionable de Spider-Man', 19.99, 50, 10.00, 'aasdf', 'aasdf', 1, '2024-12-16 12:00:00'),
    ('Funko Pop Iron Man', 'Figura coleccionable de Iron Man', 24.99, 30, 5.00, 'aasdf', 'aasdf', 2, '2024-12-16 12:05:00'),
    ('Funko Pop Darth Vader', 'Figura coleccionable de Darth Vader', 29.99, 20, 15.00, 'aasdf', 'aasdf', 3, '2024-12-16 12:10:00');
