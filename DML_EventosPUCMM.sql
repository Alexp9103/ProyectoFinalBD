-- DML - Sentencias de Manipulacion de Datos
-- Base de datos: EventosPUCMM
-- Proyecto Final - Gestion de Eventos Cientificos PUCMM
-- Grupo 1

USE EventosPUCMM;
GO

-- SELECT (carga inicial al arrancar la aplicacion)
-- Estos SELECT se ejecutan una sola vez al inicio para cargar
-- todos los datos en memoria desde la base de datos.

SELECT roll, nombre_usuario, contrasena
FROM Usuario;

SELECT codparticipante, cedula, nombre, telefono
FROM Participante;

SELECT codjurado, cedula, nombre, telefono, areaespecializado
FROM Jurado;

SELECT codigo, disponible, ubicacion, tipo, descripcion
FROM Recurso;

SELECT idcomision, area, codjurado_presidente
FROM Comision;

SELECT idcomision, codjurado
FROM Comision_Jurado;

SELECT codigo, nombre, ubicacion, fechainicio, fechafinal, cupo
FROM Evento;

SELECT codevento, idcomision
FROM Evento_Comision;

SELECT codevento, codrecurso
FROM Evento_Recurso;

SELECT codigo, titulo, codparticipante, calificacion, primeracalificacion, idcomision
FROM TrabajoCientifico;

GO

-- INSERT (se ejecuta cada vez que el usuario registra una nueva entidad)

INSERT INTO Participante (codparticipante, cedula, nombre, telefono)
VALUES ('P001', '001-0000001-1', 'Juan Perez', '809-555-0001');

INSERT INTO Jurado (codjurado, cedula, nombre, telefono, areaespecializado)
VALUES ('J001', '001-0000002-2', 'Maria Lopez', '809-555-0002', 'Inteligencia Artificial');

INSERT INTO Recurso (codigo, disponible, ubicacion, tipo, descripcion)
VALUES ('R001', 1, 'Salon A-101', 'Proyector', 'Proyector HD 1080p');

-- Comision con presidente asignado
INSERT INTO Comision (idcomision, area, codjurado_presidente)
VALUES ('C001', 'Tecnologia', 'J001');

-- Comision sin presidente todavia
INSERT INTO Comision (idcomision, area, codjurado_presidente)
VALUES ('C002', 'Ciencias Basicas', NULL);

-- Agregar jurado como miembro de una comision
INSERT INTO Comision_Jurado (idcomision, codjurado)
VALUES ('C001', 'J001');

INSERT INTO Evento (codigo, nombre, ubicacion, fechainicio, fechafinal, cupo)
VALUES ('E001', 'Congreso Cientifico 2025', 'Campus PUCMM', '2025-03-01', '2025-03-03', 200);

INSERT INTO Evento_Comision (codevento, idcomision)
VALUES ('E001', 'C001');

INSERT INTO Evento_Recurso (codevento, codrecurso)
VALUES ('E001', 'R001');

INSERT INTO TrabajoCientifico (codigo, titulo, codparticipante, calificacion, primeracalificacion, idcomision)
VALUES ('T001', 'Aplicaciones de IA en Medicina', 'P001', 0.0, 0, 'C001');

INSERT INTO Usuario (roll, nombre_usuario, contrasena)
VALUES ('admin', 'admin01', 'clave123');

GO

-- UPDATE (se ejecuta al modificar datos o calificar un trabajo)

UPDATE Participante
SET cedula   = '001-0000001-1',
    nombre   = 'Juan Alexander Perez',
    telefono = '809-555-9999'
WHERE codparticipante = 'P001';

UPDATE Jurado
SET cedula           = '001-0000002-2',
    nombre           = 'Maria Elena Lopez',
    telefono         = '809-555-8888',
    areaespecializado = 'Machine Learning'
WHERE codjurado = 'J001';

UPDATE Comision
SET area                = 'Tecnologia e Innovacion',
    codjurado_presidente = 'J001'
WHERE idcomision = 'C001';

UPDATE Evento
SET nombre      = 'Congreso Cientifico PUCMM 2025',
    ubicacion   = 'Campus Santiago',
    fechainicio = '2025-03-10',
    fechafinal  = '2025-03-12',
    cupo        = 250
WHERE codigo = 'E001';

UPDATE Recurso
SET disponible  = 0,
    ubicacion   = 'Salon B-202',
    descripcion = 'Proyector 4K asignado a evento'
WHERE codigo = 'R001';

-- Calificar un trabajo cientifico
UPDATE TrabajoCientifico
SET calificacion        = 87.5,
    primeracalificacion = 1
WHERE codigo = 'T001';

GO

-- DELETE (se ejecuta al eliminar entidades desde la aplicacion)
-- Antes de eliminar se deben limpiar las referencias FK para
-- evitar errores de integridad referencial.

-- Eliminar participante: primero se desvincula de sus trabajos
UPDATE TrabajoCientifico
SET codparticipante = NULL
WHERE codparticipante = 'P001';

DELETE FROM Participante
WHERE codparticipante = 'P001';

-- Eliminar jurado: se quita como presidente y de las comisiones
UPDATE Comision
SET codjurado_presidente = NULL
WHERE codjurado_presidente = 'J001';

DELETE FROM Comision_Jurado
WHERE codjurado = 'J001';

DELETE FROM Jurado
WHERE codjurado = 'J001';

-- Eliminar recurso: se quita de los eventos donde estaba asignado
DELETE FROM Evento_Recurso
WHERE codrecurso = 'R001';

DELETE FROM Recurso
WHERE codigo = 'R001';

-- Eliminar trabajo cientifico
DELETE FROM TrabajoCientifico
WHERE codigo = 'T001';

-- Eliminar evento: se limpian sus relaciones con comisiones y recursos
DELETE FROM Evento_Comision
WHERE codevento = 'E001';

DELETE FROM Evento_Recurso
WHERE codevento = 'E001';

DELETE FROM Evento
WHERE codigo = 'E001';

GO
