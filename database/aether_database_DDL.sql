-- Muy importante acticar las foreign keys antes de anadir ninguna tabla.
-- Las PK tienen que ser INTEGER para que puedan autoincrementar implicitamente.
PRAGMA foreign_keys = ON;

-- ############################### --
-- 		  CREATE DIM TABLES 	   --
-- ############################### --
-- Don't name constraints, its useless in SQLite.

CREATE TABLE dim_person ( 
    person_sk                 INTEGER		NOT NULL,
    person_nk                 TEXT  		NOT NULL,
    person_rank               TEXT  		NOT NULL,
    person_name               TEXT  		NOT NULL,
    person_last_name_1        TEXT  		NOT NULL,
    person_last_name_2        TEXT  		NOT NULL,
    person_phone              TEXT  		NOT NULL,
	person_dni				  TEXT			NOT NULL,
    person_division           TEXT  		NOT NULL,
	person_rol		          TEXT 			NOT NULL,
	person_order	          INTEGER 		NOT NULL,
    person_current_flag       INTEGER  		NOT NULL,
	PRIMARY KEY (person_sk),
	CHECK 		(person_current_flag 	IN (0,1))
);

CREATE TABLE dim_helo (
	helo_sk			 INTEGER 	    NOT NULL, 
	helo_plate_nk    TEXT 			NOT NULL,
    helo_name        TEXT   		NOT NULL,
    helo_number      TEXT 			NOT NULL,
	PRIMARY KEY (helo_sk)	
);

CREATE TABLE dim_event (
    event_sk     INTEGER		NOT NULL,
    event_name   TEXT			NOT NULL,
    event_place  TEXT			NOT NULL,
	event_code	 TEXT,
	PRIMARY KEY (event_sk)
);

CREATE TABLE dim_period (
    period_sk       INTEGER NOT NULL,
    period_name     TEXT 	NOT NULL,
	PRIMARY KEY (period_sk)
);

CREATE TABLE dim_ifr_app_type (
  ifr_app_type_sk     INTEGER 	NOT NULL,
  ifr_app_type_name   TEXT 		NOT NULL,
  ifr_app_type_type   TEXT		NOT NULL,
  PRIMARY KEY (ifr_app_type_sk)
);

CREATE TABLE dim_landing_place (
    landing_place_sk        INTEGER NOT NULL,
    landing_place_name      TEXT	NOT NULL,
	PRIMARY KEY (landing_place_sk)
);

CREATE TABLE dim_projectile_type (
  projectile_type_sk         INTEGER 	NOT NULL,
  projectile_type_name       TEXT		NOT NULL,
  projectile_type_weapon     TEXT		NOT NULL,
  PRIMARY KEY (projectile_type_sk)
);

CREATE TABLE dim_session  ( 
    session_sk            INTEGER 	NOT NULL, 
    session_name          TEXT		NOT NULL,
    session_description   TEXT		NOT NULL,
    session_block         TEXT		NOT NULL,
	session_plan          TEXT		NOT NULL,
    session_tv            REAL		NOT NULL,
    session_crp_value 	  REAL 		NOT NULL,
	session_expiration	  INTEGER   NOT NULL,
	PRIMARY KEY (session_sk)
);

CREATE TABLE dim_authority (
    authority_sk            INTEGER		NOT NULL,
	authority_name			TEXT 		NOT NULL,
    authority_abrv  		TEXT		NOT NULL,
	PRIMARY KEY (authority_sk)
);

 
CREATE TABLE dim_passenger_type (
	passenger_type_sk   INTEGER 	NOT NULL,
	passenger_type_name TEXT 		NOT NULL,
    PRIMARY KEY (passenger_type_sk)
);

CREATE TABLE dim_capba (
    capba_sk                         INTEGER 	NOT NULL, 
    capba_name                       TEXT		NOT NULL, 
    capba_ca_code_dd                 TEXT		NOT NULL, 
    capba_ca_name                    TEXT		NOT NULL, 
    capba_ca_section_code_dd         TEXT		NOT NULL, 
    capba_ca_section                 TEXT		NOT NULL, 
    capba_ca_operational_level_dd    TEXT		NOT NULL,
	PRIMARY KEY (capba_sk)
);

