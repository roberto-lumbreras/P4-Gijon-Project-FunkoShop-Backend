CREATE TABLE IF NOT EXISTS categories(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_hash VARCHAR(255),
    highlights BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS products(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    discount INT NOT NULL CHECK (discount>=0),
    price DECIMAL(10,2) NOT NULL CHECK(price>=0),
    stock INT NOT NULL CHECK(stock>=0),
    image_hash VARCHAR(255),
    image_hash2 VARCHAR(255),
    category_id BIGINT,
    created_at DATE,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id)
);
