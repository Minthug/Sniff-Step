--
-- PostgreSQL database dump
--

-- Dumped from database version 14.11 (Homebrew)
-- Dumped by pg_dump version 14.11 (Homebrew)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: board; Type: TABLE; Schema: public; Owner: minthug
--

CREATE TABLE public.board (
    board_id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone NOT NULL,
    activity_date character varying(255),
    activity_location character varying(255),
    activity_time character varying(255),
    board_type character varying(255),
    description text,
    title character varying(255),
    member_id bigint,
    activity_dates character varying(255),
    activity_times character varying(255),
    CONSTRAINT board_activity_date_check CHECK (((activity_date)::text = ANY ((ARRAY['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying])::text[]))),
    CONSTRAINT board_activity_time_check CHECK (((activity_time)::text = ANY ((ARRAY['MORNING'::character varying, 'AFTERNOON'::character varying, 'EVENING'::character varying])::text[]))),
    CONSTRAINT board_board_type_check CHECK (((board_type)::text = ANY ((ARRAY['PET_SITTER'::character varying, 'PET_OWNER'::character varying])::text[])))
);


ALTER TABLE public.board OWNER TO minthug;

--
-- Name: board_activity_date; Type: TABLE; Schema: public; Owner: minthug
--

CREATE TABLE public.board_activity_date (
    board_board_id bigint NOT NULL,
    activity_date character varying(255),
    board_id bigint NOT NULL,
    CONSTRAINT board_activity_date_activity_date_check CHECK (((activity_date)::text = ANY ((ARRAY['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying])::text[])))
);


ALTER TABLE public.board_activity_date OWNER TO minthug;

--
-- Name: board_activity_time; Type: TABLE; Schema: public; Owner: minthug
--

CREATE TABLE public.board_activity_time (
    board_board_id bigint NOT NULL,
    activity_time character varying(255),
    board_id bigint NOT NULL,
    CONSTRAINT board_activity_time_activity_time_check CHECK (((activity_time)::text = ANY ((ARRAY['MORNING'::character varying, 'AFTERNOON'::character varying, 'EVENING'::character varying])::text[])))
);


ALTER TABLE public.board_activity_time OWNER TO minthug;

--
-- Name: board_board_id_seq; Type: SEQUENCE; Schema: public; Owner: minthug
--

CREATE SEQUENCE public.board_board_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.board_board_id_seq OWNER TO minthug;

--
-- Name: board_board_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: minthug
--

ALTER SEQUENCE public.board_board_id_seq OWNED BY public.board.board_id;


--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: minthug
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO minthug;

--
-- Name: image; Type: TABLE; Schema: public; Owner: minthug
--

CREATE TABLE public.image (
    image_id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone NOT NULL,
    image_name character varying(255),
    s3file_path character varying(255),
    s3url character varying(255),
    unique_name character varying(255),
    board_id bigint,
    member_id bigint
);


ALTER TABLE public.image OWNER TO minthug;

--
-- Name: image_image_id_seq; Type: SEQUENCE; Schema: public; Owner: minthug
--

CREATE SEQUENCE public.image_image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.image_image_id_seq OWNER TO minthug;

--
-- Name: image_image_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: minthug
--

ALTER SEQUENCE public.image_image_id_seq OWNED BY public.image.image_id;


--
-- Name: member; Type: TABLE; Schema: public; Owner: minthug
--

CREATE TABLE public.member (
    member_id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone NOT NULL,
    access_token character varying(255),
    email character varying(255),
    image_url character varying(255),
    introduce character varying(255),
    member_type character varying(255),
    name character varying(255),
    nickname character varying(255),
    password character varying(255),
    phone_number character varying(255),
    provider character varying(255),
    refresh_token character varying(255),
    member_role character varying(255),
    social_id character varying(255),
    CONSTRAINT member_member_role_check CHECK (((member_role)::text = ANY ((ARRAY['GUEST'::character varying, 'USER'::character varying, 'ADMIN'::character varying, 'SOCIAL'::character varying])::text[]))),
    CONSTRAINT member_member_type_check CHECK (((member_type)::text = ANY ((ARRAY['GENERAL'::character varying, 'GOOGLE'::character varying])::text[])))
);


ALTER TABLE public.member OWNER TO minthug;

--
-- Name: member_seq; Type: SEQUENCE; Schema: public; Owner: minthug
--

CREATE SEQUENCE public.member_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.member_seq OWNER TO minthug;

--
-- Name: member_sequence; Type: SEQUENCE; Schema: public; Owner: minthug
--

CREATE SEQUENCE public.member_sequence
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.member_sequence OWNER TO minthug;

--
-- Name: oauth_user; Type: TABLE; Schema: public; Owner: minthug
--

CREATE TABLE public.oauth_user (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    image_url character varying(255),
    name character varying(255) NOT NULL,
    provider character varying(255) NOT NULL,
    provider_id character varying(255) NOT NULL,
    member_id bigint
);


ALTER TABLE public.oauth_user OWNER TO minthug;

--
-- Name: oauth_user_id_seq; Type: SEQUENCE; Schema: public; Owner: minthug
--

CREATE SEQUENCE public.oauth_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.oauth_user_id_seq OWNER TO minthug;

--
-- Name: oauth_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: minthug
--

ALTER SEQUENCE public.oauth_user_id_seq OWNED BY public.oauth_user.id;


--
-- Name: refresh_token; Type: TABLE; Schema: public; Owner: minthug
--

CREATE TABLE public.refresh_token (
    refresh_token_key character varying(255) NOT NULL,
    refresh_token_value character varying(255)
);


ALTER TABLE public.refresh_token OWNER TO minthug;

--
-- Name: board board_id; Type: DEFAULT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.board ALTER COLUMN board_id SET DEFAULT nextval('public.board_board_id_seq'::regclass);


--
-- Name: image image_id; Type: DEFAULT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.image ALTER COLUMN image_id SET DEFAULT nextval('public.image_image_id_seq'::regclass);


--
-- Name: oauth_user id; Type: DEFAULT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.oauth_user ALTER COLUMN id SET DEFAULT nextval('public.oauth_user_id_seq'::regclass);


--
-- Data for Name: board; Type: TABLE DATA; Schema: public; Owner: minthug
--

COPY public.board (board_id, created_at, updated_at, activity_date, activity_location, activity_time, board_type, description, title, member_id, activity_dates, activity_times) FROM stdin;
65	2024-07-05 16:57:46.910095	2024-07-05 16:58:06.86578	\N	\N	\N	\N	This is Patch Method Test	Patch Test	402	MON,TUE,FRI	MORNING,AFTERNOON,EVENING
1	2024-06-01 17:45:12.701999	2024-06-01 18:03:37.866494	\N	Ansan	\N	\N	hi testing now	testMON	2	\N	\N
2	2024-06-01 18:19:53.593499	2024-06-01 18:19:53.585939	\N	Ansan	\N	\N	TEST MESSAGE	Minseok	2	\N	\N
66	2024-07-05 18:50:49.988862	2024-07-06 18:19:27.318852	\N	\N	\N	\N	This is Patch Method Test	Patch Test	402	MON,TUE,FRI,SUN	MORNING,AFTERNOON
5	2024-06-10 17:00:30.308463	2024-06-10 17:49:41.783664	\N	Ansan	\N	\N	hi testing now	testMONday	102	\N	\N
6	2024-06-10 18:16:21.020249	2024-06-10 18:16:21.012298	\N	Ansan	\N	\N	TEST MESSAGE	Minseok	102	\N	\N
7	2024-06-13 19:20:49.168152	2024-06-13 19:20:49.130709	\N	Ansan	\N	\N	TEST MESSAGE	Minseok	102	\N	\N
8	2024-06-21 16:20:35.872593	2024-06-21 16:20:35.867294	\N	Ansan	\N	\N	TEST MESSAGE	Minseok	52	\N	\N
9	2024-06-21 17:18:53.641032	2024-06-21 17:18:53.634477	\N	Seoul	\N	\N	TEST MESSAGE22	Minseok22	252	\N	\N
10	2024-06-25 17:14:10.450326	2024-06-25 17:14:10.426035	\N	Seoul	\N	\N	TEST MESSAGE22	Minseok22	52	\N	\N
11	2024-06-25 17:14:47.099126	2024-06-25 17:14:47.098879	\N	Seoul	\N	\N	TEST MESSAGE22	KIMKIM	52	\N	\N
12	2024-06-25 17:17:02.289801	2024-06-25 17:17:02.261163	\N	Seoul	\N	\N	TEST MESSAGE22	KIMKIM	52	\N	\N
17	2024-06-26 17:29:22.377175	2024-06-26 17:29:22.359134	\N	\N	\N	\N	\N	test	52	\N	\N
18	2024-06-26 17:33:06.851599	2024-06-26 17:33:06.832491	\N	\N	\N	\N	\N	test2	52	\N	\N
19	2024-06-26 17:42:26.282515	2024-06-26 17:42:26.282327	\N	Seoul	\N	\N	TEST MESSAGE22	KIMKIM	302	\N	\N
23	2024-06-26 18:46:45.422455	2024-06-26 18:46:45.406754	\N	Seoul	\N	\N	TEST MESSAGE22	TEST AWS S3	302	\N	\N
24	2024-06-26 18:47:49.485944	2024-06-26 18:47:49.485659	\N	Seoul	\N	\N	TEST MESSAGE22	TEST AWS S3 V2	52	\N	\N
25	2024-06-26 19:22:07.17093	2024-06-26 19:22:07.16115	\N	Seoul	\N	\N	TEST MESSAGE22	TEST AWS S3 V3	352	\N	\N
26	2024-06-27 17:28:55.879788	2024-06-27 17:28:55.874353	\N	Seoul	\N	\N	TEST MESSAGE22	TEST AWS S3 V4	402	\N	\N
63	2024-06-29 21:42:36.151614	2024-07-03 17:36:09.198097	\N	\N	\N	\N	This is Patch Method Test	Patch Test	402	MON,TUE	MORNING,AFTERNOON
64	2024-07-04 18:56:52.594924	2024-07-04 21:10:44.265504	\N	\N	\N	\N	This is Patch Method Test	Patch Test	402	MON,TUE,FRI	MORNING,AFTERNOON,EVENING
67	2024-07-10 18:55:27.99556	2024-07-10 18:55:27.921662	\N	Seoul	\N	\N	TEST MESSAGE223	TEST AWS S3 V13	402	MON,TUE,FRI,SUN	MORNING,AFTERNOON
68	2024-07-11 19:06:29.882028	2024-07-11 19:06:29.862736	\N	Seoul	\N	\N	TEST MESSAGE223	TEST AWS S3 V13	402	MON,TUE,FRI,SUN	MORNING,AFTERNOON
69	2024-07-11 21:38:58.63587	2024-07-11 21:38:58.610792	\N	Seoul	\N	\N	TEST MESSAGE223	TEST AWS S3 V13	402	MON,TUE,FRI,SUN	MORNING,AFTERNOON,EVENING
70	2024-07-17 19:09:15.901646	2024-07-17 19:09:15.900947	\N	Seoul	\N	\N	test	TEST AWS S3 V13	452	MON,TUE,FRI,SUN	MORNING,AFTERNOON,EVENING
71	2024-07-17 19:12:45.999207	2024-07-17 21:01:21.3549	\N	\N	\N	\N	test	test patch	452	TUE,FRI	MORNING
\.


--
-- Data for Name: board_activity_date; Type: TABLE DATA; Schema: public; Owner: minthug
--

COPY public.board_activity_date (board_board_id, activity_date, board_id) FROM stdin;
\.


--
-- Data for Name: board_activity_time; Type: TABLE DATA; Schema: public; Owner: minthug
--

COPY public.board_activity_time (board_board_id, activity_time, board_id) FROM stdin;
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: minthug
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	<< Flyway Baseline >>	BASELINE	<< Flyway Baseline >>	\N	minthug	2024-07-10 17:59:37.621249	0	t
2	2	create backup	SQL	V2__create_backup.sql	0	minthug	2024-07-18 18:59:18.715901	16	t
\.


--
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: minthug
--

COPY public.image (image_id, created_at, updated_at, image_name, s3file_path, s3url, unique_name, board_id, member_id) FROM stdin;
1	2024-06-01 17:45:12.742289	2024-06-01 17:45:12.739446	pngtree.png	\N	\N	8281a673-4ab5-40de-a7de-6e71b941a821.png	1	\N
2	2024-06-01 18:19:53.690368	2024-06-01 18:19:53.68861	test.png	\N	\N	805a27a8-79ba-4601-9cf8-90287e6de327.png	2	\N
5	2024-06-10 17:00:30.31502	2024-06-10 17:00:30.314741	test.png	\N	\N	21d2a579-7869-4c61-b1fd-bae15698cc23.png	5	\N
6	2024-06-10 18:16:21.052378	2024-06-10 18:16:21.050834	test.png	\N	\N	a8be60c1-f315-407a-8719-11df8c696eb8.png	6	\N
7	2024-06-13 19:20:49.281236	2024-06-13 19:20:49.277799	test.png	\N	\N	80939f6e-2865-46a7-841a-f5e3f1e2d8ab.png	7	\N
8	2024-06-21 16:20:35.932025	2024-06-21 16:20:35.927783	test.png	\N	\N	451d9432-2420-452b-b3ef-555a6d31f6ec.png	8	\N
9	2024-06-21 17:18:53.682192	2024-06-21 17:18:53.678222	test.png	\N	\N	8cf6dcd2-be16-4c66-a215-7151ea4f8f91.png	9	\N
10	2024-06-25 17:14:10.492242	2024-06-25 17:14:10.48927	test.png	\N	\N	05672414-bb86-4dd9-91e9-a3bc3c549acf.png	10	\N
11	2024-06-25 17:14:47.104231	2024-06-25 17:14:47.10415	test.png	\N	\N	98d46edd-c938-4d67-bd8f-5ea2e75255ff.png	11	\N
12	2024-06-25 17:17:02.346532	2024-06-25 17:17:02.341447	test.png	\N	\N	cfb8a429-d824-4a6a-9c31-8d8ddf3cb66d.png	12	\N
17	2024-06-26 17:29:22.396867	2024-06-26 17:29:22.394528	istockphoto-1482199015-612x612.jpg	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/images/board/750d9d11-8dc5-4884-9a2b-8fb404828801.jpg	750d9d11-8dc5-4884-9a2b-8fb404828801.jpg	\N	17	\N
18	2024-06-26 17:33:06.901098	2024-06-26 17:33:06.898406	pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/images/board/a598f30e-c86c-4c68-bea9-2708d7b3dac4.jpg	a598f30e-c86c-4c68-bea9-2708d7b3dac4.jpg	\N	18	\N
19	2024-06-26 17:42:26.310144	2024-06-26 17:42:26.310067	test.png	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/images/board/72afd034-0013-42bf-9d6c-c9e4beb9b27f.png	72afd034-0013-42bf-9d6c-c9e4beb9b27f.png	\N	19	\N
23	2024-06-26 18:46:45.447483	2024-06-26 18:46:45.444108	test.png	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/302/4cb26770-d2b6-4a4c-86ae-68897867c1a1.png	4cb26770-d2b6-4a4c-86ae-68897867c1a1.png	\N	23	\N
24	2024-06-26 18:47:49.491322	2024-06-26 18:47:49.491228	test.png	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/52/0331aca6-70a4-4746-bb84-08e980ae7316.png	0331aca6-70a4-4746-bb84-08e980ae7316.png	\N	24	\N
25	2024-06-26 19:22:07.220326	2024-06-26 19:22:07.218785	다운로드.jpeg	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/352/a74489e5-55ca-449a-ac21-4420bba484eb.jpeg	a74489e5-55ca-449a-ac21-4420bba484eb.jpeg	\N	25	\N
26	2024-06-27 17:28:55.905346	2024-06-27 17:28:55.902979	다운로드.jpeg	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/c9b03993-4d61-48fb-9a1d-4ccb3670e92f.jpeg	c9b03993-4d61-48fb-9a1d-4ccb3670e92f.jpeg	\N	26	\N
61	2024-06-29 21:42:36.161842	2024-06-29 21:42:36.161656	다운로드.jpeg	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/fdde5c4d-d935-44b9-880f-ebb8e334cc85.jpeg	fdde5c4d-d935-44b9-880f-ebb8e334cc85.jpeg	\N	63	\N
62	2024-07-03 17:36:09.15139	2024-07-03 17:36:09.059475	istockphoto-1482199015-612x612.jpg	\N	\N	ae53a992-1d87-4188-a4ab-86d7f2f25e46.jpg	63	\N
63	2024-07-03 17:40:00.52075	2024-07-03 17:40:00.520379	다운로드.jpeg	\N	\N	000410c3-edc7-40f6-949e-78c800aac49a.jpeg	63	\N
64	2024-07-04 18:56:52.671248	2024-07-04 18:56:52.659205	다운로드 (1).jpeg	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/260c25af-652f-4af4-8b79-cb3684805ace.jpeg	260c25af-652f-4af4-8b79-cb3684805ace.jpeg	\N	64	\N
65	2024-07-04 21:10:44.216381	2024-07-04 21:10:44.195283	다운로드.jpeg	\N	\N	20043642-8db4-465c-8fe5-7f6c854338de.jpeg	64	\N
66	2024-07-04 21:38:54.683404	2024-07-04 21:38:54.662484	다운로드.jpeg	\N	\N	b69c564d-5a41-4a45-9bcf-248dba972a55.jpeg	64	\N
67	2024-07-05 16:57:46.976623	2024-07-05 16:57:46.973465	다운로드 (1).jpeg	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/d7bf51e6-232d-47b0-a980-da8582eb6299.jpeg	d7bf51e6-232d-47b0-a980-da8582eb6299.jpeg	\N	65	\N
68	2024-07-05 16:58:06.860489	2024-07-05 16:58:06.860225	다운로드.jpeg	\N	\N	65d608c8-9d92-4b17-9cfe-ae6d023cddbc.jpeg	65	\N
69	2024-07-05 17:47:08.392924	2024-07-05 17:47:08.28646	다운로드.jpeg	member/402	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/2893d9da-f029-4e11-9308-4cdc77bedf4c.jpeg	f872176e-6060-413d-82b2-ca5409642045.jpeg	65	\N
70	2024-07-05 17:48:10.018143	2024-07-05 17:48:10.017426	istockphoto-1482199015-612x612.jpg	member/402	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/c615d3a0-4d91-4577-96d8-7e00486b32e6.jpg	3d99334f-8bfc-4489-b944-d9a31a67605f.jpg	65	\N
71	2024-07-05 18:50:50.663016	2024-07-05 18:50:50.658424	다운로드 (1).jpeg	member/402/boards/66	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/boards/66/fbe5d584-62cb-4cb7-9dcb-3bcd97aefcaf.jpeg	5906928a-ffd1-4d75-ba6f-69e16ed0b8e4.jpeg	66	\N
72	2024-07-06 18:19:27.293128	2024-07-06 18:19:27.271659	pngtree.png	member/402/boards/66	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/boards/66/bd39482f-7f3a-46a3-9acc-a7c6c2c798f3.png	922772d1-aac2-43ca-abfa-ff3dc419780c.png	66	\N
73	2024-07-10 18:55:28.593778	2024-07-10 18:55:28.59078	다운로드 (1).jpeg	member/402/boards/67	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/boards/67/6891e9d4-a79a-4c52-bd9b-c07be9328642.jpeg	a3e4cb93-2bbf-4045-ba56-b86acfd8bea6.jpeg	67	\N
74	2024-07-11 19:06:30.459527	2024-07-11 19:06:30.453651	다운로드 (1).jpeg	member/402/boards/68	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/boards/68/e61dd94b-9bbc-4a71-b081-ff7e357f1e10.jpeg	c0d72ece-a973-40fb-a106-72f527bf7dfa.jpeg	68	\N
75	2024-07-11 21:38:59.270976	2024-07-11 21:38:59.267173	다운로드 (1).jpeg	member/402/boards/69	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/boards/69/350f353e-2c1c-4b51-af31-4128e0869085.jpeg	8f0b3925-afe3-44da-b826-c4f8fff3939e.jpeg	69	\N
76	2024-07-17 19:09:16.912097	2024-07-17 19:09:16.906391	다운로드 (1).jpeg	member/452/boards/70	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/452/boards/70/992e5394-0a46-4b0f-86a7-a20cd6d27da5.jpeg	a26328f3-6c15-4cea-9a7a-7dd226245c16.jpeg	70	\N
77	2024-07-17 19:12:47.158159	2024-07-17 19:12:47.123476	다운로드 (1).jpeg	member/452/boards/71	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/452/boards/71/1192c88c-690d-4e7b-b552-8a6b1e88845f.jpeg	1d382302-1a29-4744-b442-18c2afdd3ac0.jpeg	71	\N
\.


--
-- Data for Name: member; Type: TABLE DATA; Schema: public; Owner: minthug
--

COPY public.member (member_id, created_at, updated_at, access_token, email, image_url, introduce, member_type, name, nickname, password, phone_number, provider, refresh_token, member_role, social_id) FROM stdin;
202	2024-06-21 15:30:28.267013	2024-06-21 15:30:28.234172	\N	test4@email.com	\N	hi14	GENERAL	test4	momomo4	{bcrypt}$2a$10$KZ5gF2L.bnc71RD/Hz1rvuYNIwC6t3kh6BbVfXJ3Q3k0PBebHmSKm	010-5444-1234	\N	\N	USER	\N
2	2024-06-01 16:05:00.790315	2024-06-01 16:05:00.722439	\N	momo2@email.com	\N	hi	\N	woo13	momo	{bcrypt}$2a$10$yM8pgN4nLFA2n/noMVnv..RnzhI/1fHHW6lPua9YUZ.W8t6P237Nm	010-1234-1234	\N	\N	USER	\N
52	2024-06-03 15:56:17.095457	2024-06-21 16:20:09.42921	eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsImV4cCI6MTcxNzQwMTM3NywiZW1haWwiOiJ2dnNvZ2lAZ21haWwuY29tIn0.uzDpf5rIkeTZs0eA2ic3xV2J3B0pqqp50pzM9AQf4Kh8J2FAyf2DPlVKua190eRHobkB9SHDXC3Ln8WiuWnjuw	vvsogi@gmail.com	https://lh3.googleusercontent.com/a/ACg8ocINhRCtIt_SWq2i4Kk4HdpL1eyjiLDUOK00HgMTB9lGcwKYlqWu=s96-c	New introduction12	GOOGLE	김우석	new	{bcrypt}$2a$10$8I2cdcQGMZca/fPa6m9lGuzbcgem8TX9bg8ut.gkh83Aj..Qc20Pu	010-1234-5678	google	eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSZWZyZXNoVG9rZW4iLCJleHAiOjE3MTk5ODk3NzcsImVtYWlsIjoidnZzb2dpQGdtYWlsLmNvbSJ9.YoHZt3S4WVUi1bIVrpLMod6K8PXed7dEjgFPw9uQA6Xi_TkbUNPpOXqPz5GOsz_IQlBDeb9UBepudsVqcSY-9w	USER	117284599072019177951
102	2024-06-10 16:46:28.212747	2024-06-10 18:15:41.924643	\N	momo3@email.com	\N	New introduction12	\N	min	new	{bcrypt}$2a$10$5Qg.wt608IvsK8nkgvPuvuQwarJTrMJumrnQXRLgSdWrp6Dyuksky	010-1234-5678	\N	\N	USER	\N
252	2024-06-21 17:18:12.457912	2024-07-17 18:44:24.16571	eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsImV4cCI6MTcyMTIxMzA2NCwiZW1haWwiOiJqa2lzaW1tb3J0YWw0QGdtYWlsLmNvbSJ9.Gr1eQq3QL2hfkKyJbFBXC3_VK_bNdu0j-2t3zjJIa7Kx-pJFulQhhIqE0pYTrvo9GM41kaW4Ssp4VOpOTBlE0g	jkisimmortal4@gmail.com	https://lh3.googleusercontent.com/a/ACg8ocLHmPz8AI2EX3qZdD5Yh7OpI3UKRMLFxWJd2katTkeuji2etrtX=s96-c	\N	GOOGLE	김민석	\N	\N	\N	google	eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSZWZyZXNoVG9rZW4iLCJleHAiOjE3MjM4MDE0NjQsImVtYWlsIjoiamtpc2ltbW9ydGFsNEBnbWFpbC5jb20ifQ.YV3oqix1eBn4NN3ncONsnE2PlWdHRrIpm4r73YSXAUVmYX8GZB73HfyBjdg_BhoWN3WDC6HfjU1Qlx26-L-Veg	USER	108553246091781192919
152	2024-06-14 18:46:41.120323	2024-06-14 18:51:51.260873	\N	test@email.com	\N	New introduction12	GENERAL	test	new	{bcrypt}$2a$10$u1VLQfbA6rL7bOSLTiwCVuKxFXn4CQcypfrKEWoMNmj/eOx7woEt6	010-1234-5678	\N	\N	USER	\N
302	2024-06-26 17:42:00.939897	2024-06-26 19:17:20.175089	\N	jkisimmortal@naver.com	[org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@5943208e]	hi14	GENERAL	test4	momomomo4	{bcrypt}$2a$10$2GcnsboFnEKtm/kk09MPIu5bsQW3BnDRRe5L0NJ5xak5K34GZZ2AO	010-5444-1234	\N	\N	USER	\N
352	2024-06-26 19:20:18.755429	2024-06-27 17:03:29.700575	\N	testId@naver.com	\N	hi14	GENERAL	testId	thisTestId	{bcrypt}$2a$10$MwwaIurYJpOgRE1O309/sO/T3VBP/i66z4.7esPh1hN2/lIkWX8lW	010-5444-1234	\N	\N	USER	\N
402	2024-06-27 17:27:31.115357	2024-07-09 19:30:42.791602	\N	testId2@naver.com	https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/member/402/profile/1655b159-d8e4-47c5-ac2b-473dfac0aa29_pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg/2a5edb7d-bf8e-48a7-868b-bed2c110ca14.jpg	hi15	GENERAL	testId2	thisTestId2	{bcrypt}$2a$10$cRKMR2GiNqKH64eIuDvKfei1sgcmLsMEMDkRCNsP6aFEA7ARIxYza	010-5555-1234	\N	\N	USER	\N
452	2024-07-15 18:12:35.607383	2024-07-15 18:12:35.588651	\N	testId3@naver.com	\N	hi153	GENERAL	\N	thisTestId3	{bcrypt}$2a$10$cbzO15S7Rr6yDbKNCa7v7e0Y5e8cznjK34M5PgyMJKBqr/S02X/kO	010-1234-1234	\N	\N	USER	\N
\.


--
-- Data for Name: oauth_user; Type: TABLE DATA; Schema: public; Owner: minthug
--

COPY public.oauth_user (id, email, image_url, name, provider, provider_id, member_id) FROM stdin;
\.


--
-- Data for Name: refresh_token; Type: TABLE DATA; Schema: public; Owner: minthug
--

COPY public.refresh_token (refresh_token_key, refresh_token_value) FROM stdin;
\.


--
-- Name: board_board_id_seq; Type: SEQUENCE SET; Schema: public; Owner: minthug
--

SELECT pg_catalog.setval('public.board_board_id_seq', 71, true);


--
-- Name: image_image_id_seq; Type: SEQUENCE SET; Schema: public; Owner: minthug
--

SELECT pg_catalog.setval('public.image_image_id_seq', 77, true);


--
-- Name: member_seq; Type: SEQUENCE SET; Schema: public; Owner: minthug
--

SELECT pg_catalog.setval('public.member_seq', 501, true);


--
-- Name: member_sequence; Type: SEQUENCE SET; Schema: public; Owner: minthug
--

SELECT pg_catalog.setval('public.member_sequence', 1, false);


--
-- Name: oauth_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: minthug
--

SELECT pg_catalog.setval('public.oauth_user_id_seq', 1, false);


--
-- Name: board board_pkey; Type: CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.board
    ADD CONSTRAINT board_pkey PRIMARY KEY (board_id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: image image_pkey; Type: CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_pkey PRIMARY KEY (image_id);


--
-- Name: member member_pkey; Type: CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT member_pkey PRIMARY KEY (member_id);


--
-- Name: oauth_user oauth_user_pkey; Type: CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.oauth_user
    ADD CONSTRAINT oauth_user_pkey PRIMARY KEY (id);


--
-- Name: refresh_token refresh_token_pkey; Type: CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT refresh_token_pkey PRIMARY KEY (refresh_token_key);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: minthug
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: board_activity_time fk8p62cyl1dbp9y4v537l8gshe6; Type: FK CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.board_activity_time
    ADD CONSTRAINT fk8p62cyl1dbp9y4v537l8gshe6 FOREIGN KEY (board_board_id) REFERENCES public.board(board_id);


--
-- Name: board_activity_date fkecuhjcu9axdqf2x3nkq87i3jr; Type: FK CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.board_activity_date
    ADD CONSTRAINT fkecuhjcu9axdqf2x3nkq87i3jr FOREIGN KEY (board_id) REFERENCES public.board(board_id);


--
-- Name: image fkil875c0myaxwwf0hty0u1ej2d; Type: FK CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT fkil875c0myaxwwf0hty0u1ej2d FOREIGN KEY (board_id) REFERENCES public.board(board_id);


--
-- Name: board_activity_date fkkx4abi3naxy7yklh2c1q3aw84; Type: FK CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.board_activity_date
    ADD CONSTRAINT fkkx4abi3naxy7yklh2c1q3aw84 FOREIGN KEY (board_board_id) REFERENCES public.board(board_id);


--
-- Name: oauth_user fkme4ymf05wg1obcrdwhis358lv; Type: FK CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.oauth_user
    ADD CONSTRAINT fkme4ymf05wg1obcrdwhis358lv FOREIGN KEY (member_id) REFERENCES public.member(member_id);


--
-- Name: image fknnvd0itj2hhoyuua7g3ive7vo; Type: FK CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT fknnvd0itj2hhoyuua7g3ive7vo FOREIGN KEY (member_id) REFERENCES public.member(member_id);


--
-- Name: board_activity_time fkogh3oggj3spy79desxd4svg2t; Type: FK CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.board_activity_time
    ADD CONSTRAINT fkogh3oggj3spy79desxd4svg2t FOREIGN KEY (board_id) REFERENCES public.board(board_id);


--
-- Name: board fksds8ox89wwf6aihinar49rmfy; Type: FK CONSTRAINT; Schema: public; Owner: minthug
--

ALTER TABLE ONLY public.board
    ADD CONSTRAINT fksds8ox89wwf6aihinar49rmfy FOREIGN KEY (member_id) REFERENCES public.member(member_id);


--
-- PostgreSQL database dump complete
--

