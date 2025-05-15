package com.proyecto.farmacia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicamento")
public class Medicamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s\\-.,()]+$", message = "El nombre contiene caracteres inválidos")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Column(name = "precio", nullable = false)
    private Double precio;

    @NotNull(message = "El stock no puede estar vacío")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @NotNull(message = "La fecha de vencimiento no puede estar vacía")
    @FutureOrPresent(message = "La fecha de vencimiento debe ser futura")
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @NotNull(message = "Debe especificar si requiere receta")
    @Column(name = "receta_requerida", nullable = false)
    private Boolean recetaRequerida;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    @JsonIgnoreProperties("medicamentos")
    @NotNull(message = "El proveedor no puede estar vacío")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "medicamento")
    @JsonIgnoreProperties("medicamento")
    private List<DetalleVenta> detalleVentas;
}
