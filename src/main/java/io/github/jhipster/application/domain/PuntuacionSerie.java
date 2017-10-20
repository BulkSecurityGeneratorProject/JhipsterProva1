package io.github.jhipster.application.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PuntuacionSerie.
 */
@Entity
@Table(name = "puntuacion_serie")
public class PuntuacionSerie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "puntuacion")
    private Double puntuacion;

    @Column(name = "momento")
    private ZonedDateTime momento;

    @ManyToOne
    private Serie serie;

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

    public PuntuacionSerie puntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
        return this;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public ZonedDateTime getMomento() {
        return momento;
    }

    public PuntuacionSerie momento(ZonedDateTime momento) {
        this.momento = momento;
        return this;
    }

    public void setMomento(ZonedDateTime momento) {
        this.momento = momento;
    }

    public Serie getSerie() {
        return serie;
    }

    public PuntuacionSerie serie(Serie serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public User getUser() {
        return user;
    }

    public PuntuacionSerie user(User user) {
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
        PuntuacionSerie puntuacionSerie = (PuntuacionSerie) o;
        if (puntuacionSerie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), puntuacionSerie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PuntuacionSerie{" +
            "id=" + getId() +
            ", puntuacion='" + getPuntuacion() + "'" +
            ", momento='" + getMomento() + "'" +
            "}";
    }
}
