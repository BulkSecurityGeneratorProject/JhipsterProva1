package io.github.jhipster.application.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Capitulo.
 */
@Entity
@Table(name = "capitulo")
public class Capitulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "duracion")
    private Double duracion;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @ManyToOne
    private Serie serie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Capitulo numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public Capitulo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDuracion() {
        return duracion;
    }

    public Capitulo duracion(Double duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Capitulo foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Capitulo fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Serie getSerie() {
        return serie;
    }

    public Capitulo serie(Serie serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
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
        Capitulo capitulo = (Capitulo) o;
        if (capitulo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capitulo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Capitulo{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", duracion='" + getDuracion() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            "}";
    }
}
