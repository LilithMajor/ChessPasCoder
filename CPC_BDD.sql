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
DROP TABLE GAMES CASCADE CONSTRAINTS purge;
DROP TABLE TOPICS CASCADE CONSTRAINTS purge;
DROP TABLE RESPONSES CASCADE CONSTRAINTS purge;
DROP SEQUENCE TOPIC_NUMBER;
DROP SEQUENCE RESPONSE_NUMBER;
DROP SEQUENCE GAME_NUMBER;


CREATE TABLE USERS (	
	Name VARCHAR2(40) NOT NULL,
	Login VARCHAR2(20),
	Password VARCHAR2(40) NOT NULL,
	Email VARCHAR2(100),
	Elo INTEGER,
	CONSTRAINT PK_USERS PRIMARY KEY (Login)
);

CREATE TABLE GAMES (	
	Id INTEGER NOT NULL,
	NbRound INTEGER,
	LoginWin VARCHAR2(40),
	LoginLoss VARCHAR2(40),
	nbPlayer SMALLINT,
	CONSTRAINT PK_GAMES PRIMARY KEY (Id),
	CONSTRAINT FK_WIN FOREIGN KEY (LoginWin) REFERENCES USERS(Login),
	CONSTRAINT FK_LOSS FOREIGN KEY (LoginLoss) REFERENCES USERS(Login)
);

CREATE TABLE TOPICS (	
	Id_Topic INTEGER NOT NULL,
	Name VARCHAR2(100) NOT NULL,
	T_Creator VARCHAR2(40) NOT NULL,
	DateCreation DATE NOT NULL,
	CONSTRAINT PK_TOPICS PRIMARY KEY (Id_Topic),
	CONSTRAINT FK_TCreator FOREIGN KEY (T_Creator) REFERENCES USERS(Login)
);

CREATE TABLE RESPONSES (	
	Id_Response INTEGER NOT NULL,
	Text VARCHAR2(1000) NOT NULL,
	R_Creator VARCHAR2(40) NOT NULL,
	DatePost DATE NOT NULL,
	R_Id_Topic INTEGER NOT NULL,
	CONSTRAINT PK_RESPONSE PRIMARY KEY (Id_Response),
	CONSTRAINT FK_TOPIC FOREIGN KEY (R_Id_Topic) REFERENCES TOPICS(Id_Topic),
	CONSTRAINT FK_Creator FOREIGN KEY (R_Creator) REFERENCES USERS(Login)
);

CREATE SEQUENCE GAME_NUMBER START WITH 0 MINVALUE 0 INCREMENT BY 1;
CREATE SEQUENCE TOPIC_NUMBER START WITH 0 MINVALUE 0 INCREMENT BY 1;
CREATE SEQUENCE RESPONSE_NUMBER START WITH 0 MINVALUE 0 INCREMENT BY 1;

PROMPT  ->  Tables creees



COMMIT;

PROMPT
PROMPT -> FIN DE CREATION
PROMPT
PROMPT IMPORTANT : Merci de verifier que les tables suivantes sont bien creees :
PROMPT USERS
PROMPT ************************************************************
PROMPT
SET FEEDBACK ON
