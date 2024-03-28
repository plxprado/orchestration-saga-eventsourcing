DROP SCHEMA eventsourcing;

CREATE SCHEMA eventsourcing AUTHORIZATION postgres;
-

-- Drop table

-- DROP TABLE eventsourcing.command_business_context;

CREATE TABLE eventsourcing.command_business_context (
	id int8 NOT NULL,
	"name" varchar NOT NULL,
	crreation_date timestamptz NOT NULL,
	CONSTRAINT command_acction_pk PRIMARY KEY (id),
	CONSTRAINT command_acction_unique UNIQUE (name)
);

-- Permissions

ALTER TABLE eventsourcing.command_business_context OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.command_business_context TO postgres;


-- eventsourcing.process_saga_status definition

-- Drop table

-- DROP TABLE eventsourcing.process_saga_status;

CREATE TABLE eventsourcing.process_saga_status (
	id int8 NOT NULL,
	"name" varchar NOT NULL,
	CONSTRAINT process_saga_status_pk PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE eventsourcing.process_saga_status OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.process_saga_status TO postgres;


-- eventsourcing.aggregation_controller definition

-- Drop table

-- DROP TABLE eventsourcing.aggregation_controller;

CREATE TABLE eventsourcing.aggregation_controller (
	id_transaction_event uuid NULL,
	"version" int8 NOT NULL,
	id uuid NOT NULL,
	related_id uuid NULL,
	creation_date timestamptz NOT NULL,
	command_id int8 NOT NULL,
	CONSTRAINT event_aggregation_pk PRIMARY KEY (id),
	CONSTRAINT event_aggregation_unique UNIQUE (id_transaction_event, command_id, version),
	CONSTRAINT event_aggregation_command_business_context_fk FOREIGN KEY (command_id) REFERENCES eventsourcing.command_business_context(id),
	CONSTRAINT event_aggregation_event_aggregation_fk FOREIGN KEY (related_id) REFERENCES eventsourcing.aggregation_controller(id)
);

-- Permissions

ALTER TABLE eventsourcing.aggregation_controller OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.aggregation_controller TO postgres;


-- eventsourcing.event_content definition

-- Drop table

-- DROP TABLE eventsourcing.event_content;

CREATE TABLE eventsourcing.event_content (
	id uuid NOT NULL,
	transaction_value numeric(12, 4) NOT NULL,
	"type" varchar NOT NULL,
	transaction_id uuid NOT NULL,
	aggregation_id uuid NOT NULL,
	transaction_date timestamptz NOT NULL,
	CONSTRAINT event_unique UNIQUE (id),
	CONSTRAINT event_event_aggregation_fk FOREIGN KEY (aggregation_id) REFERENCES eventsourcing.aggregation_controller(id)
);

-- Permissions

ALTER TABLE eventsourcing.event_content OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.event_content TO postgres;


-- eventsourcing.saga_roudmap definition

-- Drop table

-- DROP TABLE eventsourcing.saga_roudmap;

CREATE TABLE eventsourcing.saga_roudmap (
	id int8 NOT NULL,
	order_step int8 NOT NULL,
	finalizer bool NOT NULL,
	command_id int8 NOT NULL,
	step_name varchar NOT NULL,
	creation_date timestamptz NOT NULL,
	CONSTRAINT saga_cicle_pk PRIMARY KEY (id),
	CONSTRAINT saga_cicle_command_acction_fk FOREIGN KEY (command_id) REFERENCES eventsourcing.command_business_context(id)
);

-- Permissions

ALTER TABLE eventsourcing.saga_roudmap OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.saga_roudmap TO postgres;


-- eventsourcing."snapshot" definition

-- Drop table

-- DROP TABLE eventsourcing."snapshot";

CREATE TABLE eventsourcing."snapshot" (
	id int8 NOT NULL,
	"version" int8 NOT NULL,
	aggregation_id uuid NOT NULL,
	creation_date timestamptz NOT NULL,
	CONSTRAINT snapshot_pk PRIMARY KEY (id),
	CONSTRAINT snapshot_event_aggregation_fk FOREIGN KEY (aggregation_id) REFERENCES eventsourcing.aggregation_controller(id)
);

-- Permissions

ALTER TABLE eventsourcing."snapshot" OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing."snapshot" TO postgres;


-- eventsourcing.saga_event_stream definition

-- Drop table

-- DROP TABLE eventsourcing.saga_event_stream;

CREATE TABLE eventsourcing.saga_event_stream (
	id int8 NOT NULL,
	status varchar NOT NULL,
	saga_roudmap_id int8 NOT NULL,
	aggregation_id uuid NOT NULL,
	transaction_json jsonb NULL,
	date_processed timestamptz NOT NULL,
	process_status_id int8 NOT NULL,
	CONSTRAINT saga_event_aggregation_pk PRIMARY KEY (id),
	CONSTRAINT saga_event_aggregation_unique UNIQUE (status, saga_roudmap_id, aggregation_id),
	CONSTRAINT saga_event_aggregation_event_aggregation_fk FOREIGN KEY (aggregation_id) REFERENCES eventsourcing.aggregation_controller(id),
	CONSTRAINT saga_event_aggregation_saga_cicle_fk FOREIGN KEY (saga_roudmap_id) REFERENCES eventsourcing.saga_roudmap(id),
	CONSTRAINT saga_event_stream_process_saga_status_fk FOREIGN KEY (process_status_id) REFERENCES eventsourcing.process_saga_status(id)
);

-- Permissions

ALTER TABLE eventsourcing.saga_event_stream OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.saga_event_stream TO postgres;


-- Permissions

GRANT ALL ON SCHEMA eventsourcing TO postgres;


-- DROP SEQUENCE eventsourcing.sq_saga_cicle_id;

CREATE SEQUENCE eventsourcing.sq_saga_saga_roudmap_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE
	OWNED BY eventsourcing.saga_roudmap.id;

-- Permissions


CREATE SEQUENCE eventsourcing.sq_saga_event_stream_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE
	OWNED BY eventsourcing.saga_event_stream.id;




CREATE SEQUENCE eventsourcing.sq_process_saga_status_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE
	OWNED BY eventsourcing.process_saga_status.id;



CREATE SEQUENCE eventsourcing.sq_snapshot_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE
	OWNED BY eventsourcing."snapshot".id;

CREATE SEQUENCE eventsourcing.sq_command_business_context_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE
	OWNED BY eventsourcing.command_business_context.id;

CREATE SEQUENCE eventsourcing.sq_saga_roudmap_item_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE
	OWNED BY eventsourcing.saga_roudmap_item.id;

