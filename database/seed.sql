-- Usuario de prueba (Password '1234' encriptada)
INSERT INTO usuario (nombre, email, password, rol) VALUES
    ('Cristian Moll', 'cristian@test.com', '$2a$10$N.zmdr9k7uOCQb376ye.5.Z95431G5.M5.iE55l5.54848548', 'PACIENTE');

-- Cita de prueba
INSERT INTO cita (usuario_id, nombre_medico, especialidad, fecha_hora, estado) VALUES
    (1, 'Dr. House', 'Medicina General', '2026-02-20 10:00:00', 'PENDIENTE');