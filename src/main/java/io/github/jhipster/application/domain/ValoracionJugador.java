package io.github.jhipster.application.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ValoracionJugador.
 */
@Entity
@Table(name = "valoracion_jugador")
public class ValoracionJugador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "puntuacion", nullable = false)
    private Double puntuacion;

    @Column(name = "momento")
    private ZonedDateTime momento;

    @ManyToOne
    private Jugador jugador;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public ValoracionJugador puntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
        return this;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public ZonedDateTime getMomento() {
        return momento;
    }

    public ValoracionJugador momento(ZonedDateTime momento) {
        this.momento = momento;
        return this;
    }

    public void setMomento(ZonedDateTime momento) {
        this.momento = momento;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public ValoracionJugador jugador(Jugador jugador) {
        this.jugador = jugador;
        return this;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public User getUser() {
        return user;
    }

    public ValoracionJugador user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        ValoracionJugador valoracionJugador = (ValoracionJugador) o;
        if (valoracionJugador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valoracionJugador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValoracionJugador{" +
            "id=" + getId() +
            ", puntuacion='" + getPuntuacion() + "'" +
            ", momento='" + getMomento() + "'" +
            "}";
    }
}
