REM *************************************************
REM *******       Echecs       *******
REM *******   Copyright © 2017 Grammont  *******
REM *************************************************

SET FEEDBACK OFF

PROMPT
PROMPT ************************************************************
PROMPT
PROMPT -> CREATION DE LA BASE DE DONNEES << Echecs >> 
PROMPT

DROP TABLE USERS CASCADE CONSTRAINTS purge;

CREATE TABLE USERS (	
	Name VARCHAR2(40) NOT NULL,
	Login VARCHAR2(20),
	Password VARCHAR2(40) NOT NULL,
	Email VARCHAR2(100),
	Elo NUMBER(10,2),
	CONSTRAINT PK_PROPRIETAIRES PRIMARY KEY (Login)
);

PROMPT  ->  Tables creees

INSERT INTO USERS VALUES ('Admin', 'admin','admin','alex.bournadet@etu.parisdescartes.fr');


COMMIT;

PROMPT
PROMPT -> FIN DE CREATION
PROMPT
PROMPT IMPORTANT : Merci de verifier que les tables suivantes sont bien creees :
PROMPT USERS
PROMPT ************************************************************
PROMPT
SET FEEDBACK ON