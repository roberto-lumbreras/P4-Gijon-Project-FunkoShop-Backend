INSERT INTO categories (name, img_url, highlights) VALUES 
('Marvel', 'aasdf', true), 
('Iron Man', 'aasdf', false), 
('Star Wars', 'aasdf', true);

INSERT INTO products (name, description, price, stock, discount, img_url, img_url2, category_id, created_at)
VALUES
    ('Funko Pop Spider-Man', 'Figura coleccionable de Spider-Man', 19.99, 50, 10.00, 'aasdf', 'aasdf', 1, '2024-12-16 12:00:00'),
    ('Funko Pop Iron Man', 'Figura coleccionable de Iron Man', 24.99, 30, 5.00, 'aasdf', 'aasdf', 2, '2024-12-16 12:05:00'),
    ('Funko Pop Darth Vader', 'Figura coleccionable de Darth Vader', 29.99, 20, 15.00, 'aasdf', 'aasdf', 3, '2024-12-16 12:10:00');