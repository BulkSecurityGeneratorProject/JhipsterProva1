package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Gatito;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Gatito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GatitoRepository extends JpaRepository<Gatito, Long> {

}
