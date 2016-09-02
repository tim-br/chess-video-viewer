--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.5.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: schema_migrations; Type: TABLE; Schema: public; Owner: omeuyemgggibci
--

CREATE TABLE schema_migrations (
    version character varying NOT NULL
);


ALTER TABLE schema_migrations OWNER TO omeuyemgggibci;

--
-- Name: users; Type: TABLE; Schema: public; Owner: omeuyemgggibci
--

CREATE TABLE users (
    id integer NOT NULL,
    full_name character varying,
    is_admin boolean,
    email character varying,
    password character varying,
    password_digest character varying,
    string character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE users OWNER TO omeuyemgggibci;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: omeuyemgggibci
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO omeuyemgggibci;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: omeuyemgggibci
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: videos; Type: TABLE; Schema: public; Owner: omeuyemgggibci
--

CREATE TABLE videos (
    id integer NOT NULL,
    title character varying,
    author character varying,
    url character varying,
    week_number integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    is_beginner boolean DEFAULT false,
    is_advanced boolean DEFAULT false,
    semester_number integer
);


ALTER TABLE videos OWNER TO omeuyemgggibci;

--
-- Name: videos_id_seq; Type: SEQUENCE; Schema: public; Owner: omeuyemgggibci
--

CREATE SEQUENCE videos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE videos_id_seq OWNER TO omeuyemgggibci;

--
-- Name: videos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: omeuyemgggibci
--

ALTER SEQUENCE videos_id_seq OWNED BY videos.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: omeuyemgggibci
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: omeuyemgggibci
--

ALTER TABLE ONLY videos ALTER COLUMN id SET DEFAULT nextval('videos_id_seq'::regclass);


--
-- Data for Name: schema_migrations; Type: TABLE DATA; Schema: public; Owner: omeuyemgggibci
--

COPY schema_migrations (version) FROM stdin;
20150813175111
20150815094231
20150815095005
20150815110110
20150913165416
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: omeuyemgggibci
--

COPY users (id, full_name, is_admin, email, password, password_digest, string, created_at, updated_at) FROM stdin;
2	\N	\N	TownCentreMontessori	\N	$2a$10$ZrjY9xNJqCEKzlOa98j49.PdgV5NZ3m/X8hauXP//PR.B16A1sjEy	\N	2015-08-17 00:07:15.668367	2015-08-17 00:07:15.668367
4	\N	\N	TownCentreMontessori	\N	$2a$10$7BVwk8itVNt4MzWrsb7Gz.2EhT8NkhwMMbKXNU7MIK1GHd2tyUcoO	\N	2015-08-17 00:07:32.365564	2015-08-17 00:07:32.365564
1	Jacob Stein	t	jacobdstein@yahoo.ca	\N	$2a$10$8DT8.y4/KCrUMhzO6vRWOe96ZSC8zw95NDssBBsDLmgukwMF7LTjm	\N	2015-08-17 00:07:15.267076	2015-09-13 17:11:52.386375
5	Artiom	t	samsonkin@list.ru	\N	$2a$10$pObMKx/PN7q36O7YCPbyKeks75xdwxD5.SGofVd./6LdbT0u2/90i	\N	2015-09-13 17:11:52.505683	2015-09-13 17:11:52.505683
3	Jacob Stein	t	jacobdstein@yahoo.ca	\N	$2a$10$uJ4p7O2TsrwuAJBXO5UHMOs8K3hhnyCJE.eIInIJZTVvKX3woDtLi	\N	2015-08-17 00:07:32.214497	2015-09-13 17:16:04.359948
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: omeuyemgggibci
--

SELECT pg_catalog.setval('users_id_seq', 5, true);


--
-- Data for Name: videos; Type: TABLE DATA; Schema: public; Owner: omeuyemgggibci
--

COPY videos (id, title, author, url, week_number, created_at, updated_at, is_beginner, is_advanced, semester_number) FROM stdin;
71	Week 1-Lesson 1. Introduction and pieces. Pawn Game!	\N	https://www.youtube.com/embed/4Xh8LyYyjI0	1	2016-08-03 04:28:55.253175	2016-08-03 04:28:55.253175	t	f	1
\.


--
-- Name: videos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: omeuyemgggibci
--

SELECT pg_catalog.setval('videos_id_seq', 71, true);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: omeuyemgggibci
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: videos_pkey; Type: CONSTRAINT; Schema: public; Owner: omeuyemgggibci
--

ALTER TABLE ONLY videos
    ADD CONSTRAINT videos_pkey PRIMARY KEY (id);


--
-- Name: unique_schema_migrations; Type: INDEX; Schema: public; Owner: omeuyemgggibci
--

CREATE UNIQUE INDEX unique_schema_migrations ON schema_migrations USING btree (version);


--
-- Name: public; Type: ACL; Schema: -; Owner: omeuyemgggibci
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM omeuyemgggibci;
GRANT ALL ON SCHEMA public TO omeuyemgggibci;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

