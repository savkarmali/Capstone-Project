INSERT INTO shop_locations (shop_name, address, city, country, phone_number)
SELECT 'Flowers Online - Central Shop', '12 Rose Street, Near City Mall', 'Bengaluru', 'India', '9876543210'
    WHERE NOT EXISTS (SELECT 1 FROM shop_locations WHERE shop_name = 'Flowers Online - Central Shop');

INSERT INTO shop_locations (shop_name, address, city, country, phone_number)
SELECT 'Flowers Online - Garden Branch', '45 Lily Road, Green Park', 'Chennai', 'India', '9876543211'
    WHERE NOT EXISTS (SELECT 1 FROM shop_locations WHERE shop_name = 'Flowers Online - Garden Branch');

INSERT INTO shop_locations (shop_name, address, city, country, phone_number)
SELECT 'Flowers Online - Celebration Store', '78 Orchid Avenue, Market Square', 'Hyderabad', 'India', '9876543212'
    WHERE NOT EXISTS (SELECT 1 FROM shop_locations WHERE shop_name = 'Flowers Online - Celebration Store');