-- Second Create Facts
CREATE TABLE fact_flight (
	flight_sk          			INTEGER  	NOT NULL,
	flight_datetime    			TEXT		NOT NULL,
	flight_helo_fk     			INTEGER		NOT NULL,
	flight_event_fk    			INTEGER		NOT NULL,
	flight_person_cta_fk	    INTEGER  	NOT NULL,
	flight_total_hours 			REAL		NOT NULL,
	PRIMARY KEY (flight_sk),
	FOREIGN KEY (flight_helo_fk)  		 REFERENCES dim_helo (helo_sk),
	FOREIGN KEY (flight_event_fk) 		 REFERENCES dim_event (event_sk),
	FOREIGN KEY (flight_person_cta_fk)   REFERENCES dim_person (person_sk)	
);

CREATE TABLE junction_person_hour (
    person_hour_sk        	 	INTEGER		NOT NULL,
    person_hour_flight_fk       INTEGER		NOT NULL,
    person_hour_person_fk       INTEGER		NOT NULL,
    person_hour_period_fk 	 	INTEGER		NOT NULL,
    person_hour_hour_qty       	REAL		NOT NULL,
	PRIMARY KEY (person_hour_sk),
	FOREIGN KEY (person_hour_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
	FOREIGN KEY (person_hour_person_fk) 		REFERENCES dim_person (person_sk),
	FOREIGN KEY (person_hour_period_fk) 		REFERENCES dim_period (period_sk)
);

CREATE TABLE junction_ift_hour (
    ift_hour_sk               INTEGER NOT NULL,
    ift_hour_flight_fk        INTEGER NOT NULL,
    ift_hour_person_fk        INTEGER NOT NULL,
    ift_hour_qty     	  	  REAL 	  NOT NULL,
	PRIMARY KEY (ift_hour_sk),
	FOREIGN KEY (ift_hour_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
	FOREIGN KEY (ift_hour_person_fk) 		REFERENCES dim_person (person_sk)
);

CREATE TABLE junction_instructor_hour (
    instructor_hour_sk                INTEGER NOT NULL,
    instructor_hour_flight_fk         INTEGER NOT NULL,
    instructor_hour_person_fk         INTEGER NOT NULL,
    instructor_hour_qty     	  	  REAL 	  NOT NULL,
	PRIMARY KEY (instructor_hour_sk),
	FOREIGN KEY (instructor_hour_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
	FOREIGN KEY (instructor_hour_person_fk) 		REFERENCES dim_person (person_sk)
);

CREATE TABLE junction_hdms_hour (
    hdms_hour_sk                INTEGER 	NOT NULL,
    hdms_hour_flight_fk         INTEGER 	NOT NULL,
    hdms_hour_person_fk         INTEGER 	NOT NULL,
    hdms_hour_qty     	  		REAL 	    NOT NULL,
	PRIMARY KEY (hdms_hour_sk),
	FOREIGN KEY (hdms_hour_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
	FOREIGN KEY (hdms_hour_person_fk) 		REFERENCES dim_person (person_sk)
);

CREATE TABLE junction_formation_hour (
    formation_hour_sk               INTEGER 	NOT NULL,
    formation_hour_flight_fk        INTEGER		NOT NULL,
    formation_hour_person_fk        INTEGER		NOT NULL,
	formation_hour_period_fk 	 	INTEGER		NOT NULL,
    formation_hour_formation_qty   	REAL,
	PRIMARY KEY (formation_hour_sk),
	FOREIGN KEY (formation_hour_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
	FOREIGN KEY (formation_hour_person_fk) 		REFERENCES dim_person (person_sk),
	FOREIGN KEY (formation_hour_period_fk) 		REFERENCES dim_period (period_sk)
);

CREATE TABLE junction_wt_hour (
    wt_hour_sk          	INTEGER NOT NULL,
    wt_hour_flight_fk   	INTEGER NOT NULL,
    wt_hour_person_fk   	INTEGER NOT NULL,
    wt_hour_qty       		REAL  NOT NULL,
	PRIMARY KEY (wt_hour_sk),
	FOREIGN KEY (wt_hour_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
	FOREIGN KEY (wt_hour_person_fk) 		REFERENCES dim_person (person_sk)  
);

CREATE TABLE junction_app (
    app_sk           INTEGER NOT NULL,
    app_flight_fk    INTEGER NOT NULL,
    app_person_fk    INTEGER NOT NULL,
    app_type_fk      INTEGER NOT NULL,
    app_qty          INTEGER NOT NULL,
	PRIMARY KEY (app_sk),
	FOREIGN KEY (app_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
	FOREIGN KEY (app_person_fk) 		REFERENCES dim_person (person_sk)
);

CREATE TABLE junction_landing (
    landing_sk            INTEGER NOT NULL,
    landing_flight_fk     INTEGER NOT NULL,
    landing_person_fk     INTEGER NOT NULL,
    landing_place_fk      INTEGER NOT NULL,
    landing_period_fk     INTEGER NOT NULL,
    landing_qty           INTEGER NOT NULL,
	PRIMARY KEY (landing_sk),
	FOREIGN KEY (landing_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
	FOREIGN KEY (landing_person_fk) 		REFERENCES dim_person (person_sk),
	FOREIGN KEY (landing_place_fk)			REFERENCES dim_landing_place (landing_place_sk),
	FOREIGN KEY (landing_period_fk)			REFERENCES dim_period (period_sk)
);

CREATE TABLE junction_projectile (
	projectile_sk         INTEGER NOT NULL,
	projectile_flight_fk  INTEGER NOT NULL,
	projectile_person_fk  INTEGER NOT NULL,
	projectile_type_fk    INTEGER NOT NULL,
	projectile_qty        INTEGER NOT NULL,
	PRIMARY KEY (projectile_sk),
	FOREIGN KEY (projectile_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
	FOREIGN KEY (projectile_person_fk) 		REFERENCES dim_person (person_sk),
	FOREIGN KEY (projectile_type_fk)		REFERENCES dim_projectile_type (projectile_type_sk)
);

CREATE TABLE junction_session_crew_count (
    session_crew_count_sk          			INTEGER  NOT NULL,
    session_crew_count_flight_fk   			INTEGER	 NOT NULL,
    session_crew_count_person_fk   			INTEGER  NOT NULL,
    session_crew_count_session_fk     	    INTEGER  NOT NULL,
    PRIMARY KEY (session_crew_count_sk),
    FOREIGN KEY (session_crew_count_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan fk a fact_flight. para cuando se equivoquen y quieran borrar un vuelo para volverlo a meter, que se borren todos los datos relaciondos ya que los guardo en masa.
    FOREIGN KEY (session_crew_count_person_fk) 		REFERENCES dim_person (person_sk),
    FOREIGN KEY (session_crew_count_session_fk)		REFERENCES dim_session (session_sk)
);

CREATE TABLE junction_cupo_hour ( 
    cupo_hour_sk          INTEGER NOT NULL,
    cupo_flight_fk        INTEGER NOT NULL,
    cupo_authority_fk     INTEGER NOT NULL,
    cupo_hour_qty         REAL 	  NOT NULL,
	PRIMARY KEY (cupo_hour_sk),
	FOREIGN KEY (cupo_flight_fk) 			REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan
	FOREIGN KEY (cupo_authority_fk)			REFERENCES dim_authority (authority_sk)
);

CREATE TABLE junction_passenger (
    passenger_sk            INTEGER 	NOT NULL,
    passenger_flight_fk     INTEGER		NOT NULL,
    passenger_type_fk       INTEGER		NOT NULL,
    passenger_qty           INTEGER		NOT NULL,
    passenger_route         TEXT		NOT NULL,
	PRIMARY KEY (passenger_sk),
	FOREIGN KEY (passenger_flight_fk) 		REFERENCES fact_flight (flight_sk) ON DELETE CASCADE, -- Importante poner ON DELETE CASCADE en todas las tablas que tengan
	FOREIGN KEY (passenger_type_fk)			REFERENCES dim_passenger_type (passenger_type_sk)
);

CREATE TABLE fact_session_capba (
	session_capba_sk	INTEGER		NOT NULL,
	session_fk  		INTEGER 	NOT NULL,
	capba_fk    		INTEGER 	NOT NULL,
	PRIMARY KEY (session_capba_sk),
	FOREIGN KEY (session_fk)	REFERENCES dim_session(session_sk),
	FOREIGN KEY (capba_fk)	    REFERENCES dim_capba(capba_sk),
	UNIQUE (session_fk, capba_fk) -- Ensure uniqueness of natural keys
);

-- Log for deleted flights
CREATE TABLE deleted_flights_log (
    deleted_flights_id  INTEGER NOT NULL,
    flight_id           INTEGER NOT NULL,
    user_name           TEXT    NOT NULL,
    timestamp           TEXT    NOT NULL,
    PRIMARY KEY (deleted_flights_id)
);

-- ############################### --
-- 		  INSERT DATA		 	   --
-- ############################### --
-- DIM_PERSON
INSERT INTO dim_person (person_nk, person_rank, person_name, person_last_name_1, person_last_name_2, person_phone, person_dni, person_division, person_rol, person_order, person_current_flag)
VALUES ('JON', 'SG1', 'Jonatan', 'Carbonell', 'Martinez', '647168956','39390040X', 'N2', 'Piloto' ,1 , 1);

-- Insert into dim_helo
INSERT INTO dim_helo (helo_plate_nk, helo_name, helo_number)
VALUES ('1234', 'NH-90', '1401');

INSERT INTO dim_helo (helo_plate_nk, helo_name, helo_number)
VALUES (1235, 'NH-90', '1402');

INSERT INTO dim_helo (helo_plate_nk, helo_name, helo_number)
VALUES ('1236', 'NH-90', '1403');

-- Insert into dim_event
INSERT INTO dim_event (event_name, event_place) VALUES ('Adaptación', 'BNR');
INSERT INTO dim_event (event_name, event_place) VALUES ('Instrucción', 'BNR');
INSERT INTO dim_event (event_name, event_place) VALUES ('Adiestramiento', 'BNR');
INSERT INTO dim_event (event_name, event_place) VALUES ('Adiestramiento', 'Logroño');
INSERT INTO dim_event (event_name, event_place) VALUES ('Pruebas', 'BNR');
INSERT INTO dim_event (event_name, event_place) VALUES ('Maniobra nacional', 'ARMEX');
INSERT INTO dim_event (event_name, event_place) VALUES ('Maniobra nacional', 'GRUFLEX');
INSERT INTO dim_event (event_name, event_place) VALUES ('Maniobra nacional', 'ADELFIBEX');
INSERT INTO dim_event (event_name, event_place) VALUES ('Maniobra nacional', 'FLOTEX');
INSERT INTO dim_event (event_name, event_place) VALUES ('Maniobra nacional', 'SINKEX');
INSERT INTO dim_event (event_name, event_place) VALUES ('Maniobra internacional', 'DEDALO');
INSERT INTO dim_event (event_name, event_place) VALUES ('Misión', 'ATALANTA');
INSERT INTO dim_event (event_name, event_place) VALUES ('Misión', 'SNMG-1');
INSERT INTO dim_event (event_name, event_place) VALUES ('Misión', 'SNMG-2');
INSERT INTO dim_event (event_name, event_place) VALUES ('Colaboración', 'BNR');
INSERT INTO dim_event (event_name, event_place) VALUES ('Colaboración', 'Cartagena');


-- Insert into dim_period
INSERT INTO dim_period (period_name) VALUES ('Diurno');
INSERT INTO dim_period (period_name) VALUES ('Noche sin GVN');
INSERT INTO dim_period (period_name) VALUES ('GVN');

-- Insert into dim_ifr_app_type
INSERT INTO dim_ifr_app_type (ifr_app_type_name, ifr_app_type_type) VALUES ('Precisión', 'Instrumental');
INSERT INTO dim_ifr_app_type (ifr_app_type_name, ifr_app_type_type) VALUES ('No precisión', 'Instrumental');
INSERT INTO dim_ifr_app_type (ifr_app_type_name, ifr_app_type_type) VALUES ('T/D', 'SAR');
INSERT INTO dim_ifr_app_type (ifr_app_type_name, ifr_app_type_type) VALUES ('Search Pattern', 'SAR');

-- Insert into dim_landing_place
INSERT INTO dim_landing_place (landing_place_name) VALUES ('Tierra');
INSERT INTO dim_landing_place (landing_place_name) VALUES ('Monospot');
INSERT INTO dim_landing_place (landing_place_name) VALUES ('Multispot');
INSERT INTO dim_landing_place (landing_place_name) VALUES ('Carrier');


-- Insert into dim_projectile_type
INSERT INTO dim_projectile_type (projectile_type_name, projectile_type_weapon)	VALUES ('7.62', 'M3M');
INSERT INTO dim_projectile_type (projectile_type_name, projectile_type_weapon)	VALUES ('12.7', 'MAG58');

-- Insert into dim_session

-- Insert into dim_session
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('FAM-301', 'Vuelo de Familiarización', 'Vuelo', 'Básico', 1.5, 4, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('TAC-302', 'Vuelo de Táctico', 'Vuelo', 'Básico', 1.5, 4, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('CAL-303', 'Tomas Confinadas', 'Vuelo', 'Básico', 1.5, 4, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('FORM-304', 'Vuelo táctico en formación', 'Vuelo', 'Básico', 1.5, 4, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('VERTREP-305', 'Operaciones de Gancho', 'Vuelo', 'Básico', 1.5, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('VOD-306', 'Operaciones de grúa', 'Vuelo', 'Básico', 1.5, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('BUQ-307', 'Calificación en buque mono/multi/carrier Día/noche/GVN', 'Vuelo', 'Básico', 1.5, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('INST-308', 'Requisito de App Instrumentales anuales', 'Vuelo', 'Básico', 1.5, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SIM-310', 'Familiarización/Emergencias en tierra y en vuelo', 'Simulador', 'Básico', 3, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SIM-311', 'Vuelo IFR en IMC', 'Simulador', 'Básico', 3, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SIM-312', 'Vuelo en montaña/táctico. IIMC con aproximación instrumental', 'Simulador', 'Básico', 3, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SIM-313', 'Tomas en buques mono/multi/carrier. Procedimientos de emergencia', 'Simulador', 'Básico', 3, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SIM-314(G)', 'Familiarización/Emergencias en tierra/ en vuelo y buque con GVN', 'Simulador', 'Básico', 3, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('TFM', 'Tactical Formation Maneuvering', 'Vuelo', 'Avanzado', 0, 1, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('HIGE/HOGE T/D', 'Hover automático sobre la mar o sobre tierra', 'Vuelo', 'Avanzado', 0, 2, 90);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('TCT', 'Threat Counter Tactics', 'Vuelo', 'Avanzado', 0, 2, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('FR', 'Técnica de Fast Rope', 'Vuelo', 'Avanzado', 0, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('RAP', 'Técnica de Rappel', 'Vuelo', 'Avanzado', 0, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SPIE', 'Empleo de Técnicas de extracción Spie Rig o AirTrep', 'Vuelo', 'Avanzado', 0, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('RUBBER CAST', 'Cast de embarcaciones y equipo asociado.', 'Vuelo', 'Avanzado', 0, 1, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('DIV CAST', 'Cast nadadores/buceadores', 'Vuelo', 'Avanzado', 0, 2, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('PARAOPS', 'Lanzamiento de Paracaidistas', 'Vuelo', 'Avanzado', 0, 1, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('GND TPT', 'Transporte Táctico de Tropas con Infil/Exfil por toma en LZ', 'Vuelo', 'Avanzado', 0, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('TPT (E)', 'Transporte Táctico empleando líneas de vida', 'Vuelo', 'Avanzado', 0, 1, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('CA INF/EXF', 'Confined Area Infil/Exfil', 'Vuelo', 'Avanzado', 0, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('CAS/CCA', 'Procedimientos de Apoyo de Fuegos', 'Vuelo', 'Avanzado', 0, 2, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SNP', 'Tirador de precisión desde helicóptero', 'Vuelo', 'Avanzado', 0, 1, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('ESCORT', 'Escolta de columnas de vehículos o helicópteros', 'Vuelo', 'Avanzado', 0, 1, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('WPN LF', 'Weapons Live Fire', 'Vuelo', 'Avanzado', 0, 3, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('MIO (OB)', 'Maritime Interdiction Operation', 'Vuelo', 'Avanzado', 0, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('PR', 'Misión de Recuperación de Personal', 'Vuelo', 'Avanzado', 0, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('AASLT', 'Misión de Asalto Aéreo o formación compleja', 'Vuelo', 'Avanzado', 0, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('LOG/AE', 'Misión logística Material/Personal o evacuaciones aéreas', 'Vuelo', 'Avanzado', 0, 1, 180);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SIM FAM-410', 'Vuelo Estandarización Básico FAM. (50% GVN)', 'Simulador', 'Avanzado', 2, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SIM NAVTAC-411', 'Vuelo Estandarización Táctico y Navegación Táctica (50% GVN)', 'Simulador', 'Avanzado', 2, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SIM SAR-412', 'Vuelo Estandarización Procedimientos SAR y SARN (50% GVN)', 'Simulador', 'Avanzado', 1, 1, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('SIM BUQ-413', 'Vuelo Estandarización Procedimientos Aeronavales (50% GVN)', 'Simulador', 'Avanzado', 1, 2, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('ASSAULT MISSION', 'CHECK Misión Anfibia o SAO en entorno terrestre (100% GVN)', 'Simulador', 'Avanzado', 2, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('MARITIME MISSION', 'CHECK Misión marítima incluyendo un MIO (OB) (100% GVN)', 'Simulador', 'Avanzado', 2, 3, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('AIR TACPLAN', 'Planeamiento completo de misión aérea (4Ts o SOATU Planning & Briefing Guidelines)', 'Tierra', 'Avanzado', 0, 2, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('ECM', 'Electronic countermeasures (ECM)', 'Tierra', 'Avanzado', 0, 2, 365);
INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration) VALUES ('ECCM', 'Electronic counter-countermeasures (ECCM)', 'Tierra', 'Avanzado', 0, 2, 365);

-- Insert into dim_unit

INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('COMFLOAN', 'CF');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('ALPER', 'NA');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('ALFLOT', 'NA');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('COMGRUPFLOT', 'GD');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('COMANDES-31', 'C3');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('COMANDES-41', 'C4');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('COMTEMECOM', 'MC');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('ALMART', 'AM');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('GETEAR', 'GT');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('GEPROAR', 'GP');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('COMNAVES', 'CN');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('COMSUBMAR', 'CS');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('COCEVACO', 'CC');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('Ejercicios en cumplimiento', 'OE');
INSERT INTO dim_authority (authority_name, authority_abrv) VALUES ('Operaciones en cumplimiento', 'OE');


-- Insert into dim_passenger_type

INSERT INTO dim_passenger_type (passenger_type_name)	VALUES ('Civiles');
INSERT INTO dim_passenger_type (passenger_type_name)	VALUES ('Militares');


-- ############################### --
-- 		  	   VIEWS 		       --
-- ############################### --

-- Last flight view

CREATE VIEW view_last_flights AS
SELECT
    f.flight_sk AS ID,
    f.flight_datetime AS "Fecha Hora",

    -- Helo details
    h.helo_number AS Helicóptero,

    -- Event details
    e.event_name || ' ' || e.event_place AS Evento,

    -- Person details
    p.person_nk AS HAC,

    f.flight_total_hours AS 'Horas'

FROM
    fact_flight f
        INNER JOIN dim_helo h ON f.flight_helo_fk = h.helo_sk
        INNER JOIN dim_event e ON f.flight_event_fk = e.event_sk
        INNER JOIN dim_person p ON f.flight_person_cta_fk = p.person_sk;

select * from view_last_flights;

drop view view_last_flights;

-- Query for last 50 flights
SELECT *
FROM view_last_flights
ORDER BY "ID" DESC
LIMIT 50;


-- Crew hours detail view

CREATE VIEW view_crew_hours_detail AS
SELECT
    f.flight_sk,
    p.person_nk AS Crew,
    p.person_rol AS Rol,
    p.person_order,
    SUM(CASE WHEN ph.person_hour_period_fk = 1 THEN ph.person_hour_hour_qty ELSE 0 END) AS Vuelo_Dia,
    SUM(CASE WHEN ph.person_hour_period_fk = 2 THEN ph.person_hour_hour_qty ELSE 0 END) AS Vuelo_Noche,
    SUM(CASE WHEN ph.person_hour_period_fk = 3 THEN ph.person_hour_hour_qty ELSE 0 END) AS Vuelo_GVN,
    (
        SELECT SUM(ift_sub.ift_hour_qty)
        FROM junction_ift_hour ift_sub
        WHERE ift_sub.ift_hour_flight_fk = f.flight_sk
          AND ift_sub.ift_hour_person_fk = p.person_sk
    ) AS Instr,
    (
        SELECT SUM(hdm_sub.hdms_hour_qty)
        FROM junction_hdms_hour hdm_sub
        WHERE hdm_sub.hdms_hour_flight_fk = f.flight_sk
          AND hdm_sub.hdms_hour_person_fk = p.person_sk
    ) AS HMDS,
    (
        SELECT SUM(ins_sub.instructor_hour_qty)
        FROM junction_instructor_hour ins_sub
        WHERE ins_sub.instructor_hour_flight_fk = f.flight_sk
          AND ins_sub.instructor_hour_person_fk = p.person_sk
    ) AS IP,
    (
        SELECT SUM(fh_sub.formation_hour_formation_qty)
        FROM junction_formation_hour fh_sub
        WHERE fh_sub.formation_hour_flight_fk = f.flight_sk
          AND fh_sub.formation_hour_person_fk = p.person_sk
          AND fh_sub.formation_hour_period_fk = 1
    ) AS Formacion_Dia,
    (
        SELECT SUM(fh_sub.formation_hour_formation_qty)
        FROM junction_formation_hour fh_sub
        WHERE fh_sub.formation_hour_flight_fk = f.flight_sk
          AND fh_sub.formation_hour_person_fk = p.person_sk
          AND fh_sub.formation_hour_period_fk = 3
    ) AS Formacion_GVN,
    (
        SELECT SUM(wt_sub.wt_hour_qty)
        FROM junction_wt_hour wt_sub
        WHERE wt_sub.wt_hour_flight_fk = f.flight_sk
          AND wt_sub.wt_hour_person_fk = p.person_sk
    ) AS Winch_Trim
FROM
    fact_flight f
        LEFT JOIN junction_person_hour ph ON f.flight_sk = ph.person_hour_flight_fk
        LEFT JOIN dim_person p ON
        p.person_sk = ph.person_hour_person_fk
GROUP BY
    f.flight_sk, p.person_nk, p.person_order
ORDER BY
    CASE p.person_rol
        WHEN 'Piloto' THEN 1
        WHEN 'Dotación' THEN 2
        ELSE 3
        END,
    p.person_order;

-- Session detail view
CREATE VIEW view_session_details AS
SELECT
    ff.flight_sk AS "Vuelo_ID",
    dp.person_nk AS "Crew",
    dp.person_rol AS "Rol",
    ds.session_name AS "Papeleta",
    ds.session_description AS "Descripción",
    ds.session_plan AS "Plan",
    ds.session_block AS "Bloque"
FROM
    junction_session_crew_count jscc
        INNER JOIN
    dim_person dp ON jscc.session_crew_count_person_fk = dp.person_sk
        INNER JOIN
    dim_session ds ON jscc.session_crew_count_session_fk = ds.session_sk
        INNER JOIN
    fact_flight ff ON jscc.session_crew_count_flight_fk = ff.flight_sk;
	
-- Landings view
CREATE VIEW view_landings AS
SELECT
    f.flight_sk AS Vuelo_ID,
    p.person_nk AS Piloto,
    lp.landing_place_name AS Lugar,
    pd.period_name AS Periodo,
    SUM(l.landing_qty) AS Cantidad
FROM
    junction_landing l
        INNER JOIN fact_flight f ON l.landing_flight_fk = f.flight_sk
        INNER JOIN dim_person p ON l.landing_person_fk = p.person_sk
        INNER JOIN dim_landing_place lp ON l.landing_place_fk = lp.landing_place_sk
        INNER JOIN dim_period pd ON l.landing_period_fk = pd.period_sk
GROUP BY
    f.flight_sk, p.person_nk, lp.landing_place_name, pd.period_name, p.person_order
ORDER BY
    p.person_order;
	
-- Instrumental apps view
CREATE VIEW view_instrumental_apps AS
SELECT
    f.flight_sk AS Vuelo_ID,
    p.person_nk AS Piloto,
    at.ifr_app_type_name AS Tipo,
    a.app_qty AS Cantidad
FROM
    junction_app a
        INNER JOIN fact_flight f ON a.app_flight_fk = f.flight_sk
        INNER JOIN dim_person p ON a.app_person_fk = p.person_sk
        INNER JOIN dim_ifr_app_type at ON a.app_type_fk = at.ifr_app_type_sk
WHERE
    at.ifr_app_type_type LIKE '%Instrumental%';	
	
-- SAR apps view
CREATE VIEW view_sar_apps AS
SELECT
    f.flight_sk AS Vuelo_ID,
    p.person_nk AS Piloto,
    at.ifr_app_type_name AS Tipo,
    a.app_qty AS Cantidad
FROM
    junction_app a
        INNER JOIN fact_flight f ON a.app_flight_fk = f.flight_sk
        INNER JOIN dim_person p ON a.app_person_fk = p.person_sk
        INNER JOIN dim_ifr_app_type at ON a.app_type_fk = at.ifr_app_type_sk
WHERE
    at.ifr_app_type_type LIKE '%SAR%';

-- Projectile view
CREATE VIEW view_projectiles AS
SELECT
    f.flight_sk AS Vuelo_ID,
    p.person_nk AS Dotación,
    pt.projectile_type_weapon AS Arma,
    pj.projectile_qty AS Cantidad
FROM
    junction_projectile pj
        INNER JOIN fact_flight f ON pj.projectile_flight_fk = f.flight_sk
        INNER JOIN dim_person p ON pj.projectile_person_fk = p.person_sk
        INNER JOIN dim_projectile_type pt ON pj.projectile_type_fk = pt.projectile_type_sk;

-- Cupo view
CREATE VIEW view_cupo AS
SELECT
    f.flight_sk AS Vuelo_ID,
    a.authority_name AS Autoridad,
    ch.cupo_hour_qty AS Horas
FROM
    junction_cupo_hour ch
        INNER JOIN fact_flight f ON ch.cupo_flight_fk = f.flight_sk
        INNER JOIN dim_authority a ON ch.cupo_authority_fk = a.authority_sk;

-- Passenger view
CREATE VIEW view_passengers AS
SELECT
    f.flight_sk AS Vuelo_ID,
    dpt.passenger_type_name AS Tipo,
    jp.passenger_qty AS Cantidad,
    jp.passenger_route AS Ruta
FROM
    junction_passenger jp
        INNER JOIN fact_flight f ON jp.passenger_flight_fk = f.flight_sk
        INNER JOIN dim_passenger_type dpt ON jp.passenger_type_fk = dpt.passenger_type_sk;

select * from view_passengers;

-- ############################### --
-- 		  	  TRIGGERS		       --
-- ############################### --

-- Demomento dejo el tema del person_orden para meterlo en manual. Ya me calentare la cabeza mas adelante. Ninguno de los triggers de abajo funciona bien.
-- Seguramente consiga hacerlo mejor dejandole esa logica a Java, en lugar de con un trigger.



CREATE TRIGGER set_event_code
    AFTER INSERT ON dim_event
    FOR EACH ROW
BEGIN
    UPDATE dim_event
    SET event_code =
            CASE
                WHEN NEW.event_name = 'Adaptación' THEN 'A'
                WHEN NEW.event_name = 'Instrucción' THEN 'E'
                WHEN NEW.event_name = 'Adiestramiento' THEN 'I'
                WHEN NEW.event_name IN ('Maniobra nacional', 'Maniobra internacional') THEN 'O'
                WHEN NEW.event_name = 'Misión' THEN 'M'
                WHEN NEW.event_name = 'Colaboración' THEN 'U'
                WHEN NEW.event_name = 'Pruebas' THEN 'X'
                ELSE event_code -- Keep existing code if no match
                END
    WHERE rowid = NEW.rowid;
END;

