# 🗂️ Project Manager API — Software Colombia

> API reactiva para la gestión de proyectos en un entorno **SaaS Multi-Tenant**, donde un usuario puede pertenecer a múltiples espacios de trabajo (*Workspaces*) con roles diferenciados por contexto (**Admin**, **Editor**, **Lector**), implementada con Spring WebFlux y arquitectura hexagonal.
 
---

## 🛠️ Stack Tecnológico

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.5 (WebFlux — Programación Reactiva) |
| Seguridad | Spring Security + JWT (JSON Web Token) |
| Persistencia | R2DBC + PostgreSQL (no bloqueante) |
| Build Tool | Gradle |
| Contenedores | Docker + Docker Compose |
| Arquitectura | Hexagonal (Ports & Adapters) |
 
---

## 📐 Decisiones de Arquitectura

### ¿Por qué WebFlux?
Spring WebFlux permite manejar alta concurrencia con bajo consumo de hilos mediante programación reactiva (Project Reactor). Ideal para APIs que deben escalar sin bloquear recursos mientras esperan respuestas de base de datos o servicios externos.

### ¿Por qué Arquitectura Hexagonal?
Separa completamente la lógica de negocio (dominio) de los detalles de infraestructura (base de datos, seguridad, HTTP). Esto permite:
- Cambiar la base de datos sin tocar el dominio.
- Testear la lógica de negocio de forma aislada.
- Mantener un código desacoplado y extensible.
```
src/
├── application/         # Casos de uso (orquestación)
├── domain/              # Entidades, puertos e interfaces puras
└── infrastructure/
    ├── adapters/        # Implementaciones de persistencia (R2DBC)
    ├── config/          # Seguridad, JWT, configuración global
    └── entrypoints/     # Handlers y routers funcionales (WebFlux)
```

### ¿Por qué dos tokens JWT?
El flujo de autenticación maneja dos tokens con responsabilidades distintas:

```
POST /auth/login
  └─► preAuthToken (5 min) — identifica al usuario, lista sus workspaces
 
POST /auth/token  (con preAuthToken + workspaceId)
  └─► accessToken (1 hora) — contiene workspaceId + role del contexto activo
```

Esto garantiza que el `role` y el `workspaceId` estén embebidos en el token de acceso, manteniendo el sistema **stateless** sin consultar la base de datos en cada request.

### Seguridad
Un filtro JWT personalizado (`JwtAuthenticationFilter`) intercepta cada request, valida el token criptográficamente y construye el `AuthPrincipal` con `userId`, `workspaceId` y `role`. Spring Security evalúa los permisos por ruta sin necesidad de repetir validaciones en los handlers.
 
---

## ✅ Requisitos Previos

Para ejecutar con Docker (**recomendado**):
- [Docker](https://www.docker.com/) instalado y corriendo.
- [Docker Compose](https://docs.docker.com/compose/) v2+.
  Para ejecutar en local sin Docker:
- JDK 21+
- PostgreSQL 16+
- Gradle 8+
---

## 🚀 Ejecución con Docker (Recomendado)

> Con un solo comando toda la aplicación queda funcional: base de datos con datos precargados + backend.

### 1. Clonar el repositorio

```bash
git clone https://github.com/Jaiderin123/software-colombia-technical-test.git
cd software-colombia-technical-test
```

### 2. Levantar los servicios

```bash
docker compose up --build
```

> La primera vez tarda unos minutos mientras descarga las imágenes y compila el proyecto.

### 3. Verificar que está corriendo

```
✅ postgres-db   → localhost:5432
✅ backend       → localhost:8080
```

### 4. Detener los servicios

```bash
docker compose down
```

> ⚠️ Al bajar los contenedores, la base de datos se reinicializa con los datos del script `init.sql` al volver a levantarlos. Esto garantiza un entorno limpio y reproducible.
 
---

## ⚙️ Ejecución en Local (Sin Docker)

### 1. Configurar variables de entorno

Crea un archivo `.env` en la raíz o expórtalas en tu terminal:

```env
DATABASE_ENDPOINT=localhost:5432
DATABASE_NAME=project_manager_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=password123
JWT_SECRET=CR0zMxXVfqMzeCsQJcEnJjhkphcScyueBh3b8Eq6l6S
APP_BASE_PATH=/api/v1
```

### 2. Crear la base de datos y cargar el script

```bash
docker compose up --build postgres-db -d
```

> Esto levanta únicamente el contenedor de PostgreSQL con los datos precargados del script `init.sql`. El flag `-d` lo ejecuta en segundo plano.

### 3. Ejecutar la aplicación

```bash
./gradlew bootRun
```
 
---

## 🔐 Usuarios Precargados

Todos los usuarios tienen la misma contraseña: **`admin123`**

| Email | Nombre | Workspace Alfa | Workspace Beta |
|---|---|---|---|
| `test@software-colombia.com` | Usuario Prueba | ADMIN | LECTOR |
| `carlos.dev@software-colombia.com` | Carlos Developer | EDITOR | ADMIN |
| `laura.qa@software-colombia.com` | Laura QA | LECTOR | EDITOR |
| `andres.pm@software-colombia.com` | Andres PM | ADMIN | ADMIN |
| `sofia.design@software-colombia.com` | Sofia UI/UX | EDITOR | LECTOR |
 
---

## 📡 Documentación de Endpoints

Base URL: `http://localhost:8080/api/v1`


## 🧪 Colección de Postman

Para probar los endpoints sin configuración manual, importa la colección incluida en el repositorio:

1. Abrir **Postman**.
2. Click en **Import**.
3. Seleccionar el archivo `backend-postman-collection.json` en la raíz del proyecto.
4. La colección incluye todos los endpoints con sus scripts de automatización para guardar el `preAuthToken` y el `accessToken` entre requests automáticamente.
---

## 🧱 Principios Aplicados

- **SOLID** — cada clase tiene una responsabilidad única y clara.
- **DRY** — configuración centralizada (rutas públicas en `application.yml`, propiedades en `SecurityProperties`).
- **KISS** — flujos simples y directos sin complejidad innecesaria.
- **Clean Code** — nombres descriptivos, métodos cortos, sin comentarios redundantes.
- **Stateless** — sin sesiones en servidor; toda la información de contexto viaja en el JWT.
---

## 📁 Estructura del Proyecto

```
src/main/java/com/softwarecolombia/projectmanager/
├── application/
│   └── usecase/
│       ├── auth/         # LoginAppUserService, LoginWorkspaceContextService
│       └── project/      # CreateProjectService, GetProjectsByWorkspaceService
├── domain/
│   ├── project/          # Modelo Project, puertos in/out
│   ├── user/             # Modelo AppUser, puertos in/out
│   ├── workspace/        # Modelo WorkspaceWithUserRole, puertos out
│   └── shared/           # Excepciones, utilidades de dominio
└── infrastructure/
    ├── adapters/
    │   └── persistence/  # Implementaciones R2DBC por entidad
    ├── config/
    │   └── security/     # JWT, filtros, Spring Security config
    └── entrypoints/
        ├── auth/         # AuthHandler + AuthRouterConfig
        └── project/      # ProjectHandler + ProjectRouterConfig
```
 
---

<div align="center">
  <sub>Desarrollado por <strong>Jaider Betancur</strong> ( <strong>torrezjaider10@gmail.com</strong> ) como prueba técnica para Software Colombia.</sub>
</div>