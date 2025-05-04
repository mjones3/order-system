CREATE TABLE IF NOT EXISTS public.payments
(
    id integer NOT NULL,
    order_id integer NOT NULL,
    total money DEFAULT 0.00,
    status character varying(64) COLLATE pg_catalog."default",
    CONSTRAINT payments_pkey PRIMARY KEY (id, order_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.payments
    OWNER to paymentuser;