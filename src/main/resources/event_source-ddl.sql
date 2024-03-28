
-- Permissions

ALTER SEQUENCE eventsourcing.sq_saga_event_aggregation_id OWNER TO postgres;
GRANT ALL ON SEQUENCE eventsourcing.sq_saga_event_aggregation_id TO postgres;

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

-- DROP SEQUENCE eventsourcing.sq_step_saga_id;

CREATE SEQUENCE eventsourcing.sq_step_saga_id
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE eventsourcing.sq_step_saga_id OWNER TO postgres;
GRANT ALL ON SEQUENCE eventsourcing.sq_step_saga_id TO postgres;
-- eventsourcing.step_saga definition

-- Drop table

-- DROP TABLE eventsourcing.step_saga;

CREATE TABLE eventsourcing.step_saga (
	id int8 NOT NULL,
	"name" varchar NOT NULL,
	CONSTRAINT step_saga_pk PRIMARY KEY (id),
	CONSTRAINT step_saga_unique UNIQUE (name)
);

-- Permissions

ALTER TABLE eventsourcing.step_saga OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.step_saga TO postgres;


-- eventsourcing.event_aggregation definition

-- Drop table

-- DROP TABLE eventsourcing.event_aggregation;

CREATE TABLE eventsourcing.event_aggregation (
	"name" varchar NOT NULL, -- name
	creatrion_date timetz NOT NULL,
	id_transaction_event uuid NULL,
	command_action varchar NOT NULL,
	"version" int8 NOT NULL,
	id uuid NOT NULL,
	related_id uuid NOT NULL,
	CONSTRAINT event_aggregation_pk PRIMARY KEY (id),
	CONSTRAINT event_aggregation_event_aggregation_fk FOREIGN KEY (related_id) REFERENCES eventsourcing.event_aggregation(id)
);

-- Column comments

COMMENT ON COLUMN eventsourcing.event_aggregation."name" IS 'name';

-- Permissions

ALTER TABLE eventsourcing.event_aggregation OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.event_aggregation TO postgres;


-- eventsourcing.saga_cicle definition

-- Drop table

-- DROP TABLE eventsourcing.saga_cicle;

CREATE TABLE eventsourcing.saga_cicle (
	id int8 NOT NULL,
	"order" int8 NOT NULL,
	finalizer bool NOT NULL,
	id_step int8 NOT NULL,
	CONSTRAINT saga_cicle_pk PRIMARY KEY (id),
	CONSTRAINT saga_cicle_step_saga_fk FOREIGN KEY (id_step) REFERENCES eventsourcing.step_saga(id)
);

-- Permissions

ALTER TABLE eventsourcing.saga_cicle OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.saga_cicle TO postgres;


-- eventsourcing.saga_event_aggregation definition

-- Drop table

-- DROP TABLE eventsourcing.saga_event_aggregation;

CREATE TABLE eventsourcing.saga_event_aggregation (
	id int8 NOT NULL,
	status varchar NOT NULL,
	saga_cicle_id int8 NOT NULL,
	aggregation_id uuid NOT NULL,
	date_processed timetz NOT NULL,
	state_value numeric(14, 2) DEFAULT 0.0 NOT NULL,
	CONSTRAINT saga_event_aggregation_pk PRIMARY KEY (id),
	CONSTRAINT saga_event_aggregation_unique UNIQUE (status),
	CONSTRAINT saga_event_aggregation_event_aggregation_fk FOREIGN KEY (aggregation_id) REFERENCES eventsourcing.event_aggregation(id),
	CONSTRAINT saga_event_aggregation_saga_cicle_fk FOREIGN KEY (saga_cicle_id) REFERENCES eventsourcing.saga_cicle(id)
);

-- Permissions

ALTER TABLE eventsourcing.saga_event_aggregation OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing.saga_event_aggregation TO postgres;


-- eventsourcing."snapshot" definition

-- Drop table

-- DROP TABLE eventsourcing."snapshot";

CREATE TABLE eventsourcing."snapshot" (
	id int8 NOT NULL,
	"version" int8 NOT NULL,
	creation_date timetz NOT NULL,
	aggregation_id uuid NOT NULL,
	CONSTRAINT snapshot_pk PRIMARY KEY (id),
	CONSTRAINT snapshot_event_aggregation_fk FOREIGN KEY (aggregation_id) REFERENCES eventsourcing.event_aggregation(id)
);

-- Permissions

ALTER TABLE eventsourcing."snapshot" OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing."snapshot" TO postgres;


-- eventsourcing."event" definition

-- Drop table

-- DROP TABLE eventsourcing."event";

CREATE TABLE eventsourcing."event" (
	id uuid NOT NULL,
	transaction_date timetz NOT NULL,
	transaction_value numeric(12, 4) NOT NULL,
	"type" varchar NOT NULL,
	transaction_id uuid NOT NULL,
	aggregation_id uuid NOT NULL,
	CONSTRAINT event_unique UNIQUE (id),
	CONSTRAINT event_event_aggregation_fk FOREIGN KEY (aggregation_id) REFERENCES eventsourcing.event_aggregation(id)
);

-- Permissions

ALTER TABLE eventsourcing."event" OWNER TO postgres;
GRANT ALL ON TABLE eventsourcing."event" TO postgres;




-- Permissions

GRANT ALL ON SCHEMA eventsourcing TO postgres;


CREATE SEQUENCE eventsourcing.sq_snapshot_id
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1
    OWNED BY eventsourcing."snapshot".id;


CREATE SEQUENCE eventsourcing.sq_saga_event_aggregation_id
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1
    OWNED BY eventsourcing.saga_event_aggregation.id;

CREATE SEQUENCE eventsourcing.sq_saga_cicle_id
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1
    OWNED BY eventsourcing.saga_cicle.id;


CREATE SEQUENCE eventsourcing.sq_step_saga_id
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1
    OWNED BY eventsourcing.step_saga.id;

CREATE SEQUENCE eventsourcing.sq_command_business_context_id
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1
    OWNED BY eventsourcing.command_business_context.id;


