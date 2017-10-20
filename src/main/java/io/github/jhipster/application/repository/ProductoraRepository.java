package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Productora;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Productora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoraRepository extends JpaRepository<Productora, Long> {

}
