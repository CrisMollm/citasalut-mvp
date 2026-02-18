
INSERT INTO usuario (nombre, email, password, rol) VALUES
    ('Cristian Moll', 'cristian@test.com', '1234', 'PACIENTE');


INSERT INTO cita (usuario_id, nombre_medico, especialidad, fecha_hora, estado) VALUES
    (1, 'Dr. House', 'Medicina General', '2026-02-20 10:00:00', 'PENDIENTE');

INSERT INTO especialidad (nombre) VALUES
    ('Medicina General'), ('Pediatría');


INSERT INTO medico (nombre, especialidad_id) VALUES
    ('Dr. Smith', 1), ('Dra. García', 2);