-- Locations (with type)
INSERT INTO inventory_location (id, name, type) VALUES 
(1, 'Main Warehouse', 'Warehouse'),
(2, 'Distribution Center North', 'Distribution Center'),
(3, 'Retail Store Downtown', 'Retail Store'),
(4, 'Retail Store Uptown', 'Retail Store')
ON DUPLICATE KEY UPDATE name = VALUES(name), type = VALUES(type);

-- Variants (8 items, with unit_price)
INSERT INTO variant (id, title, category, size, color, unit_price) VALUES 
(1, 'Classic Tee', 'Tops', 'M', 'Black', 19.99),
(2, 'Denim Jacket', 'Outerwear', 'L', 'Blue', 79.50),
(3, 'Chino Pants', 'Bottoms', '32', 'Khaki', 49.00),
(4, 'Hoodie Pullover', 'Tops', 'L', 'Heather Gray', 54.95),
(5, 'Performance Polo', 'Tops', 'M', 'Navy', 39.99),
(6, 'Summer Shorts', 'Bottoms', 'M', 'Olive', 34.50),
(7, 'Beanie Knit', 'Accessories', 'One Size', 'Charcoal', 14.00),
(8, 'Rain Shell', 'Outerwear', 'M', 'Forest Green', 99.00)
ON DUPLICATE KEY UPDATE 
title = VALUES(title), category = VALUES(category), size = VALUES(size), 
color = VALUES(color), unit_price = VALUES(unit_price);

-- Stock by location (with bin_location)
INSERT INTO variant_stock (id, variant_id, location_id, quantity, bin_location) VALUES 
(1, 1, 1, 120, 'A-12-3'),
(2, 1, 2, 40, 'N-0502'),
(3, 1, 3, 18, 'D-01-07'),
(4, 2, 1, 25, 'B-03-11'),
(5, 2, 2, 10, 'N-0110'),
(6, 3, 1, 60, 'C-22-1'),
(7, 3, 3, 14, 'D-02-04'),
(8, 4, 1, 45, 'A-09-2'),
(9, 4, 2, 20, 'N-0721'),
(10, 4, 4, 8, 'U-01-03'),
(11, 5, 1, 70, 'A-14-8'),
(12, 5, 3, 16, 'D-05-02'),
(13, 6, 1, 55, 'C-10-6'),
(14, 6, 2, 15, 'N-0212'),
(15, 7, 1, 90, 'ACC-2-01'),
(16, 7, 3, 22, 'D-ACC-03'),
(17, 8, 1, 18, 'B-18-9'),
(18, 8, 2, 12, 'N-0330'),
(19, 8, 4, 5, 'U-02-01')
ON DUPLICATE KEY UPDATE 
variant_id = VALUES(variant_id), location_id = VALUES(location_id), 
quantity = VALUES(quantity), bin_location = VALUES(bin_location);

-- Sample deliveries
INSERT INTO delivery (id, delivery_type, location, delivery_date, status, variant_id, quantity, created_at) VALUES 
(100, 'Express', 'Main Warehouse', '2025-03-10', 'Delivered', 1, 50, NOW()),
(101, 'Regular', 'Distribution Center North', '2025-03-12', 'In Transit', 2, 30, NOW()),
(102, 'Express', 'Retail Store Downtown', '2025-03-15', 'Pending', 3, 20, NOW())
ON DUPLICATE KEY UPDATE 
delivery_type = VALUES(delivery_type), location = VALUES(location), 
delivery_date = VALUES(delivery_date), status = VALUES(status), 
variant_id = VALUES(variant_id), quantity = VALUES(quantity);

