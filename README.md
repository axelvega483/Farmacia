<h1 align="center">
  💊 Sistema de Gestión para Farmacia
</h1>

<p align="center">
  <b>Sistema backend completo para administración integral de farmacia</b>
  <br>
  <em>Desarrollado con Spring Boot • MySQL • OpenAPI 3</em>
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
</p>

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

</div>

---

## 📦 Módulos del Sistema

<div align="center">

| Módulo | Icono | Descripción | Endpoints |
|--------|-------|-------------|-----------|
| **Empleados** | 👨‍💼 | Gestión de personal y roles | `GET/POST/PUT/DELETE /usuario` |
| **Clientes** | 👥 | Registro y seguimiento | `GET/POST/PUT /cliente` |
| **Medicamentos** | 💊 | Control de inventario y stock | `GET/POST/PUT/DELETE /medicamento` |
| **Proveedores** | 🏢 | Administración de proveedores | `GET/POST/PUT/DELETE /proveedor` |
| **Ventas** | 💰 | Procesos de venta y facturación | `GET/POST/PUT /venta` |
| **Recetas Médicas** | 📝 | Gestión de recetas y prescripciones | `GET/POST/PUT/DELETE /receta` |

</div>

---

## 🛠️ Tecnologías Utilizadas

<div align="center">

### Back-end (API REST)

| Tecnología | Icono | Uso |
|------------|-------|-----|
| **Java 17** | <img src="https://img.shields.io/badge/Java-17-blue?style=flat&logo=openjdk" alt="Java 17"> | Lenguaje de programación principal |
| **Spring Boot** | <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=flat&logo=springboot" alt="Spring Boot"> | Framework principal de desarrollo |
| **Spring Data JPA** | <img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat&logo=hibernate" alt="Spring Data JPA"> | Persistencia y mapeo ORM |
| **MySQL** | <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql" alt="MySQL"> | Base de datos relacional |
| **Bean Validation** | <img src="https://img.shields.io/badge/Validation-JSR380-orange?style=flat" alt="Bean Validation"> | Validación de datos y modelos |
| **Maven** | <img src="https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven" alt="Maven"> | Gestión de dependencias y build |
| **Lombok** | <img src="https://img.shields.io/badge/Lombok-red?style=flat&logo=lombok" alt="Lombok"> | Reducción de código boilerplate |

</div>

---

## 📝 Requerimientos Funcionales

<div align="center">

| Módulo | Funcionalidades | Estado |
|--------|-----------------|--------|
| **👨‍💼 Empleados** | Alta, baja, edición y listado • Roles (ADMIN, EMPLEADO) • Login integrado | ✅ Implementado |
| **👥 Clientes** | Gestión completa • Visualización de historial de compras y recetas | ✅ Implementado |
| **💊 Medicamentos** | Control de stock • Búsqueda por nombre • Gestión de proveedores | ✅ Implementado |
| **🏢 Proveedores** | CRUD completo • Asociación con medicamentos | ✅ Implementado |
| **💰 Ventas** | Registro con control de stock • Anulación con restauración • Generación de facturas PDF | ✅ Implementado |
| **📝 Recetas Médicas** | Gestión completa • Asociación con clientes • Validaciones integradas | ✅ Implementado |

</div>

---

## 📄 Documentación Técnica

<div align="center">

| Recurso | Enlace | Descripción |
|---------|--------|-------------|
| **📖 Swagger UI** | [Swagger](http://localhost:8080/swagger-ui/index.html) | Documentación interactiva completa de la API |
| **🔧 Endpoints** | Ver tabla de módulos | Lista completa de endpoints disponibles |

</div>

---

## ⚙️ Requerimientos No Funcionales

<div align="center">

| Categoría | Especificación | Estado |
|-----------|----------------|--------|
| **🛡️ Validaciones** | Entidades con mensajes claros y personalizados | ✅ Implementado |
| **📐 Modularidad** | Arquitectura escalable para futuras integraciones (web, mobile) | ✅ Implementado |
| **💻 Código Limpio** | Principios SOLID y buenas prácticas de desarrollo | ✅ Implementado |
| **🔒 Seguridad** | Validación de datos y relaciones consistentes | ✅ Implementado |
| **📊 Performance** | Consultas optimizadas y gestión eficiente de recursos | ✅ Implementado |
| **🧾 Facturación** | Sistema preparado para generación de PDF | ✅ Implementado |

</div>

---

## 🔄 Flujos Principales del Sistema

<div align="center">

| Proceso | Descripción | Endpoints Involucrados |
|---------|-------------|------------------------|
| **Venta de Medicamentos** | Registro de venta → Control de stock → Generación de factura | `POST /venta` → `GET /venta/factura/{id}` |
| **Gestión de Inventario** | Búsqueda de medicamentos → Actualización de stock | `GET /medicamento` → `PUT /medicamento/{id}` |
| **Atención con Receta** | Validación de receta → Venta asociada → Control de existencias | `GET /receta/{id}` → `POST /venta` |
| **Administración** | Login usuario → Gestión de proveedores → Control de ventas | `POST /usuario/login` → `CRUD /proveedor` |

</div>

---

<div align="center">

## 🚀 ¿Listo para Comenzar?

[**📖 Ir a la Documentación Interactiva**](http://localhost:8080/swagger-ui/index.html) •

**⭐ ¡No olvides darle una estrella al repo si te fue útil!**

---
*Desarrollado con ❤️ usando Spring Boot y Java 17*

</div>