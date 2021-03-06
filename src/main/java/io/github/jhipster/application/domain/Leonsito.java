package io.github.jhipster.application.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Leonsito.
 */
@Entity
@Table(name = "leonsito")
public class Leonsito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "ano_creacion")
    private LocalDate anoCreacion;

    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "good_cat")
    private Boolean goodCat;

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

    public Leonsito nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getAnoCreacion() {
        return anoCreacion;
    }

    public Leonsito anoCreacion(LocalDate anoCreacion) {
        this.anoCreacion = anoCreacion;
        return this;
    }

    public void setAnoCreacion(LocalDate anoCreacion) {
        this.anoCreacion = anoCreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Leonsito descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Leonsito foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Leonsito fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Boolean isGoodCat() {
        return goodCat;
    }

    public Leonsito goodCat(Boolean goodCat) {
        this.goodCat = goodCat;
        return this;
    }

    public void setGoodCat(Boolean goodCat) {
        this.goodCat = goodCat;
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
        Leonsito leonsito = (Leonsito) o;
        if (leonsito.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leonsito.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Leonsito{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", anoCreacion='" + getAnoCreacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            ", goodCat='" + isGoodCat() + "'" +
            "}";
    }
}
