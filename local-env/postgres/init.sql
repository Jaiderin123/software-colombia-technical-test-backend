-- 1. Table Structure

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL, -- Stores BCrypt encoded hashes (60 characters)
                       full_name VARCHAR(255) NOT NULL
);

CREATE TABLE workspaces (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

CREATE TABLE workspace_users (
                                 user_id INT REFERENCES users(id) ON DELETE CASCADE,
                                 workspace_id INT REFERENCES workspaces(id) ON DELETE CASCADE,
                                 role VARCHAR(50) NOT NULL, -- Allowed values: 'ADMIN', 'EDITOR', 'LECTOR'
                                 PRIMARY KEY (user_id, workspace_id)
);

CREATE TABLE projects (
                          id SERIAL PRIMARY KEY,
                          workspace_id INT REFERENCES workspaces(id) ON DELETE CASCADE,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Seed Data (Mandatory Test Requirements)

-- Test user (Credentials: test@software-colombia.com / admin123)
-- The password hash below corresponds to 'admin123' encoded with BCrypt
INSERT INTO users (email, password, full_name)
VALUES ('test@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Usuario Prueba');

-- Creation of the 2 required Workspaces
INSERT INTO workspaces (name) VALUES ('Workspace Alfa');
INSERT INTO workspaces (name) VALUES ('Workspace Beta');

-- Role Assignments based on requirements:
-- Admin in Workspace Alfa (user_id = 1, workspace_id = 1)
INSERT INTO workspace_users (user_id, workspace_id, role)
VALUES (1, 1, 'ADMIN');

-- Lector in Workspace Beta (user_id = 1, workspace_id = 2)
INSERT INTO workspace_users (user_id, workspace_id, role)
VALUES (1, 2, 'LECTOR');

-- Projects for Workspace Alfa (User has ADMIN role here)
INSERT INTO projects (workspace_id, name, description)
VALUES (1, 'Migración de Microservicios', 'Proyecto para migrar monolito a Spring Boot');
INSERT INTO projects (workspace_id, name, description)
VALUES (1, 'Implementación de WebFlux', 'Refactorización a programación reactiva');

-- Projects for Workspace Beta (User has LECTOR role here)
INSERT INTO projects (workspace_id, name, description)
VALUES (2, 'Auditoría de Seguridad', 'Revisión de accesos y roles');
INSERT INTO projects (workspace_id, name, description)
VALUES (2, 'Análisis de Logs', 'Monitoreo de trazabilidad en contenedores');

-- ==========================================
-- 3. Additional Seed Data for Robust Testing
-- ==========================================

-- --- More Users ---
-- (All passwords are 'admin123' encoded with BCrypt)
INSERT INTO users (email, password, full_name) VALUES
                                                   ('carlos.dev@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Carlos Developer'),
                                                   ('laura.qa@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Laura Quality Assurance'),
                                                   ('andres.pm@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Andres Project Manager'),
                                                   ('sofia.design@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Sofia UI/UX');

-- --- More Workspaces ---
INSERT INTO workspaces (name) VALUES
                                  ('Workspace Gamma - Desarrollo'),
                                  ('Workspace Delta - Marketing'),
                                  ('Workspace Epsilon - Finanzas');

-- --- Complex Role Assignments (workspace_users) ---

-- Carlos (User 2)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (2, 1, 'EDITOR'); -- EDITOR in Alfa
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (2, 3, 'ADMIN');  -- ADMIN in Gamma (Development)

-- Laura (User 3)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (3, 1, 'LECTOR'); -- LECTOR in Alfa
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (3, 3, 'EDITOR'); -- EDITOR in Gamma

-- Andres (User 4)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (4, 4, 'ADMIN');  -- ADMIN in Delta (Marketing)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (4, 5, 'ADMIN');  -- ADMIN in Epsilon (Finance)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (4, 1, 'LECTOR'); -- LECTOR in Alfa

-- Sofia (User 5)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (5, 4, 'EDITOR'); -- EDITOR in Delta

-- Test User (User 1 - Base requirement, granting more access)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (1, 3, 'LECTOR'); -- LECTOR in Gamma


-- --- More Projects ---

-- Projects for Workspace 1 (Alfa)
INSERT INTO projects (workspace_id, name, description) VALUES
                                                           (1, 'Actualización de Dependencias', 'Subir versión de Spring Boot a 3.2.x'),
                                                           (1, 'Configuración de CI/CD', 'Crear pipelines en GitHub Actions'),
                                                           (1, 'Optimización de Consultas R2DBC', 'Revisar JOINs lentos en el módulo de reportes');

-- Projects for Workspace 2 (Beta)
INSERT INTO projects (workspace_id, name, description) VALUES
                                                           (2, 'Pruebas de Carga', 'Ejecutar script de JMeter para validar concurrencia'),
                                                           (2, 'Migración a AWS', 'Mover base de datos a RDS y backend a ECS');

-- Projects for Workspace 3 (Gamma - Development)
INSERT INTO projects (workspace_id, name, description) VALUES
                                                           (3, 'Nuevo Módulo de Facturación', 'Implementar pasarela de pagos con Stripe'),
                                                           (3, 'Rediseño del Dashboard', 'Migrar componentes de Angular a React puro'),
                                                           (3, 'API Pública', 'Diseñar endpoints REST para integración de terceros'),
                                                           (3, 'App Móvil MVP', 'Prototipo en Flutter para iOS y Android');

-- Projects for Workspace 4 (Delta - Marketing)
INSERT INTO projects (workspace_id, name, description) VALUES
                                                           (4, 'Campaña Q3 Redes Sociales', 'Planificación de posts y anuncios para Instagram'),
                                                           (4, 'SEO Technical Audit', 'Mejorar el Core Web Vitals del landing page'),
                                                           (4, 'Lanzamiento de Newsletter', 'Configuración de Mailchimp y plantillas HTML');

-- Projects for Workspace 5 (Epsilon - Finance)
INSERT INTO projects (workspace_id, name, description) VALUES
                                                           (5, 'Presupuesto Anual 2027', 'Definición de OPEX y CAPEX por departamento'),
                                                           (5, 'Auditoría Fiscal', 'Preparar documentación para revisión externa');