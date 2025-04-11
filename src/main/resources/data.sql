-- Insert customers
INSERT INTO CUSTOMER (uuid, email, name, lastName) VALUES
('537fd0c4-2d15-3bc5-8932-25d4bf615b7f', 'bbunny@mail.com', 'Bugs', 'Bunny'),
('729c5d13-261a-4b46-bb11-9af04cb2cd3f', 'lbunny@mail.com', 'Lola', 'Bunny'),
('1adfa52b-a57c-3b40-8da4-388526f6595b', 'plucas@mail.com', 'Pato', 'Lucas'),
('1adfa52b-a57c-3b40-8da4-388526f6595c', 'sgonzales@mail.com', 'Speedy', 'Gonzales'),
('729c5d13-261a-4b46-bb11-9af04cb2cd3a', 'plepew@mail.com', 'Pepe', 'Le Pew'),
('1adfa52b-a57c-3b40-8da4-388526f6595d', 'ddtasmania@mail.com', 'Demonio', 'De Tasmania'),
('7b2e3405-5b5f-4d72-9a36-96da47e7b273', 'john.doe@example.com', 'John', 'Doe'),
('14e46437-1bdd-4a8e-ac81-085d286e7aba', 'jgo@mail.com', 'Julian', 'Garcia O');

-- **** Start use these with H2 DB **** --
------ Insert order1
INSERT INTO ORDERS (id, name, description, customer_uuid, totalorder)
VALUES (1, 'Order01', 'This is the first order.', '537fd0c4-2d15-3bc5-8932-25d4bf615b7f', 100.0);

---- Insert saleDetails1A
INSERT INTO SALEDETAILS (id, sku, quantity, unitprice, totalprice, discountamount, order_id)
VALUES (1, '0001', 2, 50.0, 100.0, 0.0, 1);

---- Insert saleDetails1B
INSERT INTO SALEDETAILS (id, sku, quantity, unitprice, totalprice, discountamount, order_id)
VALUES (2, '0002', 1, 20.0, 20.0, 0.0, 1);

---- Insert order2
INSERT INTO ORDERS (id, name, description, customer_uuid, totalorder)
VALUES (2, 'Order02', 'This is the second order.', '537fd0c4-2d15-3bc5-8932-25d4bf615b7f', 120.0);

---- Insert saleDetails2A
INSERT INTO SALEDETAILS (id, sku, quantity, unitprice, totalprice, discountamount, order_id)
VALUES (3, '0003', 2, 30.0, 60.0, 0.0, 2);

---- Insert saleDetails2B
INSERT INTO SALEDETAILS (id, sku, quantity, unitprice, totalprice, discountamount, order_id)
VALUES (4, '0004', 3, 10.0, 30.0, 0.0, 2);
-- **** End use these with H2 DB **** --

-- **** Start use these with POSTGRES DB **** --
-- -- Insert order1
-- INSERT INTO ORDERS (id, name, description, customer_uuid, totalorder)
-- VALUES (1, 'Order01', 'This is the first order.', '537fd0c4-2d15-3bc5-8932-25d4bf615b7f', 100.0);
--
-- -- Insert saleDetails1A
-- INSERT INTO SALEDETAILS (sku, quantity, unitprice, totalprice, discountamount, order_id)
-- VALUES ('0001', 2, 50.0, 100.0, 0.0, 1);
--
-- -- Insert saleDetails1B
-- INSERT INTO SALEDETAILS (sku, quantity, unitprice, totalprice, discountamount, order_id)
-- VALUES ('0002', 1, 20.0, 20.0, 0.0, 1);
--
-- -- Insert order2
-- INSERT INTO ORDERS (id, name, description, customer_uuid, totalorder)
-- VALUES (2, 'Order02', 'This is the second order.', '537fd0c4-2d15-3bc5-8932-25d4bf615b7f', 120.0);
--
-- -- Insert saleDetails2A
-- INSERT INTO SALEDETAILS (sku, quantity, unitprice, totalprice, discountamount, order_id)
-- VALUES ('0003', 2, 30.0, 60.0, 0.0, 2);
--
-- -- Insert saleDetails2B
-- INSERT INTO SALEDETAILS (sku, quantity, unitprice, totalprice, discountamount, order_id)
-- VALUES ('0004', 3, 10.0, 30.0, 0.0, 2);
-- **** End use these with POSTGRES DB **** --
