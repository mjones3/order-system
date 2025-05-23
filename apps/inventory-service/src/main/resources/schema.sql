DROP TABLE IF EXISTS public.inventory;

CREATE TABLE IF NOT EXISTS public.inventory
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    product_id VARCHAR(255),
    price numeric(38,2),
    remaining_quantity integer NOT NULL,
    CONSTRAINT inventory_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.inventory
    OWNER to inventoryuser;


INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00001', 101.19, 11);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00002',  57.83,  7);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00003', 129.45,  3);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00004', 198.72, 12);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00005', 150.00,  5);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00006',  65.37,  8);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00007', 175.89,  2);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00008',  88.12,  9);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00009', 142.56,  4);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00010',  59.99, 10);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00011', 200.00,  6);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00012', 110.55,  2);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00013', 162.30, 12);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00014',  76.44,  3);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00015', 190.10,  7);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00016',  54.25, 11);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00017', 134.78,  5);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00018', 159.99,  8);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00019',  73.21,  4);
INSERT INTO public.inventory (product_id, price, remaining_quantity) VALUES ('A00020', 185.68, 12);


