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

INSERT INTO users(username,password,enabled) VALUES ('user','password',true);
INSERT INTO users(username,password,enabled) VALUES ('admin','password',true);

-- Inserciones para la tabla orders y order_details con datos generados.
-- Suponiendo que ya existen productos insertados y un usuario con user_id=1.

-- Inserciones en la tabla orders
INSERT INTO orders (order_date, paid, payment_method, status, total_amount, product_quantity, user_id) 
VALUES
('2024-01-15', true, 'Credit Card', 'DELIVERED', 89.97, 3, 1),
('2024-02-20', false, 'PayPal', 'PENDING', 63.98, 2, 1),
('2024-03-05', true, 'Credit Card', 'DELIVERED', 29.99, 1, 2),
('2024-04-18', true, 'Debit Card', 'DELIVERED', 102.47, 4, 2),
('2024-05-10', false, 'Credit Card', 'PENDING', 39.99, 1, 1),
('2024-06-25', true, 'Credit Card', 'DELIVERED', 126.95, 5, 1),
('2024-07-30', true, 'PayPal', 'DELIVERED', 62.48, 2, 1),
('2024-08-15', true, 'Debit Card', 'DELIVERED', 34.99, 1, 2),
('2024-09-22', false, 'Credit Card', 'PENDING', 94.47, 3, 2),
('2024-10-12', true, 'Credit Card', 'DELIVERED', 55.98, 2, 2),
('2024-11-28', false, 'PayPal', 'PENDING', 31.49, 1, 1),
('2024-12-03', true, 'Credit Card', 'DELIVERED', 69.98, 2, 2);

-- Inserciones en la tabla order_details
INSERT INTO details (product_id, quantity,price, order_id) 
VALUES
(1, 1,(select price from products where id=1), 1),
(2, 1,(select price from products where id=2), 1),
(3, 1,(select price from products where id=3), 1),
(4, 1,(select price from products where id=4), 2),
(5, 1,(select price from products where id=5), 2),
(1, 1,(select price from products where id=1), 3),
(2, 1,(select price from products where id=2), 4),
(3, 1,(select price from products where id=3), 4),
(4, 1,(select price from products where id=4), 4),
(6, 1,(select price from products where id=6), 4),
(6, 1,(select price from products where id=6), 5),
(1, 2,(select price from products where id=1), 6),
(2, 1,(select price from products where id=2), 6),
(3, 1,(select price from products where id=3), 6),
(5, 1,(select price from products where id=5), 7),
(6, 1,(select price from products where id=6), 7),
(4, 1,(select price from products where id=4), 8),
(2, 1,(select price from products where id=2), 9),
(3, 2,(select price from products where id=3), 9),
(5, 1,(select price from products where id=5), 10),
(6, 1,(select price from products where id=6), 10),
(5, 1,(select price from products where id=5), 11),
(4, 1,(select price from products where id=4), 12),
(3, 1,(select price from products where id=3), 12);


