package io.github.jhipster.application.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Jugador.
 */
@Entity
@Table(name = "jugador")
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "num_canastas")
    private Integer numCanastas;

    @Min(value = 0)
    @Column(name = "num_asistencias")
    private Integer numAsistencias;

    @Min(value = 0)
    @Column(name = "num_rebotes")
    private Integer numRebotes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Jugador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Jugador fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getNumCanastas() {
        return numCanastas;
    }

    public Jugador numCanastas(Integer numCanastas) {
        this.numCanastas = numCanastas;
        return this;
    }

    public void setNumCanastas(Integer numCanastas) {
        this.numCanastas = numCanastas;
    }

    public Integer getNumAsistencias() {
        return numAsistencias;
    }

    public Jugador numAsistencias(Integer numAsistencias) {
        this.numAsistencias = numAsistencias;
        return this;
    }

    public void setNumAsistencias(Integer numAsistencias) {
        this.numAsistencias = numAsistencias;
    }

    public Integer getNumRebotes() {
        return numRebotes;
    }

    public Jugador numRebotes(Integer numRebotes) {
        this.numRebotes = numRebotes;
        return this;
    }

    public void setNumRebotes(Integer numRebotes) {
        this.numRebotes = numRebotes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Jugador jugador = (Jugador) o;
        if (jugador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jugador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Jugador{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", numCanastas='" + getNumCanastas() + "'" +
            ", numAsistencias='" + getNumAsistencias() + "'" +
            ", numRebotes='" + getNumRebotes() + "'" +
            "}";
    }
}
