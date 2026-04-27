-- PROYECTO FINAL - BASE DE DATOS
-- Sistema de Gestion de Eventos Cientificos PUCMM
-- Grupo 1

CREATE DATABASE EventosPUCMM;
GO

USE EventosPUCMM;
GO

-- Tabla Usuario
CREATE TABLE Usuario (
    roll           VARCHAR(50)  NOT NULL,
    nombre_usuario VARCHAR(100) NOT NULL,
    contrasena     VARCHAR(100) NOT NULL,
    CONSTRAINT PK_Usuario PRIMARY KEY (nombre_usuario)
);
GO

-- Tabla Participante
CREATE TABLE Participante (
    codparticipante VARCHAR(20)  NOT NULL,
    cedula          VARCHAR(20)  NOT NULL,
    nombre          VARCHAR(100) NOT NULL,
    telefono        VARCHAR(20),
    CONSTRAINT PK_Participante PRIMARY KEY (codparticipante)
);
GO

-- Tabla Jurado
CREATE TABLE Jurado (
    codjurado         VARCHAR(20)  NOT NULL,
    cedula            VARCHAR(20)  NOT NULL,
    nombre            VARCHAR(100) NOT NULL,
    telefono          VARCHAR(20),
    areaespecializado VARCHAR(100),
    CONSTRAINT PK_Jurado PRIMARY KEY (codjurado)
);
GO

-- Tabla Recurso
-- disponible: 1 = libre, 0 = asignado a un evento
CREATE TABLE Recurso (
    codigo      VARCHAR(20)  NOT NULL,
    disponible  BIT          NOT NULL DEFAULT 1,
    ubicacion   VARCHAR(100),
    tipo        VARCHAR(50),
    descripcion VARCHAR(255),
    CONSTRAINT PK_Recurso PRIMARY KEY (codigo)
);
GO

-- Tabla Evento
CREATE TABLE Evento (
    codigo      VARCHAR(20)  NOT NULL,
    nombre      VARCHAR(100) NOT NULL,
    ubicacion   VARCHAR(100),
    fechainicio VARCHAR(50),
    fechafinal  VARCHAR(50),
    cupo        INT,
    CONSTRAINT PK_Evento PRIMARY KEY (codigo)
);
GO

-- Tabla Comision
-- codjurado_presidente puede ser NULL si aun no se asigna presidente
CREATE TABLE Comision (
    idcomision           VARCHAR(20) NOT NULL,
    area                 VARCHAR(100),
    codjurado_presidente VARCHAR(20),
    CONSTRAINT PK_Comision PRIMARY KEY (idcomision),
    CONSTRAINT FK_Comision_Presidente FOREIGN KEY (codjurado_presidente)
        REFERENCES Jurado(codjurado)
);
GO

-- Tabla TrabajoCientifico
-- codparticipante e idcomision permiten NULL para conservar el trabajo
-- si se elimina el participante o la comision (preservar historial)
CREATE TABLE TrabajoCientifico (
    codigo              VARCHAR(20)  NOT NULL,
    titulo              VARCHAR(200),
    codparticipante     VARCHAR(20),
    calificacion        FLOAT        DEFAULT 0,
    primeracalificacion BIT          DEFAULT 0,
    idcomision          VARCHAR(20),
    CONSTRAINT PK_TrabajoCientifico PRIMARY KEY (codigo),
    CONSTRAINT FK_Trabajo_Participante FOREIGN KEY (codparticipante)
        REFERENCES Participante(codparticipante),
    CONSTRAINT FK_Trabajo_Comision FOREIGN KEY (idcomision)
        REFERENCES Comision(idcomision)
);
GO

-- Tabla de union Evento_Comision (M:N entre Evento y Comision)
CREATE TABLE Evento_Comision (
    codevento  VARCHAR(20) NOT NULL,
    idcomision VARCHAR(20) NOT NULL,
    CONSTRAINT PK_Evento_Comision PRIMARY KEY (codevento, idcomision),
    CONSTRAINT FK_EC_Evento   FOREIGN KEY (codevento)  REFERENCES Evento(codigo),
    CONSTRAINT FK_EC_Comision FOREIGN KEY (idcomision) REFERENCES Comision(idcomision)
);
GO

-- Tabla de union Evento_Recurso (M:N entre Evento y Recurso)
CREATE TABLE Evento_Recurso (
    codevento  VARCHAR(20) NOT NULL,
    codrecurso VARCHAR(20) NOT NULL,
    CONSTRAINT PK_Evento_Recurso PRIMARY KEY (codevento, codrecurso),
    CONSTRAINT FK_ER_Evento  FOREIGN KEY (codevento)  REFERENCES Evento(codigo),
    CONSTRAINT FK_ER_Recurso FOREIGN KEY (codrecurso) REFERENCES Recurso(codigo)
);
GO

-- Tabla de union Comision_Jurado (M:N entre Comision y Jurado)
CREATE TABLE Comision_Jurado (
    idcomision VARCHAR(20) NOT NULL,
    codjurado  VARCHAR(20) NOT NULL,
    CONSTRAINT PK_Comision_Jurado PRIMARY KEY (idcomision, codjurado),
    CONSTRAINT FK_CJ_Comision FOREIGN KEY (idcomision) REFERENCES Comision(idcomision),
    CONSTRAINT FK_CJ_Jurado   FOREIGN KEY (codjurado)  REFERENCES Jurado(codjurado)
);
GO
