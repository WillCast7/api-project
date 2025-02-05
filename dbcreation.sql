CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(255),
    validator VARCHAR(50),
    description VARCHAR(255),
    role_name VARCHAR(50),
    status BOOLEAN
);

CREATE TABLE IF NOT exists permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT exists menus (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    father VARCHAR(255),
    route VARCHAR(255),
    menu_order INT,
    icon VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS rolepermission (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS menuroles(
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, menu_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menus(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS configparams(
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    shortname VARCHAR(50) NOT NULL,
    parent VARCHAR(50) NOT NULL,
    status INT NOT NULL
);


INSERT INTO permissions (name) VALUES ('READ'), ('CREATE'), ('UPDATE'), ('DELETE'), ('SUPERUSER');

INSERT INTO roles (role, validator, description, role_name, status) VALUES
('superuser', 'superuser', 'Este rol tiene permisos globales sobre toda la aplicación', 'Super Usuario', TRUE),
('admin', 'admin', 'Este rol tiene privilegios elevados', 'Administrador', TRUE),
('supervisor', 'supervisor', 'Este rol esta mas enfocado en reportes y métricas', 'Supervisor', TRUE),
('operative', 'operative', 'Usuario operativo con capacidad de gestionar turnos', 'Operativo', TRUE),
('digiter', 'digiter', 'Este rol solo ingresará datos', 'Digitador', TRUE);

INSERT INTO rolepermission (role_id, permission_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(2, 1), (2, 2), (2, 3), (2, 4),
(3, 1), (3, 2), (3, 3),
(4, 1), (4, 2),
(5, 1);

INSERT INTO menus (name, father, route, menu_order, icon) VALUES
('Asesores', 'Mercadeo', 'mercadeo/asesores', 1, NULL),
('Contacto de clientes', 'Mercadeo', 'mercadeo/contactarclientes', 2, NULL),
('Ventas', 'Mercadeo', 'mercadeo/ventas', 1, NULL),
('Llamadas', 'Mercadeo', 'mercadeo/llamadas', 2, NULL),
('Mi cuenta', 'Configuracion', 'configuracion/micuenta', 1, NULL);

INSERT INTO menuroles (role_id, menu_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5),
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5),
(4, 1), (4, 2), (4, 3), (4, 4), (4, 5),
(5, 1), (5, 2), (5, 3), (5, 4), (5, 5);
