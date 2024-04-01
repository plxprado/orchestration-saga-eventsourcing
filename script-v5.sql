-- DROP SCHEMA eventsourcing;

CREATE SCHEMA eventsourcing AUTHORIZATION postgres;

-- DROP SEQUENCE eventsourcing.sq_process_saga_status_id;

CREATE SEQUENCE eventsourcing.sq_process_saga_status_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE eventsourcing.sq_process_saga_status_id OWNER TO postgres;
GRANT ALL ON SEQUENCE eventsourcing.sq_process_saga_status_id TO postgres;

-- DROP SEQUENCE eventsourcing.sq_saga_cicle_id;

CREATE SEQUENCE eventsourcing.sq_saga_cicle_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE eventsourcing.sq_saga_cicle_id OWNER TO postgres;
GRANT ALL ON SEQUENCE eventsourcing.sq_saga_cicle_id TO postgres;

-- DROP SEQUENCE eventsourcing.sq_saga_event_aggregation_id;

CREATE SEQUENCE eventsourcing.sq_saga_event_aggregation_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE eventsourcing.sq_saga_event_aggregation_id OWNER TO postgres;
GRANT ALL ON SEQUENCE eventsourcing.sq_saga_event_aggregation_id TO postgres;

-- DROP SEQUENCE eventsourcing.sq_saga_event_stream_id;

CREATE SEQUENCE eventsourcing.sq_saga_event_stream_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE eventsourcing.sq_saga_event_stream_id OWNER TO postgres;
GRANT ALL ON SEQUENCE eventsourcing.sq_saga_event_stream_id TO postgres;

-- DROP SEQUENCE eventsourcing.sq_saga_roudmap_item_id;

CREATE SEQUENCE eventsourcing.sq_saga_roudmap_item_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE eventsourcing.sq_saga_roudmap_item_id OWNER TO postgres;
GRANT ALL ON SEQUENCE eventsourcing.sq_saga_roudmap_item_id TO postgres;

-- DROP SEQUENCE eventsourcing.sq_saga_saga_roudmap_id;

CREATE SEQUENCE eventsourcing.sq_saga_saga_roudmap_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE eventsourcing.sq_saga_saga_roudmap_id OWNER TO postgres;
GRANT ALL ON SEQUENCE eventsourcing.sq_saga_saga_roudmap_id TO postgres;

-- DROP SEQUENCE eventsourcing.sq_snapshot_id;

CREATE SEQUENCE eventsourcing.sq_snapshot_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE eventsourcing.sq_snapshot_id OWNER TO postgres;
GRANT ALL ON SEQUENCE eventsourcing.sq_snapshot_id TO postgres;
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
	CONSTRAINT event_unique UNIQUE (id)
);

-- Permissions

ALTER TABLE eventsourcing.event_content OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.event_content TO postgres;


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


-- eventsourcing.saga_workflow definition

-- Drop table

-- DROP TABLE eventsourcing.saga_workflow;

CREATE TABLE eventsourcing.saga_workflow (
	id int8 NOT NULL,
	creation_date timestamptz NOT NULL,
	"name" varchar NOT NULL,
	CONSTRAINT saga_workflow_pk PRIMARY KEY (id),
	CONSTRAINT saga_workflow_unique UNIQUE (name)
);

-- Permissions

ALTER TABLE eventsourcing.saga_workflow OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.saga_workflow TO postgres;


-- eventsourcing.aggregation_controller definition

-- Drop table

-- DROP TABLE eventsourcing.aggregation_controller;

CREATE TABLE eventsourcing.aggregation_controller (
	id_transaction_event uuid NULL,
	"version" int8 NOT NULL,
	id uuid NOT NULL,
	related_id uuid NULL,
	creation_date timestamptz NOT NULL,
	saga_workflow_id int8 NOT NULL,
	CONSTRAINT aggregation_controller_unique UNIQUE (id_transaction_event, saga_workflow_id),
	CONSTRAINT event_aggregation_pk PRIMARY KEY (id),
	CONSTRAINT aggregation_controller_saga_workflow_fk FOREIGN KEY (saga_workflow_id) REFERENCES eventsourcing.saga_workflow(id),
	CONSTRAINT event_aggregation_event_aggregation_fk FOREIGN KEY (related_id) REFERENCES eventsourcing.aggregation_controller(id)
);

-- Permissions

ALTER TABLE eventsourcing.aggregation_controller OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.aggregation_controller TO postgres;


-- eventsourcing.saga_workflow_item definition

-- Drop table

-- DROP TABLE eventsourcing.saga_workflow_item;

CREATE TABLE eventsourcing.saga_workflow_item (
	id int8 NOT NULL,
	step_name varchar NOT NULL,
	step_order int8 NOT NULL,
	saga_workflow_id int8 NOT NULL,
	finalizer bool NOT NULL,
	CONSTRAINT saga_roudmap_item_pk PRIMARY KEY (id),
	CONSTRAINT saga_roudmap_item_unique UNIQUE (step_name, step_order),
	CONSTRAINT saga_workflow_item_saga_workflow_fk FOREIGN KEY (saga_workflow_id) REFERENCES eventsourcing.saga_workflow(id)
);

-- Permissions

ALTER TABLE eventsourcing.saga_workflow_item OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.saga_workflow_item TO postgres;


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


-- eventsourcing.event_stream definition

-- Drop table

-- DROP TABLE eventsourcing.event_stream;

CREATE TABLE eventsourcing.event_stream (
	id int8 NOT NULL,
	status varchar NOT NULL,
	workflow_item_id int8 NOT NULL,
	aggregation_id uuid NOT NULL,
	transaction_json jsonb NULL,
	date_processed timestamptz NOT NULL,
	process_status_id int8 NOT NULL,
	"version" int8 NOT NULL,
	CONSTRAINT event_stream_unique UNIQUE (status, workflow_item_id, aggregation_id, version),
	CONSTRAINT saga_event_aggregation_pk PRIMARY KEY (id),
	CONSTRAINT saga_event_aggregation_event_aggregation_fk FOREIGN KEY (aggregation_id) REFERENCES eventsourcing.aggregation_controller(id),
	CONSTRAINT saga_event_stream_process_saga_status_fk FOREIGN KEY (process_status_id) REFERENCES eventsourcing.process_saga_status(id),
	CONSTRAINT saga_event_stream_saga_roudmap_item_fk FOREIGN KEY (workflow_item_id) REFERENCES eventsourcing.saga_workflow_item(id)
);

-- Permissions

ALTER TABLE eventsourcing.event_stream OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.event_stream TO postgres;




-- Permissions

GRANT ALL ON SCHEMA eventsourcing TO postgres;