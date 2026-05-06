-- Datos iniciales para ejecucion local con H2.

-- Usuario: admin / admin123
INSERT INTO usuarios (username, password_hash, rol) VALUES
('admin', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy76.km', 'ADMIN');

INSERT INTO vendedores (nombre, telefono, email, usuario_id) VALUES
('Juan Perez', '3001234567', 'juan.perez@telecom.com', 1);

INSERT INTO barrios (nombre, cobertura, descripcion) VALUES
('Ciudad Jardin', true, 'Zona residencial de Cali con cobertura de internet fibra y fija'),
('Granada', true, 'Barrio comercial con cobertura estable para servicios residenciales'),
('Valle del Lili', false, 'Zona en expansion sin cobertura disponible actualmente'),
('El Penon', true, 'Sector con cobertura disponible para nuevos clientes de internet');

INSERT INTO clientes (nombre, direccion, telefono, barrio_id) VALUES
('Empresa ABC S.A.S', 'Calle Principal 123, Cali', '3105678901', 1);

INSERT INTO planes (nombre, tipo_servicio, precio_mensual, descripcion, activo) VALUES
('Internet Basico 100 Mbps', 'INTERNET', 80000.00, 'Internet residencial de 100 Mbps', true),
('Television Hogar', 'TELEVISION', 55000.00, 'Television con canales nacionales e internacionales', true),
('Combo Internet + Television', 'INTERNET_TELEVISION', 120000.00, 'Internet residencial de 200 Mbps con television', true);

INSERT INTO contratos (fecha_inicio, fecha_fin, estado, cliente_id_cliente, plan_id_plan, vendedor_id_vendedor) VALUES
('2024-01-15', '2025-01-15', 'ACTIVO', 1, 1, 1);

INSERT INTO facturas (fecha_emision, total, estado, contrato_id_contrato) VALUES
('2024-04-01', 80000.00, 'PENDIENTE', 1);
