INSERT INTO `profesores`
(`activo`, `apellido`, `ciudad`, `correo`, `distrito`, `edad`, `especialidad`, `nombre`, `ruc_dni`, `telefono`)
VALUES
(1, 'Gonzales', 'Lima', 'mgonzales@correo.com', 'Miraflores', 42, 'Matemáticas', 'María', '74851236', '987456321'),
(1, 'Rojas', 'Arequipa', 'jrojas@correo.com', 'Yanahuara', 51, 'Física', 'Jorge', '63521847', '945123789'),
(1, 'Fernández', 'Trujillo', 'lfernandez@correo.com', 'Cercado', 38, 'Comunicación', 'Lucía', '79842561', '912874563'),
(1, 'Valverde', 'Cusco', 'pvalverde@correo.com', 'San Sebastián', 47, 'Filosofía', 'Pedro', '70158924', '956231478'),
(1, 'Salazar', 'Piura', 'csalazar@correo.com', 'Castilla', 35, 'Biología', 'Carolina', '81256947', '998742561'),
(1, 'Mendoza', 'Lima', 'rmendoza@correo.com', 'San Isidro', 44, 'Economía', 'Ricardo', '72451896', '987321654'),
(1, 'Cárdenas', 'Chiclayo', 'acardenas@correo.com', 'La Victoria', 39, 'Historia', 'Andrea', '75869412', '921456873'),
(1, 'Peralta', 'Huancayo', 'jperalta@correo.com', 'El Tambo', 50, 'Química', 'Julio', '69524187', '954789123'),
(1, 'Serrano', 'Iquitos', 'dserrano@correo.com', 'Belén', 33, 'Sociología', 'Daniela', '84215963', '987654123'),
(1, 'Torres', 'Tacna', 'mtorres@correo.com', 'Alto de la Alianza', 46, 'Derecho', 'Miguel', '71894523', '963852741'),
(1, 'Ramírez', 'Lima', 'framirez@correo.com', 'Comas', 36, 'Inglés', 'Fabiola', '75961428', '945789632'),
(1, 'Ortega', 'Callao', 'lortega@correo.com', 'La Perla', 41, 'Estadística', 'Luis', '80412659', '917654328'),
(1, 'Vargas', 'Puno', 'svargas@correo.com', 'Juliaca', 52, 'Filosofía', 'Sandra', '74569821', '999555444'),
(1, 'Chávez', 'Lambayeque', 'pchavez@correo.com', 'José Leonardo Ortiz', 37, 'Literatura', 'Pablo', '78124569', '987111369'),
(1, 'Reyes', 'Lima', 'greyes@correo.com', 'Surco', 43, 'Computación', 'Gabriela', '71425698', '912365478');

INSERT INTO `cursos`
(`activo`, `descripcion`, `horas_semanales`, `nombre`, `nota_minima`)
VALUES
(1, 'Fundamentos del razonamiento matemático universitario', 5, 'Cálculo I', 11),
(1, 'Principios básicos de la mecánica clásica', 4, 'Física General', 11),
(1, 'Desarrollo de habilidades comunicativas académicas', 3, 'Comunicación Oral y Escrita', 11),
(1, 'Estudio introductorio de la filosofía occidental', 3, 'Introducción a la Filosofía', 11),
(1, 'Conceptos esenciales de la biología moderna', 4, 'Biología General', 11),
(1, 'Teoría económica del comportamiento del consumidor', 4, 'Microeconomía I', 11),
(1, 'Análisis histórico de la formación del Perú', 3, 'Historia del Perú', 11),
(1, 'Estudio de la materia y sus transformaciones', 4, 'Química General', 11),
(1, 'Comprensión de la estructura social contemporánea', 3, 'Introducción a la Sociología', 11),
(1, 'Bases de la organización del Estado peruano', 4, 'Derecho Constitucional', 11),
(1, 'Curso orientado al dominio del idioma inglés', 3, 'Inglés Intermedio', 11),
(1, 'Manejo de datos para investigación científica', 5, 'Estadística Aplicada', 11),
(1, 'Reflexión ética en el ejercicio profesional', 3, 'Ética Profesional', 11),
(1, 'Análisis de obras literarias universales', 3, 'Literatura Universal', 11),
(1, 'Bases de la programación y pensamiento lógico', 5, 'Fundamentos de Programación', 11);

INSERT INTO `usuarios`(`clave`, `nombre`, `rol`, `usuario`) VALUES ('$2a$12$UyZ/0sfHrA2ivPlcSonE9er82qfMQBalL1l8G0SZdGz4kHMc64I0a','ADMIN','ADMIN','ADMIN')