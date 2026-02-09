-- Usuario de prueba (Password '1234' encriptada)
INSERT INTO usuario (nombre, email, password, rol) VALUES
    ('Cristian Moll', 'cristian@test.com', '1234', 'PACIENTE');

-- Cita de prueba
INSERT INTO cita (usuario_id, nombre_medico, especialidad, fecha_hora, estado) VALUES
    (1, 'Dr. House', 'Medicina General', '2026-02-20 10:00:00', 'PENDIENTE');