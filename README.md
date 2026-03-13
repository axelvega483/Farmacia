<h1 align="center">
  💊 Sistema de Gestión para Farmacia
</h1>

<p align="center">
  <b>Sistema backend completo para administración integral de farmacia</b>
  <br>
  <em>Desarrollado con Spring Boot • MySQL • OpenAPI 3 • Spring Security • JWT</em>
</p>

<p align="center">
  <a href="http://localhost:8080/swagger-ui/index.html">
    <img src="https://img.shields.io/badge/Documentación-SwaggerUI-brightgreen?style=for-the-badge&logo=swagger" alt="Swagger UI">
  </a>
  <a href="http://localhost:8080/v3/api-docs">
    <img src="https://img.shields.io/badge/API-OpenAPI3-orange?style=for-the-badge&logo=openapi-initiative" alt="OpenAPI 3">
  </a>
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Spring_Security-6.4.5-green?style=for-the-badge&logo=springsecurity" alt="Spring Security">
  <img src="https://img.shields.io/badge/JWT-Authentication-black?style=for-the-badge&logo=json-web-tokens" alt="JWT">
</p>

---

## 🔐 Seguridad y Autenticación

<div align="center">

| Característica | Icono | Descripción |
|----------------|-------|-------------|
| **Autenticación JWT** | 🔑 | Sistema completo de autenticación basado en tokens JWT |
| **Roles de Usuario** | 👥 | Distinción entre ADMIN y EMPLEADO con diferentes permisos |
| **Endpoints Públicos** | 🌐 | Registro y login accesibles sin autenticación |
| **Protección de Rutas** | 🛡️ | Endpoints protegidos según rol y autenticación |
| **Token Bearer** | 🎫 | Autenticación mediante header Authorization: Bearer {token} |

</div>

### 📋 Endpoints de Autenticación

| Endpoint | Método | Descripción | Acceso |
|----------|--------|-------------|--------|
| `/usuario/login` | POST | Inicio de sesión (devuelve token JWT) | Público |
| Resto de endpoints | Varios | Operaciones del sistema | Requiere JWT |

---

## 🌟 Características del Sistema

<div align="center">

| Característica | Icono | Descripción |
|----------------|-------|-------------|
| **Gestión Avanzada de Ventas** | 💰 | Control automático de stock y totales |
| **Anulación Inteligente** | 🔄 | Cancelación de ventas con restauración de inventario |
| **Control de Inventario** | 📊 | Seguimiento en tiempo real de medicamentos |
| **Relaciones Sólidas** | 🔗 | Entidades interconectadas: clientes, empleados, medicamentos |
| **Validaciones Integradas** | ✅ | Modelo con mensajes personalizados y robustos |
| **DTOs Personalizados** | 🎯 | Vistas específicas para diferentes respuestas |
| **Inicialización Automática** | 🤖 | Empleado administrador creado automáticamente |
| **Generación de Facturas** | 🧾 | Sistema preparado para generación de PDF |
| **Seguridad JWT** | 🔒 | Autenticación stateless con tokens |

</div>

---

## 📦 Módulos del Sistema

<div align="center">

| Módulo              | Icono | Descripción | Endpoints        |
|---------------------|-------|------------|------------------|
|**Usuarios**        | 👨‍💼 |  Login JWT | `/auth/login`    |
| **Usuarios**        | 👨‍💼 | Gestión de personal y roles  | `/usuario/*`     |
| **Clientes**        | 👥 | Registro y seguimiento | `/cliente/*`     |
| **Medicamentos**    | 💊 | Control de inventario y stock | `/medicamento/*` |
| **Proveedores**     | 🏢 | Administración de proveedores | `/proveedor/*`   |
| **Ventas**          | 💰 | Procesos de venta y facturación | `/venta/*`       |
| **Recetas Médicas** | 📝 | Gestión de recetas y prescripciones | `/receta/*`      |

</div>

---

## 🛠️ Tecnologías Utilizadas

<div align="center">

### Back-end (API REST)

