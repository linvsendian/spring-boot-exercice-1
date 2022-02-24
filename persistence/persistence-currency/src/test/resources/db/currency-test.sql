DROP SCHEMA IF EXISTS exercicedb;
CREATE SCHEMA exercicedb;

CREATE TABLE IF NOT EXISTS public.T_CURRENCIES
(
    ID       SERIAL PRIMARY KEY NOT NULL,
    CODE     VARCHAR(3)         NOT NULL,
    SYMBOL   VARCHAR(1)         NOT NULL,
    DECIMALS INT                NOT NULL
);
CREATE INDEX currencies_code_index ON public.T_CURRENCIES (CODE);
INSERT INTO public.t_currencies(code, symbol, decimals)
VALUES ('EUR', '€', 2);
INSERT INTO public.t_currencies(code, symbol, decimals)
VALUES ('USD', '$', 2);