-- Locations (with type)
merge into inventory_location (id, name, type) key(id) values (1, 'Main Warehouse', 'Warehouse');
merge into inventory_location (id, name, type) key(id) values (2, 'Distribution Center North', 'Distribution Center');
merge into inventory_location (id, name, type) key(id) values (3, 'Retail Store Downtown', 'Retail Store');
merge into inventory_location (id, name, type) key(id) values (4, 'Retail Store Uptown', 'Retail Store');

-- Variants (8 items, with unit_price)
merge into variant (id, title, category, size, color, unit_price) key(id)
values (1, 'Classic Tee', 'Tops', 'M', 'Black', 19.99);

merge into variant (id, title, category, size, color, unit_price) key(id)
values (2, 'Denim Jacket', 'Outerwear', 'L', 'Blue', 79.50);

merge into variant (id, title, category, size, color, unit_price) key(id)
values (3, 'Chino Pants', 'Bottoms', '32', 'Khaki', 49.00);

merge into variant (id, title, category, size, color, unit_price) key(id)
values (4, 'Hoodie Pullover', 'Tops', 'L', 'Heather Gray', 54.95);

merge into variant (id, title, category, size, color, unit_price) key(id)
values (5, 'Performance Polo', 'Tops', 'M', 'Navy', 39.99);

merge into variant (id, title, category, size, color, unit_price) key(id)
values (6, 'Summer Shorts', 'Bottoms', 'M', 'Olive', 34.50);

merge into variant (id, title, category, size, color, unit_price) key(id)
values (7, 'Beanie Knit', 'Accessories', 'One Size', 'Charcoal', 14.00);

merge into variant (id, title, category, size, color, unit_price) key(id)
values (8, 'Rain Shell', 'Outerwear', 'M', 'Forest Green', 99.00);

-- Stock by location (with bin_location)
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (1, 1, 1, 120, 'A-12-3');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (2, 1, 2, 40, 'N-0502');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (3, 1, 3, 18, 'D-01-07');

merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (4, 2, 1, 25, 'B-03-11');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (5, 2, 2, 10, 'N-0110');

merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (6, 3, 1, 60, 'C-22-1');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (7, 3, 3, 14, 'D-02-04');

merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (8, 4, 1, 45, 'A-09-2');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (9, 4, 2, 20, 'N-0721');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (10, 4, 4, 8, 'U-01-03');

merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (11, 5, 1, 70, 'A-14-8');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (12, 5, 3, 16, 'D-05-02');

merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (13, 6, 1, 55, 'C-10-6');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (14, 6, 2, 15, 'N-0212');

merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (15, 7, 1, 90, 'ACC-2-01');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (16, 7, 3, 22, 'D-ACC-03');

merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (17, 8, 1, 18, 'B-18-9');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (18, 8, 2, 12, 'N-0330');
merge into variant_stock (id, variant_id, location_id, quantity, bin_location) key(id)
values (19, 8, 4, 5, 'U-02-01');