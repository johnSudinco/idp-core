--1 ANTES DE EJECUTAR CREAR POR LA API EL CLIENT
INSERT INTO auth.roles
(id, "name", description)
VALUES(1, 'SUPER_ADMIN', 'Administradores');

INSERT INTO auth.roles
(id, "name", description)
VALUES(2, 'ADMIN', 'Administradores');

INSERT INTO auth.roles
(id, "name", description)
VALUES(3, 'USER', 'Usuarios');


--2
INSERT INTO config.client_scopes (client_id, scope_id)
SELECT c.id, s.id
FROM config.clients c, config.scopes s
WHERE c.client_id = 'core' AND s.name IN ('openid','profile','email');
--3
INSERT INTO auth.permissions (id, "name", description, created_at)
VALUES (nextval('auth.permissions_id_seq'::regclass), 'USER_READ', 'Permite leer información de usuarios', NOW());

INSERT INTO auth.permissions (id, "name", description, created_at)
VALUES (nextval('auth.permissions_id_seq'::regclass), 'USER_WRITE', 'Permite crear o actualizar usuarios', NOW());

INSERT INTO auth.permissions (id, "name", description, created_at)
VALUES (nextval('auth.permissions_id_seq'::regclass), 'ROLE_ASSIGN', 'Permite asignar roles a usuarios', NOW());

INSERT INTO auth.permissions (id, "name", description, created_at)
VALUES (nextval('auth.permissions_id_seq'::regclass), 'ROLE_REMOVE', 'Permite remover roles de usuarios', NOW());

INSERT INTO auth.permissions (id, "name", description, created_at)
VALUES (nextval('auth.permissions_id_seq'::regclass), 'AUDIT_VIEW', 'Permite ver eventos de auditoría', NOW());
--4
INSERT INTO auth.role_permissions (role_id, permission_id, granted_at)
SELECT r.id, p.id, CURRENT_TIMESTAMP
FROM auth.roles r, auth.permissions p
WHERE r.name = 'SUPER_ADMIN'
  AND p.name IN ('USER_READ','USER_WRITE','ROLE_ASSIGN','ROLE_REMOVE','AUDIT_VIEW');

INSERT INTO auth.role_permissions (role_id, permission_id, granted_at)
SELECT r.id, p.id, CURRENT_TIMESTAMP
FROM auth.roles r, auth.permissions p
WHERE r.name = 'ADMIN'
  AND p.name IN ('USER_READ','USER_WRITE','ROLE_ASSIGN');

INSERT INTO auth.role_permissions (role_id, permission_id, granted_at)
SELECT r.id, p.id, CURRENT_TIMESTAMP
FROM auth.roles r, auth.permissions p
WHERE r.name = 'USER'
  AND p.name IN ('USER_READ','USER_WRITE');
--5 ANTES DE ESTE PASO REGISTRAR POR LA API EL USUARIO MASTER

INSERT INTO auth.user_roles (user_id, role_id, assigned_at, id)
SELECT 2, r.id, CURRENT_TIMESTAMP, nextval('auth.user_roles_id_seq'::regclass)
FROM auth.roles r
WHERE r.name = 'SUPER_ADMIN';




