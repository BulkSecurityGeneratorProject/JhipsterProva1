package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Capitulo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Capitulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapituloRepository extends JpaRepository<Capitulo, Long> {

}
