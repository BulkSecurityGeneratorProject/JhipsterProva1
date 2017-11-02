package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Leonsito;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Leonsito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeonsitoRepository extends JpaRepository<Leonsito, Long> {

}
