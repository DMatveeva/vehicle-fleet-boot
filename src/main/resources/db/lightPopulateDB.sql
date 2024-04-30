DELETE
FROM vehicles;
DELETE
FROM vehicle_models;
DELETE
FROM drivers;
DELETE
FROM enterprises;
DELETE
FROM managers;
DELETE
FROM users;
DELETE
FROM enterprises_managers;
DELETE
FROM tracks;
DELETE
FROM vehicle_coordinates;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO public.vehicle_models (id, brand, name, vehicle_type, num_seats, engine_type, load_capacity) VALUES (100000, 'OPEL', 'Astra', 'CAR', 5, 'PETROL', 500);
INSERT INTO public.vehicle_models (id, brand, name, vehicle_type, num_seats, engine_type, load_capacity) VALUES (100001, 'FORD', 'Focus 1', 'CAR', 5, 'PETROL', 400);

INSERT INTO public.enterprises (id, name, city, time_zone) VALUES (100002, 'It''s not the car but the driver', 'Los Angeles', 'America/Los_Angeles');
INSERT INTO public.enterprises (id, name, city, time_zone) VALUES (100003, 'Drive anywhere', 'Las Vegas', 'America/Chicago');
INSERT INTO public.enterprises (id, name, city, time_zone) VALUES (100004, 'Best drivers ever', 'Paris', 'Europe/Paris');

INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100005, 100000, '4Y1SL65848Z411439', 'dark blue', 10000, 60000, 2008, 100002, '2020-01-01 20:00:00.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100006, 100001, '8F0KL65848Z490765', 'white', 5000, 120000, 2003, 100003, '2011-01-01 09:00:00.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100007, 100001, '90KKL65848Z490000', 'black', 9000, 1200, 2009, 100004, '2008-01-01 10:00:00.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100025, 100001, 'vZa6Kab9B5oN8RnM7', 'Purple', 36.53, 130261, 2002, 100002, '2011-07-31 21:19:59.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100026, 100000, 'evOeRcL3QiEzmAuLr', 'Black', 215.89, 73871, 2008, 100002, '2000-06-25 14:41:54.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100027, 100000, 'TujODTK6pAkuOwKr1', 'Red', 966.83, 356611, 2013, 100002, '2008-11-16 05:34:45.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100028, 100001, 'TrGAoGGtCAl9cBkGG', 'Blue', 126.84, 43518, 1989, 100002, '2008-07-01 09:31:18.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100029, 100001, 'C3Apaxc6Q7htgR8zi', 'Green', 285.31, 152761, 1992, 100002, '2001-02-13 14:23:06.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100031, 100000, 'LY10zxmyagrApRs6x', 'Dark blue', 288.99, 446421, 1989, 100003, '2006-05-14 19:09:22.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100033, 100000, 'Vl2a5aTfmR0C7um3h', 'Red', 720.56, 246190, 1993, 100003, '2018-09-02 20:18:02.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100035, 100000, 'pysE8AJoAJd1CFwDw', 'Pink', 970.65, 45103, 2006, 100003, '2003-12-29 21:28:59.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100036, 100001, 'qMg46k3kG68LNkW5M', 'Purple', 786.64, 231153, 1992, 100003, '2009-04-06 23:27:22.000000');
INSERT INTO public.vehicles (id, model_id, vin, color, cost_usd, mileage, production_year, enterprise_id, purchase_date) VALUES (100038, 100000, 'Wku3MENiYzdGANqr3', 'Orange', 29.28, 23840, 1989, 100003, '2011-09-23 03:55:26.000000');

INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100008, 'Dominic', 'Toretto', 1000, 20, 100002, 100005, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100009, 'Letty', 'Ortiz', 2000, 10, 100003, 100006, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100010, 'Mia', 'Toretto', 3000, 5, 100004, 100007, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100011, 'Brian', 'O''Conner', 4000, 5, 100004, 100007, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100015, 'D1', 'D1', 1000, 20, 100002, null, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100016, 'D2', 'D2', 1000, 20, 100002, null, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100017, 'D3', 'D3', 1000, 20, 100002, null, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100018, 'D4', 'D4', 1000, 20, 100002, null, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100030, 'FN0', 'SN0', 251.54, 11, 100002, 100029, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100032, 'FN0', 'SN0', 11.5, 19, 100003, 100031, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100034, 'FN1', 'SN1', 175.42, 32, 100003, 100033, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100037, 'FN2', 'SN2', 179.22, 9, 100003, 100036, false);
INSERT INTO public.drivers (id, first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active) VALUES (100039, 'FN3', 'SN3', 136.66, 32, 100003, 100038, false);


INSERT INTO public.managers (id, login, password, first_name, second_name) VALUES (100012, 'amy.lee@gmail.com', 'amy', 'Amy', 'Lee');
INSERT INTO public.managers (id, login, password, first_name, second_name) VALUES (100013, 'user.user@gmail.com', 'pass', 'User', 'User');
INSERT INTO public.managers (id, login, password, first_name, second_name) VALUES (100014, 'john.smith@gmail.com', 'j', 'John', 'Smith');


/*INSERT INTO users (login, password, first_name, second_name)
VALUES ('user.user@gmail.com', 'pass', 'User', 'User'),
       ('user1.user1@gmail.com', 'pass', 'User1', 'User1');*/

INSERT INTO enterprises_managers (enterprise_id, manager_id)
VALUES (100002, 100012),
       (100003, 100012),
       (100003, 100013),
       (100004, 100013);


INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (100019, 100005, '2008-01-01 18:00:00.000000', '2008-01-01 22:00:00.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (100020, 100006, '2009-01-01 17:00:00.000000', '2009-01-01 23:00:00.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (100040, 100006, '2020-07-27 05:45:53.000000', '2020-07-27 06:26:13.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (100283, 100006, '2019-09-28 06:31:42.000000', '2019-09-28 06:48:02.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (100382, 100006, '2018-04-26 18:53:55.000000', '2018-04-26 19:29:15.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (100595, 100006, '2020-07-28 13:57:58.000000', '2020-07-28 14:35:58.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (100824, 100006, '2020-12-29 08:34:15.000000', '2020-12-29 09:12:55.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101057, 100006, '2020-02-16 00:37:51.000000', '2020-02-16 00:50:41.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101135, 100006, '2019-01-16 10:34:57.000000', '2019-01-16 11:12:27.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101361, 100006, '2019-05-12 10:58:55.000000', '2019-05-12 11:17:55.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101476, 100006, '2020-03-18 10:12:33.000000', '2020-03-18 10:23:03.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101540, 100006, '2020-08-07 09:08:00.000000', '2020-08-07 09:18:50.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101606, 100006, '2018-12-01 11:13:42.000000', '2018-12-01 11:31:02.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101711, 100006, '2018-09-26 07:15:45.000000', '2018-09-26 07:31:05.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101804, 100006, '2019-02-24 11:19:52.000000', '2019-02-24 11:38:32.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101917, 100006, '2019-07-05 01:24:49.000000', '2019-07-05 01:36:59.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (101991, 100006, '2018-07-26 04:02:14.000000', '2018-07-26 04:37:14.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (102202, 100006, '2020-09-09 17:41:42.000000', '2020-09-09 17:57:52.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (102300, 100006, '2018-10-31 14:31:38.000000', '2018-10-31 14:52:48.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (102428, 100006, '2018-12-23 12:21:52.000000', '2018-12-23 12:41:32.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (102547, 100006, '2020-06-05 14:10:13.000000', '2020-06-05 14:22:43.000000');
INSERT INTO public.tracks (id, vehicle_id, started, finished) VALUES (102623, 100006, '2020-06-17 15:42:08.000000', '2020-06-17 16:03:38.000000');