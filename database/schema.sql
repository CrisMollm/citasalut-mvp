
DROP TABLE IF EXISTS cita;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS medico;
DROP TABLE IF EXISTS especialidad;


CREATE TABLE usuario (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         rol VARCHAR(20) DEFAULT 'PACIENTE'
);


CREATE TABLE cita (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      usuario_id INT NOT NULL,
                      nombre_medico VARCHAR(100) NOT NULL,
                      especialidad VARCHAR(100) NOT NULL,
                      fecha_hora DATETIME NOT NULL,
                      estado VARCHAR(20) DEFAULT 'PENDIENTE', -- Valores: PENDIENTE, REALIZADA, CANCELADA

                      FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE especialidad (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE medico (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        especialidad_id INT NOT NULL,
                        FOREIGN KEY (especialidad_id) REFERENCES especialidad(id)
);