| Tecnología | Icono | Uso |
|------------|-------|-----|
| **Java 17** | <img src="https://img.shields.io/badge/Java-17-blue?style=flat&logo=openjdk" alt="Java 17"> | Lenguaje de programación principal |
| **Spring Boot** | <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=flat&logo=springboot" alt="Spring Boot"> | Framework principal de desarrollo |
| **Spring Security** | <img src="https://img.shields.io/badge/Spring_Security-6.4.5-green?style=flat&logo=springsecurity" alt="Spring Security"> | Autenticación y autorización |
| **JWT** | <img src="https://img.shields.io/badge/JWT-000000?style=flat&logo=json-web-tokens" alt="JWT"> | Tokens de autenticación stateless |
| **Spring Data JPA** | <img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat&logo=hibernate" alt="Spring Data JPA"> | Persistencia y mapeo ORM |
| **MySQL** | <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql" alt="MySQL"> | Base de datos relacional |
| **Bean Validation** | <img src="https://img.shields.io/badge/Validation-JSR380-orange?style=flat" alt="Bean Validation"> | Validación de datos y modelos |
| **Maven** | <img src="https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven" alt="Maven"> | Gestión de dependencias y build |
| **Lombok** | <img src="https://img.shields.io/badge/Lombok-red?style=flat&logo=lombok" alt="Lombok"> | Reducción de código boilerplate |

</div>

---

## 📝 Requerimientos Funcionales

<div align="center">

| Módulo                 | Funcionalidades | Estado |
|------------------------|-----------------|--------|
| **👨‍💼 Usuarios**     | Alta, baja, edición y listado • Roles (ADMIN, EMPLEADO) • Login con JWT | ✅ Implementado |
| **👥 Clientes**        | Gestión completa • Visualización de historial de compras y recetas | ✅ Implementado |
| **💊 Medicamentos**    | Control de stock • Búsqueda por nombre • Gestión de proveedores | ✅ Implementado |
| **🏢 Proveedores**     | CRUD completo • Asociación con medicamentos | ✅ Implementado |
| **💰 Ventas**          | Registro con control de stock • Anulación con restauración • Generación de facturas PDF | ✅ Implementado |
| **📝 Recetas Médicas** | Gestión completa • Asociación con clientes • Validaciones integradas | ✅ Implementado |

</div>

---

## 📄 Documentación Técnica

<div align="center">

| Recurso | Enlace | Descripción |
|---------|--------|-------------|
| **📖 Swagger UI** | [Swagger](http://localhost:8080/swagger-ui/index.html) | Documentación interactiva completa de la API |
| **🔧 OpenAPI JSON** | [OpenAPI](http://localhost:8080/v3/api-docs) | Especificación OpenAPI en formato JSON |

</div>

---

## ⚙️ Requerimientos No Funcionales

<div align="center">

| Categoría | Especificación | Estado |
|-----------|----------------|--------|
| **🛡️ Validaciones** | Entidades con mensajes claros y personalizados | ✅ Implementado |
| **🔒 Seguridad** | Autenticación JWT • Protección de endpoints • Roles y permisos | ✅ Implementado |
| **📐 Modularidad** | Arquitectura escalable para futuras integraciones | ✅ Implementado |
| **💻 Código Limpio** | Principios SOLID y buenas prácticas de desarrollo | ✅ Implementado |
| **📊 Performance** | Consultas optimizadas y gestión eficiente de recursos | ✅ Implementado |
| **🧾 Facturación** | Sistema preparado para generación de PDF | ✅ Implementado |

</div>

---

## 🚀 Credenciales por Defecto

| Usuario           | Contraseña | Rol |
|-------------------|---------|-----|
| `admin@admin.com` | `admin` | ADMIN |

---

<div align="center">

## 🚀 ¿Listo para Comenzar?

[**📖 Ir a la Documentación Interactiva**](http://localhost:8080/swagger-ui/index.html)

---
*Desarrollado con ❤️ usando Spring Boot, Spring Security, JWT y Java 17*

</div>