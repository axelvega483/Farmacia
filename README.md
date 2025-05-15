# 💊 Sistema de gestion de una Farmacia

Sistema de backend para la administración integral de una farmacia. Permite gestionar empleados, clientes, medicamentos, ventas, proveedores y recetas médicas. Está pensado para facilitar y optimizar las tareas operativas mediante una API REST robusta, extensible y segura.

---

## 🌟 Características del Sistema

- **Autogeneración de empleado administrador** al iniciar la aplicación.
- **Gestión completa de ventas** con actualización automática del stock.
- **Anulación de ventas** con restauración de inventario.
- **Relaciones entre entidades**: clientes, recetas médicas, ventas y empleados.
- **Validaciones integradas** en el modelo con mensajes personalizados.
- **DTOs personalizados** para diferentes vistas y respuestas.
- **Factura generada automáticamente**.

---

## 🛠️ Tecnologías Utilizadas

### Back-end (API REST)
- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Bean Validation (javax.validation)**
- **Lombok**
- **MySQL** / PostgreSQL (configurable)
- **Maven**

---

## 📝 Requerimientos Funcionales

1. **Empleados**:
   - Alta, baja, edición y listado de empleados.
   - Rol de acceso definido (`ADMIN`, `EMPLEADO`).
   - Inicialización automática del administrador si no hay registros.

2. **Clientes**:
   - Gestión completa de clientes.
   - Visualización de historial de ventas y recetas asociadas.

3. **Medicamentos**:
   - Control de stock, disponibilidad y proveedor.
   - Búsqueda por nombre y filtrado por categoría.

4. **Proveedores**:
   - CRUD completo de proveedores asociados a medicamentos.

5. **Ventas**:
   - Registro de venta con detalle y control automático del stock.
   - Anulación de ventas con devolución de stock.
   - Cálculo automático de totales.

6. **Recetas Médicas**:
   - Asociación de recetas a clientes.
   - Visualización detallada por cliente o general.

7. **Factura de Compra**:
   - Preparado para generar factura (PDF).

---

## ⚙️ Requerimientos No Funcionales

- **Validaciones en entidades** con mensajes personalizados.
- **Seguridad en progreso**: sistema preparado para autenticación JWT.
- **Modularidad y escalabilidad** para futuras integraciones (front-end, mobile, etc.).
- **Código limpio y documentado**: siguiendo principios SOLID.





