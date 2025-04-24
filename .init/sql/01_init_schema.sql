CREATE TABLE brand (
                       id UUID PRIMARY KEY,
                       name VARCHAR(100) NOT NULL UNIQUE,
                       logo_url VARCHAR(255)
);

CREATE TABLE category (
                          id UUID PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE,
                          description TEXT
);

CREATE TABLE subcategory (
                             id UUID PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             category_id UUID NOT NULL,
                             FOREIGN KEY (category_id) REFERENCES category(id),
                             UNIQUE(name, category_id)
);

CREATE TABLE price (
                       id UUID PRIMARY KEY,
                       amount DECIMAL(10, 2) NOT NULL CHECK (amount > 0),
                       currency VARCHAR(3) NOT NULL
);

CREATE TABLE product (
                         id UUID PRIMARY KEY,
                         name VARCHAR(150) NOT NULL,
                         description TEXT NOT NULL,
                         brand_id UUID NOT NULL,
                         category_id UUID NOT NULL,
                         subcategory_id UUID,
                         price_id UUID NOT NULL,
                         stock INT NOT NULL CHECK (stock >= 0),
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP NOT NULL,
                         FOREIGN KEY (brand_id) REFERENCES brand(id),
                         FOREIGN KEY (category_id) REFERENCES category(id),
                         FOREIGN KEY (subcategory_id) REFERENCES subcategory(id),
                         FOREIGN KEY (price_id) REFERENCES price(id)
);

CREATE TABLE product_image (
                               id UUID PRIMARY KEY,
                               product_id UUID NOT NULL,
                               url VARCHAR(255) NOT NULL,
                               is_main BOOLEAN NOT NULL DEFAULT FALSE,
                               FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE inventory (
                           product_id UUID PRIMARY KEY,
                           quantity INT NOT NULL CHECK (quantity >= 0),
                           location VARCHAR(100),
                           FOREIGN KEY (product_id) REFERENCES product(id)
);