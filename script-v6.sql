-- DROP SCHEMA orquestra;

CREATE SCHEMA orquestra AUTHORIZATION postgres;

-- DROP SEQUENCE orquestra.sq_evento_saga_stream_id;

CREATE SEQUENCE orquestra.sq_evento_saga_stream_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE orquestra.sq_evento_saga_stream_id OWNER TO postgres;
GRANT ALL ON SEQUENCE orquestra.sq_evento_saga_stream_id TO postgres;

-- DROP SEQUENCE orquestra.sq_status_processo_saga_id;

CREATE SEQUENCE orquestra.sq_status_processo_saga_id
	MINVALUE 0
	NO MAXVALUE
	START 0
	NO CYCLE;

-- Permissions

ALTER SEQUENCE orquestra.sq_status_processo_saga_id OWNER TO postgres;
GRANT ALL ON SEQUENCE orquestra.sq_status_processo_saga_id TO postgres;
-- orquestra.controle_agregacao definition

-- Drop table

-- DROP TABLE orquestra.controle_agregacao;

CREATE TABLE orquestra.controle_agregacao (
	id uuid NOT NULL,
	id_transacao_externa uuid NOT NULL,
	versao int8 NOT NULL,
	id_autorelacao_agregacao uuid NOT NULL,
	data_criacao timestamptz NOT NULL,
	workflow_id int8 NOT NULL,
	CONSTRAINT controle_agregacao_pk PRIMARY KEY (id),
	CONSTRAINT controle_agregacao_unique UNIQUE (id_transacao_externa, workflow_id)
);

-- Permissions

ALTER TABLE orquestra.controle_agregacao OWNER TO postgres;
GRANT ALL ON TABLE orquestra.controle_agregacao TO postgres;


-- orquestra.status_transacao definition

-- Drop table

-- DROP TABLE orquestra.status_transacao;

CREATE TABLE orquestra.status_transacao (
	id int8 NOT NULL,
	nome varchar NOT NULL,
	data_criacao timestamptz NOT NULL,
	CONSTRAINT status_transacao_pk PRIMARY KEY (id),
	CONSTRAINT status_transacao_unique UNIQUE (nome)
);

-- Permissions

ALTER TABLE orquestra.status_transacao OWNER TO postgres;
GRANT ALL ON TABLE orquestra.status_transacao TO postgres;


-- orquestra.evento_saga_stream definition

-- Drop table

-- DROP TABLE orquestra.evento_saga_stream;

CREATE TABLE orquestra.evento_saga_stream (
	id int8 NOT NULL,
	id_agregacao uuid NOT NULL,
	conteudo_orquestrador_fluxo jsonb NULL,
	data_processamento timestamptz NOT NULL,
	versao int8 NOT NULL,
	workflow_item_id int8 NOT NULL,
	id_status_transacao int8 NOT NULL,
	CONSTRAINT evento_saga_stream_pk PRIMARY KEY (id),
	CONSTRAINT evento_saga_stream_unique UNIQUE (id_agregacao, versao, workflow_item_id, id_status_transacao),
	CONSTRAINT evento_saga_stream_controle_agregacao_fk FOREIGN KEY (id_agregacao) REFERENCES orquestra.controle_agregacao(id),
	CONSTRAINT evento_saga_stream_status_transacao_fk FOREIGN KEY (id_status_transacao) REFERENCES orquestra.status_transacao(id)
);

-- Permissions

ALTER TABLE orquestra.evento_saga_stream OWNER TO postgres;
GRANT ALL ON TABLE orquestra.evento_saga_stream TO postgres;




-- Permissions

GRANT ALL ON SCHEMA orquestra TO postgres;
