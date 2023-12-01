CREATE TABLE IF NOT EXISTS public.app_user
(
    id uuid NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT app_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_1j9d9a06i600gd43uu3km82jw UNIQUE (email),
    CONSTRAINT app_user_role_check CHECK (role::text = ANY (ARRAY['USER'::character varying, 'ADMIN'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE public.app_user
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.contact_info
(
    id uuid NOT NULL,
    type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    value character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id uuid NOT NULL,
    CONSTRAINT contact_info_pkey PRIMARY KEY (id),
    CONSTRAINT fkcda96p9d2mdon67pk530ehjog FOREIGN KEY (user_id)
        REFERENCES public.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT contact_info_type_check CHECK (type::text = ANY (ARRAY['EMAIL'::character varying, 'MOBILE'::character varying, 'PERSON'::character varying, 'ADDRESS'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE public.contact_info
    OWNER to postgres;