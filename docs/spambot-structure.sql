--
-- PostgreSQL database dump
--

-- Dumped from database version 12.7
-- Dumped by pg_dump version 12.7

-- Started on 2022-05-30 09:17:52

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
-- TOC entry 2885 (class 0 OID 0)
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
-- TOC entry 2886 (class 0 OID 0)
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


--
-- TOC entry 2887 (class 0 OID 0)
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
-- TOC entry 2878 (class 0 OID 107866)
-- Dependencies: 204
-- Data for Name: keywords; Type: TABLE DATA; Schema: public; Owner: api
--

INSERT INTO public.keywords (id, word) VALUES (1, 'money');
INSERT INTO public.keywords (id, word) VALUES (2, 'link');
INSERT INTO public.keywords (id, word) VALUES (3, 'trader');
INSERT INTO public.keywords (id, word) VALUES (4, 'telegram');
INSERT INTO public.keywords (id, word) VALUES (5, 'stay');
INSERT INTO public.keywords (id, word) VALUES (6, 'scam');
INSERT INTO public.keywords (id, word) VALUES (7, 'profit');
INSERT INTO public.keywords (id, word) VALUES (8, 'phone');
INSERT INTO public.keywords (id, word) VALUES (9, 'hours');
INSERT INTO public.keywords (id, word) VALUES (10, 'bitcoin');
INSERT INTO public.keywords (id, word) VALUES (11, 'account');
INSERT INTO public.keywords (id, word) VALUES (12, 'wallet');
INSERT INTO public.keywords (id, word) VALUES (13, 'usd');
INSERT INTO public.keywords (id, word) VALUES (14, 'startup');
INSERT INTO public.keywords (id, word) VALUES (15, 'start');
INSERT INTO public.keywords (id, word) VALUES (16, 'sir');
INSERT INTO public.keywords (id, word) VALUES (17, 'referral');
INSERT INTO public.keywords (id, word) VALUES (18, 'recommendation');
INSERT INTO public.keywords (id, word) VALUES (19, 'profits');
INSERT INTO public.keywords (id, word) VALUES (20, 'platform');
INSERT INTO public.keywords (id, word) VALUES (21, 'payment');
INSERT INTO public.keywords (id, word) VALUES (22, 'pay');
INSERT INTO public.keywords (id, word) VALUES (23, 'online');
INSERT INTO public.keywords (id, word) VALUES (24, 'note');
INSERT INTO public.keywords (id, word) VALUES (25, 'message');
INSERT INTO public.keywords (id, word) VALUES (26, 'manager');
INSERT INTO public.keywords (id, word) VALUES (27, 'legit');
INSERT INTO public.keywords (id, word) VALUES (28, 'investments');
INSERT INTO public.keywords (id, word) VALUES (29, 'investment');
INSERT INTO public.keywords (id, word) VALUES (30, 'instant');
INSERT INTO public.keywords (id, word) VALUES (31, 'hrs');
INSERT INTO public.keywords (id, word) VALUES (32, 'fees');
INSERT INTO public.keywords (id, word) VALUES (33, 'enquiry');
INSERT INTO public.keywords (id, word) VALUES (34, 'cryptocurrency');
INSERT INTO public.keywords (id, word) VALUES (35, 'crypto');
INSERT INTO public.keywords (id, word) VALUES (36, 'contact');
INSERT INTO public.keywords (id, word) VALUES (37, 'channel');
INSERT INTO public.keywords (id, word) VALUES (38, 'breakthrough');
INSERT INTO public.keywords (id, word) VALUES (39, 'bonuses');
INSERT INTO public.keywords (id, word) VALUES (40, 'bills');
INSERT INTO public.keywords (id, word) VALUES (41, 'below');
INSERT INTO public.keywords (id, word) VALUES (47, 'thanks');
INSERT INTO public.keywords (id, word) VALUES (48, 'bunker');
INSERT INTO public.keywords (id, word) VALUES (49, 'whole');
INSERT INTO public.keywords (id, word) VALUES (50, 'word');
INSERT INTO public.keywords (id, word) VALUES (52, 'smartphones');
INSERT INTO public.keywords (id, word) VALUES (53, 'gmail');
INSERT INTO public.keywords (id, word) VALUES (55, 'salary');
INSERT INTO public.keywords (id, word) VALUES (56, 'details');
INSERT INTO public.keywords (id, word) VALUES (58, 'net');
INSERT INTO public.keywords (id, word) VALUES (59, 'trust');
INSERT INTO public.keywords (id, word) VALUES (60, 'week');
INSERT INTO public.keywords (id, word) VALUES (61, 'trade');
INSERT INTO public.keywords (id, word) VALUES (64, 'charges');
INSERT INTO public.keywords (id, word) VALUES (67, 'commission');
INSERT INTO public.keywords (id, word) VALUES (68, 'invention');
INSERT INTO public.keywords (id, word) VALUES (69, 'inbox');
INSERT INTO public.keywords (id, word) VALUES (70, 'admin');
INSERT INTO public.keywords (id, word) VALUES (71, 'computer');
INSERT INTO public.keywords (id, word) VALUES (72, 'feel');
INSERT INTO public.keywords (id, word) VALUES (74, 'weekend');
INSERT INTO public.keywords (id, word) VALUES (75, 'interest');
INSERT INTO public.keywords (id, word) VALUES (76, 'break');
INSERT INTO public.keywords (id, word) VALUES (77, 'richard');
INSERT INTO public.keywords (id, word) VALUES (78, 'worth');


--
-- TOC entry 2888 (class 0 OID 0)
-- Dependencies: 205
-- Name: keywords_id_seq; Type: SEQUENCE SET; Schema: public; Owner: api
--

SELECT pg_catalog.setval('public.keywords_id_seq', 78, true);


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


--
-- PostgreSQL database dump complete
--

