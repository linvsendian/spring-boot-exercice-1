SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS exercicedb;
CREATE DATABASE exercicedb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';

REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO "user";
GRANT ALL ON SCHEMA public TO PUBLIC;

CREATE TABLE IF NOT EXISTS public.T_CURRENCIES
(
    ID       SERIAL PRIMARY KEY NOT NULL,
    CODE     VARCHAR(3)         NOT NULL,
    SYMBOL   VARCHAR(1)         NOT NULL,
    DECIMALS INT                NOT NULL
);
CREATE INDEX currencies_code_index ON public.T_CURRENCIES (CODE);

CREATE TABLE IF NOT EXISTS public.T_RATES
(
    ID            SERIAL PRIMARY KEY NOT NULL,
    BRAND_ID      INT                NOT NULL,
    PRODUCT_ID    INT                NOT NULL,
    START_DATE    DATE               NOT NULL,
    END_DATE      DATE               NOT NULL,
    PRICE         INT                NOT NULL,
    CURRENCY_CODE VARCHAR(3)         NOT NULL
);

CREATE INDEX rates_brand_id_index ON public.T_RATES USING btree (brand_id);
CREATE INDEX rates_product_id_index ON public.T_RATES USING btree (product_id);
CREATE INDEX rates_currency_code_index ON public.T_RATES USING btree (currency_code);

INSERT INTO public.t_currencies (CODE, SYMBOL, DECIMALS)
VALUES ('EUR', '€', 2);
INSERT INTO public.t_currencies (CODE, SYMBOL, DECIMALS)
VALUES ('USD', '$', 2);

INSERT INTO public.t_rates
(brand_id, product_id, start_date, end_date, price, currency_code)
VALUES (1, 1, '2022-01-01', '2022-05-31', 1550, 'EUR');
INSERT INTO public.t_rates
(brand_id, product_id, start_date, end_date, price, currency_code)
VALUES (1, 1, '2022-06-01', '2022-12-31', 1850, 'USD');
INSERT INTO public.t_rates
(brand_id, product_id, start_date, end_date, price, currency_code)
VALUES (2, 1, '2022-01-01', '2022-05-31', 2050, 'EUR');
INSERT INTO public.t_rates
(brand_id, product_id, start_date, end_date, price, currency_code)
VALUES (2, 1, '2022-06-01', '2022-12-31', 2250, 'USD');
INSERT INTO public.t_rates
(brand_id, product_id, start_date, end_date, price, currency_code)
VALUES (1, 2, '2022-01-01', '2022-05-31', 2550, 'EUR');
INSERT INTO public.t_rates
(brand_id, product_id, start_date, end_date, price, currency_code)
VALUES (1, 2, '2022-06-01', '2022-12-31', 2850, 'USD');
INSERT INTO public.t_rates
(brand_id, product_id, start_date, end_date, price, currency_code)
VALUES (2, 2, '2022-01-01', '2022-05-31', 3050, 'EUR');
INSERT INTO public.t_rates
(brand_id, product_id, start_date, end_date, price, currency_code)
VALUES (2, 2, '2022-06-01', '2022-12-31', 3250, 'USD');

