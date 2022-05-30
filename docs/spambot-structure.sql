--
-- PostgreSQL database dump
--

-- Dumped from database version 12.7
-- Dumped by pg_dump version 12.7

-- Started on 2022-05-30 09:03:31

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

--
-- TOC entry 2 (class 3079 OID 107855)
-- Name: fuzzystrmatch; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS fuzzystrmatch WITH SCHEMA public;


--
-- TOC entry 2883 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION fuzzystrmatch; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION fuzzystrmatch IS 'determine similarities and distance between strings';


--
-- TOC entry 3 (class 3079 OID 107778)
-- Name: pg_trgm; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pg_trgm WITH SCHEMA public;


--
-- TOC entry 2884 (class 0 OID 0)
-- Dependencies: 3
-- Name: EXTENSION pg_trgm; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pg_trgm IS 'text similarity measurement and index searching based on trigrams';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 204 (class 1259 OID 107866)
-- Name: keywords; Type: TABLE; Schema: public; Owner: api
--

CREATE TABLE public.keywords (
    id integer NOT NULL,
    word text NOT NULL
);


ALTER TABLE public.keywords OWNER TO api;

--
-- TOC entry 205 (class 1259 OID 107869)
-- Name: keywords_id_seq; Type: SEQUENCE; Schema: public; Owner: api
--

CREATE SEQUENCE public.keywords_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.keywords_id_seq OWNER TO api;

--
-- TOC entry 2885 (class 0 OID 0)
-- Dependencies: 205
-- Name: keywords_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: api
--

ALTER SEQUENCE public.keywords_id_seq OWNED BY public.keywords.id;


--
-- TOC entry 2747 (class 2604 OID 107871)
-- Name: keywords id; Type: DEFAULT; Schema: public; Owner: api
--

ALTER TABLE ONLY public.keywords ALTER COLUMN id SET DEFAULT nextval('public.keywords_id_seq'::regclass);


--
-- TOC entry 2749 (class 2606 OID 107879)
-- Name: keywords keywords_pkey; Type: CONSTRAINT; Schema: public; Owner: api
--

ALTER TABLE ONLY public.keywords
    ADD CONSTRAINT keywords_pkey PRIMARY KEY (id);


--
-- TOC entry 2751 (class 2606 OID 107881)
-- Name: keywords keywords_word_key; Type: CONSTRAINT; Schema: public; Owner: api
--

ALTER TABLE ONLY public.keywords
    ADD CONSTRAINT keywords_word_key UNIQUE (word);


-- Completed on 2022-05-30 09:03:33

--
-- PostgreSQL database dump complete
--

