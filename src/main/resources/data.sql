-- Insertar datos iniciales en las tablas

-- Tabla: usuarios
INSERT INTO usuarios (username, password_hash, rol) VALUES
('admin', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy76.km', 'ADMIN')
ON CONFLICT (username) DO NOTHING;

-- Tabla: vendedores
INSERT INTO vendedores (nombre, telefono, email, usuario_id) VALUES
('Juan Pérez', '3001234567', 'juan.perez@telecom.com', 1)
ON CONFLICT DO NOTHING;

-- Tabla: clientes
INSERT INTO clientes (nombre, direccion, telefono) VALUES
('Empresa ABC S.A.S', 'Calle Principal 123, Bogotá', '3105678901')
ON CONFLICT DO NOTHING;

-- Tabla: planes
INSERT INTO planes (nombre, tipo_servicio, precio_mensual, descripcion, activo) VALUES
('Plan Básico', 'Voz', 25000.00, 'Plan básico de voz con 100 minutos', true)
ON CONFLICT DO NOTHING;

-- Tabla: contratos
INSERT INTO contratos (fecha_inicio, fecha_fin, estado, cliente_id_cliente, plan_id_plan, vendedor_id_vendedor) VALUES
('2024-01-15', '2025-01-15', 'ACTIVO', 1, 1, 1)
ON CONFLICT DO NOTHING;

-- Tabla: infraestructura
-- (Si existe tabla infraestructura, descomentar la siguiente línea)
-- INSERT INTO infraestructura (infraestructura_id, ...) VALUES (...);

-- Tabla: facturas
INSERT INTO facturas (fecha_emision, total, estado, contrato_id_contrato) VALUES
('2024-04-01', 25000.00, 'PENDIENTE', 1)
ON CONFLICT DO NOTHING;
