-- 1. Borrar tablas (primero cita por la clave foránea)
DROP TABLE IF EXISTS cita;
DROP TABLE IF EXISTS usuario;

-- 2. Tabla de Usuarios (Pacientes)
CREATE TABLE usuario (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         rol VARCHAR(20) DEFAULT 'PACIENTE'
);

-- 3. Tabla de Citas
CREATE TABLE cita (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      usuario_id BIGINT NOT NULL,
                      nombre_medico VARCHAR(100) NOT NULL,
                      especialidad VARCHAR(100) NOT NULL,
                      fecha_hora DATETIME NOT NULL,
                      estado VARCHAR(20) DEFAULT 'PENDIENTE', -- Valores: PENDIENTE, REALIZADA, CANCELADA

                      CONSTRAINT fk_cita_usuario
                          FOREIGN KEY (usuario_id) REFERENCES usuario(id)
                              ON DELETE CASCADE
);