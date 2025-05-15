package com.proyecto.farmacia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Table(name = "recetamedica")
public class RecetaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "La fecha de la receta no puede estar vacía")
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull(message = "El nombre del médico no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre del médico debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s.'-]+$", message = "El nombre del médico contiene caracteres inválidos")
    @Column(nullable = false)
    private String medico;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToMany
    @JoinTable(
            name = "receta_medicamento",
            joinColumns = @JoinColumn(name = "receta_id"),
            inverseJoinColumns = @JoinColumn(name = "medicamento_id")
    )
    @NotNull(message = "Debe incluir al menos un medicamento")
    private List<Medicamento> medicamentos;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties("recetas")
    @NotNull(message = "Debe estar asociado a un cliente")
    private Cliente cliente;

    @NotNull(message = "Debe especificarse hasta cuándo es válida la receta")
    @Column(nullable = false)
    private LocalDate vigenteHasta;
}
