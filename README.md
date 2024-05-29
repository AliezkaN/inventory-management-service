**INVENTORY-MANAGEMENT-SERVICE**

insert into roles (name, create_date)
values
('ADMIN', CURRENT_TIMESTAMP),
('MANAGER', CURRENT_TIMESTAMP);

INSERT INTO users (first_name, last_name, email, password, role_id, account_locked, enabled, create_date)
VALUES
('Олег', 'Прізвище', 'email@example.com', 'hashed_password', 2, FALSE, TRUE, CURRENT_TIMESTAMP),
('Петро', 'Петрович', 'email1@example.com', 'hashed_password', 2, FALSE, TRUE, CURRENT_TIMESTAMP);

insert into shops (name, address, contact_number, description)
values
('Happy shop', 'вулиця Остроградських, 10, Львів, Львівська область, 79000', '+380-66-666-6666', 'Happy shop. Ваша улюблена крамниця'),
('Happy shop', 'Соборна площа, 17, Львів, Львівська область, 79000', '+380-67-666-6666', 'Happy shop. Ваша улюблена крамниця');

insert into shops_managers (manager_id, shop_id)
values
(1, 1),
(1, 2),
(2, 2);

insert into products (name, shop_id, price, create_date)
values
('Арахіс Козацька Слава смажений солоний зі смаком сметани з зеленню 60г', 2, 29.15, CURRENT_TIMESTAMP);

insert into stocks (shop_id, product_id, quantity)
values
(1, 1, 4);