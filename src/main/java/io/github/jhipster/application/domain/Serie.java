package io.github.jhipster.application.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Serie.
 */
@Entity
@Table(name = "serie")
public class Serie implements Serializable {

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

    @ManyToOne
    private Productora productora;

    @ManyToMany
    @JoinTable(name = "serie_actor",
               joinColumns = @JoinColumn(name="series_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="actors_id", referencedColumnName="id"))
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "serie_genero",
               joinColumns = @JoinColumn(name="series_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="generos_id", referencedColumnName="id"))
    private Set<Genero> generos = new HashSet<>();

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

    public Serie nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getAnoCreacion() {
        return anoCreacion;
    }

    public Serie anoCreacion(LocalDate anoCreacion) {
        this.anoCreacion = anoCreacion;
        return this;
    }

    public void setAnoCreacion(LocalDate anoCreacion) {
        this.anoCreacion = anoCreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Serie descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Serie foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Serie fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Productora getProductora() {
        return productora;
    }

    public Serie productora(Productora productora) {
        this.productora = productora;
        return this;
    }

    public void setProductora(Productora productora) {
        this.productora = productora;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Serie actors(Set<Actor> actors) {
        this.actors = actors;
        return this;
    }

    public Serie addActor(Actor actor) {
        this.actors.add(actor);
        return this;
    }

    public Serie removeActor(Actor actor) {
        this.actors.remove(actor);
        return this;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Genero> getGeneros() {
        return generos;
    }

    public Serie generos(Set<Genero> generos) {
        this.generos = generos;
        return this;
    }

    public Serie addGenero(Genero genero) {
        this.generos.add(genero);
        return this;
    }

    public Serie removeGenero(Genero genero) {
        this.generos.remove(genero);
        return this;
    }

    public void setGeneros(Set<Genero> generos) {
        this.generos = generos;
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
        Serie serie = (Serie) o;
        if (serie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Serie{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", anoCreacion='" + getAnoCreacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            "}";
    }
}
