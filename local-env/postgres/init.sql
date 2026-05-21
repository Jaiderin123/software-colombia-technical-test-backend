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

-- ==========================================
-- 2. Seed Data (Mandatory Test Requirements & Users)
-- ==========================================

-- All passwords are 'admin123' encoded with BCrypt
INSERT INTO users (email, password, full_name) VALUES
                                                   ('test@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Usuario Prueba'),
                                                   ('carlos.dev@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Carlos Developer'),
                                                   ('laura.qa@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Laura Quality Assurance'),
                                                   ('andres.pm@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Andres Project Manager'),
                                                   ('sofia.design@software-colombia.com', '$2a$10$0OAsZdEDXSzzqeydlGFiXOeyGArlYC3169cg0TZlW4l0d/Ncl2tWG', 'Sofia UI/UX');

INSERT INTO workspaces (name) VALUES ('Workspace Alfa');
INSERT INTO workspaces (name) VALUES ('Workspace Beta');

-- ==========================================
-- 3. Role Assignments (Consolidated to W1 and W2)
-- ==========================================

-- Test User (User 1) - Base requirements
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (1, 1, 'ADMIN');  -- Admin in Alfa
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (1, 2, 'LECTOR'); -- Lector in Beta

-- Carlos (User 2)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (2, 1, 'EDITOR');
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (2, 2, 'ADMIN');  -- Reassigned from Gamma

-- Laura (User 3)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (3, 1, 'LECTOR');
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (3, 2, 'EDITOR'); -- Reassigned from Gamma

-- Andres (User 4)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (4, 1, 'LECTOR');
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (4, 2, 'ADMIN');  -- Reassigned from Delta/Epsilon

-- Sofia (User 5)
INSERT INTO workspace_users (user_id, workspace_id, role) VALUES (5, 2, 'EDITOR'); -- Reassigned from Delta

-- ==========================================
-- 4. Projects (Consolidated to W1 and W2)
-- ==========================================

-- Projects for Workspace 1 (Alfa)
INSERT INTO projects (workspace_id, name, description) VALUES
                                                           (1, 'Migración de Microservicios', 'Proyecto para migrar monolito a Spring Boot'),
                                                           (1, 'Implementación de WebFlux', 'Refactorización a programación reactiva'),
                                                           (1, 'Actualización de Dependencias', 'Subir versión de Spring Boot a 3.2.x'),
                                                           (1, 'Configuración de CI/CD', 'Crear pipelines en GitHub Actions'),
                                                           (1, 'Optimización de Consultas R2DBC', 'Revisar JOINs lentos en el módulo de reportes'),
                                                           (1, 'Nuevo Módulo de Facturación', 'Implementar pasarela de pagos con Stripe'),
                                                           (1, 'API Pública', 'Diseñar endpoints REST para integración de terceros'),
                                                           (1, 'Campaña Q3 Redes Sociales', 'Planificación de posts y anuncios para Instagram'),
                                                           (1, 'Auditoría Fiscal', 'Preparar documentación para revisión externa');

-- Projects for Workspace 2 (Beta)
INSERT INTO projects (workspace_id, name, description) VALUES
                                                           (2, 'Auditoría de Seguridad', 'Revisión de accesos y roles'),
                                                           (2, 'Análisis de Logs', 'Monitoreo de trazabilidad en contenedores'),
                                                           (2, 'Pruebas de Carga', 'Ejecutar script de JMeter para validar concurrencia'),
                                                           (2, 'Migración a AWS', 'Mover base de datos a RDS y backend a ECS'),
                                                           (2, 'Rediseño del Dashboard', 'Migrar componentes de Angular a React puro'),
                                                           (2, 'App Móvil MVP', 'Prototipo en Flutter para iOS y Android'),
                                                           (2, 'SEO Technical Audit', 'Mejorar el Core Web Vitals del landing page'),
                                                           (2, 'Presupuesto Anual 2027', 'Definición de OPEX y CAPEX por departamento'),
                                                           (2, 'Lanzamiento de Newsletter', 'Configuración de Mailchimp y plantillas HTML');