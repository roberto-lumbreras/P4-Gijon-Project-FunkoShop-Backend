INSERT INTO categories (id, name, image_hash, highlights) VALUES
    (1, 'Marvel', 'X''FFD8FF''', true),
    (2, 'Iron Man', 'X''FFD8FF''', false),
    (3, 'Star Wars', 'X''FFD8FF''', true);

INSERT INTO products (name, description, price, stock, discount, image_hash, image_hash2, category_id, created_at)
VALUES
    ('Funko Pop Spider-Man', 'Figura coleccionable de Spider-Man', 19.99, 50, 10, X'FFD8FF', X'FFD8FF', 1, '2024-12-16 12:00:00'),
    ('Funko Pop Iron Man', 'Figura coleccionable de Iron Man', 24.99, 30, 5, X'FFD8FF', X'FFD8FF', 2, '2024-12-16 12:05:00'),
    ('Funko Pop Darth Vader', 'Figura coleccionable de Darth Vader', 29.99, 20, 15, X'FFD8FF', X'FFD8FF', 3, '2024-12-16 12:10:00');