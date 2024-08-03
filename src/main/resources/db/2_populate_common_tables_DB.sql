DELETE FROM vehicles;
DELETE FROM vehicle_models;
DELETE FROM drivers;
DELETE FROM enterprises;
DELETE FROM managers;
DELETE FROM users;
DELETE FROM enterprises_managers;
DELETE FROM tracks;
DELETE FROM vehicle_coordinates;

ALTER SEQUENCE enterprises_seq RESTART WITH 500000;
ALTER SEQUENCE vehicle_models_seq RESTART WITH 500000;
ALTER SEQUENCE vehicles_seq RESTART WITH 500000;
ALTER SEQUENCE drivers_seq RESTART WITH 500000;
ALTER SEQUENCE managers_seq RESTART WITH 500000;
ALTER SEQUENCE users_seq RESTART WITH 500000;
ALTER SEQUENCE tracks_seq RESTART WITH 500000;
ALTER SEQUENCE vehicle_coordinates_seq RESTART WITH 500000;

INSERT INTO public.vehicle_models (id, brand, name, vehicle_type, num_seats, engine_type, load_capacity)
VALUES (100000, 'OPEL', 'Astra', 'CAR', 5, 'PETROL', 500);
INSERT INTO public.vehicle_models (id, brand, name, vehicle_type, num_seats, engine_type, load_capacity)
VALUES (100001, 'FORD', 'Focus 1', 'CAR', 5, 'PETROL', 400);
INSERT INTO public.vehicle_models (id, brand, name, vehicle_type, num_seats, engine_type, load_capacity)
VALUES (100002, 'BMW', 'X5', 'CAR', 5, 'PETROL', 800);
INSERT INTO public.vehicle_models (id, brand, name, vehicle_type, num_seats, engine_type, load_capacity)
VALUES (100003, 'INFINITY', 'Y9', 'CAR', 8, 'PETROL', 1000);
INSERT INTO public.vehicle_models (id, brand, name, vehicle_type, num_seats, engine_type, load_capacity)
VALUES (100004, 'FORD', 'Mondeo', 'CAR', 8, 'PETROL', 900);
INSERT INTO public.vehicle_models (id, brand, name, vehicle_type, num_seats, engine_type, load_capacity)
VALUES (100005, 'LADA', 'Vesta', 'CAR', 5, 'PETROL', 500);

INSERT INTO public.enterprises (id, name, city, time_zone)
VALUES (100002, 'It''s not the car but the driver', 'Los Angeles', 'America/Los_Angeles');
INSERT INTO public.enterprises (id, name, city, time_zone)
VALUES (100003, 'Drive anywhere', 'Las Vegas', 'America/Chicago');
INSERT INTO public.enterprises (id, name, city, time_zone)
VALUES (100004, 'Best drivers ever', 'Paris', 'Europe/Paris');

INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100005, 100000, '4Y1SL65848Z411439', 'dark blue', 10000, 60000, 2008, 100002, '2020-01-01 20:00:00.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100006, 100001, '8F0KL65848Z490765', 'white', 5000, 120000, 2003, 100003, '2011-01-01 09:00:00.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100007, 100001, '90KKL65848Z490000', 'black', 9000, 1200, 2009, 100004, '2008-01-01 10:00:00.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100025, 100001, 'vZa6Kab9B5oN8RnM7', 'Purple', 36.53, 130261, 2002, 100002, '2011-07-31 21:19:59.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100026, 100000, 'evOeRcL3QiEzmAuLr', 'Black', 215.89, 73871, 2008, 100002, '2000-06-25 14:41:54.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100027, 100000, 'TujODTK6pAkuOwKr1', 'Red', 966.83, 356611, 2013, 100002, '2008-11-16 05:34:45.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100028, 100001, 'TrGAoGGtCAl9cBkGG', 'Blue', 126.84, 43518, 1989, 100002, '2008-07-01 09:31:18.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100029, 100001, 'C3Apaxc6Q7htgR8zi', 'Green', 285.31, 152761, 1992, 100002, '2001-02-13 14:23:06.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100031, 100000, 'LY10zxmyagrApRs6x', 'Dark blue', 288.99, 446421, 1989, 100003, '2006-05-14 19:09:22.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100033, 100000, 'Vl2a5aTfmR0C7um3h', 'Red', 720.56, 246190, 1993, 100003, '2018-09-02 20:18:02.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100035, 100000, 'pysE8AJoAJd1CFwDw', 'Pink', 970.65, 45103, 2006, 100003, '2003-12-29 21:28:59.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100036, 100001, 'qMg46k3kG68LNkW5M', 'Purple', 786.64, 231153, 1992, 100003, '2009-04-06 23:27:22.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date)
VALUES (100038, 100000, 'Wku3MENiYzdGANqr3', 'Orange', 29.28, 23840, 1989, 100003, '2011-09-23 03:55:26.000000');

INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES (100008, 'Dominic', 'Toretto', 1000, 20, 100002, 100005, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES (100009, 'Letty', 'Ortiz', 2000, 10, 100003, 100006, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES (100010, 'Mia', 'Toretto', 3000, 5, 100004, 100007, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES (100011, 'Brian', 'O''Conner', 4000, 5, 100004, 100007, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES (100030, 'FN0', 'SN0', 251.54, 11, 100002, 100029, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES (100032, 'FN0', 'SN0', 11.5, 19, 100003, 100031, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES (100034, 'FN1', 'SN1', 175.42, 32, 100003, 100033, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES (100037, 'FN2', 'SN2', 179.22, 9, 100003, 100036, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES (100039, 'FN3', 'SN3', 136.66, 32, 100003, 100038, false);


INSERT INTO public.managers (id, login, password, first_name, second_name)
VALUES (100012, 'amy.lee@gmail.com', 'amy', 'Amy', 'Lee');
INSERT INTO public.managers (id, login, password, first_name, second_name)
VALUES (100013, 'user.user@gmail.com', 'pass', 'User', 'User');
INSERT INTO public.managers (id, login, password, first_name, second_name)
VALUES (100014, 'john.smith@gmail.com', 'j', 'John', 'Smith');


/*INSERT INTO users (login, password, first_name, second_name)
VALUES ('user.user@gmail.com', 'pass', 'User', 'User'),
       ('user1.user1@gmail.com', 'pass', 'User1', 'User1');*/

INSERT INTO enterprises_managers (enterprise_id, manager_id)
VALUES (100002, 100012),
       (100003, 100012),
       (100003, 100013),
       (100004, 100013);