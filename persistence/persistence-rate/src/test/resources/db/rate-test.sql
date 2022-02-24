DROP SCHEMA IF EXISTS exercicedb;
CREATE SCHEMA exercicedb;

-- REVOKE ALL ON SCHEMA public FROM PUBLIC;
-- GRANT ALL ON SCHEMA public TO "user";
-- GRANT ALL ON SCHEMA public TO PUBLIC;


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

CREATE INDEX rates_brand_id_index ON public.T_RATES (BRAND_ID);
CREATE INDEX rates_product_id_index ON public.T_RATES (PRODUCT_ID);
CREATE INDEX rates_currency_code_index ON public.T_RATES (CURRENCY_CODE);