-- Insert marcas
INSERT INTO brand (id, name, logo_url) VALUES
                                           ('11111111-1111-1111-1111-111111111111', 'Yamaha', 'https://logos.com/yamaha.png'),
                                           ('22222222-2222-2222-2222-222222222222', 'Kawasaki', 'https://logos.com/kawasaki.png');

-- Insert categoría y subcategoría
INSERT INTO category (id, name, description) VALUES
    ('33333333-3333-3333-3333-333333333333', 'Equipamiento', 'Ropa y protecciones para moto');

INSERT INTO subcategory (id, name, category_id) VALUES
    ('44444444-4444-4444-4444-444444444444', 'Cascos', '33333333-3333-3333-3333-333333333333');

-- Insert precio primero, ya que product ahora necesita price_id como FK
INSERT INTO price (id, amount, currency) VALUES
    ('66666666-6666-6666-6666-666666666666', 179.99, 'EUR');

-- Insert producto con referencia a price_id
INSERT INTO product (id, name, description, brand_id, category_id, subcategory_id, price_id, stock, created_at, updated_at) VALUES
    ('55555555-5555-5555-5555-555555555555',
     'Casco Integral ZX-R',
     'Casco integral con diseño aerodinámico y ventilación optimizada.',
     '22222222-2222-2222-2222-222222222222',
     '33333333-3333-3333-3333-333333333333',
     '44444444-4444-4444-4444-444444444444',
     '66666666-6666-6666-6666-666666666666',
     15,
     NOW(),
     NOW());

-- Insert imagen del producto
INSERT INTO product_image (id, product_id, url, is_main) VALUES
    ('77777777-7777-7777-7777-777777777777',
     '55555555-5555-5555-5555-555555555555',
     'https://images.com/casco1.jpg',
     true);

-- Insert inventario
INSERT INTO inventory (product_id, quantity, location) VALUES
    ('55555555-5555-5555-5555-555555555555', 15, 'Almacén Central');
