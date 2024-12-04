INSERT INTO categories(name,highlights) VALUES ('DC',true);
INSERT INTO categories(name,highlights) VALUES ('MARVEL',true);

INSERT INTO products (name, description, category_id, stock, price,discount) 
VALUES
('Batman Figure', 'A detailed collectible of Batman.', 1, 50, 29.99,0),
('Superman Figure', 'A powerful Superman collectible.', 1, 40, 25.99,15),
('Wonder Woman Figure', 'A stunning Wonder Woman figurine.', 1, 45, 27.49,0),
('Iron Man Figure', 'A sleek Iron Man collectible.', 2, 60, 34.99,0),
('Captain America Figure', 'A patriotic Captain America figurine.', 2, 55, 31.49,10),
('Thor Hammer', 'A replica of Thor''s mighty hammer.', 2, 35, 39.99,5);