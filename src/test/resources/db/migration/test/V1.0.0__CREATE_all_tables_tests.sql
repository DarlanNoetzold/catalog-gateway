-- Table: public.categorymodel

-- DROP TABLE IF EXISTS public.categorymodel;

CREATE TABLE IF NOT EXISTS public.categorymodel
(
    categoryid uuid NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT categorymodel_pkey PRIMARY KEY (categoryid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.categorymodel
    OWNER to postgres;

-- Table: public.productmodel

-- DROP TABLE IF EXISTS public.productmodel;

CREATE TABLE IF NOT EXISTS public.productmodel
(
    allowautomaticskumarketplacecreation boolean,
    calculatedprice boolean,
    hasvariations boolean,
    warrantytime integer,
    categoryid uuid,
    productid uuid NOT NULL,
    brand character varying(255) COLLATE pg_catalog."default",
    code character varying(255) COLLATE pg_catalog."default",
    definitionpricescope character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    gender character varying(255) COLLATE pg_catalog."default",
    message character varying(255) COLLATE pg_catalog."default",
    model character varying(255) COLLATE pg_catalog."default",
    title character varying(255) COLLATE pg_catalog."default",
    videourl character varying(255) COLLATE pg_catalog."default",
    warrantytext character varying(255) COLLATE pg_catalog."default",
    height bytea,
    length bytea,
    pricefactor bytea,
    weight bytea,
    width bytea,
    CONSTRAINT productmodel_pkey PRIMARY KEY (productid),
    CONSTRAINT fktmpfd90i3i23kjmtklssmbipg FOREIGN KEY (categoryid)
        REFERENCES public.categorymodel (categoryid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.productmodel
    OWNER to postgres;


-- Table: public.skumodel

-- DROP TABLE IF EXISTS public.skumodel;

CREATE TABLE IF NOT EXISTS public.skumodel
(
    enabled boolean,
    height numeric(38,2),
    length numeric(38,2),
    price numeric(38,2),
    saleprice numeric(38,2),
    weight numeric(38,2),
    width numeric(38,2),
    sellerid bigint,
    stocklevel bigint,
    productid uuid,
    skuid uuid NOT NULL,
    displayname character varying(255) COLLATE pg_catalog."default",
    ean character varying(255) COLLATE pg_catalog."default",
    lwhuom character varying(255) COLLATE pg_catalog."default",
    partnerid character varying(255) COLLATE pg_catalog."default",
    weightuom character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT skumodel_pkey PRIMARY KEY (skuid),
    CONSTRAINT fkmlvwji7h8sw53untyx0n6bq9i FOREIGN KEY (productid)
        REFERENCES public.productmodel (productid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.skumodel
    OWNER to postgres;


-- Table: public.attributemodel

-- DROP TABLE IF EXISTS public.attributemodel;

CREATE TABLE IF NOT EXISTS public.attributemodel
(
    attributeid uuid NOT NULL,
    skuid uuid,
    description character varying(255) COLLATE pg_catalog."default",
    hexcode character varying(255) COLLATE pg_catalog."default",
    imageurl character varying(255) COLLATE pg_catalog."default",
    internalname character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    priority character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT attributemodel_pkey PRIMARY KEY (attributeid),
    CONSTRAINT fkjlqnum2r8hox63aai6kbsug2s FOREIGN KEY (skuid)
        REFERENCES public.skumodel (skuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.attributemodel
    OWNER to postgres;

-- Table: public.keywordmodel

-- DROP TABLE IF EXISTS public.keywordmodel;

CREATE TABLE IF NOT EXISTS public.keywordmodel
(
    keywordid uuid NOT NULL,
    skuid uuid,
    keyword character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT keywordmodel_pkey PRIMARY KEY (keywordid),
    CONSTRAINT fkt5t4dcldjk08dfis2mxyn2sit FOREIGN KEY (skuid)
        REFERENCES public.skumodel (skuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.keywordmodel
    OWNER to postgres;

-- Table: public.mediamodel

-- DROP TABLE IF EXISTS public.mediamodel;

CREATE TABLE IF NOT EXISTS public.mediamodel
(
    mediaid uuid NOT NULL,
    skuid uuid,
    largeimageurl character varying(255) COLLATE pg_catalog."default",
    mediumimageurl character varying(255) COLLATE pg_catalog."default",
    smallimageurl character varying(255) COLLATE pg_catalog."default",
    thumbnailimageurl character varying(255) COLLATE pg_catalog."default",
    zoomimageurl character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT mediamodel_pkey PRIMARY KEY (mediaid),
    CONSTRAINT fkkf99ds3sq269uw0twltsk5et8 FOREIGN KEY (skuid)
        REFERENCES public.skumodel (skuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.mediamodel
    OWNER to postgres;